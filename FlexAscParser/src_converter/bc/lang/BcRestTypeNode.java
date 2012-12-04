package bc.lang;

public class BcRestTypeNode extends BcTypeNode
{
	private BcTypeNode restType;

	protected BcRestTypeNode(BcTypeNode resType)
	{
		super("_AS_REST");
		this.restType = resType;
	}
	
	public BcTypeNode getRestType()
	{
		return restType;
	}
}
