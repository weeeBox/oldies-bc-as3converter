package bc.help;

public class CsCodeHelper extends BcCodeHelper
{
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
	
	public CsCodeHelper()
	{
		setSetting(SETTING_DELEGATE_STRINGS_CALLS, true);
	}
	
	@Override
	protected String[] getKeywords()
	{
		return keyWords;
	}
	
	@Override
	public String literalNull()
	{
		return "null";
	}
	
	// FIXME: fix string literals
//	@Override
//	public String literalString(String value) 
//	{
//		if (BcStringUtils.needsEscaped(value))
//		{
//			return "@\"" + value + '"';
//		}
//		return '"' + value + '"';
//	}
	
	@Override
	public String literalBool()
	{
		return "Boolean";
	}
}
