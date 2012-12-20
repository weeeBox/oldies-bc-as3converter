package bc.lang;

public class BcVoidType extends BcTypeNode
{
	private static BcVoidType instance;
	
	private BcVoidType()
	{
		super("void");
	}
	
	@Override
	public boolean isVoid()
	{
		return true;
	}
	
	private BcVoidType(String name)
	{
		this();
	}
	
	private BcVoidType(String name, String qualifier)
	{
		this();
	}
	
	private BcVoidType(BcTypeName typeName)
	{
		this();
	}
	
	public static BcVoidType getInstance()
	{
		if (instance == null)
		{
			instance = new BcVoidType();
		}
		return instance;
	}

}
