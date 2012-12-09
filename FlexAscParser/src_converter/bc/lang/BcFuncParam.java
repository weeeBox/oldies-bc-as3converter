package bc.lang;

import macromedia.asc.parser.Node;

public class BcFuncParam extends BcVariableDeclaration
{
	private Node defaultInitializer;
	
	public BcFuncParam(BcTypeNodeInstance type, String identifier)
	{
		super(type, identifier);
	}
	
	public boolean hasDefaultInitializer()
	{
		return defaultInitializer != null;
	}
	
	public Node getDefaultInitializer()
	{
		return defaultInitializer;
	}
	
	public void setDefaultInitializer(Node defaultInitializer)
	{
		this.defaultInitializer = defaultInitializer;
	}
}
