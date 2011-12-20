package bc.lang;

public class BcCallExpression extends BcSelector
{
	private BcArgumentsListNode args;
	
	public BcCallExpression()
	{
		args = new BcArgumentsListNode();
	}
	
	public void addArgument(BcNode node)
	{
		args.add(node);
	}
}
