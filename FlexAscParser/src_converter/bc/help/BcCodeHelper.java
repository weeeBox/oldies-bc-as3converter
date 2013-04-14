package bc.help;

import java.util.HashMap;
import java.util.Map;

import macromedia.asc.parser.IdentifierNode;
import bc.lang.BcFunctionTypeNode;
import bc.lang.BcTypeName;
import bc.lang.BcTypeNodeInstance;
import bc.utils.string.StringUtils;

public abstract class BcCodeHelper
{
	public static final String VECTOR_TYPE = "Vector";
	
	public static final String superCallMarker = "__$super$__";
	public static final String thisCallMarker = "__$this$__";
	
	protected abstract String[] getKeywords();
	
	private static Map<String, String> integralTypes;	
	private Map<String, Object> settingsMap;
	
	static
	{
		integralTypes = new HashMap<String, String>();
		integralTypes.put("uint", "uint");
		integralTypes.put("int", "int");
		integralTypes.put("long", "long"); // there's no "long" type in actionscript, but we keep it to store int+uint operations
		integralTypes.put("Number", "float");
		integralTypes.put("Boolean", "bool");
	};
	
	public BcCodeHelper()
	{
		settingsMap = new HashMap<String, Object>();
	}
	
	public abstract String literalNull();
	public abstract String literalBool();
	
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
	
	public static String findIntegralType(String name)
	{
		return integralTypes.get(name);
	}
	
	public static boolean isIntegralType(String name)
	{
		return findIntegralType(name) != null;
	}
	
	public static boolean isIntegralType(BcTypeNodeInstance type)
	{
		return findIntegralType(type.getName()) != null;
	}
	
	public static boolean isIntegralType(BcTypeName typeName)
	{
		return findIntegralType(typeName.getName()) != null;
	}
	
	public String extractIdentifier(IdentifierNode identifier)
	{
		return extractTypeName(identifier).getQualifiedName();
	}
	
	public BcTypeName extractTypeName(IdentifierNode identifier)
	{
		return extractTypeName(BcNodeHelper.extractIdentifier(identifier));
	}

	public BcTypeName extractTypeName(String identifier)
	{
		int dotIndex = identifier.lastIndexOf('.');
		if (dotIndex != -1)
		{
			String packageName = identifier.substring(0, dotIndex);
			String name = identifier.substring(dotIndex + 1);
			
			return new BcTypeName(BcNodeHelper.safeQualifier(packageName), identifier(name));
		}
			
		return new BcTypeName(identifier(identifier));
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
	
	// TODO: not sure how to handle it
	public String anonymousFunction()
	{
		return "function";
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

	public Object literalRegexp(String value)
	{
		return literalString(value);
	}
	
	public String undefinedType()
	{
		return "*";
	}

	public boolean shouldCastFunction(BcFunctionTypeNode bcFunc)
	{
		return false;
	}
}
