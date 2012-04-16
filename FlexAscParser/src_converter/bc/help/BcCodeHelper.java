package bc.help;

import java.util.HashMap;
import java.util.Map;

import macromedia.asc.parser.IdentifierNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVectorTypeNode;

public abstract class BcCodeHelper
{
	public static final String TYPE_PREFIX = "As";
	public static final String VECTOR_TYPE = "Vector";
	
	public static final String superCallMarker = "__$super$__";
	public static final String thisCallMarker = "__$base$__";
	
	private static String[] keyWords = 
	{
		"abstract", "add", "alias", "as", "ascending", 
		"base", "bool", "break", "byte", "case", 
		"catch", "char", "checked", "class", "const", 
		"continue", "decimal", "default", "delegate", "descending", 
		"do", "double", "dynamic", "else", "enum", 
		"event", "explicit", "extern", "false", "finally", 
		"fixed", "float", "for", "foreach", "from", 
		"get", "global", "goto", "group", "if", 
		"implicit", "in", "interface", "internal", 
		"into", "is", "join", "let", "lock", 
		"long", "namespace", "new", "null", "object", 
		"operator", "orderby", "out", "override", "params", 
		"partial", "private", "protected", "public", "readonly", 
		"ref", "remove", "return", "sbyte", "sealed", 
		"select", "set", "short", "sizeof", "stackalloc", 
		"static", "string", "struct", "switch", "this", 
		"throw", "true", "try", "typeof", 
		"ulong", "unchecked", "unsafe", "ushort", "using", 
		"value", "var", "virtual", "void", "volatile", 
		"while", "yield"
	};
	
	private static Map<String, String> basicTypes;
	static
	{
		basicTypes = new HashMap<String, String>();
		basicTypes.put("void", "void");
		basicTypes.put("uint", "uint");
		basicTypes.put("int", "int");
		basicTypes.put("Number", "float");
		basicTypes.put("float", "float");
		basicTypes.put("Boolean", "bool");
	};
	
	public abstract String construct(String type, Object initializer);
	public abstract String operatorIs(Object lhs, Object rhs);
	public abstract String literalNull();
	
	protected abstract String classType(String name);
	protected abstract String vectorType(BcVectorTypeNode vectorType);
	protected abstract String constructVector(BcVectorTypeNode vectorType, Object initializer);
	
	protected abstract String staticCall(String type, String method, Object args);
	
	
	public String type(BcTypeNode bcType)
	{
		String typeName = bcType.getName();
		if (bcType instanceof BcVectorTypeNode)
		{
			return vectorType((BcVectorTypeNode) bcType);
		}
		
		return type(typeName);
	}
	
	public String type(String name)
	{
		String basic = basicTypes.get(name);
		if (basic != null)
		{
			return basic;
		}
		
		return classType(name);
	}
	
	public String keywordSafe(String name)
	{
		for (String keyword : keyWords)
		{
			if (name.equals(keyword))
			{
				return "_" + name;
			}
		}
		
		return name;
	}
	
	public boolean isBasicType(BcTypeNode type)
	{
		return isBasicType(type.getName());
	}
	
	public boolean isBasicType(String name)
	{
		return basicTypes.containsKey(name);
	}
	
	public String typeArgRef(BcTypeNode bcType)
	{		
		return type(bcType);
	}
	
	public String typeRef(BcTypeNode bcType)
	{
		return type(bcType);
	}

	public String typeRef(String type)
	{
		return type(type);
	}

	public String identifier(IdentifierNode identifier)
	{
		String name = identifier.name;
		if (identifier.isAttr() && name.startsWith("@"))
		{
			name = name.substring(1);
		}
		
		return identifier(name);
	}
	
	public String identifier(String name)
	{
		return keywordSafe(name);
	}
	
	public String construct(BcTypeNode type)
	{
		return construct(type, "");
	}
	
	public String construct(BcTypeNode type, Object initializer)
	{
		if (type instanceof BcVectorTypeNode)
		{
			return constructVector((BcVectorTypeNode)type, initializer);
		}
		return construct(type.getName(), initializer);
	}
	
	
	public String parseString(Object expr, BcTypeNode exprType)
	{
		String typeString = type(exprType);
		typeString = Character.toUpperCase(typeString.charAt(0)) + typeString.substring(1);
		return staticCall("AsString", "parse" + typeString, expr);
	}
	
	
	public String cast(Object expr, BcTypeNode type)
	{
		return String.format("(%s)(%s)", type(type), expr);
	}
	
	public String getter(String name)
	{
		return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
	
	public String setter(String name)
	{
		return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
	
	public boolean isVectorType(String typeName)
	{
		return typeName.equals(VECTOR_TYPE);
	}

	public String namespace(String packageName) 
	{
		return packageName;
	}
	
	public String literalBool(boolean value)
	{
		return value ? "true" : "false";
	}
	
	public String literalString(String value)
	{
		return String.format("\"%s\"", BcStringUtils.replaceEscapes(value));
	}
}
