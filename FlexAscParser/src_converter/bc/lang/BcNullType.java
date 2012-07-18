package bc.lang;


public class BcNullType extends BcTypeNode
{	
	private static BcNullType instance = new BcNullType();
	
	protected BcNullType()
	{
		super("null");
	}
	
	public static BcNullType instance()
	{
		return instance;
	}
}
