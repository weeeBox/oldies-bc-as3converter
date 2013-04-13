package bc.help;

import java.util.ArrayList;
import java.util.List;

import macromedia.asc.parser.DefinitionNode;
import macromedia.asc.parser.Node;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcClassList;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcImportList;
import bc.lang.BcMetadata;
import bc.lang.BcVariableDeclaration;

// TODO: make it into a context class
public class BcGlobal
{
	public static String lastBcPath;
	public static String lastBcPackageName;
	
	public static BcClassDefinitionNode lastBcClass;
	public static BcFunctionDeclaration lastBcFunction;
	public static BcImportList lastBcImportList;
	
	public static List<BcFunctionDeclaration> enclosingFunctionStack = new ArrayList<BcFunctionDeclaration>();
	public static List<BcVariableDeclaration> declaredVars;
	
	public static BcClassList bcPlatformClasses;
	public static BcClassList bcApiClasses;
	public static BcClassList bcClasses;
	
	public static void clean()
	{
		lastBcPath = null;
		lastBcPackageName = null;
		lastBcClass = null;
		lastBcFunction = null;
		lastBcImportList = null;
		declaredVars = null;
		bcClasses = null;
		
		enclosingFunctionStack.clear();
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
		
		if ((bcFunc = findBuiltinGlobalFunction(bcApiClasses, name)) != null)
		{
			return bcFunc;
		}
		
		return findBuiltinGlobalFunction(bcPlatformClasses, name);
	}

	private static BcFunctionDeclaration findBuiltinGlobalFunction(BcClassList classes, String name) 
	{
		BcFunctionDeclaration bcFunc;
		if (classes != null && classes != bcClasses)
		{
			if ((bcFunc = findGlobalFunction(classes, name)) != null)
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

	public static void addMetadata(DefinitionNode def, BcMetadata metadata) 
	{
		if (bcClasses == null)
		{
			throw new IllegalStateException("No class list selected. Unable to add metadata: " + metadata);
		}
		
		bcClasses.addMetadata(def, metadata);
	}

	public static BcMetadata findMetadata(Node node) 
	{
		if (bcClasses == null)
		{
			throw new IllegalStateException("No class list selected. Unable to find metadata: " + node);
		}
		
		BcMetadata metadata;
		if ((metadata = bcClasses.findMetadata(node)) != null)
		{
			return metadata;
		}
		
		if ((metadata = findMetadata(bcApiClasses, node)) != null)
		{
			return metadata;
		}
		
		return findMetadata(bcPlatformClasses, node);
	}

	private static BcMetadata findMetadata(BcClassList classes, Node node) 
	{
		BcMetadata metadata;
		if (classes != null && classes != bcClasses)
		{
			if ((metadata = classes.findMetadata(node)) != null)
			{
				return metadata;
			}
		}
		
		return null;
	}
	
	public static void setFunction(BcFunctionDeclaration func)
	{
		lastBcFunction = func;
		enclosingFunctionStack.clear();
	}
	
	public static void pushFunction(BcFunctionDeclaration func)
	{
		if (lastBcFunction != null)
		{
			enclosingFunctionStack.add(lastBcFunction);
		}
		lastBcFunction = func;
	}
	
	public static void popFunction()
	{
		assert enclosingFunctionStack.size() > 0;
		lastBcFunction = enclosingFunctionStack.remove(enclosingFunctionStack.size()-1);
	}
}
