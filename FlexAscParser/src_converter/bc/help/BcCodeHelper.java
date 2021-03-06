package bc.help;

import java.util.HashMap;
import java.util.Map;

import macromedia.asc.parser.IdentifierNode;
import macromedia.asc.parser.LiteralStringNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.QualifiedIdentifierNode;
import bc.lang.BcTypeName;
import bc.lang.BcTypeNode;
import bc.lang.BcTypeNodeInstance;

public abstract class BcCodeHelper
{
	public static final String VECTOR_TYPE = "Vector";
	
	public static final String superCallMarker = "__$super$__";
	public static final String thisCallMarker = "__$this$__";
	
	protected abstract String[] getKeywords();
	
	private static Map<String, String> basicTypes;
	private static Map<String, String> integralTypes;	
	
	private Map<String, Object> settingsMap;
	
	public static String SETTING_DELEGATE_STRINGS_CALLS = "SETTING_DELEGATE_STRINGS_CALLS";
	
	static
	{
		basicTypes = new HashMap<String, String>();
		basicTypes.put("void", "void");
		basicTypes.put("uint", "uint");
		basicTypes.put("int", "int");
		basicTypes.put("Number", "float");
		basicTypes.put("float", "float");
		basicTypes.put("Boolean", "bool");
		
		integralTypes = new HashMap<String, String>();
		integralTypes.put("void", "void");
		integralTypes.put("uint", "uint");
		integralTypes.put("int", "int");
		integralTypes.put("long", "long");
		integralTypes.put("Number", "float");
		integralTypes.put("float", "float");
		integralTypes.put("Boolean", "bool");
	};
	
	public BcCodeHelper()
	{
		settingsMap = new HashMap<String, Object>();
	}
	
	public abstract String literalNull();
	public abstract String literalBool();
	
	public String getter(String name)
	{
		return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
	
	public String setter(String name)
	{
		return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
	
	public String literalBool(boolean value)
	{
		return value ? "true" : "false";
	}
	
	public String literalString(Object value)
	{
		if (value == null)
		{
			throw new IllegalArgumentException("Value is null");
		}
		
		return literalString(value.toString());
	}
	
	public String literalString(String value)
	{
		return BcStringUtils.parentesis(value);
	}
	
	public String isNull(Object value)
	{
		return String.format("%s == %s", value, literalNull());
	}
	
	public String notNull(Object value)
	{
		return String.format("%s != %s", value, literalNull());
	}
	
	public String isZero(Object value)
	{
		return String.format("%s == 0", value);
	}
	
	public String notZero(Object value)
	{
		return String.format("%s != 0", value);
	}
	
	public String keywordSafe(String name)
	{
		String[] keywords = getKeywords();
		for (String keyword : keywords)
		{
			if (name.equals(keyword))
			{
				return "_" + name;
			}
		}
		
		return name;
	}
	
	public static boolean isBasicType(BcTypeName typeName)
	{
		return isBasicType(typeName.getName());
	}
	
	public static boolean isBasicType(BcTypeNodeInstance typeInstance)
	{
		return isBasicType(typeInstance.getType());
	}
	
	public static boolean isBasicType(BcTypeNode type)
	{
		return isBasicType(type.getName());
	}
	
	public static String findBasicType(String name)
	{
		return basicTypes.get(name);
	}
	
	public static boolean isBasicType(String name)
	{
		return basicTypes.containsKey(name);
	}
	
	public static boolean isIntegralType(String name)
	{
		return integralTypes.containsKey(name);
	}
	
	public String extractIdentifier(IdentifierNode identifier)
	{
		return extractTypeName(identifier).getQualifiedName();
	}
	
	public BcTypeName extractTypeName(IdentifierNode identifier)
	{
		String identifierString = BcNodeHelper.extractIdentifier(identifier);
		int dotIndex = identifierString.lastIndexOf('.');
		if (dotIndex != -1)
		{
			String packageName = identifierString.substring(0, dotIndex);
			String name = identifierString.substring(dotIndex + 1);
			
			return new BcTypeName(BcNodeHelper.safeQualifier(packageName), identifier(name));
		}
			
		return new BcTypeName(identifier(identifierString));
	}
	
	public String identifier(String name)
	{
		return keywordSafe(name);
	}
	
	public boolean isVectorType(String typeName)
	{
		return typeName.equals(VECTOR_TYPE);
	}

	public String namespace(String packageName) 
	{
		return packageName;
	}

	protected void setSetting(String name, Object value)
	{
		settingsMap.put(name, value);
	}
	
	public String stringSetting(String name)
	{
		return stringSetting(name, null);
	}
	
	public String stringSetting(String name, String defaultValue)
	{
		Object value = objectSetting(name);
		return value == null ? defaultValue : (String) value;
	}
	
	public boolean boolSetting(String name)
	{
		return boolSetting(name, false);
	}
	
	public boolean boolSetting(String name, boolean defaultValue)
	{
		Object value = objectSetting(name);
		return value == null ? defaultValue : ((Boolean)value).booleanValue();
	}
	
	public Object objectSetting(String name)
	{
		return settingsMap.get(name);
	}
}
