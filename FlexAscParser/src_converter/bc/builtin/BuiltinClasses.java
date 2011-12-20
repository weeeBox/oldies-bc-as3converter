package bc.builtin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcInterfaceDefinitionNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVariableDeclaration;

public class BuiltinClasses
{
	public static final String TOPLEVEL_DUMMY_CLASS_NAME = "__TopLevelDummyClass__"; // this name is used for fake class which holds all of the top level functions
	
	private static String space = "\\s+";
	private static String mbspace = "\\s*";
	private static String type = "([\\p{Upper}][\\w\\d_]+)";
	private static String identifier = "([\\w\\d_]+)";
	private static Pattern classPattern = Pattern.compile("(class)?(interface)?" + space + type + "(" + space + "extends" + space + type + ")?");
	private static Pattern fieldPattern = Pattern.compile("(var)?(const)?" + space + identifier + mbspace + ":" + mbspace + identifier);
	private static Pattern functionPattern = Pattern.compile("((get)?(set)?" + space + ")?"+identifier + mbspace + "\\((.*)\\)" + mbspace + ":" + mbspace + identifier);
	private static Pattern argPattern = Pattern.compile(identifier + mbspace + ":" + mbspace + identifier);
	
	private static BcClassDefinitionNode dummyTopLevelClass;
	
	public static List<BcClassDefinitionNode> load(File dir) throws IOException
	{
		File[] files = dir.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(".as");
			}
		});
		
		List<BcClassDefinitionNode> classes = new ArrayList<BcClassDefinitionNode>();
		for (File file : files)
		{
			BcClassDefinitionNode bcClass = loadFile(file);
			if (bcClass != dummyTopLevelClass)
			{
				classes.add(bcClass);
			}
		}
		
		if (dummyTopLevelClass != null)
		{
			classes.add(dummyTopLevelClass);
		}
		
		return classes;
	}

	private static BcClassDefinitionNode loadFile(File file) throws IOException
	{
		BcClassDefinitionNode bcClass = null;
		
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				if (line.trim().startsWith("//"))
				{
					continue; // skip single line comments. TODO: add support for multiple line comments
				}
				
				Matcher m;
				if ((m = functionPattern.matcher(line)).find())
				{
					String name = m.group(4);
					String returnType = m.group(6);
					BcFunctionDeclaration bcFunc = new BcFunctionDeclaration(name);
					if (!returnType.equals("void"))
					{
						bcFunc.setReturnType(BcTypeNode.create(returnType));
					}
					if (m.group(2) != null)
					{
						bcFunc.setGetter();
					}
					else if (m.group(3) != null)
					{
						bcFunc.setSetter();
					}
					
					String args = m.group(5);
					m = argPattern.matcher(args);
					while (m.find())
					{
						String argName = m.group(1);
						String argType = m.group(2);
						bcFunc.addParam(new BcFuncParam(BcTypeNode.create(argType), argName));
					}
					
					bcClass = checkForTopLevelDummy(bcClass);
					bcClass.add(bcFunc);
				}
				else if ((m = fieldPattern.matcher(line)).find())
				{
					
					String fieldName = m.group(3);
					String fieldType = m.group(4);
					boolean isConst = m.group(2) != null;
					
					BcVariableDeclaration bcVar = new BcVariableDeclaration(BcTypeNode.create(fieldType), fieldName);
					bcVar.setConst(isConst);
					
					bcClass = checkForTopLevelDummy(bcClass);
					bcClass.add(bcVar);
				}
				else if ((m = classPattern.matcher(line)).find())
				{
					boolean isInterface = m.group(2) != null;
					
					String className = m.group(3);
					String classExtendsName = m.group(5);
					
					BcTypeNode classType = BcTypeNode.create(className);
					bcClass = isInterface ? new BcInterfaceDefinitionNode(classType) : new BcClassDefinitionNode(classType);					
					if (classExtendsName != null)
					{
						bcClass.setExtendsType(BcTypeNode.create(classExtendsName));
					}
				}
			}
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
		return bcClass;
	}

	private static BcClassDefinitionNode checkForTopLevelDummy(BcClassDefinitionNode bcClass)
	{
		if (bcClass == null)
		{
			if (dummyTopLevelClass == null) dummyTopLevelClass = new BcClassDefinitionNode(BcTypeNode.create(TOPLEVEL_DUMMY_CLASS_NAME, false));
			return dummyTopLevelClass;
		}
		return bcClass;
	}
}
