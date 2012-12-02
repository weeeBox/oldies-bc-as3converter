package bc.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import bc.converter.As2CsConverter;
import bc.help.BcGlobal;
import bc.lang.BcClassList;
import bc.utils.filesystem.FileUtils;
import bc.utils.filesystem.StringFilter;

public class SyntaxConversionTest {

	private static final String DIR_TEST = "code_test";
	private static final String DIR_EXPECTED = "code_expected";
	private static final String DIR_ACTUAL = "code_actual";
	
	private static As2CsConverter converter;
	private static File actualdDir;

	@BeforeClass
	public static void setUp() throws IOException 
	{
		File currentDir = new File(System.getProperty("user.dir"));
		File userDir = new File("../FlexAscParser");
		actualdDir = new File(currentDir, DIR_ACTUAL);

		boolean succeed = FileUtils.delete(actualdDir);
		if (!succeed)
		{
			Assert.fail("Unable to delete output directory: " + actualdDir.getAbsolutePath());
		}
		
		succeed = actualdDir.mkdir();
		if (!succeed)
		{
			Assert.fail("Unable to create output directory: " + actualdDir.getAbsolutePath());
		}
		
		converter = new As2CsConverter();
		converter.setUserDir(userDir);
		converter.addIgnoreFile(new File(actualdDir, As2CsConverter.SECTION_PLATFORM));
		converter.addIgnoreFile(new File(actualdDir, As2CsConverter.SECTION_API));
		BcGlobal.bcApiClasses = new BcClassList();
		
		converter.convert(actualdDir);
	}

	@Test
	public void testInheritance() throws IOException
	{
		converter.convert(actualdDir, DIR_TEST + "/bc/test/basic/inheritance");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/inheritance/AsBaseClass.cs",
			"Converted/bc/test/basic/inheritance/AsChildClass.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testFields() throws IOException
	{
		converter.convert(actualdDir, DIR_TEST + "/bc/test/basic/fields");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/fields/AsClassA.cs",
			"Converted/bc/test/basic/fields/AsClassB.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testFunctionTypes() throws IOException 
	{
		converter.convert(actualdDir, DIR_TEST + "/bc/test/functions");
		
		String[] filenames = 
		{
			"Converted/bc/test/functions/AsFunctions.cs",
			"Converted/bc/test/functions/AsFunctionsTypeTest.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testObjectClass() throws IOException
	{
		converter.convert(actualdDir, DIR_TEST + "/bc/test/objects");
		
		String[] filenames = 
		{
				"Converted/bc/test/objects/AsObjectTypeTest.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testStrings() throws IOException
	{
		converter.convert(actualdDir, DIR_TEST + "/bc/test/strings");
		
		String[] filenames = 
		{
			"Converted/bc/test/strings/AsStringsTest.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}

	private void assertExpectedAndActualEquals(String[] filenames) throws IOException
	{
		File[] expectedFiles = createFiles(DIR_EXPECTED, filenames);
		File[] actualFiles = createFiles(DIR_ACTUAL, filenames);
		
		assertEquals(expectedFiles, actualFiles);
	}

	private void assertEquals(File[] expectedFiles, File[] actualFiles) throws IOException
	{
		if (expectedFiles.length != actualFiles.length)
		{
			Assert.fail("Expected and actual files list sizes are differnt");
		}
		
		for (int fileIndex = 0; fileIndex < actualFiles.length; fileIndex++) 
		{
			assertEquals(expectedFiles[fileIndex], actualFiles[fileIndex]);
		}
	}
	
	private void assertEquals(File expectedFile, File actualFile) throws IOException
	{
		if (!expectedFile.exists())
		{
			Assert.fail("Expected file doesn't exist: " + expectedFile);
		}
		
		if (!actualFile.exists())
		{
			Assert.fail("Actual file doesn't exist: " + actualFile);
		}
		
		StringFilter filter = new StringFilter() 
		{
			@Override
			public String filter(String str) 
			{
				String trimmed = str.trim();
				return trimmed.length() > 0 ? trimmed : null;
			}
		};
		
		List<String> expectedLines = FileUtils.readFile(expectedFile, filter);
		List<String> actualLines = FileUtils.readFile(actualFile, filter);
		
		assertEquals(expectedLines, actualLines);
	}

	private void assertEquals(List<String> expectedLines, List<String> actualLines) 
	{
		if (expectedLines.size() != actualLines.size())
		{
			Assert.fail("Expected and actual lines lists have different sizes: " + expectedLines.size() + "!=" + actualLines.size());
		}
		
		for (int lineIndex = 0; lineIndex < expectedLines.size(); lineIndex++) 
		{
			String expectedLine = expectedLines.get(lineIndex);
			String actualLine = actualLines.get(lineIndex);
			
			Assert.assertTrue(String.format("\nExpected: %s\n    Actual: %s",expectedLine, actualLine), expectedLine.equals(actualLine));
		}
	}
	
	private File[] createFiles(String baseDir, String[] filenames) 
	{
		File[] files = new File[filenames.length];
		for (int nameIndex = 0; nameIndex < filenames.length; ++nameIndex) 
		{
			files[nameIndex] = new File(baseDir, filenames[nameIndex]);
		}
		
		return files;
	}
}
