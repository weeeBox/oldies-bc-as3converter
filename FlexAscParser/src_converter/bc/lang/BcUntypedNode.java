package bc.lang;

public class BcUntypedNode extends BcTypeNode
{
	private static BcUntypedNode instance = new BcUntypedNode();
	
	private BcUntypedNode()
	{
		super("*");
	}
	
	@Override
	public boolean isUntyped()
	{
		return true;
	}
	
	public static BcUntypedNode instance()
	{
		return instance;
	}
}
