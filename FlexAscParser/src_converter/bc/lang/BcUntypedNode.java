package bc.lang;

public class BcUntypedNode extends BcTypeNode
{
	public BcUntypedNode()
	{
		super("*");
	}
	
	@Override
	public boolean isUntyped()
	{
		return true;
	}
}
