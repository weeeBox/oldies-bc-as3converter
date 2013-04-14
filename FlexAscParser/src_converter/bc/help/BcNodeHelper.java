package bc.help;

import java.util.ArrayList;
import java.util.List;

import macromedia.asc.parser.ApplyTypeExprNode;
import macromedia.asc.parser.ArgumentListNode;
import macromedia.asc.parser.AttributeListNode;
import macromedia.asc.parser.BinaryExpressionNode;
import macromedia.asc.parser.CallExpressionNode;
import macromedia.asc.parser.ClassDefinitionNode;
import macromedia.asc.parser.ConditionalExpressionNode;
import macromedia.asc.parser.FunctionDefinitionNode;
import macromedia.asc.parser.GetExpressionNode;
import macromedia.asc.parser.IdentifierNode;
import macromedia.asc.parser.ListNode;
import macromedia.asc.parser.LiteralArrayNode;
import macromedia.asc.parser.LiteralBooleanNode;
import macromedia.asc.parser.LiteralNumberNode;
import macromedia.asc.parser.LiteralStringNode;
import macromedia.asc.parser.MemberExpressionNode;
import macromedia.asc.parser.MetaDataNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.PackageDefinitionNode;
import macromedia.asc.parser.PackageNameNode;
import macromedia.asc.parser.ParameterNode;
import macromedia.asc.parser.QualifiedIdentifierNode;
import macromedia.asc.parser.RestParameterNode;
import macromedia.asc.parser.SelectorNode;
import macromedia.asc.parser.SetExpressionNode;
import macromedia.asc.parser.SuperExpressionNode;
import macromedia.asc.parser.ThisExpressionNode;
import macromedia.asc.parser.Tokens;
import macromedia.asc.parser.TypeExpressionNode;
import macromedia.asc.parser.TypedIdentifierNode;
import macromedia.asc.parser.UnaryExpressionNode;
import macromedia.asc.util.ObjectList;
import bc.lang.BcMetadata;
import bc.lang.BcMetadataNode;
import bc.lang.BcTypeName;
import bc.lang.BcTypeNode;
import bc.lang.BcTypeNodeInstance;
import bc.lang.BcUntypedNode;

public class BcNodeHelper
{
	private static final int[][] OPERATORS_PRECENDENCE = 
	{
		{ Tokens.MULT_TOKEN, Tokens.DIV_TOKEN, Tokens.MODULUS_TOKEN },
		{ Tokens.PLUS_TOKEN, Tokens.MINUS_TOKEN },
		{ Tokens.RIGHTSHIFT_TOKEN, Tokens.LEFTSHIFT_TOKEN, Tokens.UNSIGNEDRIGHTSHIFT_TOKEN },
		{ Tokens.LESSTHAN_TOKEN, Tokens.GREATERTHAN_TOKEN, Tokens.LESSTHANOREQUALS_TOKEN, Tokens.GREATERTHANOREQUALS_TOKEN, Tokens.AS_TOKEN, Tokens.INSTANCEOF_TOKEN, Tokens.IS_TOKEN },
		{ Tokens.EQUALS_TOKEN, Tokens.NOTEQUALS_TOKEN, Tokens.STRICTEQUALS_TOKEN, Tokens.STRICTNOTEQUALS_TOKEN },
		{ Tokens.BITWISEAND_TOKEN},
		{ Tokens.BITWISEXOR_TOKEN },
		{ Tokens.BITWISEOR_TOKEN },
		{ Tokens.LOGICALAND_TOKEN },
		{ Tokens.LOGICALOR_TOKEN },
	};
	
	public static String tryExtractPackageName(ClassDefinitionNode classDefinitionNode)
	{
		PackageDefinitionNode pkgdef = classDefinitionNode.pkgdef;
		if (pkgdef != null)
		{
			return tryExtractPackageName(pkgdef.name);
		}		
		
		return null;
	}

	public static String tryExtractPackageName(PackageNameNode packageNameNode)
	{
		if (packageNameNode != null)
		{
			return safeQualifier(packageNameNode.id.pkg_part);
		}
		return null;
	}
	
	public static String safeQualifier(String qualifier)
	{
		if (qualifier != null)
		{
			if (qualifier.startsWith("flash."))
			{
				// replace Flash api class usage with bc platform class usage
				// in order to avoid issues with unique reference types
				return "bc." + qualifier;
			}
			
			if (qualifier.startsWith("__AS3__."))
			{
				return "bc.flash";
			}
		}
		
		return qualifier;
	}
	
