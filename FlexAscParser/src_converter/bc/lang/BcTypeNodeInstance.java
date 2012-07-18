package bc.lang;

public class BcTypeNodeInstance
{
	private BcTypeNode type;
	private boolean qualified;

	public BcTypeNodeInstance(BcTypeNode type, boolean qualified)
	{
		this.type = type;
		this.qualified = qualified;
	}
	
	public BcTypeNode getType()
	{
		return type;
	}

	public String getName()
	{
		return type.getName();
	}
	
	public boolean isQualified()
	{
		return qualified;
	}
}
