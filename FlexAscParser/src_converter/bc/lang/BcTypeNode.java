package bc.lang;

import java.util.HashMap;
import java.util.Map;

import bc.help.BcCodeHelper;

public class BcTypeNode extends BcNode
{
	private String name;
	private BcClassDefinitionNode classNode;
	private boolean integral;
	
	public static Map<String, BcTypeNode> uniqueTypes = new HashMap<String, BcTypeNode>();

	public static BcTypeNode create(String name)
	{
		return create(name, true);
	}
	
	public static BcTypeNode create(String name, boolean registerType)
	{
		BcTypeNode node = uniqueTypes.get(name);
		if (node == null)
		{
			if (name.equals("Function"))
			{
				node = new BcFunctionTypeNode();
			}
			else
			{			
				node = new BcTypeNode(name);
			}
			if (registerType) uniqueTypes.put(name, node);
		}
		return node;
	}
	
	public static void add(String name, BcTypeNode type)
	{
		uniqueTypes.put(name, type);
	}
	
	protected BcTypeNode(String name)
	{
		this.name = name;
		integral = BcCodeHelper.isBasicType(name);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getNameEx()
	{
		return getName();
	}
	
	public void setClassNode(BcClassDefinitionNode classNode)
	{
		this.classNode = classNode;
	}
	
	public boolean hasClassNode()
	{
		return classNode != null;
	}
	
	public BcClassDefinitionNode getClassNode()
	{
		return classNode;
	}
	
	public boolean isIntegral()
	{
		return integral;
	}
	
	public boolean isInterface()
	{
		return !integral && classNode != null && classNode.isInterface();
	}
	
	public boolean isClass()
	{
		return !integral && classNode != null && !classNode.isInterface();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BcTypeNode other = (BcTypeNode) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}
}
