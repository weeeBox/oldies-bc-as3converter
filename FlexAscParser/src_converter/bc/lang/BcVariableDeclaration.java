package bc.lang;

import macromedia.asc.parser.Node;
import bc.code.ListWriteDestination;

public class BcVariableDeclaration extends BcDeclaration
{
	private BcTypeNode type;
	private String identifier;
	private ListWriteDestination initializer;
	private boolean integralInitializerFlag;
	
	private Node initializerNode;
	
	public BcVariableDeclaration(BcTypeNode type, String identifier)
	{
		this.type = type;
		this.identifier = identifier;
	}
	
	public void setInitializerNode(Node initializerNode)
	{
		this.initializerNode = initializerNode;
	}
	
	public Node getInitializerNode()
	{
		return initializerNode;
	}
	
	public BcTypeNode getType()
	{
		return type;
	}
	
	public void setType(BcTypeNode type)
	{
		this.type = type;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	public void setInitializer(ListWriteDestination initializer)
	{
		this.initializer = initializer;
	}

	public boolean isIntegralInitializerFlag()
	{
		return integralInitializerFlag;
	}

	public void setIntegralInitializerFlag(boolean integralInitializerFlag)
	{
		this.integralInitializerFlag = integralInitializerFlag;
	}

	public boolean hasInitializer()
	{
		return initializer != null;
	}
	
	public ListWriteDestination getInitializer()
	{
		return initializer;
	}
}
