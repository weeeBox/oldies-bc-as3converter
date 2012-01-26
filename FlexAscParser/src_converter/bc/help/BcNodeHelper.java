package bc.help;

import java.util.ArrayList;
import java.util.List;

import macromedia.asc.parser.ApplyTypeExprNode;
import macromedia.asc.parser.AttributeListNode;
import macromedia.asc.parser.GetExpressionNode;
import macromedia.asc.parser.IdentifierNode;
import macromedia.asc.parser.ListNode;
import macromedia.asc.parser.LiteralBooleanNode;
import macromedia.asc.parser.LiteralNumberNode;
import macromedia.asc.parser.MemberExpressionNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.TypeExpressionNode;
import bc.lang.BcFunctionTypeNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVectorTypeNode;
import bc.lang.BcWildcardTypeNode;

public class BcNodeHelper
{
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
				modifiers.add(tryExtractIdentifier((MemberExpressionNode) node));
			}
			else if (node instanceof ListNode)
			{
				ListNode list = (ListNode) node;
				for (Node innerNode : list.items)
				{
					if (innerNode instanceof MemberExpressionNode)
					{
						modifiers.add(tryExtractIdentifier((MemberExpressionNode) innerNode));
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

	public static BcTypeNode extractBcType(Node type)
	{
		if (type == null)
		{
			assert false : "Found * type";
			return new BcWildcardTypeNode();
		}
		
		if (type instanceof IdentifierNode)
		{
			IdentifierNode identifier = (IdentifierNode) type;
			return BcTypeNode.create(identifier.name);
		}
		
		MemberExpressionNode expr = null;
		if (type instanceof TypeExpressionNode)
		{
			TypeExpressionNode typeNode = (TypeExpressionNode) type;
			expr = (MemberExpressionNode) typeNode.expr;
		}
		else if (type instanceof MemberExpressionNode)
		{
			expr = (MemberExpressionNode) type;
		}
		else
		{
			assert false;
		}
		
		if (expr.selector instanceof GetExpressionNode)
		{
			GetExpressionNode selector = (GetExpressionNode) expr.selector;
			String name = ((IdentifierNode)selector.expr).name;
			
			return BcTypeNode.create(name);
		}
		
		if (expr.selector instanceof ApplyTypeExprNode)
		{
			ApplyTypeExprNode selector = (ApplyTypeExprNode) expr.selector;
			String typeName = ((IdentifierNode)selector.expr).name;
			
			ListNode typeArgs = selector.typeArgs;
			assert typeArgs.size() == 1;
			
			BcTypeNode genericType = extractBcType(typeArgs.items.get(0));
			return new BcVectorTypeNode(typeName, genericType);
		}
		
		assert false : expr.selector.getClass();
		return null;
	}
	
	public static String tryExtractIdentifier(MemberExpressionNode memberNode)
	{
		if (memberNode.base != null)
		{
			return null;
		}
		
		if (!(memberNode.selector instanceof GetExpressionNode))
		{
			return null;
		}
		GetExpressionNode selector = (GetExpressionNode) memberNode.selector;
		if (!(selector.expr instanceof IdentifierNode))
		{
			return null;
		}
		return ((IdentifierNode) selector.expr).name;
	}
	
	public static String extractBcIdentifier(IdentifierNode identifier)
	{
		return identifier.name;
	}
	
	public static boolean isIntegralLiteralNode(Node node)
	{
		return node instanceof LiteralNumberNode || node instanceof LiteralBooleanNode;
	}
}
