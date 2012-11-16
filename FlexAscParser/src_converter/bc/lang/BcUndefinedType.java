package bc.lang;

public class BcUndefinedType extends BcTypeNode 
{
	private static BcUndefinedType instance = new BcUndefinedType();
	
	protected BcUndefinedType()
	{
		super("undefined");
	}
	
	public static BcUndefinedType instance()
	{
		return instance;
	}
}
