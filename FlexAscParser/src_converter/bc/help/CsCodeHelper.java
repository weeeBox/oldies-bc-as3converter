package bc.help;

public class CsCodeHelper extends BcCodeHelper
{
	protected static final String NULL = "null";
	
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
	
	public static String SETTING_UPPERCASE_STRING_SELECTORS = "SETTING_UPPERCASE_STRING_SELECTORS";
	
	public CsCodeHelper()
	{
		setSetting(SETTING_UPPERCASE_STRING_SELECTORS, true);
	}
	
	@Override
	protected String[] getKeywords()
	{
		return keyWords;
	}
	
	@Override
	public String literalNull()
	{
		return NULL;
	}
}
