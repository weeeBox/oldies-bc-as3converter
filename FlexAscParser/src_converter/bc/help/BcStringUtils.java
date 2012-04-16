package bc.help;

public class BcStringUtils
{
	public static String replaceEscapes(String str)
	{
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\b", "\\\b").replace("\f", "\\\f").replace("\n", "\\\n").replace("\r", "\\\r").replace("\t", "\\\t");
	}
}
