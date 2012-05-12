package bc.help;

public class CppCodeHelper extends BcCodeHelper
{
	private static final String STRING_LITERAL = "ASL";
	
	private static String[] keyWords = 
	{ 
		"alignas", "alignof", "and", "and_eq", "asm", "auto", "bitand", 
		"bitor", "bool", "break", "case", "catch", "char", "char16_t", "char32_t", 
		"class", "compl", "const", "constexpr", "const_cast", "continue", 
		"decltype", "default", "delete", "do", "double", "dynamic_cast", 
		"else", "enum", "explicit", "export", "extern", "false", "float", 
		"for", "friend", "goto", "if", "inline", "int", "long", "mutable", 
		"namespace", "new", "noexcept", "not", "not_eq", "nullptr", "operator", 
		"or", "or_eq", "private", "protected", "public", "register", "reinterpret_cast", 
		"return", "short", "signed", "sizeof", "static", "static_assert", "static_cast", 
		"struct", "switch", "template", "this", "thread_local", "throw", "true", "try", 
		"typedef", "typeid", "typename", "union", "unsigned", "using", "virtual", 
		"void", "volatile", "wchar_t", "while", "xor", "xor_eq" 
	};

	@Override
	protected String[] getKeywords()
	{
		return keyWords;
	}
	
	@Override
	public String literalNull()
	{
		return "AS_NULL";
	}
	
	@Override
	public String literalString(String value)
	{
		return String.format("%s(\"%s\")", STRING_LITERAL, BcStringUtils.replaceEscapes(value));
	}
	
	public String include(String filename)
	{
		return String.format("#include \"%s\"", filename);
	}
}
