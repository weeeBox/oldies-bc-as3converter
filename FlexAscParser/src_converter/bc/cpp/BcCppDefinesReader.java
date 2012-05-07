package bc.cpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BcCppDefinesReader
{
	private static final String LITERAL_DEFINE = "#define";

	private static final String SPACE = "\\s+";
	private static final String MB_SPACE = "\\s*";
	private static final String IDENTIFIER = group("[\\w\\d_]+");
	private static final String LPAR = "\\(";
	private static final String ANY = group(".*?");
	private static final String RPAR = "\\)";
	
	private static String group(String regex)
	{
		return "(" + regex + ")";
	}
	
	private static final Pattern definePattern = Pattern.compile("\\#define" + SPACE + IDENTIFIER + MB_SPACE + LPAR + ANY + RPAR);
	
	public static BcCppDefines read(File file) throws IOException
	{
		List<String> lines = readLines(file);
		
		BcCppDefines defines = new BcCppDefines();
		Iterator<String> iter = lines.iterator();
		while (iter.hasNext())
		{
			String line = iter.next();
			if (line.startsWith(LITERAL_DEFINE))
			{
				BcCppDefine define = readDefine(line, iter);
				defines.add(define);
			}
		}
		
		return defines;
	}

	private static BcCppDefine readDefine(String line, Iterator<String> iter)
	{
		Matcher m = definePattern.matcher(line);
		boolean found = m.find();
		assert found : line;
		
		String name = m.group(1);
		String paramsStr = m.group(2);
		
		assert paramsStr != null;
		String[] params = paramsStr.trim().split("," + MB_SPACE);
		
		BcCppDefine define = new BcCppDefine(name, params);

		while (iter.hasNext())
		{
			String bodyLine = iter.next();
			if (bodyLine.trim().length() == 0)
				break;
			
			define.addLine(bodyLine);
		}
		
		return define;
	}

	protected static List<String> readLines(File file) throws IOException
	{
		BufferedReader reader = null;
		try
		{
			List<String> lines = new ArrayList<String>();
			
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				lines.add(line);
			}
			
			return lines;
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
	}
}
