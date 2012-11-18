package bc.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import bc.converter.As2CsConverter;
import bc.converter.As2WhateverConverter;
import bc.utils.filesystem.FileUtils;

public class FunctionsTypesTest {

	@Before
	public void setUp() throws IOException 
	{
		File currentDir = new File(System.getProperty("user.dir"));
		File userDir = new File("../FlexAscParser");
		File outputDir = new File(currentDir, "output");

		boolean succeed = FileUtils.delete(outputDir);
		if (!succeed)
		{
			fail("Unable to delete output directory: " + outputDir.getAbsolutePath());
		}
		
		succeed = outputDir.mkdir();
		if (!succeed)
		{
			fail("Unable to create output directory: " + outputDir.getAbsolutePath());
		}
		
		As2WhateverConverter converter = new As2CsConverter();
		converter.setUserDir(userDir);
		converter.addIgnoreFile(new File(outputDir, As2CsConverter.SECTION_PLATFORM));
		converter.addIgnoreFile(new File(outputDir, As2CsConverter.SECTION_API));
		
		converter.convert(outputDir, "input");
	}

	@Test
	public void test() {
	}

}
