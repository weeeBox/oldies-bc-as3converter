package bc.lang;

import java.util.HashMap;
import java.util.Map;

import bc.help.BcCodeHelper;

public class BcTypeNode extends BcNode
{
	private String name;
	private String qualifier;
	
	private BcClassDefinitionNode classNode;
	protected boolean integral;
	
	public static Map<String, BcTypeNode> uniqueTypes = new HashMap<String, BcTypeNode>();

	public static BcTypeNode create(String name)
	{
		return create(name, true);
	}
	
	public static BcTypeNode create(String name, boolean registerType)
	{
		return create(name, null, registerType);
	}
	
	public static BcTypeNode create(String name, String qualifier)
	{
		return create(name, qualifier, true);
	}
	
	public static BcTypeNode create(String name, String quialifier, boolean registerType)
	{
		BcTypeNode node = uniqueTypes.get(name);
		if (node == null)
		{
			node = name.equals("Function") ? new BcFunctionTypeNode() : new BcTypeNode(name);
			if (registerType)
			{
				uniqueTypes.put(name, node);
			}
		}
		return node;
	}
	
	public static BcTypeNode createRestType()
	{
		return new BcRestTypeNode();
	}
	
	public static void add(String name, BcTypeNode type)
	{
		uniqueTypes.put(name, type);
	}
	
	protected BcTypeNode(String name)
	{
		this(name, null);
	}
	
	protected BcTypeNode(String name, String qualifier)
	{
		this.name = name;
		this.qualifier = qualifier;
		integral = BcCodeHelper.isIntegralType(name);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean hasQualifier()
	{
		return qualifier != null;
	}
	
	public void setQualifier(String qualifier)
	{
		this.qualifier = qualifier;
	}
	
	public String getQualifier()
	{
		return qualifier;
	}
	
	public String getQualifiedName()
	{
		return qualifier != null ? (qualifier + "." + name) : name;
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
	
	public boolean isNull()
	{
		return name.equals("null");
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
