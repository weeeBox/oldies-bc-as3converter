package bc.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bc.help.BcCodeHelper;
import bc.help.BcGlobal;

public class BcTypeNode extends BcNode
{
	public static final String typeGlobal = "Global";
	public static final String typeNumber = "Number";
	public static final String typeObject = "Object";
	public static final String typeString = "String";
	public static final String typeVector = "Vector";
	public static final String typeArray = "Array";
	public static final String typeDictionary = "Dictionary";
	public static final String typeFunction = "Function";
	public static final String typeXML = "XML";
	public static final String typeXMLList = "XMLList";
	public static final String typeBoolean = "Boolean";
	
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
		BcTypeName typeName = new BcTypeName(qualifier, name);
		
		BcTypeNode node = uniqueTypes.get(typeName);
		if (node == null)
		{
			if (node == null && qualifier == null)
			{
				node = findByName(name);
			}
			
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
		
		// search imported type
		if (BcGlobal.lastBcImportList != null)
		{
			// find imported type
			String packageName = BcGlobal.lastBcImportList.findPackage(name);
			if (packageName != null)
			{
				for (BcTypeNode type : foundTypes)
				{
					if (packageName.equals(type.getQualifier()))
					{
						return type;
					}
				}
			}
			
			// try to select by wildcard import mask
			for (BcTypeNode foundType : foundTypes)
			{
				String qualifier = foundType.getQualifier();
				if (BcGlobal.lastBcImportList.hasWildcardMaskPackage(qualifier))
				{
					BcTypeName typeName = new BcTypeName(qualifier, name);
					if (uniqueTypes.containsKey(typeName))
					{
						return foundType;
					}
				}
			}
		}
		
		// search current package
		if (BcGlobal.lastBcPackageName != null)
		{
			BcTypeName typeName = new BcTypeName(BcGlobal.lastBcPackageName, name);
			BcTypeNode type = uniqueTypes.get(typeName);
			if (type != null)
			{
				return type;
			}
		}
		
		assert false : name;		
		return null;
	}
	
	public static BcTypeNode createRestType(BcTypeNode type)
	{
		return new BcRestTypeNode(type);
	}
	
	public static List<BcTypeNode> typesForPackage(String packageName)
	{
		Collection<BcTypeNode> types = uniqueTypes.values();
		List<BcTypeNode> foundTypes = new ArrayList<BcTypeNode>();
		
		for (BcTypeNode bcType : types)
		{
			if (packageName.equals(bcType.getQualifier()))
			{
				foundTypes.add(bcType);
			}
		}
		
		return foundTypes;
	}
	
	protected BcTypeNode(String name)
	{
		this(name, null);
	}
	
	protected BcTypeNode(String name, String qualifier)
	{
		this(new BcTypeName(qualifier, name));
	}
	
	protected BcTypeNode(BcTypeName typeName)
	{
		this.typeName = typeName;
		integral = BcCodeHelper.isIntegralType(typeName.getName());
	}
	
	public BcTypeNode createGeneric(BcTypeNode genericType)
	{
		if (getName().equals(typeVector))
		{
			BcVectorTypeNode vectorType = new BcVectorTypeNode(getTypeName(), genericType);
			vectorType.setClassNode(getClassNode());
			return vectorType;
		}
		return null;
	}
	
	public BcTypeNodeInstance createTypeInstance()
	{
		return createTypeInstance(false);
	}
	
	public BcTypeNodeInstance createTypeInstance(boolean qualified)
	{
		return new BcTypeNodeInstance(this, qualified);
	}
	
	public BcTypeName getTypeName()
	{
		return typeName;
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
	
	@Override
	public String toString()
	{
		return typeName.toString();
	}
}