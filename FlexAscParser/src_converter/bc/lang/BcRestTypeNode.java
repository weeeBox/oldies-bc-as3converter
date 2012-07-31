package bc.lang;

public class BcRestTypeNode extends BcTypeNode
{
	private BcTypeNode type;

	protected BcRestTypeNode(BcTypeNode type)
	{
		super("_AS_REST");
		this.type = type;
	}
	
	public BcTypeNode getType()
	{
		return type;
	}
}
