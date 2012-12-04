package bc.lang;

public class BcArgumentsType extends BcTypeNode {

	private static BcArgumentsType instance = new BcArgumentsType();
	
	protected BcArgumentsType()
	{
		super("arguments");
	}
	
	public static BcArgumentsType instance()
	{
		return instance;
	}

}
