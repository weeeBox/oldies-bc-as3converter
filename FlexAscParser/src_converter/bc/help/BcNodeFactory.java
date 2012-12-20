package bc.help;

import java.util.HashMap;
import java.util.Map;

import macromedia.asc.parser.ArgumentListNode;
import macromedia.asc.parser.BinaryExpressionNode;
import macromedia.asc.parser.CallExpressionNode;
import macromedia.asc.parser.GetExpressionNode;
import macromedia.asc.parser.IdentifierNode;
import macromedia.asc.parser.ListNode;
import macromedia.asc.parser.LiteralNullNode;
import macromedia.asc.parser.LiteralNumberNode;
import macromedia.asc.parser.LiteralStringNode;
import macromedia.asc.parser.MemberExpressionNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.QualifiedIdentifierNode;
import macromedia.asc.parser.SelectorNode;
import macromedia.asc.parser.SetExpressionNode;
import macromedia.asc.parser.Tokens;
import macromedia.asc.parser.UnaryExpressionNode;
import bc.lang.BcTypeNode;
import bc.lang.BcTypeNodeInstance;

public class BcNodeFactory {
	
	private static Map<String, SelectorNode> STRING_SELECTOR_LOOKUP;
	
	// TODO: move that to code helper
	static
	{
		STRING_SELECTOR_LOOKUP = new HashMap<String, SelectorNode>();
		STRING_SELECTOR_LOOKUP.put("length", getExpression("Length"));
		STRING_SELECTOR_LOOKUP.put("toString", callExpression("ToString"));
		STRING_SELECTOR_LOOKUP.put("toLowerCase", callExpression("ToLower"));
		STRING_SELECTOR_LOOKUP.put("toUpperCase", callExpression("ToUpper"));
		STRING_SELECTOR_LOOKUP.put("replace", callExpression("Replace"));
	}
	
	public static Node notNull(BcTypeNode type, Node expr)
	{
		return type.isIntegral() ? notZero(expr) : notNull(expr);
	}
	
	public static Node isNull(BcTypeNode type, Node expr)
	{
		return type.isIntegral() ? isZero(expr) : isNull(expr);
	}
	
	public static Node notNull(Node expr)
	{
		return binaryBooleanExpr(expr, new LiteralNullNode(), Tokens.NOTEQUALS_TOKEN);
	}
	
	public static Node isNull(Node expr)
	{
		return binaryBooleanExpr(expr, new LiteralNullNode(), Tokens.EQUALS_TOKEN);
	}
	
	public static Node notZero(Node expr)
	{
		if (expr instanceof ListNode)
		{
			return notZero((ListNode)expr);
		}
		
		LiteralNumberNode zeroNode = new LiteralNumberNode("0");
		if (expr instanceof UnaryExpressionNode)
		{
			UnaryExpressionNode unaryNode = (UnaryExpressionNode) expr;
			if (unaryNode.op == Tokens.NOT_TOKEN)
			{
				return binaryBooleanExpr(expr, zeroNode, Tokens.EQUALS_TOKEN);
			}
		}
		return binaryBooleanExpr(expr, zeroNode, Tokens.NOTEQUALS_TOKEN);
	}
	
	public static Node notZero(ListNode expr)
	{
		return notZero(expr.items.get(0));
	}
	
	public static Node isZero(Node expr)
	{
		if (expr instanceof ListNode)
		{
			return isZero((ListNode)expr);
		}
		
		LiteralNumberNode zeroNode = new LiteralNumberNode("0");
		if (expr instanceof UnaryExpressionNode)
		{
			UnaryExpressionNode unaryNode = (UnaryExpressionNode) expr;
			if (unaryNode.op == Tokens.NOT_TOKEN)
			{
				return binaryBooleanExpr(expr, zeroNode, Tokens.NOTEQUALS_TOKEN);
			}
		}
		return binaryBooleanExpr(expr, zeroNode, Tokens.EQUALS_TOKEN);
	}

	public static Node isZero(ListNode expr)
	{
		return isZero(expr.items.get(0));
	}
	
	private static Node binaryBooleanExpr(Node expr, Node literal, int op) 
	{
		Node lhs = expr instanceof BinaryExpressionNode ? new ListNode(null, expr, 0) : expr;
		Node rhs = literal;
		return new BinaryExpressionNode(op, lhs, rhs);
	}
	
