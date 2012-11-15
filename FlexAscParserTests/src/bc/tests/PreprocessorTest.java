package bc.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bc.preprocessor.Preprocessor;
import bc.preprocessor.PreprocessorException;

public class PreprocessorTest {

	private Preprocessor preprocessor = new Preprocessor();
	
	@Test
	public void testIfBlock() 
	{
		List<String> lines = toList(new String[]{
				"	//#if C#",
				"	C# code",
				"	//# C# code",
				"	//#endif"
		});
		List<String> expected = toList(new String[]{
				"",
				"	C# code",
				"	C# code",
				""
		});
		
		try 
		{
			List<String> actual = preprocessor.process(lines);
			assertTrue(equals(actual, expected));
		} 
		catch (PreprocessorException e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testElseBlock() 
	{
		List<String> lines = toList(new String[]{
			"	//#if AS3_ONLY",
			"	As3 only code",
			"	//#else",
			"	//# Alternative code",
			"	//#endif"
		});
		List<String> expected = toList(new String[]{
				"",
				"",
				"",
				"	Alternative code",
				""
		});
		
		try 
		{
			List<String> actual = preprocessor.process(lines);
			assertTrue(equals(actual, expected));
		} 
		catch (PreprocessorException e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testIfElseBlock() 
	{
		List<String> lines = toList(new String[]{
				"	//#if C#",
				"	C# code",
				"	//# C# code",
				"	//#else",
				"	//# Alternative code",
				"	//#endif"
		});
		List<String> expected = toList(new String[]{
				"",
				"	C# code",
				"	C# code",
				"",
				"",
				""
		});
		
		try 
		{
			List<String> actual = preprocessor.process(lines);
			assertTrue(equals(actual, expected));
		} 
		catch (PreprocessorException e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testIfElifElseBlock() 
	{
		List<String> lines = toList(new String[]{
				"	//#if AS3_ONLY",
				"	As3 only code",
				"	//# As3 only code",
				"	//#elif AS3",
				"	//#else",
				"	//# Alternative code",
				"	//#endif"
		});
		List<String> expected = toList(new String[]{
				"",
				"",
				"",
				"",
				"",
				"	Alternative code",
				""
		});
		
		try 
		{
			List<String> actual = preprocessor.process(lines);
			assertTrue(equals(actual, expected));
		} 
		catch (PreprocessorException e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBadCode() 
	{
		List<String> lines = toList(new String[]{
				"	//#if AS3_ONLY",
				"	As3 only code",
				"	//# As3 only code",
				"	//#elif AS3",
				"	//#else",
				"	//# Alternative code",
		});
		
		try 
		{
			preprocessor.process(lines);
		} 
		catch (PreprocessorException e) 
		{
			assertTrue(true);
		}
	}
	
	private boolean equals(List<String> actual, List<String> expected) 
	{
		if (actual.size() != expected.size())
		{
			return false;
		}
		
		for (int i = 0; i < actual.size(); i++) 
		{
			if (!actual.get(i).equals(expected.get(i)))
			{
				return false;
			}
		}
		
		return true;
	}

	private List<String> toList(String[] lines)
	{
		List<String> list = new ArrayList<String>(lines.length);
		for (String line : lines) 
		{
			list.add(line);
		}
		return list;
	}

}
