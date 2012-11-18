package bc.help;

import java.util.List;
import java.util.Map;

import macromedia.asc.parser.DefinitionNode;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcClassList;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcImportList;
import bc.lang.BcMetadata;
import bc.lang.BcVariableDeclaration;

public class BcGlobal
{
	public static String lastBcPath;
	public static String lastBcPackageName;
	
	public static BcClassDefinitionNode lastBcClass;
	public static BcFunctionDeclaration lastBcFunction;
	public static BcImportList lastBcImportList;
	
	public static List<BcVariableDeclaration> declaredVars;
	
	public static BcClassList bcPlatformClasses;
	public static BcClassList bcApiClasses;
	public static BcClassList bcClasses;
	
	public static Map<DefinitionNode, BcMetadata> bcMetadataMap;
	
	public static void clean()
	{
		lastBcPath = null;
		lastBcPackageName = null;
		lastBcClass = null;
		lastBcFunction = null;
		lastBcImportList = null;
		declaredVars = null;
		bcClasses = null;
	}

	public static void addGlobalFunction(BcFunctionDeclaration bcFunc) 
	{
		if (bcClasses == null)
		{
			throw new IllegalStateException("No class list selected. Unable to add global function: " + bcFunc);
		}

		bcClasses.addGlobalFunction(bcFunc);
	}

	public static BcFunctionDeclaration findGlobalFunction(String name)
	{
		if (bcClasses == null)
		{
			throw new IllegalStateException("No class list selected. Unable to find global function: " + name);
		}
		
		BcFunctionDeclaration bcFunc;
		if ((bcFunc = findGlobalFunction(bcClasses, name)) != null)
		{
			return bcFunc;
		}
		
		if (bcApiClasses != bcClasses)
		{
			if ((bcFunc = findGlobalFunction(bcApiClasses, name)) != null)
			{
				return bcFunc;
			}
		}
		
		if (bcPlatformClasses != bcClasses)
		{
			if ((bcFunc = findGlobalFunction(bcPlatformClasses, name)) != null)
			{
				return bcFunc;
			}
		}
		
		return null;
	}
	
	public static BcFunctionDeclaration findGlobalFunction(BcClassList classList, String name) 
	{
		List<BcFunctionDeclaration> globalFunctions = classList.getGlobalFunctions();
		for (BcFunctionDeclaration bcFunc : globalFunctions)
		{
			if (bcFunc.getName().equals(name))
			{
				return bcFunc;
			}
		}
		return null;
	}
}
