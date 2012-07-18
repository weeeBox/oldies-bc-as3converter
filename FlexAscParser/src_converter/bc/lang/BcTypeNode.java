package bc.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bc.help.BcCodeHelper;
import bc.help.BcGlobal;
import bc.help.BcNodeHelper;

public class BcTypeNode extends BcNode
{
	private BcTypeName typeName;
	
	private BcClassDefinitionNode classNode;
	protected boolean integral;
	
	public static Map<BcTypeName, BcTypeNode> uniqueTypes = new HashMap<BcTypeName, BcTypeNode>();

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
	
	public static BcTypeNode create(String name, String qualifier, boolean registerType)
	{
		BcTypeName typeName = new BcTypeName(name, qualifier);
		
		BcTypeNode node = uniqueTypes.get(typeName);
		if (node == null)
		{
			node = findByName(name);
			if (node == null)
			{			
				node = name.equals("Function") ? new BcFunctionTypeNode() : new BcTypeNode(typeName);
				System.out.println("Add type: " + typeName.getQualifiedName());
				if (registerType)
				{
					uniqueTypes.put(typeName, node);
				}
			}
		}
		return node;
	}
	
	private static BcTypeNode findByName(String name)
	{
		Collection<BcTypeNode> types = uniqueTypes.values();
		List<BcTypeNode> foundTypes = new ArrayList<BcTypeNode>();
		
		for (BcTypeNode type : types)
		{
			if (type.getName().equals(name))
			{
				foundTypes.add(type);
			}
		}
		
		if (foundTypes.isEmpty())
		{
			return null;
		}
		
		if (foundTypes.size() == 1)
		{
			return foundTypes.get(0);
		}
		
		assert false;
		
		return null;
	}
	
	public static BcTypeNode createRestType()
	{
		return new BcRestTypeNode();
	}
	
	protected BcTypeNode(String name)
	{
		this(name, null);
	}
	
	protected BcTypeNode(String name, String qualifier)
	{
		this(new BcTypeName(name, qualifier));
	}
	
	protected BcTypeNode(BcTypeName typeName)
	{
		this.typeName = typeName;
		integral = BcCodeHelper.isIntegralType(typeName.getName());
	}
	
	public String getName()
	{
		return typeName.getName();
	}
	
	public boolean hasQualifier()
	{
		return typeName.hasQualifier();
	}
	
	public void setQualifier(String qualifier)
	{
		typeName.setQualifier(qualifier);
	}
	
	public String getQualifier()
	{
		return typeName.getQualifier();
	}
	
	public String getQualifiedName()
	{
		return typeName.getQualifiedName();
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
		return getName().equals("null");
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
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
		if (typeName == null)
		{
			if (other.typeName != null)
				return false;
		}
		else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}
}

class BcTypeName
{
	private String name;
	private String qualifier;

	public BcTypeName(String name)
	{
		this(name, null);
	}
	
	public BcTypeName(String name, String qualifier)
	{
		this.name = name;
		this.qualifier = qualifier;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getQualifier()
	{
		return qualifier;
	}
	
	public void setQualifier(String qualifier)
	{
		this.qualifier = qualifier;
	}
	
	public boolean hasQualifier()
	{
		return qualifier != null;
	}
	
	public String getQualifiedName()
	{
		if (hasQualifier())
			return getQualifier() + "." + getName();
		
		return getName();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
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
		BcTypeName other = (BcTypeName) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (qualifier == null)
		{
			if (other.qualifier != null)
				return false;
		}
		else if (!qualifier.equals(other.qualifier))
			return false;
		return true;
	}
}
