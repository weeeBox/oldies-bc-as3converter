package bc.help;

import macromedia.asc.parser.BinaryExpressionNode;
import macromedia.asc.parser.ListNode;
import macromedia.asc.parser.LiteralNullNode;
import macromedia.asc.parser.LiteralNumberNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.Tokens;
import macromedia.asc.parser.UnaryExpressionNode;
import bc.lang.BcTypeNode;

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
}
