package bc.help;

import java.util.HashMap;
import java.util.Map;

import macromedia.asc.parser.IdentifierNode;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVectorTypeNode;

public class BcCodeCs
{
	private static final String NEW = "new";

	private static final String IS = "is";
	
	public static final String TYPE_PREFIX = "As";
	
	public static final String VECTOR_TYPE = "Vector";
	public static final String VECTOR_BC_TYPE = "Vector";
	
	public static final String NULL = "null";
	
	public static final String superCallMarker = "super";
	public static final String thisCallMarker = "this";
	
	private static Map<String, String> basicTypes;
	static
	{
		basicTypes = new HashMap<String, String>();
		basicTypes.put("void", "void");
		basicTypes.put("uint", "int");
		basicTypes.put("int", "int");
		basicTypes.put("Number", "float");
		basicTypes.put("float", "float");
		basicTypes.put("Boolean", "bool");
		basicTypes.put("String", "String");
	};
	
	public static String fullType(BcTypeNode bcType)
	{
		if (isBasicType(bcType))
			return type(bcType);
		
		BcClassDefinitionNode classNode = bcType.getClassNode();
		assert classNode != null : bcType.getName();
		
		String packageName = classNode.getPackageName();
		assert packageName != null : classNode.getName();
		
		return packageName + "." + type(bcType.getName());
	}
	
	public static String type(BcTypeNode bcType)
	{
		String typeName = bcType.getName();
		if (bcType instanceof BcVectorTypeNode)
		{
			BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;
			String genericName = BcCodeCs.type(vectorType.getGeneric());
			return type(VECTOR_BC_TYPE) + "<" + genericName + ">";
		}
		
		return type(typeName);
	}
	
	public static String type(String name)
	{
		String basic = basicTypes.get(name);
		if (basic != null)
		{
			return basic;
		}
		
		if (name.startsWith(TYPE_PREFIX))
		{
			return name; 
		}
		return TYPE_PREFIX + name;
	}
	
	public static boolean isBasicType(BcTypeNode type)
	{
		return isBasicType(type.getName());
	}
	
	public static boolean isBasicType(String name)
	{
		return basicTypes.containsKey(name);
	}
	
	public static boolean canBeClass(BcTypeNode type)
	{
		return canBeClass(type.getName());
	}

	public static boolean canBeClass(String name)
	{
		if (isBasicType(name))
			return false;
		
		char firstChar = name.charAt(0);
		return Character.isUpperCase(firstChar);
	}

	public static String typeArgRef(BcTypeNode bcType)
	{		
		return type(bcType);
	}
	
	public static String typeRef(BcTypeNode bcType)
	{
		return type(bcType);
	}

	public static String typeRef(String type)
	{
		return type(type);
	}

	public static String identifier(IdentifierNode identifier)
	{
		return identifier(identifier.name);
	}
	
	public static String identifier(String name)
	{
		return name;
	}
	
	public static String construct(BcTypeNode type, Object initializer)
	{
		if (type instanceof BcVectorTypeNode)
		{
			BcVectorTypeNode vectorType = (BcVectorTypeNode) type;
			return NEW + " " + type(VECTOR_BC_TYPE) + "<" + type(vectorType.getGeneric()) + ">" + "(" + initializer + ")";
		}
		return construct(type.getName(), initializer);
	}
	
	public static String construct(String type, Object initializer)
	{
		return NEW + " " + type(type) + "(" + initializer + ")";
	}

	public static String operatorIs(Object lhs, Object rhs)
	{
		return String.format("%s %s %s", lhs, IS, type(rhs.toString()));
	}
	
	public static boolean isVectorType(String typeName)
	{
		return typeName.equals(VECTOR_TYPE);
	}

	public static String namespace(String packageName) 
	{
		return packageName;
	}
}