	public static List<String> extractModifiers(AttributeListNode attrs)
	{
		List<String> modifiers = new ArrayList<String>();
		if (attrs == null || attrs.items == null)
		{
			modifiers.add("public");
			return modifiers;
		}
		
		for (Node node : attrs.items)
		{
			if (node instanceof MemberExpressionNode)
			{
				modifiers.add(tryExtractIdentifierNode((MemberExpressionNode) node).name);
			}
			else if (node instanceof ListNode)
			{
				ListNode list = (ListNode) node;
				for (Node innerNode : list.items)
				{
					if (innerNode instanceof MemberExpressionNode)
					{
						modifiers.add(tryExtractIdentifierNode((MemberExpressionNode) innerNode).name);
					}
					else
					{
						assert false : innerNode.getClass();
					}					
				}
			}
		}
		return modifiers;
	}

	public static BcMetadata extractBcMetadata(MetaDataNode metadata)
	{
		LiteralArrayNode data = metadata.data;
		if (data != null && data.elementlist != null)
		{
			BcMetadata bcMetadata = new BcMetadata();
			
			ArgumentListNode elementlist = data.elementlist;
			for (Node arg : elementlist.items)
			{
				extractBcMetadataNode(arg, bcMetadata.getRootNode());
			}
				
			return bcMetadata;
		}
		return null;
	}
	
	private static void extractBcMetadataNode(Node node, BcMetadataNode parentNode) 
	{		
		if (node instanceof MemberExpressionNode)
		{
			MemberExpressionNode memberNode = (MemberExpressionNode) node;
			if (memberNode.selector instanceof GetExpressionNode)
			{
				IdentifierNode identifier = tryExtractIdentifierNode(memberNode.selector);
				assert identifier != null;
				
				parentNode.add(new BcMetadataNode(identifier.name));
			}
			else if (memberNode.selector instanceof SetExpressionNode)
			{
				IdentifierNode identifier = tryExtractIdentifierNode(memberNode.selector);
				assert identifier != null;
				
				SetExpressionNode setNode = (SetExpressionNode) memberNode.selector;
				ArgumentListNode args = setNode.args;
				assert args != null;
				assert args.items.size() == 1;
				
				Node item = args.items.get(0);
				assert item instanceof LiteralStringNode : item.getClass();
				
				LiteralStringNode literalString = (LiteralStringNode) item;				
				parentNode.setAttribute(identifier.name, literalString.value);
			}
			else if (memberNode.selector instanceof CallExpressionNode)
			{
				IdentifierNode identifier = tryExtractIdentifierNode(memberNode.selector);
				assert identifier != null;
				
				BcMetadataNode metadata = new BcMetadataNode(identifier.name);
				
				CallExpressionNode callNode = (CallExpressionNode) memberNode.selector;
				if (callNode.args != null)
				{
					ArgumentListNode args = callNode.args;
					for (Node arg : args.items) 
					{
						extractBcMetadataNode(arg, metadata);
					}
				}
				
				parentNode.add(metadata);
			}
			else
			{
				assert false;
			}
		}
		else if (node instanceof LiteralStringNode)
		{
			// ignore
		}
		else
		{
			assert false;
		}
	}

	public static BcTypeNodeInstance extractBcTypeInstance(Node node)
	{
		BcTypeNode type = extractBcType(node);
		if (type != null)
		{
			boolean qualified = isTypeQualified(node);
			return type.createTypeInstance(qualified);
		}
		
		return null;
	}
	
	public static BcTypeNode extractBcType(Node type)
	{
		if (type == null)
		{			
			return BcUntypedNode.instance();
		}
		
		if (type instanceof IdentifierNode)
		{
			IdentifierNode identifier = (IdentifierNode) type;
			return BcTypeNode.create(extractIdentifier(identifier));
		}
		
		if (type instanceof TypeExpressionNode)
		{
			TypeExpressionNode typeNode = (TypeExpressionNode) type;
			return extractBcType(typeNode.expr);
		}
		
		if (type instanceof MemberExpressionNode)
		{
			MemberExpressionNode expr = (MemberExpressionNode) type;
			return extractBcType(expr.selector);
		}
		
		if (type instanceof GetExpressionNode)
		{
			// FIXME: remove that!
			GetExpressionNode selector = (GetExpressionNode) type;
			if (selector.expr instanceof QualifiedIdentifierNode)
			{
				QualifiedIdentifierNode identifier = (QualifiedIdentifierNode)selector.expr;
				String name = identifier.name;
				String qualifier = identifier.qualifier != null ? ((LiteralStringNode)identifier.qualifier).value : null;			 
				
				return BcTypeNode.create(name, safeQualifier(qualifier));
			}
			if (selector.expr instanceof IdentifierNode)
			{
				return extractBcType(selector.expr);
			}
			
			assert false;
			return null;
		}
		
		if (type instanceof ApplyTypeExprNode)
		{
			ApplyTypeExprNode selector = (ApplyTypeExprNode) type;
			String typeName = BcNodeHelper.extractIdentifier((IdentifierNode)selector.expr);
			
			BcTypeNode baseType = BcTypeNode.create(typeName);
			assert baseType != null;
			
			ListNode typeArgs = selector.typeArgs;
			assert typeArgs.size() == 1;
			
			Node genericNode = typeArgs.items.get(0);
			BcTypeNode genericType = extractBcType(genericNode);
			assert genericType != null;
			
			boolean qualified = isTypeQualified(genericNode);
			return baseType.createGeneric(genericType.createTypeInstance(qualified));
		}
		
		if (type instanceof TypedIdentifierNode)
		{			
			return extractBcType(((TypedIdentifierNode) type).type);
		}
		
		if (type instanceof RestParameterNode)
		{
			RestParameterNode restTypeNode = (RestParameterNode) type;
			BcTypeNodeInstance argTypeInstance = extractBcTypeInstance(restTypeNode.type);
			assert argTypeInstance != null;
			
			return BcTypeNode.createRestType(argTypeInstance);
		}
		
		if (type instanceof ParameterNode)
		{
			return extractBcType(((ParameterNode) type).type);
		}
		
		assert false : type.getClass();
		return null;
	}
	
