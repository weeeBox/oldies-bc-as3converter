package bc.help;

public class BcStringUtils
{
	public static String replaceEscapes(String str)
	{
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\b", "\\\b").replace("\f", "\\\f").replace("\n", "\\\n").replace("\r", "\\\r").replace("\t", "\\\t");
	}
	
	public static String commaSeparated(Object... values)
	{
		StringBuilder buffer = new StringBuilder();
		
		int valIndex = 0;
		for (Object val : values)
		{
			buffer.append(val);
			if (++valIndex < values.length)
			{
				buffer.append(", ");
			}
		}
		
		return buffer.toString();
	}
}
