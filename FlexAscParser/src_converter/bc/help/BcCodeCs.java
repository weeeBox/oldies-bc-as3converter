package bc.help;

import java.util.HashMap;
import java.util.Map;

import macromedia.asc.parser.IdentifierNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVectorTypeNode;

public class BcCodeCs
{
	private static final String NEW = "__NEW";
	private static final String NEWVECTOR = "__NEWVECTOR";
	private static final String REF_POSTFIX = "_ref";

	private static final String INSTANCE_OF = "__INSTANCEOF";
	
	public static final String TYPE_PREFIX = "As";
	
	public static final String VECTOR_TYPE = "Vector";
	public static final String VECTOR_TYPE_PREFIX = "__V_";
	
	public static final String NULL = "__NULL";
	
	public static final String superCallMarker = "__$super$__";
	public static final String thisCallMarker = "__$this$__";
	
	private static Map<String, String> basicTypes;
	static
	{
		basicTypes = new HashMap<String, String>();
		basicTypes.put("void", "void");
		basicTypes.put("uint", "int");
		basicTypes.put("int", "int");
		basicTypes.put("Number", "float");
		basicTypes.put("float", "float");
		basicTypes.put("Boolean", "BOOL");
	};
	
	public static String type(BcTypeNode bcType)
	{
		String typeName = bcType.getName();
		if (bcType instanceof BcVectorTypeNode)
		{
			BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;
			String genericName = BcCodeCs.type(vectorType.getGeneric());
			return BcCodeCs.VECTOR_TYPE_PREFIX + genericName;
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
		
		if (name.startsWith(TYPE_PREFIX) || name.startsWith(VECTOR_TYPE_PREFIX))
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
		if (name.startsWith(VECTOR_TYPE_PREFIX))
			return true;
		
		if (isBasicType(name))
			return false;
		
		char firstChar = name.charAt(0);
		return Character.isUpperCase(firstChar);
	}

	public static String typeArgRef(BcTypeNode bcType)
	{
		if (canBeClass(bcType))
		{
			return "const " + typeRef(bcType) + "&";
		}
		return type(bcType);
	}
	
	public static String typeRef(BcTypeNode bcType)
	{
		if (canBeClass(bcType))
		{
			return type(bcType) + REF_POSTFIX;
		}
		
		return type(bcType);
	}

	public static String typeRef(String type)
	{
		if (canBeClass(type))
		{
			return type(type) + REF_POSTFIX;
		}
		
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
			return NEWVECTOR + "(" + VECTOR_TYPE_PREFIX + type(vectorType.getGeneric()) + ", (" + initializer + "))";
		}
		return construct(type.getName(), initializer);
	}
	
	public static String construct(String type, Object initializer)
	{
		return NEW + "(" + type(type) + ", (" + initializer + "))";
	}

	public static String operatorIs(Object lhs, Object rhs)
	{
		return INSTANCE_OF + String.format("(%s, %s)", lhs, type(rhs.toString()));
	}
	
	public static boolean isVectorType(String typeName)
	{
		return typeName.equals(VECTOR_TYPE);
	}
}