	public static boolean isTypeQualified(Node node)
	{
		if (node == null)
		{			
			return false;
		}
		
		if (node instanceof QualifiedIdentifierNode)
		{
			return true;
		}
		
		if (node instanceof IdentifierNode)
		{
			return false;
		}
		
		if (node instanceof TypeExpressionNode)
		{
			TypeExpressionNode typeNode = (TypeExpressionNode) node;
			return isTypeQualified(typeNode.expr);
		}
		
		if (node instanceof MemberExpressionNode)
		{
			MemberExpressionNode expr = (MemberExpressionNode) node;
			return isTypeQualified(expr.selector);
		}
		
		if (node instanceof SelectorNode)
		{
			SelectorNode selector = (SelectorNode) node;
			return selector.expr instanceof QualifiedIdentifierNode;
		}
		
		if (node instanceof ApplyTypeExprNode)
		{
			return false;
		}
		
		if (node instanceof TypedIdentifierNode)
		{			
			return isTypeQualified(((TypedIdentifierNode) node).type);
		}
		
		if (node instanceof RestParameterNode)
		{
			return false;
		}
		
		if (node instanceof ParameterNode)
		{
			return isTypeQualified(((ParameterNode) node).type);
		}
		
		if (node instanceof ThisExpressionNode || node instanceof SuperExpressionNode)
		{
			return false;
		}
		
		if (node.isList())
		{
			ListNode list = (ListNode) node;
			return isTypeQualified(list.items.first());
		}
		
		if (node instanceof BinaryExpressionNode || node instanceof UnaryExpressionNode)
		{
			return false;
		}
		
		if (node instanceof LiteralStringNode)
		{
			return false;
		}
		
		assert false : node.getClass();
		return false;
	}
		
	public static IdentifierNode tryExtractIdentifierNode(SelectorNode selector)
	{
		if ((selector.expr instanceof IdentifierNode))
		{
			return (IdentifierNode) selector.expr;
		}
		return null;
	}
	
	public static IdentifierNode tryExtractIdentifierNode(MemberExpressionNode memberNode)
	{
		if (memberNode.base != null)
		{
			return null;
		}
		
		return Cast.tryCast(memberNode.selector.expr, IdentifierNode.class);
	}
		
	public static IdentifierNode tryExtractIdentifierNode(Node node)
	{
		if (node instanceof MemberExpressionNode)
		{
			return tryExtractIdentifierNode((MemberExpressionNode)node);
		}
		
		if (node instanceof SelectorNode)
		{
			return tryExtractIdentifierNode((SelectorNode)node);
		}
		
		if (node instanceof IdentifierNode)
		{
			return (IdentifierNode) node;
		}
		
		if (node instanceof ListNode)
		{
			ListNode listNode = (ListNode) node;
			ObjectList<Node> items = listNode.items;
			if (items != null && items.size() == 1)
			{
				return tryExtractIdentifierNode(listNode.items.first());
			}
		}
		
		if (node instanceof ArgumentListNode)
		{
			ArgumentListNode args = (ArgumentListNode) node;
			ObjectList<Node> items = args.items;
			if (items != null && items.size() == 1)
			{
				return tryExtractIdentifierNode(args.items.first());
			}
		}
		
//		if (node instanceof SuperExpressionNode ||
//			node.isLiteral() ||
//			node instanceof BinaryExpressionNode ||
//			node instanceof UnaryExpressionNode ||
//			node instanceof ConditionalExpressionNode)
//		{
//			return null;
//		}
		
		return null;
	}
	
