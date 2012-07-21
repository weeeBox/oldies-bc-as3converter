package bc.lang;

import macromedia.asc.parser.Node;
import bc.code.ListWriteDestination;

public class BcVariableDeclaration extends BcDeclaration
{
	private BcTypeNodeInstance typeInstance;
	private String identifier;
	private ListWriteDestination initializer;
	private boolean integralInitializerFlag;
	
	private Node initializerNode;
	
	public BcVariableDeclaration(BcTypeNode type, String identifier, boolean qualified)
	{
		this.identifier = identifier;
		typeInstance = new BcTypeNodeInstance(type, qualified);
	}
	
	public boolean isQualified()
	{
		return typeInstance.isQualified();
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
		return typeInstance.getType();
	}
	
	public BcTypeNodeInstance getTypeInstance()
	{
		return typeInstance;
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
	
	@Override
	public String toString()
	{
		return typeInstance.getQualifiedName() + " " + getIdentifier();
	}
}
