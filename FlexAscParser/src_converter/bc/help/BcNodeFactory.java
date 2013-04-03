package bc.help;

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

	/**
	 * Changes the node to delegate member call:
	 * 
	 * <pre>
	 * obj.func(arg1, arg2, ...) => ObjClass.func(obj, arg1, arg2, ...)
	 * </pre>
	 * 
	 * @param node
	 *            target member expression node
	 * @param typeInstance
	 *            type instance for the static call
	 * */
	public static void turnToStaticTypeDelegateCall(Node node, BcTypeNodeInstance typeInstance)
	{
		turnToStaticTypeDelegateCall(node, typeInstance, null);
	}
	
	/**
	 * Changes the node to delegate member call:
	 * 
	 * <pre>
	 * obj.func(arg1, arg2, ...) => ObjClass.func(obj, arg1, arg2, ...)
	 * </pre>
	 * 
	 * @param node
	 *            target member expression node
	 * @param typeInstance
	 *            type instance for the static call
	 * @param selectorsLookup
	 *            lookup map for selectors replacements
	 * */
	public static void turnToStaticTypeDelegateCall(Node node, BcTypeNodeInstance typeInstance, Map<String, SelectorNode> selectorsLookup)
	{
		if (node.isMemberExpression())
		{
			MemberExpressionNode memberExpression = (MemberExpressionNode) node;
			if (memberExpression.base != null)
			{
				String callTarget = BcNodeHelper.tryExtractIdentifier(memberExpression.base);
				if (callTarget != null && (typeInstance.getName().equals(callTarget) || typeInstance.getQualifiedName().equals(callTarget)))
				{
					return; // do not modify static calls
				}
			}
			
			if (selectorsLookup != null)
			{
				String identifier = BcNodeHelper.tryExtractIdentifier(memberExpression.selector);
				SelectorNode replacementSelector = identifier != null ? selectorsLookup.get(identifier) : null;
				if (replacementSelector != null)
				{
					replaceSelector(memberExpression, replacementSelector);
					return;
				}
			}
			
			SelectorNode selector = null;
		
			MemberExpressionNode memberExpression1 = (MemberExpressionNode) node;
			if (memberExpression1.selector.isCallExpression())
			{
				CallExpressionNode call = (CallExpressionNode) memberExpression1.selector;
				selector = new CallExpressionNode(call.expr, concat(memberExpression1.base, call.args));
			}
		
			if (selector != null) 
			{
				memberExpression1.base = memberExpression(typeInstance);
				memberExpression1.selector = selector;
			}
		}
	}
	
	public static void turnSetToCall(MemberExpressionNode node, String funcName)
	{
		if (!node.selector.isSetExpression())
		{
			throw new IllegalArgumentException("Member selector should be set");
		}
		
		SetExpressionNode set = (SetExpressionNode) node.selector;
		node.selector = callExpression(funcName, set.args);
	}
	
	public static void turnSelectorToCall(MemberExpressionNode node, String funcName, ArgumentListNode args)
	{
		node.selector = callExpression(funcName, args);
	}

	private static void replaceSelector(MemberExpressionNode node, SelectorNode selector)
	{
		if (selector.isCallExpression())
		{
			CallExpressionNode oldCall = (CallExpressionNode) node.selector;
			CallExpressionNode newCall = new CallExpressionNode(selector.expr, oldCall.args);
			
			node.selector = newCall;
		}
		else if (selector.isSetExpression())
		{
			SetExpressionNode oldSet = (SetExpressionNode) node.selector;
			SetExpressionNode newSet = new SetExpressionNode(selector.expr, oldSet.args);
			
			node.selector = newSet;
		}
		else
		{
			node.selector = selector;
		}
	}
	
	public static MemberExpressionNode memberExpression(BcTypeNodeInstance typeInstance)
	{
		return new MemberExpressionNode(null, getExpression(typeInstance), -1);
	}
	
	public static CallExpressionNode callExpression(String identifier)
	{
		return callExpression(identifier, null);
	}
	
	public static CallExpressionNode callExpression(String identifier, ArgumentListNode args)
	{
		return new CallExpressionNode(identifier(identifier), args);
	}
	
	public static GetExpressionNode getExpression(BcTypeNodeInstance typeInstance)
	{
		return new GetExpressionNode(identifier(typeInstance));
	}
	
	public static GetExpressionNode getExpression(String identifier)
	{
		return new GetExpressionNode(identifier(identifier));
	}

	public static IdentifierNode identifier(BcTypeNodeInstance typeInstance)
	{
		return identifier(typeInstance.isQualified() ? typeInstance.getQualifier() : null, typeInstance.getName());
	}
	
	public static IdentifierNode identifier(String identifier)
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
	
	public static IdentifierNode identifier(String qualifier, String name)
	{
		if (qualifier != null)
		{
			Node qualifierNode = new LiteralStringNode(qualifier);
			return new QualifiedIdentifierNode(qualifierNode, name, -1);
		}
		
		return new IdentifierNode(name, -1);
	}
	
	public static ArgumentListNode concat(Node item, ArgumentListNode list)
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
	
	public static ArgumentListNode args(Node...items)
	{
		if (items.length == 0) return null;
		
		ArgumentListNode args = new ArgumentListNode(items[0], -1);
		for (int i = 1; i < items.length; ++i)
		{
			args.items.add(items[i]);
		}
		
		return args;
	}
}
