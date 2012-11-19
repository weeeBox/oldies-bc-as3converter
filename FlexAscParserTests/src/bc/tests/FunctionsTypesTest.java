package bc.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import bc.converter.As2CsConverter;
import bc.utils.filesystem.FileUtils;

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
			fail("Unable to delete output directory: " + actualdDir.getAbsolutePath());
		}
		
		succeed = actualdDir.mkdir();
		if (!succeed)
		{
			fail("Unable to create output directory: " + actualdDir.getAbsolutePath());
		}
		
		converter = new As2CsConverter();
		converter.setUserDir(userDir);
		converter.addIgnoreFile(new File(actualdDir, As2CsConverter.SECTION_PLATFORM));
		converter.addIgnoreFile(new File(actualdDir, As2CsConverter.SECTION_API));
		
		converter.convert(actualdDir);
	}

	@Test
	public void test() throws IOException 
	{
		converter.convert(actualdDir, DIR_TEST);
	}

}
