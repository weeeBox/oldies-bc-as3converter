package bc.help;

import java.util.List;

public class BcStringUtils
{
	public static String replaceEscapes(String str)
	{
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\b", "\\\b").replace("\f", "\\\f").replace("\n", "\\\n").replace("\r", "\\\r").replace("\t", "\\\t");
	}
	
	public static <T> String commaSeparated(T... values)
	{
		StringBuilder buffer = new StringBuilder();
		
		int valIndex = 0;
		for (T val : values)
		{
			buffer.append(val);
			if (++valIndex < values.length)
			{
				buffer.append(", ");
			}
		}
		
		return buffer.toString();
	}
	
	public static <T> String commaSeparated(List<T> values)
	{
		StringBuilder buffer = new StringBuilder();
		
		int valIndex = 0;
		for (Object val : values)
		{
			buffer.append(val);
			if (++valIndex < values.size())
			{
				buffer.append(", ");
			}
		}
		
		return buffer.toString();
	}
	
	public static String captureBraces(String line, int startIndex)
	{
		boolean insideString = false;
		boolean insideChar = false;
		char prevChar = 0;
		char chr = 0;
		
		StringBuilder capture = new StringBuilder();
		
		int parentnessisCounter = 0;
		boolean insideComment = false;
		
		for (int index = startIndex; index < line.length(); ++index)
		{
			prevChar = chr;
			chr = line.charAt(index);
			
			capture.append(chr);
			
			if (chr == '"' && prevChar != '\\')
			{
				insideString = !insideString;
			}
			
			if (insideString)
			{
				continue;
			}
			
			if (chr =='\'' && prevChar != '\\')
			{
				insideChar = !insideChar;
			}
			
			if (insideChar)
			{
				continue;
			}
			
			if (chr == '(')
			{
				parentnessisCounter++;
			}
			else if (chr == ')')
			{
				assert parentnessisCounter > 0;
				parentnessisCounter--;
				
				if (parentnessisCounter == 0)
				{
					break;
				}
			}
		}

		assert !insideString;
		assert !insideComment;		
		assert parentnessisCounter == 0;
		
		return capture.toString();
	}
}
