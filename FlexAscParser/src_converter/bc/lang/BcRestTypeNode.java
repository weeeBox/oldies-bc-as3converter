package bc.lang;

public class BcRestTypeNode extends BcTypeNode
{
	private BcTypeNodeInstance restTypeInstance;

	protected BcRestTypeNode(BcTypeNodeInstance resType)
	{
		super("...");
		this.restTypeInstance = resType;
	}
	
	public BcTypeNodeInstance getRestTypeInstance()
	{
		return restTypeInstance;
	}
	
	public BcTypeNode getRestType()
	{
		return restTypeInstance.getType();
	}

	@Override
	public boolean isRest()
	{
		return true;
	}
}
