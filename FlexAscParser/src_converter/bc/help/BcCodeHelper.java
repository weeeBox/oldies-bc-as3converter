package bc.help;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import macromedia.asc.parser.IdentifierNode;
import bc.code.ListWriteDestination;
import bc.lang.BcFuncParam;
import bc.lang.BcTypeNode;
import bc.lang.BcVariableDeclaration;
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
	
	protected abstract String vectorType(BcVectorTypeNode vectorType);
	protected abstract String constructVector(BcVectorTypeNode vectorType, Object initializer);
	
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
	
	
	protected String classType(String name)
	{
		if (name.startsWith(TYPE_PREFIX))
		{
			return name; 
		}
		return TYPE_PREFIX + name;
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
	
	public String varDecl(BcTypeNode type, String identifier)
	{
		return String.format("%s %s", type(type), identifier(identifier));
	}
	
	public String paramsDef(List<BcFuncParam> params)
	{
		StringBuilder buffer = new StringBuilder();
		
		int paramIndex = 0;
		for (BcVariableDeclaration bcParam : params)
		{
			buffer.append(paramDecl(bcParam.getType(), bcParam.getIdentifier()));
			if (++paramIndex < params.size())
			{
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}
	
	public String paramDecl(BcTypeNode type, String identifier)
	{
		return varDecl(type, identifier);
	}
	
	public String memberSelector(Object target, Object selector)
	{
		return String.format("%s.%s", target, selector);
	}
	
	public String staticSelector(Object target, Object selector)
	{
		return memberSelector(target, selector);
	}
	
	public String staticCall(Object target, Object selector, Object... args)
	{
		return memberCall(target, selector, args);
	}
	
	public String memberCall(Object target, Object selector, Object... args)
	{
		return memberSelector(target, String.format("%s(%s)", selector, BcStringUtils.commaSeparated(args)));
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
	
	public String catchClause(ListWriteDestination paramDest)
	{
		return String.format("catch (%s)", paramDest);
	}
	
	public String throwStatment(Object expr)
	{
		return "throw " + expr;
	}
}
