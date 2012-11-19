package bc.tests;

import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bc.converter.As2CsConverter;
import bc.utils.filesystem.FileUtils;
import bc.utils.filesystem.StringFilter;

public class FunctionsTypesTest {

	private static final String DIR_TEST = "code_test";
	private static final String DIR_EXPECTED = "code_expected";
	private static final String DIR_ACTUAL = "code_actual";
	
	private As2CsConverter converter;
	private File actualdDir;

	@Before
	public void setUp() throws IOException 
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
		
		converter.convert(actualdDir);
	}

	@Test
	public void testFunctionTypes() throws IOException 
	{
		converter.convert(actualdDir, DIR_TEST);
		
		String[] filenames = 
		{
			"Converted/bc/test/functions/AsFunctions.cs",
			"Converted/bc/test/functions/AsFunctionsTypeTest.cs",
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
			Assert.fail("Expected and actual lines lists have different sizes");
		}
		
		for (int lineIndex = 0; lineIndex < expectedLines.size(); lineIndex++) 
		{
			String expectedLine = expectedLines.get(lineIndex);
			String actualLine = actualLines.get(lineIndex);
			
			Assert.assertEquals(expectedLine, actualLine);
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
	
	private File createExpectedFile(String path) 
	{
		return new File(DIR_EXPECTED, path);
	}
	
	private File createActualFile(String path) 
	{
		return new File(DIR_ACTUAL, path);
	}
}