	public static void turnToStaticStringDelegateCall(Node node, BcTypeNodeInstance stringTypeInstance)
	{
		if (node.isMemberExpression())
		{
			MemberExpressionNode memberExpression = (MemberExpressionNode) node;
			if (memberExpression.base != null)
			{
				String callTarget = BcNodeHelper.tryExtractIdentifier(memberExpression.base);
				if (BcTypeNode.typeString.equals(callTarget))
				{
					return; // do not modify static calls
				}
			}
			
			String identifier = BcNodeHelper.tryExtractIdentifier(memberExpression.selector);
			
			SelectorNode replacementSelector = identifier != null ? STRING_SELECTOR_LOOKUP.get(identifier) : null;
			if (replacementSelector != null)
			{
				if (replacementSelector.isCallExpression())
				{
					CallExpressionNode oldCall = (CallExpressionNode) memberExpression.selector;
					CallExpressionNode newCall = new CallExpressionNode(replacementSelector.expr, oldCall.args);
					
					memberExpression.selector = newCall;
				}
				else if (replacementSelector.isSetExpression())
				{
					SetExpressionNode oldSet = (SetExpressionNode) memberExpression.selector;
					SetExpressionNode newSet = new SetExpressionNode(replacementSelector.expr, oldSet.args);
					
					memberExpression.selector = newSet;
				}
				else
				{
					memberExpression.selector = replacementSelector;
				}
			}
			else
			{
				turnToStaticTypeDelegateCall(node, stringTypeInstance);
			}
		}
	}
	
	/** Changes the node to delegate member call:
	 * <pre>
	 * obj.func(arg1, arg2, ...) => ObjClass.func(obj, arg1, arg2, ...)
	 * </pre>
	 *  */
	public static void turnToStaticTypeDelegateCall(Node node, BcTypeNodeInstance targetTypeInstance)
	{
		if (node.isMemberExpression())
		{
			SelectorNode selector = null;
	
			MemberExpressionNode memberExpression = (MemberExpressionNode) node;
			if (memberExpression.selector.isCallExpression())
			{
				CallExpressionNode call = (CallExpressionNode) memberExpression.selector;
				selector = new CallExpressionNode(call.expr, concat(memberExpression.base, call.args));
			}

			if (selector != null) 
			{
				memberExpression.base = memberExpression(targetTypeInstance);
				memberExpression.selector = selector;
			}
		}
	}
	
	private static MemberExpressionNode memberExpression(BcTypeNodeInstance typeInstance)
	{
		return new MemberExpressionNode(null, getExpression(typeInstance), -1);
	}
	
	private static CallExpressionNode callExpression(String identifier)
	{
		return new CallExpressionNode(identifier(identifier), null);
	}
	
	private static GetExpressionNode getExpression(BcTypeNodeInstance typeInstance)
	{
		return new GetExpressionNode(identifier(typeInstance));
	}
	
	private static GetExpressionNode getExpression(String identifier)
	{
		return new GetExpressionNode(identifier(identifier));
	}

	private static IdentifierNode identifier(BcTypeNodeInstance typeInstance)
	{
		return identifier(typeInstance.isQualified() ? typeInstance.getQualifier() : null, typeInstance.getName());
	}
	
	private static IdentifierNode identifier(String identifier)
	{
		int dotIndex = identifier.lastIndexOf('.');
		if (dotIndex != -1)
		{
			String qualifier = identifier.substring(0, dotIndex);
			String name = identifier.substring(dotIndex + 1);
			
			return identifier(qualifier, name);
		}
		
		return identifier(null, identifier);
	}
	
	private static IdentifierNode identifier(String qualifier, String name)
	{
		if (qualifier != null)
		{
			Node qualifierNode = new LiteralStringNode(qualifier);
			return new QualifiedIdentifierNode(qualifierNode, name, -1);
		}
		
		return new IdentifierNode(name, -1);
	}
	
	private static ArgumentListNode concat(Node item, ArgumentListNode list)
	{
		ArgumentListNode newList = new ArgumentListNode(item, -1);
		if (list != null)
		{
			for (Node node : list.items)
			{
				newList.items.add(node);
			}
		}
		
		return newList;
	}
}
