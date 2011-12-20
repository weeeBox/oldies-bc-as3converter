package bc.lang;

public class BcMemberExpressionNode extends BcNode
{
	private BcNode base;
	private BcSelector selector;

	public BcMemberExpressionNode(BcNode base, BcSelector selector)
	{
		this.base = base;
		this.selector = selector;
	}
}
