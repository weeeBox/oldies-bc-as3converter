package bc.tests;

import java.io.IOException;

import org.junit.Test;


public class SyntaxConversionTest extends ConverterTestSuite 
{
	@Test
	public void testTypes() throws IOException
	{
		convert("/bc/test/basic/types");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/types/AsTypesTests.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testPackages() throws IOException
	{
		convert("/bc/flash/text", "/bc/test/basic/packages");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/packages/AsPackagesTests.cs",
			"Converted/bc/test/basic/packages/foo/AsPackageClass.cs",
			"Converted/bc/test/basic/packages/bar/AsPackageClass.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testExpressions() throws IOException
	{
		convert("/bc/test/basic/expressions");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/expressions/AsExpressionsTests.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testInheritance() throws IOException
	{
		convert("/bc/test/basic/inheritance");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/inheritance/AsBaseClass.cs",
			"Converted/bc/test/basic/inheritance/AsChildClass.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testCalls() throws IOException
	{
		convert("/bc/test/basic/calls");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/calls/AsPropertiesTests.cs",
			"Converted/bc/test/basic/calls/AsCallsTests.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testFields() throws IOException
	{
		convert("/bc/test/basic/fields");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/fields/AsClassA.cs",
			"Converted/bc/test/basic/fields/AsClassB.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testArrays() throws IOException
	{
		convert("/bc/test/arrays");
		
		String[] filenames = 
		{
			"Converted/bc/test/arrays/AsArraysTest.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testVectors() throws IOException
	{
		convert("/bc/test/vectors");
		
		String[] filenames = 
		{
			"Converted/bc/test/vectors/AsVectorsTest.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testFlowControl() throws IOException
	{
		convert("/bc/test/basic/flow");
		
		String[] filenames = 
		{
			"Converted/bc/test/basic/flow/AsFlowControlTests.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testFunctionTypes() throws IOException 
	{
		convert("/bc/test/functions");
		
		String[] filenames = 
		{
			"Converted/bc/test/functions/AsFunctionsTypeTest.cs",
			"Converted/bc/test/functions/AsFunctionsSyntaxTest.cs",
			"Converted/bc/test/functions/AsFunctionsReflectionTest.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testObjectClass() throws IOException
	{
		convert("/bc/test/objects");
		
		String[] filenames = 
		{
				"Converted/bc/test/objects/AsObjectTypeTest.cs",
		};
		
		assertExpectedAndActualEquals(filenames);
	}
	
	@Test
	public void testStrings() throws IOException
	{
		convert("/bc/test/strings");
		
		String[] filenames = 
		{
			"Converted/bc/test/strings/AsStringsTest.cs"
		};
		
		assertExpectedAndActualEquals(filenames);
	}
}
