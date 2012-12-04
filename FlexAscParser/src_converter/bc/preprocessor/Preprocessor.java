package bc.preprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessor 
{
	private static final String PREPROCESSOR_TOKEN = "//#";
	private static final String TOKEN_GUARD = "//# ";
	private static final String TOKEN_IF = "//#if";
	private static final String TOKEN_ELIF = "//#elif";
	private static final String TOKEN_ELSE = "//#else";
	private static final String TOKEN_ENDIF = "//#endif";
	
	private static final Pattern IF = Pattern.compile("//#if\\s+([\\w\\d_#]+)");
	private static final Pattern ELIF = Pattern.compile("//#elif\\s+([\\w\\d_#]+)");
	
	private static String[] EXPRESSIONS = { "C#" };
	
	public List<String> process(List<String> lines) throws PreprocessorException 
	{
		List<String> newLines = new ArrayList<String>(lines.size());
		
		LineIterator iter = new LineIterator(lines);
		boolean inBlock = false;
		boolean keepBlock = false;
		int counter = 0;
		
		while (iter.hasNext())
		{
			String line = iter.next();
			String trimmed = line.trim();
			if (trimmed.startsWith(PREPROCESSOR_TOKEN))
			{
				if (trimmed.startsWith(TOKEN_GUARD))
				{
					if (inBlock && keepBlock)
					{
						int start = line.indexOf(TOKEN_GUARD);
						int end = start + TOKEN_GUARD.length();
						line = line.substring(0, start) + line.substring(end);
					}
				}
				else if (trimmed.startsWith(TOKEN_IF))
				{
					Matcher m = IF.matcher(trimmed);
					if (m.find())
					{
						String token = m.group(1);
						inBlock = true;
						keepBlock = isTrue(token);
						line = "";
						counter++;
					}
				}
				else if (trimmed.startsWith(TOKEN_ELIF))
				{
					Matcher m = ELIF.matcher(trimmed);
					if (m.find())
					{
						String token = m.group(1);
						keepBlock = isTrue(token);
						line = "";
					}
				}
				else if (trimmed.startsWith(TOKEN_ELSE))
				{
					keepBlock = !keepBlock;
					line = "";
				}
				else if (trimmed.startsWith(TOKEN_ENDIF))
				{
					inBlock = false;
					line = "";
					counter--;
				}
			}
			
			if (inBlock && !keepBlock)
			{
				line = "";
			}
			newLines.add(line);
		}

		if (counter > 0)
		{
			throw new PreprocessorException("Missing #endif statement");
		}
		
		if (counter < 0)
		{
			throw new PreprocessorException("Missing #if/#elif statement");
		}
		
		return newLines;
	}
	
	private boolean isTrue(String expression)
	{
		for (String contant : EXPRESSIONS) 
		{
			if (contant.equals(expression))
			{
				return true;
			}
		}
		return false;
	}
}