	public static String tryExtractIdentifier(Node node)
	{
		IdentifierNode identifierNode = tryExtractIdentifierNode(node);
		return identifierNode != null ? extractIdentifier(identifierNode) : null;
	}
	
	public static String extractIdentifier(IdentifierNode identifier)
	{
		String name = identifier.name;
		if (identifier.isAttr() && name.startsWith("@"))
		{
			name = name.substring(1);
		}
		
		if (identifier instanceof QualifiedIdentifierNode)
		{
			QualifiedIdentifierNode qualifiedIdentifier = (QualifiedIdentifierNode) identifier;
			Node qualifier = qualifiedIdentifier.qualifier;
			if (qualifier instanceof LiteralStringNode)
			{
				String qualifierName = ((LiteralStringNode)qualifier).value;
				return qualifierName + "." + name;
			}
		}
		
		return name;
	}
	
	public static String tryExtractFunctionType(FunctionDefinitionNode node) 
	{
		AttributeListNode attrs = node.attrs;
		if (attrs != null)
		{
			ObjectList<Node> items = attrs.items;
			if (items != null)
			{
				for (Node item : items) 
				{
					IdentifierNode identifier = tryExtractIdentifierNode(item);
					if (identifier != null)
					{
						String name = identifier.name;
						if (name.equals("override") || name.equals("virtual"))
						{
							return name;
						}
					}
				}
			}
		}
		return null;
	}
	
	public static boolean isIntegralLiteralNode(Node node)
	{
		return node instanceof LiteralNumberNode || node instanceof LiteralBooleanNode;
	}
	
	public static boolean isBinaryOperandSetExpression(Node operand)
	{
		if (!(operand instanceof ListNode))
		{
			return false;
		}
		
		ListNode list = (ListNode) operand;
		if (list.items.size() != 1)
		{
			return false;
		}
		
		Node itemNode = list.items.get(0);
		if (!(itemNode instanceof MemberExpressionNode))
		{
			return false;
		}
		
		MemberExpressionNode memberNode = (MemberExpressionNode) itemNode;
		return memberNode.selector instanceof SetExpressionNode;
	}
	
	public static boolean isFinal(ClassDefinitionNode classNode)
	{
		AttributeListNode attrs = classNode.attrs;
		if (attrs == null)
		{
			return false;
		}
		
		for (Node attr : attrs.items)
		{
			IdentifierNode identifier = tryExtractIdentifierNode(attr);
			if (identifier == null)
			{
				continue;
			}
			
			if (identifier.name.equals("final"))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isPreprocessorConditionNode(Node node)
	{
		if (node instanceof ListNode)
		{
			ObjectList<Node> items = ((ListNode) node).items;
			if (items.size() == 1 && items.get(0) instanceof MemberExpressionNode)
			{
				MemberExpressionNode memberNode = (MemberExpressionNode) items.get(0);
				if (memberNode.selector instanceof CallExpressionNode)
				{
					CallExpressionNode callExp = (CallExpressionNode) memberNode.selector;
					if (callExp.expr instanceof IdentifierNode)
					{
						return ((IdentifierNode) callExp.expr).name.equals("BC_AS3");
					}
				}
			}
		}
		
		return false;
	}

	public static boolean needsParentesisForNode(Node node, int op)
	{
		if (node instanceof MemberExpressionNode)
		{
			return needsParentesisForNode((MemberExpressionNode)node, op);
		}
		
		if (node instanceof ListNode)
		{
			return needsParentesisForNode((ListNode)node, op);
		}
		
		if (node instanceof BinaryExpressionNode)
		{
			return needsParentesisForNode((BinaryExpressionNode)node, op);
		}
		
		return false;
	}
	
	public static boolean needsParentesisForNode(MemberExpressionNode node, int op)
	{
		return false;
	}
	
	public static boolean needsParentesisForNode(ListNode node, int op)
	{
		if (node.items != null && node.items.size() == 1)
		{
			Node item = node.items.get(0);
			return item instanceof BinaryExpressionNode;
		}
		
		return true;
	}
	
	public static boolean needsParentesisForNode(BinaryExpressionNode node, int op)
	{
		return hasLessPrecendence(node.op, op);
	}
	
	private static boolean hasLessPrecendence(int op1, int op2) 
	{
		return findPrecendenceIndex(op1) > findPrecendenceIndex(op2);
	}
	
	private static int findPrecendenceIndex(int op) 
	{
		for (int precendenceIndex = 0; precendenceIndex < OPERATORS_PRECENDENCE.length; ++precendenceIndex) 
		{
			int[] precendenceGroup = OPERATORS_PRECENDENCE[precendenceIndex];
			for (int o : precendenceGroup) 
			{
				if (op == o)
				{
					return precendenceIndex;
				}
			}
		}
		
		return Integer.MAX_VALUE;
	}
}
