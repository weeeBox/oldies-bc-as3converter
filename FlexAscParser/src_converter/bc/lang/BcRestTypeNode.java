package bc.lang;

public class BcRestTypeNode extends BcTypeNode
{
	private BcTypeNodeInstance restTypeInstance;

	protected BcRestTypeNode(BcTypeNodeInstance resType)
	{
		super("_AS_REST");
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
}
