package bc.lang;

public class BcInterfaceDefinitionNode extends BcClassDefinitionNode
{
	public BcInterfaceDefinitionNode(BcTypeNode type)
	{
		super(type);
	}
	
	@Override
	public boolean isInterface()
	{
		return true;
	}
}
