package bc.help;

import java.util.ArrayList;
import java.util.List;

import macromedia.asc.parser.ApplyTypeExprNode;
import macromedia.asc.parser.ArgumentListNode;
import macromedia.asc.parser.AttributeListNode;
import macromedia.asc.parser.CallExpressionNode;
import macromedia.asc.parser.ClassDefinitionNode;
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
import macromedia.asc.parser.TypeExpressionNode;
import macromedia.asc.parser.TypedIdentifierNode;
import macromedia.asc.util.ObjectList;
import bc.lang.BcMetadata;
import bc.lang.BcMetadataNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVectorTypeNode;
import bc.lang.BcWildcardTypeNode;

public class BcNodeHelper
{
	public static String tryExtractPackageName(ClassDefinitionNode classDefinitionNode)
	{
		PackageDefinitionNode pkgdef = classDefinitionNode.pkgdef;
		if (pkgdef != null)
		{
			PackageNameNode packageNameNode = pkgdef.name;
			if (packageNameNode != null)
			{
				return packageNameNode.id.pkg_part;
			}
		}		
		
		return null;
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
				modifiers.add(tryExtractIdentifier((MemberExpressionNode) node).name);
			}
			else if (node instanceof ListNode)
			{
				ListNode list = (ListNode) node;
				for (Node innerNode : list.items)
				{
					if (innerNode instanceof MemberExpressionNode)
					{
						modifiers.add(tryExtractIdentifier((MemberExpressionNode) innerNode).name);
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
				IdentifierNode identifier = tryExtractIdentifier(memberNode.selector);
				assert identifier != null;
				
				parentNode.add(new BcMetadataNode(identifier.name));
			}
			else if (memberNode.selector instanceof SetExpressionNode)
			{
				IdentifierNode identifier = tryExtractIdentifier(memberNode.selector);
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
				IdentifierNode identifier = tryExtractIdentifier(memberNode.selector);
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

	public static BcTypeNode extractBcType(Node type)
	{
		if (type == null)
		{			
			return new BcWildcardTypeNode();
		}
		
		if (type instanceof IdentifierNode)
		{
			IdentifierNode identifier = (IdentifierNode) type;
			return BcTypeNode.create(identifier.name);
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
			GetExpressionNode selector = (GetExpressionNode) type;
			if (selector.expr instanceof QualifiedIdentifierNode)
			{
				QualifiedIdentifierNode identifier = (QualifiedIdentifierNode)selector.expr;
				String name = identifier.name;
				String qualifier = identifier.qualifier != null ? ((LiteralStringNode)identifier.qualifier).value : null;			 
				
				return BcTypeNode.create(name, qualifier);
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
			String typeName = ((IdentifierNode)selector.expr).name;
			
			ListNode typeArgs = selector.typeArgs;
			assert typeArgs.size() == 1;
			
			BcTypeNode genericType = extractBcType(typeArgs.items.get(0));
			return new BcVectorTypeNode(typeName, genericType);
		}
		
		if (type instanceof TypedIdentifierNode)
		{			
			return extractBcType(((TypedIdentifierNode) type).type);
		}
		
		if (type instanceof RestParameterNode)
		{
			return BcTypeNode.createRestType();
		}
		
		if (type instanceof ParameterNode)
		{
			return extractBcType(((ParameterNode) type).type);
		}
		
		assert false : type.getClass();
		return null;
	}
		
	public static IdentifierNode tryExtractIdentifier(SelectorNode selector)
	{
		if ((selector.expr instanceof IdentifierNode))
		{
			return (IdentifierNode) selector.expr;
		}
		return null;
	}
	
	public static IdentifierNode tryExtractIdentifier(MemberExpressionNode memberNode)
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
		return (IdentifierNode) selector.expr;
	}
	
	public static IdentifierNode tryExtractIdentifier(Node node)
	{
		if (node instanceof MemberExpressionNode)
		{
			return tryExtractIdentifier((MemberExpressionNode)node);
		}
		
		if (node instanceof SelectorNode)
		{
			return tryExtractIdentifier((SelectorNode)node);
		}
		
		if (node instanceof IdentifierNode)
		{
			return (IdentifierNode) node;
		}
		
		if (node instanceof ListNode)
		{
			ListNode listNode = (ListNode) node;
			if (listNode.items != null && listNode.items.size() == 1)
			{
				return tryExtractIdentifier(listNode.items.get(0));
			}
		}
		
		return null;
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
					IdentifierNode identifier = tryExtractIdentifier(item);
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
			IdentifierNode identifier = tryExtractIdentifier(attr);
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
}
