package bc.converter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import macromedia.asc.parser.ApplyTypeExprNode;
import macromedia.asc.parser.ArgumentListNode;
import macromedia.asc.parser.BinaryExpressionNode;
import macromedia.asc.parser.BreakStatementNode;
import macromedia.asc.parser.CallExpressionNode;
import macromedia.asc.parser.CaseLabelNode;
import macromedia.asc.parser.CatchClauseNode;
import macromedia.asc.parser.ClassDefinitionNode;
import macromedia.asc.parser.CoerceNode;
import macromedia.asc.parser.ConditionalExpressionNode;
import macromedia.asc.parser.ContinueStatementNode;
import macromedia.asc.parser.DefinitionNode;
import macromedia.asc.parser.DeleteExpressionNode;
import macromedia.asc.parser.DoStatementNode;
import macromedia.asc.parser.ExpressionStatementNode;
import macromedia.asc.parser.FinallyClauseNode;
import macromedia.asc.parser.ForStatementNode;
import macromedia.asc.parser.FunctionCommonNode;
import macromedia.asc.parser.FunctionDefinitionNode;
import macromedia.asc.parser.FunctionNameNode;
import macromedia.asc.parser.GetExpressionNode;
import macromedia.asc.parser.HasNextNode;
import macromedia.asc.parser.IdentifierNode;
import macromedia.asc.parser.IfStatementNode;
import macromedia.asc.parser.ImportDirectiveNode;
import macromedia.asc.parser.IncrementNode;
import macromedia.asc.parser.InterfaceDefinitionNode;
import macromedia.asc.parser.ListNode;
import macromedia.asc.parser.LiteralArrayNode;
import macromedia.asc.parser.LiteralBooleanNode;
import macromedia.asc.parser.LiteralFieldNode;
import macromedia.asc.parser.LiteralNullNode;
import macromedia.asc.parser.LiteralNumberNode;
import macromedia.asc.parser.LiteralObjectNode;
import macromedia.asc.parser.LiteralRegExpNode;
import macromedia.asc.parser.LiteralStringNode;
import macromedia.asc.parser.LiteralVectorNode;
import macromedia.asc.parser.LiteralXMLNode;
import macromedia.asc.parser.LoadRegisterNode;
import macromedia.asc.parser.MemberExpressionNode;
import macromedia.asc.parser.MetaDataNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.PackageDefinitionNode;
import macromedia.asc.parser.PackageIdentifiersNode;
import macromedia.asc.parser.PackageNameNode;
import macromedia.asc.parser.ParameterListNode;
import macromedia.asc.parser.ParameterNode;
import macromedia.asc.parser.Parser;
import macromedia.asc.parser.ProgramNode;
import macromedia.asc.parser.QualifiedIdentifierNode;
import macromedia.asc.parser.ReturnStatementNode;
import macromedia.asc.parser.SelectorNode;
import macromedia.asc.parser.SetExpressionNode;
import macromedia.asc.parser.StatementListNode;
import macromedia.asc.parser.StoreRegisterNode;
import macromedia.asc.parser.SuperExpressionNode;
import macromedia.asc.parser.SuperStatementNode;
import macromedia.asc.parser.SwitchStatementNode;
import macromedia.asc.parser.ThisExpressionNode;
import macromedia.asc.parser.ThrowStatementNode;
import macromedia.asc.parser.Tokens;
import macromedia.asc.parser.TryStatementNode;
import macromedia.asc.parser.UnaryExpressionNode;
import macromedia.asc.parser.VariableBindingNode;
import macromedia.asc.parser.VariableDefinitionNode;
import macromedia.asc.parser.WhileStatementNode;
import macromedia.asc.util.Context;
import macromedia.asc.util.ContextStatics;
import macromedia.asc.util.ObjectList;
import bc.code.ListWriteDestination;
import bc.code.WriteDestination;
import bc.error.ConverterException;
import bc.error.NotImplementedException;
import bc.help.BcCodeHelper;
import bc.help.BcGlobal;
import bc.help.BcNodeHelper;
import bc.help.BcStringUtils;
import bc.lang.BcArgumentsList;
import bc.lang.BcArgumentsType;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcDeclaration;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcFunctionTypeNode;
import bc.lang.BcGenericTypeNode;
import bc.lang.BcImportList;
import bc.lang.BcInterfaceDefinitionNode;
import bc.lang.BcMetadata;
import bc.lang.BcMetadataNode;
import bc.lang.BcModuleDeclarationEntry;
import bc.lang.BcModuleEntry;
import bc.lang.BcNullType;
import bc.lang.BcRestTypeNode;
import bc.lang.BcTypeName;
import bc.lang.BcTypeNode;
import bc.lang.BcTypeNodeInstance;
import bc.lang.BcUndefinedType;
import bc.lang.BcVariableDeclaration;
import bc.lang.BcVectorTypeNode;
import bc.lang.BcWildcardTypeNode;
import bc.preprocessor.Preprocessor;
import bc.preprocessor.PreprocessorException;
import bc.utils.filesystem.FileUtils;

public abstract class As2WhateverConverter
{
	protected static final String SECTION_PLATFORM = "Platform";
	protected static final String SECTION_API = "Api";
	protected static final String SECTION_CONVERTED = "Converted";
	
	private WriteDestination dest;
	private Stack<WriteDestination> destStack;

	protected static final String internalFieldInitializer = "__internalInitializeFields";

	protected boolean needFieldsInitializer;
	private BcCodeHelper codeHelper;

	protected abstract void writeForeach(WriteDestination dest, Object loopVarName, BcTypeNodeInstance loopVarTypeInstance, Object collection, BcTypeNodeInstance collectionTypeInstance, Object body);

	public As2WhateverConverter(BcCodeHelper codeHelper)
	{
		BcGlobal.bcGlobalFunctions = new ArrayList<BcFunctionDeclaration>();
		BcGlobal.bcMetadataMap = new HashMap<DefinitionNode, BcMetadata>();

		this.codeHelper = codeHelper;
	}

	public void convert(File outputDir, String... filenames) throws IOException
	{
		BcGlobal.bcPlatformClasses = collect("bc-platform/src");
		BcGlobal.bcApiClasses = collect("bc-api/src");
		BcGlobal.bcClasses = collect(filenames);
		BcGlobal.lastBcPath = null;

		process();

		write(new File(outputDir, SECTION_PLATFORM), BcGlobal.bcPlatformClasses);
		write(new File(outputDir, SECTION_API), BcGlobal.bcApiClasses);
		write(new File(outputDir, SECTION_CONVERTED), BcGlobal.bcClasses);
		
		postWrite(outputDir);
	}

	private List<BcClassDefinitionNode> collect(String... filenames) throws IOException
	{
		// hack: set empty import list to avoid crashes while creating types
		BcGlobal.lastBcImportList = new BcImportList();

		// first we collect and register all the top level types (with qualified names)
		List<BcModuleEntry> modules = new ArrayList<BcModuleEntry>();
		for (int i = 0; i < filenames.length; ++i)
		{
			collectModules(new File(filenames[i]), modules);
		}

		BcGlobal.lastBcImportList = null;

		// then we collect the stuff as usual
		// probably not a good idea, but who cares
		BcGlobal.bcClasses = new ArrayList<BcClassDefinitionNode>();

		for (BcModuleEntry module : modules)
		{
			collect(module);
		}

		return BcGlobal.bcClasses;
	}

	private void collectModules(File file, List<BcModuleEntry> modules) throws IOException
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles(new FileFilter()
			{
				@Override
				public boolean accept(File pathname)
				{
					String filename = pathname.getName();
					if (pathname.isDirectory())
						return !filename.equals(".svn") && !filename.equals(".git");

					return filename.endsWith(".as");
				}
			});

			for (File child : files)
			{
				collectModules(child, modules);
			}
		}
		else
		{
			modules.add(collectModule(file));
		}
	}

	private BcModuleEntry collectModule(File file) throws IOException
	{
		ContextStatics statics = new ContextStatics();
		Context cx = new Context(statics);
		
		InputStream in = preprocess(file);
		Parser parser = new Parser(cx, in, file.getAbsolutePath());

		ProgramNode programNode = parser.parseProgram();
		in.close();
		
		List<BcModuleDeclarationEntry> entries = new ArrayList<BcModuleDeclarationEntry>();
		
		BcImportList importList = new BcImportList();
		String modulePackageName = null;
		
		Map<DefinitionNode, BcMetadata> metadataMap = new HashMap<DefinitionNode, BcMetadata>();

		ObjectList<Node> items = programNode.statements.items;
		for (Node node : items)
		{
			if (node instanceof InterfaceDefinitionNode)
			{
				InterfaceDefinitionNode interfaceDefinitionNode = (InterfaceDefinitionNode) node;
				BcMetadata metadata = metadataMap.get(interfaceDefinitionNode);
				if (shouldIgnoreDeclaration(metadata))
				{
					continue;
				}
				
				String interfaceDeclaredName = getCodeHelper().extractIdentifier(interfaceDefinitionNode.name);
				String packageName = BcNodeHelper.tryExtractPackageName(interfaceDefinitionNode);
				if (packageName == null)
				{
					packageName = modulePackageName;
					failConversionUnless(packageName != null, "Can't detect interface package: '%s'", interfaceDeclaredName);
				}
				
				BcTypeNode interfaceType = createBcType(interfaceDeclaredName, packageName);
				BcInterfaceDefinitionNode bcInterface = new BcInterfaceDefinitionNode(interfaceType);
				
				bcInterface.setPackageName(packageName);
				bcInterface.setImportList(importList);
				bcInterface.setMetadata(metadata);
				
				entries.add(new BcModuleDeclarationEntry(bcInterface, interfaceDefinitionNode));
			}
			else if (node instanceof ClassDefinitionNode)
			{
				ClassDefinitionNode classDefinitionNode = (ClassDefinitionNode) node;
				BcMetadata metadata = metadataMap.get(classDefinitionNode);
				if (shouldIgnoreDeclaration(metadata))
				{
					continue;
				}
				
				String classDeclaredName = getCodeHelper().extractIdentifier(classDefinitionNode.name);
				String packageName = BcNodeHelper.tryExtractPackageName(classDefinitionNode);
				if (packageName == null)
				{
					packageName = modulePackageName;
					failConversionUnless(packageName != null, "Can't detect class package: '%s'", classDeclaredName);
				}
				
				BcTypeNode classType = createBcType(classDeclaredName, packageName);
				BcClassDefinitionNode bcClass = new BcClassDefinitionNode(classType);
				bcClass.setFinal(BcNodeHelper.isFinal(classDefinitionNode));
				
				bcClass.setPackageName(packageName);
				bcClass.setImportList(importList);
				
				// collect metadata
				bcClass.setMetadata(metadata);				
				
				entries.add(new BcModuleDeclarationEntry(bcClass, classDefinitionNode));
			}
			else if (node instanceof MetaDataNode)
			{
				MetaDataNode metadata = (MetaDataNode) node;
				if (metadata.def != null)
				{
					BcMetadata bcMetadata = BcNodeHelper.extractBcMetadata(metadata);
					failConversionUnless(bcMetadata != null, "Can't parse top level metadata");

					metadataMap.put(metadata.def, bcMetadata);
				}
			}
			else if (node instanceof ImportDirectiveNode)
			{
				ImportDirectiveNode importNode = (ImportDirectiveNode) node;

				PackageNameNode packageNameNode = importNode.name;
				failConversionUnless(packageNameNode != null, "Error while parsing import directive: packageNameNode is null");

				PackageIdentifiersNode packageIdentifierNode = packageNameNode.id;
				failConversionUnless(packageIdentifierNode != null, "Error while parsing import directive: packageIdentifierNode is null");

				String typeName = packageIdentifierNode.def_part;
				String packageName = BcNodeHelper.safeQualifier(packageIdentifierNode.pkg_part);

				importList.add(typeName, packageName);
			}
			else if (node instanceof PackageDefinitionNode)
			{
				PackageDefinitionNode pkgNode = (PackageDefinitionNode) node;
				String oldModulePackage = modulePackageName;
				modulePackageName = BcNodeHelper.tryExtractPackageName(pkgNode.name);
				failConversionUnless(oldModulePackage == null || oldModulePackage.equals(modulePackageName), "Conflicting package declarations: '%s' '%s'", oldModulePackage, modulePackageName);
			}
			else if (node instanceof FunctionDefinitionNode)
			{
				FunctionDefinitionNode functionDefinitionNode = (FunctionDefinitionNode) node;
				BcMetadata metadata = metadataMap.get(functionDefinitionNode);
				if (shouldIgnoreDeclaration(metadata))
				{
					continue;
				}
				
				FunctionNameNode functionNameNode = functionDefinitionNode.name;
				String name = getCodeHelper().extractIdentifier(functionNameNode.identifier);
				BcFunctionDeclaration bcFunc = new BcFunctionDeclaration(name);

				String typeString = BcNodeHelper.tryExtractFunctionType(functionDefinitionNode);
				if (typeString != null)
				{
					if (typeString.equals("virtual"))
					{
						bcFunc.setVirtual();
					}
					else if (typeString.equals("override"))
					{
						bcFunc.setOverride();
					}
				}
				
				if (functionNameNode.kind == Tokens.GET_TOKEN)
				{
					bcFunc.setGetter();
				}
				else if (functionNameNode.kind == Tokens.SET_TOKEN)
				{
					bcFunc.setSetter();
				}
				
				bcFunc.setModifiers(BcNodeHelper.extractModifiers(functionDefinitionNode.attrs));
				bcFunc.setGlobal();
				bcFunc.setImportList(importList);
				bcFunc.setMetadata(metadata);
				
				entries.add(new BcModuleDeclarationEntry(bcFunc, functionDefinitionNode));
			}
		}

		return new BcModuleEntry(file, entries);
	}

	private void collect(BcModuleEntry module) throws IOException
	{
		File file = module.getFile();

		BcGlobal.lastBcPath = file.getPath();

		List<BcModuleDeclarationEntry> entries = module.getEntries();
		for (BcModuleDeclarationEntry entry : entries)
		{
			BcDeclaration declaration = entry.getDeclaration();
			DefinitionNode definition = entry.getDefinition();
			
			BcGlobal.lastBcImportList = declaration.getImportList();
			
			if (definition instanceof InterfaceDefinitionNode)
			{
				BcGlobal.bcClasses.add(collect((InterfaceDefinitionNode) definition, (BcInterfaceDefinitionNode) declaration));
			}
			else if (definition instanceof ClassDefinitionNode)
			{
				BcGlobal.bcClasses.add(collect((ClassDefinitionNode) definition, (BcClassDefinitionNode) declaration));
			}
			else if (definition instanceof FunctionDefinitionNode)
			{
				BcGlobal.bcGlobalFunctions.add(collect((FunctionDefinitionNode) definition, (BcFunctionDeclaration) declaration));
			}
			else
			{
				failConversion("Unexpected definition: %s", definition.getClass());
			}
		}

		BcGlobal.lastBcImportList = null;
		BcGlobal.lastBcPath = null;
	}

	private BcInterfaceDefinitionNode collect(InterfaceDefinitionNode interfaceDefinitionNode, BcInterfaceDefinitionNode bcInterface)
	{
		BcGlobal.declaredVars = bcInterface.getDeclaredVars();
		BcGlobal.lastBcClass = bcInterface;
		BcGlobal.lastBcPackageName = bcInterface.getPackageName();

		if (interfaceDefinitionNode.statements != null)
		{
			ObjectList<Node> items = interfaceDefinitionNode.statements.items;

			// collect the stuff
			for (Node node : items)
			{
				if (node instanceof FunctionDefinitionNode)
				{
					bcInterface.add(collect((FunctionDefinitionNode) node));
				}
				else
				{
					failConversion("Unexpected interface element: " + node.getClass());
				}
			}
		}
		BcGlobal.lastBcClass = null;
		BcGlobal.lastBcPackageName = null;

		return bcInterface;
	}

	private BcClassDefinitionNode collect(ClassDefinitionNode classDefinitionNode, BcClassDefinitionNode bcClass)
	{
		BcGlobal.declaredVars = bcClass.getDeclaredVars();
		BcGlobal.lastBcClass = bcClass;
		BcGlobal.lastBcPackageName = bcClass.getPackageName();

		BcTypeNode classType = bcClass.getClassType();
		
		if (bcClass.hasMetadata())
		{
			collectClassMetadata(bcClass);
		}
		
		// super type
		Node baseclass = classDefinitionNode.baseclass;
		if (baseclass == null)
		{
			BcTypeNode typeObject = createBcType(BcTypeNode.typeObject);
			if (classType != typeObject)
			{
				bcClass.setExtendsType(typeObject.createTypeInstance());
			}
		}
		else
		{
			BcTypeNode bcSuperType = extractBcType(baseclass);
			boolean qualified = isTypeQualified(baseclass);
			bcClass.setExtendsType(bcSuperType.createTypeInstance(qualified));
		}

		// interfaces
		if (classDefinitionNode.interfaces != null)
		{
			for (Node interfaceNode : classDefinitionNode.interfaces.items)
			{
				BcTypeNode interfaceType = extractBcType(interfaceNode);
				boolean qualified = isTypeQualified(interfaceNode);
				bcClass.addInterface(interfaceType.createTypeInstance(qualified));
			}
		}

		// collect members
		ObjectList<Node> items = classDefinitionNode.statements.items;
		for (Node node : items)
		{
			if (node instanceof FunctionDefinitionNode)
			{
				bcClass.add(collect((FunctionDefinitionNode) node));
			}
			else if (node instanceof VariableDefinitionNode)
			{
				bcClass.add(collect((VariableDefinitionNode) node));
			}
			else if (node instanceof MetaDataNode)
			{
				MetaDataNode metadata = (MetaDataNode) node;
				if (metadata.def != null)
				{
					BcMetadata bcMetadata = BcNodeHelper.extractBcMetadata(metadata);
					failConversionUnless(bcMetadata != null, "Failed to extract metadata");

					BcGlobal.bcMetadataMap.put(metadata.def, bcMetadata);
				}
			}
			else if (node instanceof ExpressionStatementNode)
			{
				failConversion("Unsupported top level expression found");
			}
			else
			{
				failConversion("Unexpected top level class element found: " + node.getClass());
			}
		}

		BcGlobal.lastBcClass = null;
		BcGlobal.lastBcPackageName = null;
		BcGlobal.declaredVars = null;

		return bcClass;
	}
	
	private BcFunctionDeclaration collect(FunctionDefinitionNode functionDefinitionNode, BcFunctionDeclaration bcFunc)
	{
		List<BcFunctionTypeNode> functionTypes = null;
		BcMetadata funcMetadata = bcFunc.getMetadata();
		if (funcMetadata != null)
		{
			functionTypes = extractFunctionTypes(funcMetadata);
		}

		// get function params
		ParameterListNode parameterNode = functionDefinitionNode.fexpr.signature.parameter;
		if (parameterNode != null)
		{
			ObjectList<ParameterNode> params = parameterNode.items;
			for (ParameterNode param : params)
			{
				BcTypeNode paramType = extractBcType(param);
				boolean qualified = isTypeQualified(param);

				String paramName = param.identifier.name;

				// search for func param in metadata
				if (paramType instanceof BcFunctionTypeNode && functionTypes != null)
				{
					if (functionTypes.size() > 1)
					{
						for (BcFunctionTypeNode funcType : functionTypes)
						{
							if (funcType.hasAttachedParam() && funcType.getAttachedParam().equals(paramName))
							{
								paramType = funcType;
								break;
							}
						}
					}
					else
					{
						paramType = functionTypes.get(0);
					}

					BcFunctionTypeNode funcType = (BcFunctionTypeNode) paramType;
					failConversionUnless(funcType.isComplete(), "Function param '%s' is incomplete. Please provide function metadata or global function metadata", paramName);
				}

				BcFuncParam bcParam = new BcFuncParam(paramType, getCodeHelper().identifier(paramName), qualified);
				if (param.init != null)
				{
					bcParam.setDefaultInitializer(param.init);
				}

				bcFunc.addParam(bcParam);
				bcFunc.getDeclaredVars().add(bcParam);
			}
		}

		// get function return type
		Node returnTypeNode = functionDefinitionNode.fexpr.signature.result;
		if (returnTypeNode != null)
		{
			BcTypeNode bcReturnType = extractBcType(returnTypeNode);
			boolean qualified = isTypeQualified(returnTypeNode);

			bcFunc.setReturnType(bcReturnType.createTypeInstance(qualified));
		}

		bcFunc.setStatements(functionDefinitionNode.fexpr.body);
		return bcFunc;
	}

	protected void collectClassMetadata(BcClassDefinitionNode bcClass)
	{
		BcMetadata bcMetadata = bcClass.getMetadata();
		
		List<BcFunctionTypeNode> functionTypes = extractFunctionTypes(bcMetadata);
		for (BcFunctionTypeNode funcType : functionTypes)
		{
			bcClass.addFunctionType(funcType);
		}
	}

	private BcVariableDeclaration collect(VariableDefinitionNode node)
	{
		failConversionUnless(node.list.items.size() == 1, "Node list items size should be 1: %d", node.list.items.size());
		VariableBindingNode varBindNode = (VariableBindingNode) node.list.items.get(0);

		BcTypeNode bcType = extractBcType(varBindNode.variable);
		boolean qualified = isTypeQualified(varBindNode.variable);
		String bcIdentifier = getCodeHelper().extractIdentifier(varBindNode.variable.identifier);
		BcVariableDeclaration bcVar = new BcVariableDeclaration(bcType, bcIdentifier, qualified);
		bcVar.setConst(node.kind == Tokens.CONST_TOKEN);
		bcVar.setModifiers(BcNodeHelper.extractModifiers(varBindNode.attrs));

		bcVar.setInitializerNode(varBindNode.initializer);

		BcGlobal.declaredVars.add(bcVar);

		return bcVar;
	}

	private BcFunctionDeclaration collect(FunctionDefinitionNode functionDefinitionNode)
	{
		FunctionNameNode functionNameNode = functionDefinitionNode.name;
		String name = getCodeHelper().extractIdentifier(functionNameNode.identifier);
		BcFunctionDeclaration bcFunc = new BcFunctionDeclaration(name);

		BcMetadata funcMetadata = findMetadata(functionDefinitionNode);
		List<BcFunctionTypeNode> functionTypes = null;
		if (funcMetadata != null)
		{
			bcFunc.setMetadata(funcMetadata);
			functionTypes = extractFunctionTypes(funcMetadata);
		}

		String typeString = BcNodeHelper.tryExtractFunctionType(functionDefinitionNode);
		if (typeString != null)
		{
			if (typeString.equals("virtual"))
			{
				bcFunc.setVirtual();
			}
			else if (typeString.equals("override"))
			{
				bcFunc.setOverride();
			}
		}

		if (functionNameNode.kind == Tokens.GET_TOKEN)
		{
			bcFunc.setGetter();
		}
		else if (functionNameNode.kind == Tokens.SET_TOKEN)
		{
			bcFunc.setSetter();
		}

		boolean isConstructor = BcGlobal.lastBcClass != null && name.equals(BcGlobal.lastBcClass.getName());
		bcFunc.setConstructorFlag(isConstructor);

		bcFunc.setModifiers(BcNodeHelper.extractModifiers(functionDefinitionNode.attrs));

		// get function params
		ParameterListNode parameterNode = functionDefinitionNode.fexpr.signature.parameter;
		if (parameterNode != null)
		{
			ObjectList<ParameterNode> params = parameterNode.items;
			for (ParameterNode param : params)
			{
				BcTypeNode paramType = extractBcType(param);
				boolean qualified = isTypeQualified(param);

				String paramName = param.identifier.name;

				// search for func param in metadata
				if (paramType instanceof BcFunctionTypeNode && functionTypes != null)
				{
					if (functionTypes.size() > 1)
					{
						for (BcFunctionTypeNode funcType : functionTypes)
						{
							if (funcType.hasAttachedParam() && funcType.getAttachedParam().equals(paramName))
							{
								paramType = funcType;
								break;
							}
						}
					}
					else
					{
						paramType = functionTypes.get(0);
					}

					BcFunctionTypeNode funcType = (BcFunctionTypeNode) paramType;
					failConversionUnless(funcType.isComplete(), "Function param '%s' is incomplete. Please provide function metadata or global function metadata", paramName);
				}

				BcFuncParam bcParam = new BcFuncParam(paramType, getCodeHelper().identifier(paramName), qualified);
				if (param.init != null)
				{
					bcParam.setDefaultInitializer(param.init);
				}

				bcFunc.addParam(bcParam);
				bcFunc.getDeclaredVars().add(bcParam);
			}
		}

		// get function return type
		Node returnTypeNode = functionDefinitionNode.fexpr.signature.result;
		if (returnTypeNode != null)
		{
			BcTypeNode bcReturnType = extractBcType(returnTypeNode);
			boolean qualified = isTypeQualified(returnTypeNode);

			bcFunc.setReturnType(bcReturnType.createTypeInstance(qualified));
		}

		bcFunc.setStatements(functionDefinitionNode.fexpr.body);
		return bcFunc;
	}

	private void process()
	{
		Collection<BcTypeNode> values = BcTypeNode.uniqueTypes.values();
		for (BcTypeNode type : values)
		{
			process(type);
		}

		process(new ArrayList<BcClassDefinitionNode>(BcGlobal.bcPlatformClasses));
		process(BcGlobal.bcApiClasses);
		process(BcGlobal.bcClasses);
		
		postProcess(BcGlobal.bcPlatformClasses);
		postProcess(BcGlobal.bcApiClasses);
		postProcess(BcGlobal.bcClasses);
	}

	private void process(List<BcClassDefinitionNode> classes)
	{
		for (BcClassDefinitionNode bcClass : classes)
		{
			bcMembersTypesStack = new Stack<BcTypeNode>();
			dest = new ListWriteDestination();
			destStack = new Stack<WriteDestination>();

			if (bcClass.isInterface())
			{
				process((BcInterfaceDefinitionNode) bcClass);
			}
			else
			{
				process(bcClass);
			}
		}
	}

	private void process(BcInterfaceDefinitionNode bcInterface)
	{
		BcGlobal.declaredVars = bcInterface.getDeclaredVars();
	}

	private void process(BcClassDefinitionNode bcClass)
	{
		System.out.println("Process: " + bcClass.getName());

		BcGlobal.declaredVars = bcClass.getDeclaredVars();
		BcGlobal.lastBcClass = bcClass;
		BcGlobal.lastBcPackageName = bcClass.getPackageName();
		BcGlobal.lastBcImportList = bcClass.getImportList();

		failConversionUnless(BcGlobal.lastBcImportList != null, "Class import list is 'null'. Class: '%s'", bcClass.getName());

		List<BcVariableDeclaration> fields = bcClass.getFields();
		for (BcVariableDeclaration bcField : fields)
		{
			process(bcField);
		}

		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			process(bcFunc, bcClass);
		}

		BcGlobal.lastBcClass = null;
		BcGlobal.lastBcPackageName = null;
		BcGlobal.lastBcImportList = null;
		BcGlobal.declaredVars = null;
	}

	private void process(BcVariableDeclaration bcVar)
	{
		BcTypeNodeInstance varTypeInstance = bcVar.getTypeInstance();
		BcTypeNode varType = varTypeInstance.getType();

		String varId = bcVar.getIdentifier();

		dest.write(varDecl(varTypeInstance, varId));

		Node initializer = bcVar.getInitializerNode();
		if (initializer != null)
		{
			ListWriteDestination initializerDest = new ListWriteDestination();
			pushDest(initializerDest);
			process(initializer);
			popDest();

			bcVar.setInitializer(initializerDest);
			bcVar.setIntegralInitializerFlag(BcNodeHelper.isIntegralLiteralNode(initializer));

			BcTypeNode initializerType = evaluateType(initializer);
			failConversionUnless(initializerType != null, "Unable to evaluate initializer type: %s = %s", varDecl(varTypeInstance, varId), initializerDest);

			if (needExplicitCast(initializerType, varType))
			{
				dest.write(" = " + cast(initializerDest, initializerType, varType));
			}
			else
			{
				dest.write(" = " + initializerDest);
			}
		}
		dest.writeln(";");
	}

	private void process(Node node)
	{
		failConversionUnless(node != null, "Tried to process 'null' node");

		if (node instanceof MemberExpressionNode)
			process((MemberExpressionNode) node);
		else if (node instanceof SelectorNode)
			process((SelectorNode) node);
		else if (node instanceof IdentifierNode)
			process((IdentifierNode) node);
		else if (node instanceof ExpressionStatementNode)
			process((ExpressionStatementNode) node);
		else if (node instanceof VariableDefinitionNode)
			process((VariableDefinitionNode) node);
		else if (node instanceof LiteralNumberNode ||
				node instanceof LiteralBooleanNode ||
				node instanceof LiteralNullNode ||
				node instanceof LiteralObjectNode ||
				node instanceof LiteralStringNode ||
				node instanceof LiteralRegExpNode ||
				node instanceof LiteralArrayNode ||
				node instanceof LiteralVectorNode ||
				node instanceof LiteralXMLNode)
			processLiteral(node);
		else if (node instanceof BinaryExpressionNode)
			process((BinaryExpressionNode) node);
		else if (node instanceof UnaryExpressionNode)
			process((UnaryExpressionNode) node);
		else if (node instanceof IfStatementNode)
			process((IfStatementNode) node);
		else if (node instanceof ConditionalExpressionNode)
			process((ConditionalExpressionNode) node);
		else if (node instanceof WhileStatementNode)
			process((WhileStatementNode) node);
		else if (node instanceof ForStatementNode)
			process((ForStatementNode) node);
		else if (node instanceof DoStatementNode)
			process((DoStatementNode) node);
		else if (node instanceof SwitchStatementNode)
			process((SwitchStatementNode) node);
		else if (node instanceof TryStatementNode)
			process((TryStatementNode) node);
		else if (node instanceof CatchClauseNode)
			process((CatchClauseNode) node);
		else if (node instanceof FinallyClauseNode)
			process((FinallyClauseNode) node);
		else if (node instanceof ParameterNode)
			process((ParameterNode) node);
		else if (node instanceof ReturnStatementNode)
			process((ReturnStatementNode) node);
		else if (node instanceof BreakStatementNode)
			process((BreakStatementNode) node);
		else if (node instanceof ContinueStatementNode)
			process((ContinueStatementNode) node);
		else if (node instanceof ThisExpressionNode)
			process((ThisExpressionNode) node);
		else if (node instanceof SuperExpressionNode)
			process((SuperExpressionNode) node);
		else if (node instanceof ThrowStatementNode)
			process((ThrowStatementNode) node);
		else if (node instanceof SuperStatementNode)
			process((SuperStatementNode) node);
		else if (node instanceof ListNode)
			process((ListNode) node);
		else if (node instanceof StatementListNode)
			process((StatementListNode) node);
		else if (node instanceof ArgumentListNode)
			process((ArgumentListNode) node);
		else if (node instanceof FunctionCommonNode)
			process((FunctionCommonNode) node);
		else if (node instanceof StoreRegisterNode)
			process((StoreRegisterNode) node);
		else if (node instanceof LoadRegisterNode)
			process((LoadRegisterNode) node);
		else
			failConversion("Unsupported node class: %s", node.getClass());
	}

	private void process(StatementListNode statementsNode)
	{
		writeBlockOpen(dest);

		ObjectList<Node> items = statementsNode.items;
		Iterator<Node> iter = items.iterator();

		while (iter.hasNext())
		{
			Node node = iter.next();
			if (node instanceof MetaDataNode)
			{
				BcNodeHelper.extractBcMetadata((MetaDataNode) node);

				failConversionUnless(iter.hasNext(), "Error processing metadata");
				process(iter.next());
			}
			else
			{
				process(node);
			}
		}

		writeBlockClose(dest);
	}

	private void process(ArgumentListNode node)
	{
		int itemIndex = 0;
		for (Node arg : node.items)
		{
			process(arg);

			if (++itemIndex < node.items.size())
			{
				dest.write(", ");
			}
		}
	}
	
	private void process(StoreRegisterNode node)
	{
		process(node.expr);
	}
	
	private void process(LoadRegisterNode node)
	{
		// do nothing
	}

	private void process(FunctionCommonNode node)
	{
		failConversion("Unexpected function common node");
	}

	private List<BcVariableDeclaration> process(VariableDefinitionNode node)
	{
		ObjectList<Node> varBindings = node.list.items;
		List<BcVariableDeclaration> vars = new ArrayList<BcVariableDeclaration>(varBindings.size());
		
		for (Node varNode : varBindings)
		{
			if (varNode instanceof VariableBindingNode)
			{			
				BcVariableDeclaration varDeclaration = process((VariableBindingNode) varNode);
				vars.add(varDeclaration);
			}
			else
			{
				failConversion("Unexpected node in variable definition: %s", varNode.getClass());
			}			
		}		
		
		return vars;
	}

	// dirty hack: we need to check the recursion depth

	private BcTypeNode lastBcMemberType;
	private Stack<BcTypeNode> bcMembersTypesStack;

	private void process(MemberExpressionNode node)
	{
		bcMembersTypesStack.push(lastBcMemberType);
		lastBcMemberType = null;
		BcTypeNode baseType = null;
		boolean staticCall = false;
		boolean numberMemberCall = false; // блинчик-пончик-сюрприз Ж)

		Node base = node.base;
		SelectorNode selector = node.selector;

		// base expression
		ListWriteDestination baseDest = new ListWriteDestination();
		if (base != null)
		{
			pushDest(baseDest);

			ListWriteDestination baseExpr = new ListWriteDestination();
			pushDest(baseExpr);
			process(base);
			popDest();
			
			// handle function type variables
			boolean needFuncType = base instanceof MemberExpressionNode && 
								   ((MemberExpressionNode)base).selector instanceof GetExpressionNode;
			
			baseType = evaluateType(base, needFuncType);
			if (baseType instanceof BcFunctionTypeNode)
			{
				BcFunctionTypeNode funcType = (BcFunctionTypeNode) baseType;
				if (funcType.isGetter()) 
				{
					baseType = funcType.getReturnType();
				}
				else if (selector instanceof CallExpressionNode)
				{
					CallExpressionNode call = (CallExpressionNode) selector;
					IdentifierNode identifier = BcNodeHelper.tryExtractIdentifier(call);
					failConversionUnless(identifier != null, "Cannot extract function type call identifier: %s", funcType);
					
					String funcName = identifier.name;
					if (funcName.equals("apply"))
					{
						Node target = call.args.items.get(0);
						BcTypeNode targetType = evaluateType(target);
						failConversionUnless(targetType != null, "Unable to evaluate 'call' target type: %s", funcType);
						
						boolean hasTarget = !(targetType instanceof BcNullType);
						if (hasTarget)
						{
							base = target;
						}
					}
					else
					{
						failConversion("Unexpected identifier: %s", funcName);
					}
				}
			}
			
			lastBcMemberType = baseType;
			failConversionUnless(lastBcMemberType != null, "Unable to evaluate expression: %s", baseExpr);

			IdentifierNode identifierNode = BcNodeHelper.tryExtractIdentifier(base);
			if (identifierNode != null && canBeClass(identifierNode.name)) // is call?
			{
				staticCall = true;

				
				dest.write(classType(baseExpr.toString()));
			}
			else
			{
				numberMemberCall = typeEquals(baseType, BcTypeNode.typeNumber);
				process(base);
			}

			popDest();
		}

		// selector expression
		ListWriteDestination selectorDest = new ListWriteDestination();
		if (selector != null)
		{
			pushDest(selectorDest);
			process(selector);
			popDest();
		}

		boolean stringCall = false;
		boolean objectAsDictionaryCall = false;

		if (base != null)
		{
			if (selector.getMode() == Tokens.LEFTBRACKET_TOKEN)
			{
				objectAsDictionaryCall = !typeOneOf(baseType, BcTypeNode.typeVector, BcTypeNode.typeDictionary, BcTypeNode.typeArray, BcTypeNode.typeString, BcTypeNode.typeXMLList);
			}

			if (typeEquals(baseType, BcTypeNode.typeString) && getCodeHelper().boolSetting(BcCodeHelper.SETTING_DELEGATE_STRINGS_CALLS))
			{
				String selectorCode = selectorDest.toString();
				stringCall = !selectorCode.equals("ToString()") && !selectorCode.equals("Length");
			}
		}

		if (objectAsDictionaryCall)
		{
			ListWriteDestination exprDest = new ListWriteDestination();
			pushDest(exprDest);
			process(selector.expr);
			popDest();

			if (selector instanceof SetExpressionNode)
			{
				SetExpressionNode setExpr = (SetExpressionNode) selector;

				failConversionUnless(setExpr.args != null, "Set expression with no args: %s", exprDest);

				ListWriteDestination argsDest = new ListWriteDestination();
				pushDest(argsDest);
				process(setExpr.args);
				popDest();

				dest.write(memberCall(baseDest, "setOwnProperty", exprDest, argsDest));
			}
			else if (selector instanceof GetExpressionNode)
			{
				dest.writef(memberCall(baseDest, "getOwnProperty", exprDest));
			}
			else
			{
				failConversion("Unexpected selector for 'object-as-dictionary' call: type=%s expr=%s", selector.getClass(), exprDest);
			}
		}
		else if (stringCall || numberMemberCall)
		{
			failConversionUnless(selector instanceof CallExpressionNode, "'call' expression is expected: type=%s base=%s selecto=%s", selector.getClass(), baseDest, selectorDest);
			CallExpressionNode callExpr = (CallExpressionNode) selector;

			ListWriteDestination argsDest = new ListWriteDestination();
			if (callExpr.args != null)
			{
				pushDest(argsDest);
				process(callExpr.args);
				popDest();
			}

			IdentifierNode funcIndentifierNode = BcNodeHelper.tryExtractIdentifier(selector);
			failConversionUnless(funcIndentifierNode != null, "Unable to extract identifier from: %s", selectorDest);

			String funcName = getCodeHelper().extractIdentifier(funcIndentifierNode);
			if (callExpr.args != null)
			{
				dest.write(staticCall(classType(baseType), funcName, baseDest, argsDest));
			}
			else
			{
				dest.write(staticCall(classType(baseType), funcName, baseDest));
			}
		}
		else
		{
			if (base != null)
			{
				if (selector.getMode() != Tokens.LEFTBRACKET_TOKEN)
				{
					if (staticCall)
					{
						dest.write(staticSelector(baseDest, selectorDest));
					}
					else if (base instanceof ThisExpressionNode)
					{
						failConversionUnless(BcGlobal.lastBcClass != null, "Try to use 'this' without of a class: base=%s selector=%s", baseDest, selectorDest);
						dest.write(thisSelector(BcGlobal.lastBcClass, selectorDest));
					}
					else if (base instanceof SuperExpressionNode)
					{
						failConversionUnless(BcGlobal.lastBcClass != null, "Try to use 'super' without of a class: base=%s selector=%s", baseDest, selectorDest);
						dest.write(superSelector(BcGlobal.lastBcClass, selectorDest));
					}
					else
					{
						dest.write(memberSelector(baseDest, selectorDest));
					}
				}
				else
				{
					dest.write(baseDest);
					dest.write(selectorDest);
				}
			}
			else
			{
				dest.write(selectorDest);
			}
		}

		lastBcMemberType = bcMembersTypesStack.pop();
	}

	private void process(SelectorNode node)
	{
		if (node instanceof DeleteExpressionNode)
			process((DeleteExpressionNode) node);
		else if (node instanceof GetExpressionNode)
			process((GetExpressionNode) node);
		else if (node instanceof CallExpressionNode)
			process((CallExpressionNode) node);
		else if (node instanceof SetExpressionNode)
			process((SetExpressionNode) node);
		else if (node instanceof ApplyTypeExprNode)
			process((ApplyTypeExprNode) node);
		else if (node instanceof IncrementNode)
			process((IncrementNode) node);
		else 
			failConversion("Unexpected selector class: %s", node.getClass());
	}

	private void process(DeleteExpressionNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();

		failConversionUnless(node.getMode() == Tokens.LEFTBRACKET_TOKEN, "LEFTBRACKET_TOKEN expected for 'delete' expression node");
		dest.writef(".remove(%s)", expr); // fix me for member call
	}

	private void process(GetExpressionNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();

		boolean accessingDynamicProperty = false;
		boolean accessingDynamicXMLList = false;
		String identifier = expr.toString();
		boolean getterCalled = false;

		boolean needLocalVars = false;

		if (node.expr instanceof IdentifierNode)
		{
			BcClassDefinitionNode bcClass;
			if (lastBcMemberType != null)
			{
				bcClass = lastBcMemberType.getClassNode();
				failConversionUnless(bcClass != null, "Class type undefined: %s", expr);
			}
			else
			{
				failConversionUnless(BcGlobal.lastBcClass != null, "'get' expression might appear outside of the class: %s", expr);
				bcClass = BcGlobal.lastBcClass;
				needLocalVars = true;
			}

			BcClassDefinitionNode bcStaticClass = findClass(identifier);
			if (bcStaticClass != null)
			{
				lastBcMemberType = bcStaticClass.getClassType();
				failConversionUnless(lastBcMemberType != null, "Static class type undefined: %s", expr);

				addToImport(lastBcMemberType);
			}
			else
			{
				BcVariableDeclaration localVar = needLocalVars ? findLocalVar(identifier) : null;
				if (localVar != null)
				{
					lastBcMemberType = localVar.getType();
					failConversionUnless(lastBcMemberType != null, "Local variable's type undefined: %s" + expr);
				}
				else
				{
					BcFunctionDeclaration bcFunc = bcClass.findGetterFunction(identifier);
					if (bcFunc != null)
					{
						BcTypeNode funcType = bcFunc.getReturnType();
						failConversionUnless(funcType != null, "Function return type undefined: %s", expr);

						lastBcMemberType = funcType;
						if (classEquals(bcClass, BcTypeNode.typeString) && identifier.equals("length") && getCodeHelper().boolSetting(BcCodeHelper.SETTING_DELEGATE_STRINGS_CALLS))
						{
							// keep String.length property as a "Lenght" property
							identifier = Character.toUpperCase(identifier.charAt(0)) + identifier.substring(1);
						}
						else
						{
							identifier = getCodeHelper().getter(identifier);
							getterCalled = true;
						}

					}
					else
					{
						BcVariableDeclaration bcVar = bcClass.findField(identifier);
						if (bcVar != null)
						{
							lastBcMemberType = bcVar.getType();
							failConversionUnless(lastBcMemberType != null, "Field's type undefined: %s", expr);
						}
						else
						{
							int argsCount = argsCount(node);
							BcFunctionDeclaration bcFunction = bcClass.findFunction(identifier, -1); // check if it's a function type
							if (bcFunction != null)
							{
								System.err.println("Warning! Function type: " + identifier);
								lastBcMemberType = createBcType(BcTypeNode.typeFunction);
							}
							else if (classEquals(bcClass, BcTypeNode.typeXML) || classEquals(bcClass, BcTypeNode.typeXMLList))
							{
								IdentifierNode identifierNode = (IdentifierNode) node.expr;
								if (identifierNode.isAttr())
								{
									lastBcMemberType = createBcType(BcTypeNode.typeString);
								}
								else
								{
									lastBcMemberType = createBcType(BcTypeNode.typeXMLList);
									accessingDynamicXMLList = true;
									System.err.println("Warning! Dynamic XMLList: " + identifier);
								}
							}
							if (identifier.equals("arguments"))
							{
								lastBcMemberType = BcArgumentsType.instance();
							}
							else if (identifier.equals("undefined"))
							{
								lastBcMemberType = BcUndefinedType.instance();
							}
							else
							{
								IdentifierNode identifierNode = (IdentifierNode) node.expr;
								if (!identifierNode.isAttr())
								{
									failConversionUnless(lastBcMemberType != null, "Identifier not recognized: '%s'", identifier);
									
									System.err.println("Warning! Dymaic property: " + identifier);
									accessingDynamicProperty = true;
								}
							}
						}
					}
				}
			}
		}
		else if (node.expr instanceof ArgumentListNode)
		{
			failConversionUnless(lastBcMemberType != null, "Argument list found without owning type: %s", expr);
			if (lastBcMemberType instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) lastBcMemberType;
				lastBcMemberType = vectorType.getGeneric();
				failConversionUnless(lastBcMemberType != null, "Can't detect vector's generic type: %s", expr);
			}
			else if (typeEquals(lastBcMemberType, BcTypeNode.typeXMLList))
			{
				lastBcMemberType = createBcType(BcTypeNode.typeXML);
			}
			else
			{
				lastBcMemberType = createBcType(BcTypeNode.typeObject);
			}
		}
		else
		{
			failConversion("Unexpected node for 'get' expression: " + node.expr.getClass());
		}

		if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
		{
			dest.write(indexerGetter(identifier));
		}
		else if (accessingDynamicProperty)
		{
			dest.writef("getOwnProperty(\"%s\")", identifier);
		}
		else if (accessingDynamicXMLList)
		{
			dest.writef("elements(\"%s\")", identifier);
		}
		else if (getterCalled)
		{
			dest.writef("%s()", identifier);
		}
		else
		{
			dest.write(identifier);
		}
	}

	private BcTypeNode findIdentifierType(String name)
	{
		BcClassDefinitionNode bcClass = findClass(name);
		if (bcClass != null)
		{
			return bcClass.getClassType();
		}

		BcVariableDeclaration bcVar = findVariable(name);
		if (bcVar != null)
		{
			return bcVar.getType();
		}

		BcFunctionDeclaration bcFunc = findFunction(name, -1); // FIXME: pass args count
		if (bcFunc != null)
		{
			return bcFunc.getReturnType();
		}

		return null;
	}

	private BcVariableDeclaration findVariable(String name)
	{
		return findVariable(BcGlobal.lastBcClass, name);
	}

	private BcVariableDeclaration findVariable(BcClassDefinitionNode bcClass, String name)
	{
		BcVariableDeclaration bcVar = findLocalVar(name);
		if (bcVar != null)
		{
			return bcVar;
		}

		return bcClass.findField(name);
	}

	private BcVariableDeclaration findLocalVar(String name)
	{
		if (BcGlobal.lastBcFunction == null)
		{
			return null;
		}

		return BcGlobal.lastBcFunction.findVariable(name);
	}

	private void process(CallExpressionNode node)
	{
		ListWriteDestination exprDest = new ListWriteDestination();
		pushDest(exprDest);
		process(node.expr);
		popDest();

		String identifier = exprDest.toString();
		BcFunctionDeclaration calledFunction = null;

		boolean isCast = false;
		boolean isGlobalCalled = false;
		boolean isFunctionApplyCall = false;

		if (node.expr instanceof IdentifierNode)
		{
			if (lastBcMemberType == null)
			{
				if (!(identifier.equals(BcCodeHelper.thisCallMarker) && identifier.equals(BcCodeHelper.thisCallMarker)))
				{
					int argsCount = argsCount(node);
					BcFunctionDeclaration bcFunc = findFunction(identifier, argsCount);
					if (bcFunc != null)
					{
						calledFunction = bcFunc;

						if (bcFunc.hasReturnType())
						{
							lastBcMemberType = bcFunc.getReturnType();
							failConversionUnless(lastBcMemberType != null, "Can't get function's return type: %s", exprDest);
						}

						isGlobalCalled = bcFunc.isGlobal();
						if (isGlobalCalled)
						{
							addToImport(BcTypeNode.create(BcTypeNode.typeGlobal));
						}
					}
					else if (node.is_new)
					{
						BcClassDefinitionNode bcNewClass = findClass(identifier);
						failConversionUnless(bcNewClass != null, "Can't create undefined class instance: %s", exprDest);
						BcTypeNode bcClassType = bcNewClass.getClassType();
						failConversionUnless(bcClassType != null, "Can't create class instance without class type: %s", exprDest);

						lastBcMemberType = bcClassType;

						addToImport(bcClassType);
					}
					else
					{
						BcVariableDeclaration bcFuncVar = findVariable(identifier);
						if (bcFuncVar != null)
						{
							if (typeEquals(bcFuncVar.getType(), BcTypeNode.typeFunction))
							{
								System.err.println("Warning! Function type: " + identifier);
								lastBcMemberType = bcFuncVar.getType();
							}
							else
							{
								failConversion("Identifier is supposed to be 'Function' type: '" + identifier + "'");
							}
						}
						else
						{
							BcTypeNode type = extractBcType(node.expr);
							failConversionUnless(type != null, "Can't detect cast's type: ", exprDest);

							isCast = type.isIntegral() || canBeClass(type);
							if (isCast)
							{
								addToImport(type);
							}
						}
					}
				}
			}
			else if (lastBcMemberType instanceof BcFunctionTypeNode)
			{
				BcFunctionTypeNode funcType = (BcFunctionTypeNode) lastBcMemberType;
				if (identifier.equals("apply"))
				{
					failConversionUnless(funcType.isComplete(), "Can't call incomplete function: %s", funcType);
					calledFunction = funcType.getFunc();
					isFunctionApplyCall = true;
				}
				else
				{
					assert false;
				}
			}
			else
			{
				BcClassDefinitionNode bcClass = lastBcMemberType.getClassNode();
				failConversionUnless(bcClass != null, "Class type is undefined: " + exprDest);

				int argsCount = argsCount(node);
				BcFunctionDeclaration bcFunc = bcClass.findFunction(identifier, argsCount);
				if (bcFunc != null)
				{
					calledFunction = bcFunc;

					lastBcMemberType = bcFunc.getReturnType();
					if (classEquals(bcClass, BcTypeNode.typeString) && getCodeHelper().boolSetting(BcCodeHelper.SETTING_DELEGATE_STRINGS_CALLS))
					{
						if (identifier.equals("toString"))
						{
							// turn toString() into ToString() for all strings
							identifier = Character.toUpperCase(identifier.charAt(0)) + identifier.substring(1);
						}
					}
				}
				else
				{
					// TODO: remove code duplication
					BcVariableDeclaration bcFuncVar = findVariable(bcClass, identifier);
					if (bcFuncVar != null)
					{
						if (typeEquals(bcFuncVar.getType(), BcTypeNode.typeFunction))
						{
							System.err.println("Warning! Function type: " + identifier);
							lastBcMemberType = bcFuncVar.getType();
						}
						else
						{
							failConversion("Identifier is supposed to be 'Function' type: '" + identifier + "'");
						}
					}
					else
					{
						failConversion("'Function' type not found: class='%s' identifier='%s'", bcClass.getName(), identifier);
					}
				}
			}
		}
		else if (node.expr instanceof MemberExpressionNode)
		{
			lastBcMemberType = evaluateType(node.expr);
			failConversionUnless(lastBcMemberType != null, "Unable to evaluate member expression type: " + exprDest);
		}
		else
		{
			failConversion("Unexpected node type for 'call' expression: %s expr: %s", node.expr.getClass(), exprDest);
		}

		BcArgumentsList argsList;
		if (node.args != null)
		{
			if (isFunctionApplyCall)
			{
				ObjectList<Node> args = node.args.items;
				failConversionUnless(args.size() == 2, "Unexpected args list: %s", args);
				
				BcTypeNode secondArgType = evaluateType(args.get(1));
				failConversionUnless(typeEquals(secondArgType, BcTypeNode.typeArray), "Unexpected second arg type for 'apply' call: %s", secondArgType);
				
				WriteDestination argDest = new ListWriteDestination();
				pushDest(argDest);
				process(args.get(1));
				popDest();
				
				int paramsCount = calledFunction.paramsCount();
				argsList = new BcArgumentsList(paramsCount);
				if (calledFunction.hasRestParams())
				{
					failConversionUnless(paramsCount == 1, "Can't make 'apply' call on function: %s", calledFunction);
					argsList.add(argDest);
				}
				else
				{
					for (int argIndex = 0; argIndex < paramsCount; ++argIndex)
					{
						argsList.add(argDest + indexerGetter(argIndex));
					}
				}
			}
			else if (calledFunction != null && !isCast)
			{
				argsList = createArgsList(calledFunction, node.args.items);
			}
			else
			{
				argsList = getArgs(node.args);
			}
		}
		else
		{
			argsList = new BcArgumentsList();
		}

		if (node.is_new)
		{
			BcTypeNode type = extractBcType(node.expr);
			boolean qualified = isTypeQualified(node.expr);
			BcTypeNodeInstance typeInstance = type.createTypeInstance(qualified);

			failConversionUnless(type != null, "Can't detect new's type: ", exprDest);

			dest.write(construct(typeInstance, argsList));
		}
		else if (node.expr instanceof MemberExpressionNode && ((MemberExpressionNode) node.expr).selector instanceof ApplyTypeExprNode)
		{
			BcTypeNode type = extractBcType(node.expr);
			failConversionUnless(type != null, "Can't detect vector's type: ", exprDest);

			failConversionUnless(type instanceof BcVectorTypeNode, "Vector type expected: %s", exprDest);
			Node argNode = node.args.items.get(0);

			if (argNode instanceof LiteralArrayNode)
			{
				LiteralArrayNode arrayNode = (LiteralArrayNode) argNode;
				ArgumentListNode elementlist = arrayNode.elementlist;

				writeNewLiteralVector((BcVectorTypeNode) type, elementlist.items);
			}
			else
			{
				ListWriteDestination argDest = new ListWriteDestination();
				pushDest(argDest);
				process(argNode);
				popDest();

				BcTypeNode argType = evaluateType(argNode);
				failConversionUnless(argType != null, "Unable to evaluate arg's type: %s", argDest);

				dest.write(cast(argDest, argType, type));
			}
		}
		else
		{
			if (isCast)
			{
				failConversionUnless(node.args != null, "Cast should have args: %s", exprDest);
				failConversionUnless(node.args.size() == 1, "Cast should have a single arg: %s", exprDest);

				Node argNode = node.args.items.get(0);
				String argStr = argsList.get(0).toString();

				BcTypeNode argType = evaluateType(argNode);
				failConversionUnless(argType != null, "Can't evaluate arg's type: %s", argStr);

				BcTypeNode type = extractBcType(node.expr);
				failConversionUnless(type != null, "Can't detect cast's type: ", exprDest);

				if (typeEquals(type, BcTypeNode.typeString))
				{
					dest.writef(toString(argStr));
				}
				else
				{
					dest.writef("(%s)", cast(argStr, argType, type));
				}
			}
			else if (isGlobalCalled)
			{
				dest.writef(staticSelector(type(BcTypeNode.typeGlobal), String.format("%s(%s)", identifier, argsList)));
			}
			else
			{
				dest.writef("%s(%s)", identifier, argsList);
			}
		}
	}

	private void process(SetExpressionNode node)
	{
		ListWriteDestination exprDest = new ListWriteDestination();
		pushDest(exprDest);
		process(node.expr);
		popDest();

		String identifier = exprDest.toString();
		boolean addToDictionary = false;

		boolean setterCalled = false;
		if (node.expr instanceof IdentifierNode)
		{
			BcClassDefinitionNode bcClass;
			if (lastBcMemberType != null)
			{
				bcClass = lastBcMemberType.getClassNode();
				failConversionUnless(bcClass != null, "Can't get last member type: %s", exprDest);
			}
			else
			{
				failConversionUnless(BcGlobal.lastBcClass != null, "'set' expression might appear outside of a class: %s", exprDest);
				bcClass = BcGlobal.lastBcClass;
			}

			BcVariableDeclaration bcVar = null;
			if (lastBcMemberType == null)
			{
				bcVar = findVariable(bcClass, identifier);
			}
			else
			{
				bcVar = bcClass.findField(identifier);
			}

			if (bcVar != null)
			{
				lastBcMemberType = bcVar.getType();
				failConversionUnless(lastBcMemberType != null, "Can't get variable's type: %d", exprDest);
			}
			else
			{
				BcFunctionDeclaration bcFunc = bcClass.findSetterFunction(identifier);
				if (bcFunc != null)
				{
					List<BcFuncParam> funcParams = bcFunc.getParams();
					BcTypeNode setterType = funcParams.get(0).getType();
					setterCalled = true;

					identifier = getCodeHelper().setter(identifier);
					lastBcMemberType = setterType;
				}
				else
				{
					int argsCount = argsCount(node);
					BcFunctionDeclaration bcFunction = bcClass.findFunction(identifier, argsCount); // check if it's a function type
					if (bcFunction != null)
					{
						System.err.println("Warning! Function type: " + identifier);
						lastBcMemberType = createBcType(BcTypeNode.typeFunction);
					}
					else if (classEquals(bcClass, BcTypeNode.typeXML))
					{
						IdentifierNode identifierNode = (IdentifierNode) node.expr;
						if (identifierNode.isAttribute())
						{
							lastBcMemberType = createBcType(BcTypeNode.typeString);
						}
						else
						{
							failConversion("Identifier is supposed to be an attribute: %s", exprDest);
						}
					}
					else
					{
						System.err.println("Warning! Dymaic set property: " + identifier);
					}
				}
			}
		}
		else if (node.expr instanceof ArgumentListNode)
		{
			failConversionUnless(lastBcMemberType != null, "Argument list without owning type: %s", exprDest);
			if (lastBcMemberType instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) lastBcMemberType;
				lastBcMemberType = vectorType.getGeneric();
				failConversionUnless(lastBcMemberType != null, "Can't detect vector's generic type: %s", exprDest);
			}
			else
			{
				lastBcMemberType = createBcType(BcTypeNode.typeObject);
				addToDictionary = true;
			}
		}
		else if (node.expr instanceof ListNode)
		{
			ListNode listNode = (ListNode) node.expr;
			if (listNode.items.size() == 2)
			{
				if (listNode.items.get(0) instanceof StoreRegisterNode)
				{
					// do nothing
				}
				else
				{
					failConversion("Expected ListNode element");
				}
			}
			else
			{
				failConversion("Unexpected ListNode: expr=%s", node.expr.getClass(), exprDest);
			}
		}
		else
		{
			failConversion("Unexpected expr node: type=%s expr=%s", node.expr.getClass(), exprDest);
		}

		BcTypeNode selectorType = lastBcMemberType;

		ListWriteDestination argsDest = new ListWriteDestination();
		pushDest(argsDest);
		process(node.args);
		popDest();

		if (setterCalled)
		{
			if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
			{
				dest.writef("%s(%s)", indexerGetter(identifier), argsDest);
			}
			else
			{
				dest.writef("%s(%s)", identifier, argsDest);
			}
		}
		else
		{
			failConversionUnless(node.args.size() == 1, "'set' expression should have a single argument: %d", node.args.size());

			Node argNode = node.args.items.get(0);

			if (selectorType instanceof BcFunctionTypeNode)
			{
				BcTypeNode argType = evaluateType(argNode, true);
				if (argType instanceof BcFunctionTypeNode)
				{
					BcFunctionTypeNode funcType = (BcFunctionTypeNode) argType;
					failConversionUnless(funcType.isComplete(), "Selector should have a complete 'Function' type: %s", exprDest);

					BcFunctionDeclaration func = funcType.getFunc();
					failConversionUnless(func.hasOwner(), "Selector is a 'Function' type but should have an owner: %s", exprDest);

					func.setSelector();

					BcClassDefinitionNode ownerClass = func.getOwner();
					dest.writef("%s = %s", identifier, selector(ownerClass, argsDest));
				}
				else if (argType.isNull())
				{
					dest.writef("%s = %s", identifier, argsDest);
				}
				else
				{
					failConversion("Unexpected argType: '%s' expr: %s", argType.getClass(), argsDest);
				}
			}
			else
			{
				BcTypeNode argType = evaluateType(argNode);
				failConversionUnless(argType != null, "Can't evaluated arg's type: %s", argsDest);

				boolean needCast = !addToDictionary && needExplicitCast(argType, selectorType);

				if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
				{
					if (needCast)
					{
						dest.write(indexerSetter(identifier, cast(argsDest, argType, selectorType)));
					}
					else
					{
						dest.write(indexerSetter(identifier, argsDest));
					}
				}
				else
				{
					if (needCast)
					{
						dest.writef("%s = %s", identifier, cast(argsDest, argType, selectorType));
					}
					else
					{
						dest.writef("%s = %s", identifier, argsDest);
					}
				}
			}
		}
	}

	private void process(ApplyTypeExprNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();

		String typeName = getCodeHelper().extractIdentifier((IdentifierNode)node.expr); // TODO: handle qualifier here
		StringBuilder typeBuffer = new StringBuilder(typeName);

		ListNode typeArgs = node.typeArgs;
		int genericCount = typeArgs.items.size();
		if (genericCount > 0)
		{
			typeBuffer.append("<");
			int genericIndex = 0;
			for (Node genericTypeNode : typeArgs.items)
			{
				BcTypeNode genericType = extractBcType(genericTypeNode);
				typeBuffer.append(type(genericType));
				if (++genericIndex < genericCount)
				{
					typeBuffer.append(",");
				}
			}
			typeBuffer.append(">");
		}

		String type = typeBuffer.toString();
		dest.write(type);
	}

	private void process(IncrementNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();

		String op = Tokens.tokenToString[-node.op];
		if (node.isPostfix)
		{
			dest.write(expr + op);
		}
		else
		{
			dest.write(op + expr);
		}
	}

	private void process(IdentifierNode node)
	{
		if (node.isAttr())
		{
			dest.write("attributeValue(\"");
			dest.write(getCodeHelper().extractIdentifier(node));
			dest.write("\")");
		}
		else
		{
			dest.write(getCodeHelper().extractIdentifier(node));
		}
	}

	private BcVariableDeclaration process(VariableBindingNode node)
	{
		BcTypeNode varType = extractBcType(node.variable);
		boolean qualified = isTypeQualified(node.variable);

		addToImport(varType);

		String bcIdentifier = getCodeHelper().extractIdentifier(node.variable.identifier);	
		BcVariableDeclaration bcVar = new BcVariableDeclaration(varType, bcIdentifier, qualified);
		BcTypeNodeInstance varTypeInstance = bcVar.getTypeInstance();

		bcVar.setConst(node.kind == Tokens.CONST_TOKEN);
		bcVar.setModifiers(BcNodeHelper.extractModifiers(node.attrs));

		dest.write(varDecl(varTypeInstance, bcIdentifier));

		if (node.initializer != null)
		{
			ListWriteDestination initializer = new ListWriteDestination();
			pushDest(initializer);
			process(node.initializer);
			popDest();

			bcVar.setInitializer(initializer);
			bcVar.setIntegralInitializerFlag(BcNodeHelper.isIntegralLiteralNode(node.initializer));

			BcTypeNode initializerType = evaluateType(node.initializer);
			failConversionUnless(initializerType != null, "Unable to evaluate initializer's type: %s = %s", varDecl(varTypeInstance, bcIdentifier), initializer);

			if (needExplicitCast(initializerType, varType))
			{
				dest.writef(" = %s", cast(initializer, initializerType, varType));
			}
			else
			{
				dest.write(" = " + initializer);
			}
		}
		else if (BcGlobal.lastBcFunction != null)
		{
			dest.write(" = " + typeDefault(varType));
		}
		dest.writeln(";");

		BcGlobal.declaredVars.add(bcVar);

		return bcVar;
	}

	private void processLiteral(Node node)
	{
		if (node instanceof LiteralNumberNode)
		{
			LiteralNumberNode numberNode = (LiteralNumberNode) node;
			dest.write(numberNode.value);
			if (numberNode.value.indexOf('.') != -1)
			{
				dest.write("f");
			}
		}
		else if (node instanceof LiteralNullNode)
		{
			dest.write(getCodeHelper().literalNull());
		}
		else if (node instanceof LiteralBooleanNode)
		{
			LiteralBooleanNode booleanNode = (LiteralBooleanNode) node;
			dest.write(getCodeHelper().literalBool(booleanNode.value));
		}
		else if (node instanceof LiteralStringNode)
		{
			LiteralStringNode stringNode = (LiteralStringNode) node;
			dest.write(getCodeHelper().literalString(stringNode.value));
		}
		else if (node instanceof LiteralRegExpNode)
		{
			failConversion("LiteralRegExpNode is not supported");
		}
		else if (node instanceof LiteralArrayNode)
		{
			LiteralArrayNode arrayNode = (LiteralArrayNode) node;
			if (arrayNode.elementlist != null)
			{
				writeNewLiteralArray(arrayNode.elementlist.items);
			}
			else
			{
				dest.writef(construct(type(BcTypeNode.typeArray), ""));
			}
		}
		else if (node instanceof LiteralVectorNode)
		{
			LiteralVectorNode vectorNode = (LiteralVectorNode) node;
			BcTypeNode bcType = extractBcType(vectorNode.type);
			failConversionUnless(bcType instanceof BcVectorTypeNode, "Vector type expected: %s", bcType.getName());

			writeNewLiteralVector((BcVectorTypeNode) bcType, null);
		}
		else if (node instanceof LiteralObjectNode)
		{
			writeLiteralObject((LiteralObjectNode)node);
		}
		else if (node instanceof LiteralXMLNode)
		{
			writeLiteralXML((LiteralXMLNode)node);
		}
		else
		{
			failConversion("Unexpected literal node: %s", node.getClass());
		}
	}

	private void process(IfStatementNode node)
	{
		// TODO: make it right
		boolean cutWithPreprocessor = BcNodeHelper.isPreprocessorConditionNode(node.condition);
		
		if (cutWithPreprocessor)
		{
			dest.writeln("NOT.IMPLEMENTED();");
		}
		else
		{
			ListWriteDestination condDest = new ListWriteDestination();
			pushDest(condDest);
			process(node.condition);
			popDest();
	
			String condString = condDest.toString();
	
			failConversionUnless(node.condition instanceof ListNode, "'if' statement condition is supposed to be the ListNode: type=%s expr=%s", node.condition.getClass(), condDest);
			ListNode listNode = (ListNode) node.condition;
	
			condString = createSafeConditionString(condString, listNode);
			dest.writelnf("if(%s)", condString);
	
			if (node.thenactions != null)
			{
				ListWriteDestination thenDest = new ListWriteDestination();
				pushDest(thenDest);
				process(node.thenactions);
				popDest();
				dest.writeln(thenDest);
			}
			else
			{
				writeEmptyBlock();
			}
		}

		if (node.elseactions != null)
		{
			ListWriteDestination elseDest = new ListWriteDestination();
			pushDest(elseDest);
			process(node.elseactions);
			popDest();
			if (!cutWithPreprocessor)
			{
				dest.writeln("else");
			}
			dest.writeln(elseDest);
		}
	}

	private String createSafeConditionString(String condString, ListNode listNode)
	{
		failConversionUnless(listNode.size() == 1, "Condition node should have a single item: %d", listNode.size());
		Node condition = listNode.items.get(0);

		return createSafeConditionString(condString, condition);
	}

	private String createSafeConditionString(String condString, Node condition)
	{
		BcTypeNode conditionType = evaluateType(condition);
		failConversionUnless(conditionType != null, "Unable to evaluate condition expression's type: '%s'", condString);

		if (!typeEquals(conditionType, BcTypeNode.typeBoolean))
		{
			if (typeEquals(conditionType, "int") || typeEquals(conditionType, "uint"))
			{
				return getCodeHelper().notZero(condString);
			}
			return getCodeHelper().notNull(condString);
		}
		else
		{
			return String.format("%s", condString);
		}
	}

	private void process(ConditionalExpressionNode node)
	{
		ListWriteDestination condDest = new ListWriteDestination();
		pushDest(condDest);
		process(node.condition);
		popDest();

		String condString = condDest.toString();
		condString = createSafeConditionString(condString, node.condition);

		ListWriteDestination thenDest = new ListWriteDestination();
		pushDest(thenDest);
		process(node.thenexpr);
		popDest();

		ListWriteDestination elseDest = new ListWriteDestination();
		pushDest(elseDest);
		process(node.elseexpr);
		popDest();

		dest.writef("((%s) ? (%s) : (%s))", condString, thenDest, elseDest);
	}

	private void process(WhileStatementNode node)
	{
		ListWriteDestination exprDest = new ListWriteDestination();
		pushDest(exprDest);
		process(node.expr);
		popDest();

		String condString = exprDest.toString();

		failConversionUnless(node.expr instanceof ListNode, "'while' statement expression is supposed to be the ListNode: type=%s expr=%s", node.expr.getClass(), exprDest);
		ListNode listNode = (ListNode) node.expr;

		condString = createSafeConditionString(condString, listNode);
		dest.writelnf("while(%s)", condString);

		if (node.statement != null)
		{
			ListWriteDestination statementDest = new ListWriteDestination();
			pushDest(statementDest);
			process(node.statement);
			popDest();
			dest.writeln(statementDest);
		}
		else
		{
			writeEmptyBlock();
		}
	}

	private void process(ForStatementNode node)
	{
		boolean isForEach = node.test instanceof HasNextNode;
		if (isForEach)
		{
			failConversionUnless(BcGlobal.lastBcClass != null, "For each is defined outside of a class");

			// get iterable collection expression
			failConversionUnless(node.initialize != null, "For each should have initializer statement");
			failConversionUnless(node.initialize instanceof ListNode, "For each initializer should be ListNode: %s", node.initialize.getClass());

			ListNode list = (ListNode) node.initialize;
			failConversionUnless(list.items.size() == 2, "For each should have 2 items in initializer list", list.items.size());

			StoreRegisterNode register = (StoreRegisterNode) list.items.get(1);
			CoerceNode coerce = (CoerceNode) register.expr;

			ListWriteDestination collection = new ListWriteDestination();
			pushDest(collection);
			process(coerce.expr);
			popDest();

			BcTypeNode collectionType = evaluateType(coerce.expr);
			BcTypeNodeInstance collectionTypeInstance = collectionType.createTypeInstance(); // hack

			failConversionUnless(collectionType != null, "Can't evaluate for each collection's type: %s", collection);

			BcGlobal.lastBcClass.addToImport(collectionType);

			failConversionUnless(node.statement != null, "For each should have statement node");
			failConversionUnless(node.statement instanceof StatementListNode, "For each statement should be StatementListNode: %s", node.statement.getClass());

			StatementListNode statements = (StatementListNode) node.statement;
			failConversionUnless(statements.items.size() == 2, "For each should have 2 items in statement node: %d", statements.items.size());

			// get iteration
			ExpressionStatementNode child1 = (ExpressionStatementNode) statements.items.get(0);
			MemberExpressionNode child2 = (MemberExpressionNode) child1.expr;
			SetExpressionNode child3 = (SetExpressionNode) child2.selector;
			String loopVarName = null;
			if (child3.expr instanceof QualifiedIdentifierNode)
			{
				QualifiedIdentifierNode identifier = (QualifiedIdentifierNode) child3.expr;
				loopVarName = getCodeHelper().extractIdentifier(identifier);
			}
			else if (child3.expr instanceof IdentifierNode)
			{
				IdentifierNode identifier = (IdentifierNode) child3.expr;
				loopVarName = getCodeHelper().extractIdentifier(identifier);
			}
			else
			{
				failConversion("Unexpected for each statement node: %s", child3.expr.getClass());
			}

			BcVariableDeclaration loopVar = findDeclaredVar(loopVarName);
			failConversionUnless(loopVar != null, "Unable to find for each loop var: %s", loopVarName);

			BcTypeNode loopVarType = loopVar.getType();
			BcTypeNodeInstance loopVarTypeInstance = loopVar.getTypeInstance();

			String loopVarString = varDecl(loopVarTypeInstance, loopVarName);
			String loopVarStringGenerated = loopVarString + " = " + typeDefault(loopVarType) + ";";

			ListWriteDestination listDest = (ListWriteDestination) dest;
			if (listDest.peekLine().trim().equals(loopVarStringGenerated))
			{
				listDest.popLine();
			}

			// get loop body
			Node bodyNode = statements.items.get(1);
			ListWriteDestination bodyDest = new ListWriteDestination();
			pushDest(bodyDest);

			if (bodyNode != null)
			{
				failConversionUnless(bodyNode instanceof StatementListNode, "For each body should be StatementListNode: %s", bodyNode.getClass());
				StatementListNode statementsNode = (StatementListNode) bodyNode;

				writeBlockOpen(dest);

				ObjectList<Node> items = statementsNode.items;
				for (Node itemNode : items)
				{
					process(itemNode);
				}

				writeBlockClose(dest);
			}
			else
			{
				writeEmptyBlock();
			}
			popDest();

			writeForeach(dest, loopVarName, loopVarTypeInstance, collection, collectionTypeInstance, bodyDest);
		}
		else
		{
			ListWriteDestination initialize = new ListWriteDestination();
			ListWriteDestination test = new ListWriteDestination();
			ListWriteDestination increment = new ListWriteDestination();

			if (node.initialize != null)
			{
				pushDest(initialize);
				process(node.initialize);
				popDest();
			}

			if (node.test != null)
			{
				pushDest(test);
				process(node.test);
				popDest();
			}

			if (node.increment != null)
			{
				increment = new ListWriteDestination();
				pushDest(increment);
				process(node.increment);
				popDest();
			}

			dest.writelnf("for (%s; %s; %s)", initialize, test, increment);

			process(node.statement);
		}
	}

	private void process(DoStatementNode node)
	{
		failConversion("'do' statement is not supported yet. Sorry");
	}

	private void process(SwitchStatementNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();

		dest.writelnf("switch(%s)", expr);
		writeBlockOpen(dest);

		if (node.statements != null)
		{
			ObjectList<Node> statements = node.statements.items;
			for (Node statement : statements)
			{
				if (statement instanceof CaseLabelNode)
				{
					CaseLabelNode caseLabel = (CaseLabelNode) statement;
					Node label = caseLabel.label;

					if (label == null)
					{
						dest.writeln("default:");
					}
					else
					{
						ListWriteDestination caseDest = new ListWriteDestination();
						pushDest(caseDest);
						process(label);
						popDest();
						dest.writelnf("case %s:", caseDest);
					}
				}
				else
				{
					process(statement);
				}
			}
		}

		writeBlockClose(dest);
	}

	private void process(TryStatementNode node)
	{
		dest.writeln("try");

		if (node.tryblock != null)
		{
			process(node.tryblock);
		}
		else
		{
			writeEmptyBlock();
		}

		if (node.catchlist != null)
		{
			ObjectList<Node> items = node.catchlist.items;
			for (Node item : items)
			{
				process(item);
			}
		}

		if (node.finallyblock != null)
		{
			process(node.finallyblock);
		}
	}

	private void process(CatchClauseNode node)
	{
		ListWriteDestination paramDest = new ListWriteDestination();
		if (node.parameter != null)
		{
			pushDest(paramDest);
			process(node.parameter);
			popDest();
		}

		dest.writeln(catchClause(paramDest));
		process(node.statements);
	}

	private void process(ParameterNode node)
	{
		BcTypeNode type = extractBcType(node.type);
		boolean qualified = isTypeQualified(node.type);

		addToImport(type);

		String identifier = getCodeHelper().extractIdentifier(node.identifier);

		BcVariableDeclaration parameterVar = new BcVariableDeclaration(type, identifier, qualified);

		BcGlobal.declaredVars.add(parameterVar);
		dest.write(paramDecl(parameterVar.getTypeInstance(), identifier));
	}

	private void process(FinallyClauseNode node)
	{
		dest.write("finally");
		process(node.statements);
	}

	private void process(ThrowStatementNode node)
	{
		if (node.expr != null)
		{
			ListWriteDestination throwDest = new ListWriteDestination();
			pushDest(throwDest);
			process(node.expr);
			popDest();
	
			dest.writelnf("%s;", throwStatment(throwDest));
		}
	}

	private void process(BinaryExpressionNode node)
	{
		ListWriteDestination ldest = new ListWriteDestination();
		ListWriteDestination rdest = new ListWriteDestination();

		pushDest(ldest);
		process(node.lhs);
		popDest();

		pushDest(rdest);
		process(node.rhs);
		popDest();

		String lshString = ldest.toString();
		String rshString = rdest.toString();

		if (node.op == Tokens.LOGICALAND_TOKEN || node.op == Tokens.LOGICALOR_TOKEN)
		{

			BcTypeNode lshType = evaluateType(node.lhs);
			BcTypeNode rshType = evaluateType(node.rhs);

			if (!typeEquals(lshType, BcTypeNode.typeBoolean))
			{
				if (canBeClass(lshType))
				{
					lshString = String.format("(%s)", getCodeHelper().notNull(lshString));
				}
				else
				{
					lshString = String.format("(%s)", getCodeHelper().notZero(lshString));
				}
			}

			if (!typeEquals(rshType, BcTypeNode.typeBoolean))
			{
				if (canBeClass(rshType))
				{
					rshString = String.format("(%s)", getCodeHelper().notNull(rshString));
				}
				else
				{
					rshString = String.format("(%s)", getCodeHelper().notZero(rshString));
				}
			}

			dest.writef("(%s %s %s)", lshString, Tokens.tokenToString[-node.op], rshString);
		}
		else if (node.op == Tokens.IS_TOKEN)
		{
			dest.write(operatorIs(ldest, rdest));
		}
		else if (node.op == Tokens.AS_TOKEN)
		{
			BcTypeNode castType = extractBcType(node.rhs);
			dest.writef("((%s) ? (%s) : %s)", operatorIs(ldest, rdest), cast(lshString, castType), getCodeHelper().literalNull());
		}
		else if (node.op == Tokens.IN_TOKEN)
		{
			BcTypeNode rhsType = evaluateType(node.rhs);
			failConversionUnless(rhsType != null, "Can't evaluate 'in' call target type: %s", rshString);
			
			if (typeEquals(rhsType, BcTypeNode.typeDictionary))
			{			
				dest.writef(memberCall(rshString, "containsKey", lshString));
			}
			else
			{
				dest.writef(memberCall(rshString, "hasOwnProperty", lshString));
			}
		}
		else
		{
			if (BcNodeHelper.isBinaryOperandSetExpression(node.lhs))
			{
				lshString = String.format("(%s)", lshString);
			}
			if (BcNodeHelper.isBinaryOperandSetExpression(node.rhs))
			{
				rshString = String.format("(%s)", rshString);
			}
			dest.writef("(%s %s %s)", lshString, Tokens.tokenToString[-node.op], rshString);
		}
	}

	private void process(UnaryExpressionNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();

		switch (node.op)
		{
		case Tokens.NOT_TOKEN:
		{
			if (node.expr instanceof MemberExpressionNode)
			{
				BcTypeNode memberType = evaluateType(node.expr);
				if (!typeEquals(memberType, BcTypeNode.typeBoolean))
				{
					dest.writef("(%s)", getCodeHelper().isNull(expr));
				}
				else
				{
					dest.writef("!(%s)", expr);
				}
			}
			else if (node.expr instanceof ListNode)
			{
				process((ListNode)node.expr);
			}
			else
			{
				failConversion("Unexpected expression for unary 'not' token: type=%s expr=%s", node.expr.getClass(), expr);
			}
			break;
		}
		case Tokens.MINUS_TOKEN:
			dest.writef("-%s", expr);
			break;

		default:
			failConversion("Unsupported unary operation: token=%d expr=%s", node.op, expr);
		}
	}

	private void process(ReturnStatementNode node)
	{
		failConversionUnless(!node.finallyInserted, "Return statement with finally inserted is not supported yet");

		dest.write("return");
		if (node.expr != null)
		{
			dest.write(" ");

			ListWriteDestination exprDest = new ListWriteDestination();
			pushDest(exprDest);
			process(node.expr);
			popDest();

			failConversionUnless(BcGlobal.lastBcFunction != null, "'return' statemnt outside of a function: return %s", exprDest);
			failConversionUnless(BcGlobal.lastBcFunction.hasReturnType(), "'return' statement with expression inside 'void' function: return %s", exprDest);

			BcTypeNode returnValueType = evaluateType(node.expr);
			failConversionUnless(returnValueType != null, "Unable to evaluate return type from expression: %s", exprDest);

			BcTypeNode returnType = BcGlobal.lastBcFunction.getReturnType();
			if (needExplicitCast(returnValueType, returnType))
			{
				dest.write(cast(exprDest, returnValueType, returnType));
			}
			else
			{
				dest.write(exprDest);
			}

		}
		dest.writeln(";");
	}

	private void process(BreakStatementNode node)
	{
		dest.write("break");
		if (node.id != null)
		{
			String id = getCodeHelper().extractIdentifier(node.id);
			dest.write(" " + id);
		}
		dest.writeln(";");
	}
	
	private void process(ContinueStatementNode node)
	{
		failConversionUnless(node.block == null, "ContinueStatementNode has not null block");
		dest.write("continue");
		dest.writeln(";");
	}

	protected void process(ThisExpressionNode node)
	{
		dest.write("this");
	}

	protected void process(SuperExpressionNode node)
	{
		dest.write("base");
	}

	private void process(SuperStatementNode node)
	{
		ArgumentListNode args = node.call.args;

		ListWriteDestination argsDest = new ListWriteDestination();
		if (args != null)
		{
			pushDest(argsDest);
			int itemIndex = 0;
			for (Node arg : args.items)
			{
				process(arg);

				if (++itemIndex < args.items.size())
				{
					dest.write(", ");
				}
			}
			popDest();
		}

		dest.writelnf("%s(%s);", BcCodeHelper.superCallMarker, argsDest);
	}

	private void process(BcFunctionDeclaration bcFunc, BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> oldDeclaredVars = BcGlobal.declaredVars;
		BcGlobal.lastBcFunction = bcFunc;
		BcGlobal.declaredVars = bcFunc.getDeclaredVars();

		// get function statements
		ListWriteDestination body = new ListWriteDestination();
		pushDest(body);
		process(bcFunc.getStatements());
		popDest();

		List<String> lines = body.getLines();
		String lastLine = lines.get(lines.size() - 2);
		if (lastLine.contains("return;"))
		{
			lines.remove(lines.size() - 2);
		}

		bcFunc.setBody(body);
		BcGlobal.declaredVars = oldDeclaredVars;
		BcGlobal.lastBcFunction = null;
	}

	private void process(ExpressionStatementNode node)
	{
		process(node.expr);
		dest.writeln(";");
	}

	private void process(ListNode node)
	{
		ObjectList<Node> items = node.items;
		for (Node item : items)
		{
			process(item);
		}
	}

	private void process(BcTypeNode typeNode)
	{
		if (!typeNode.isIntegral() && !typeNode.hasClassNode())
		{
			BcClassDefinitionNode classNode = findClass(typeNode);
			failConversionUnless(classNode != null, "Can't find class: %s", typeNode.getNameEx());
			typeNode.setClassNode(classNode);
		}
	}

	private void postProcess(List<BcClassDefinitionNode> classes)
	{
		for (BcClassDefinitionNode bcClass : classes)
		{
			postProcess(bcClass);
		}
	}

	protected void postProcess(BcClassDefinitionNode bcClass)
	{
	}

	private BcClassDefinitionNode findClass(BcTypeNode type)
	{
		if (type.isIntegral())
		{
			return null;
		}

		if (type instanceof BcWildcardTypeNode)
		{
			return findClass(BcTypeNode.typeObject);
		}
		
		if (type instanceof BcGenericTypeNode)
		{
			return findClass(BcTypeNode.typeObject);
		}

		return findClass(type.getTypeName());
	}

	private BcClassDefinitionNode findClass(String name)
	{
		return findClass(name, null);
	}

	private BcClassDefinitionNode findClass(BcTypeName typeName)
	{
		return findClass(typeName.getName(), typeName.getQualifier());
	}

	private BcClassDefinitionNode findClass(String name, String packageName)
	{
		BcClassDefinitionNode bcClass;
		List<BcClassDefinitionNode> foundClasses = new ArrayList<BcClassDefinitionNode>();

		if ((bcClass = findClass(BcGlobal.bcPlatformClasses, name, packageName)) != null)
		{
			foundClasses.add(bcClass);
		}

		if ((bcClass = findClass(BcGlobal.bcApiClasses, name, packageName)) != null)
		{
			foundClasses.add(bcClass);
		}

		if ((bcClass = findClass(BcGlobal.bcClasses, name, packageName)) != null)
		{
			foundClasses.add(bcClass);
		}

		if (foundClasses.isEmpty())
		{
			return null;
		}

		if (foundClasses.size() == 1)
		{
			return foundClasses.get(0);
		}

		if (packageName == null)
		{
			packageName = tryFindPackage(name);
		}

		if (packageName != null)
		{
			for (BcClassDefinitionNode foundClass : foundClasses)
			{
				if (packageName.equals(foundClass.getPackageName()))
				{
					return foundClass;
				}
			}
		}
		else
		{
			if (BcGlobal.lastBcPackageName != null)
			{
				for (BcClassDefinitionNode foundClass : foundClasses)
				{
					if (BcGlobal.lastBcPackageName.equals(foundClass.getPackageName()))
					{
						return foundClass;
					}
				}
			}
			
			// search imported type
			if (BcGlobal.lastBcImportList != null)
			{
				// find imported type
				String foundPackageName = BcGlobal.lastBcImportList.findPackage(name);
				if (foundPackageName != null)
				{
					for (BcClassDefinitionNode foundClass : foundClasses)
					{
						if (foundPackageName.equals(foundClass.getPackageName()))
						{
							return foundClass;
						}
					}
				}
				
				// try to select by wildcard import mask
				for (BcClassDefinitionNode foundClass : foundClasses)
				{
					String foundPackage = foundClass.getPackageName();
					if (BcGlobal.lastBcImportList.hasWildcardMaskPackage(foundPackage))
					{
						return foundClass;
					}
				}
			}
		}

		failConversion("Can't detect type's class: '%s'", name);
		return null;
	}

	private BcClassDefinitionNode findClass(List<BcClassDefinitionNode> classes, String name, String packageName)
	{
		if (classes == null)
		{
			return null;
		}

		for (BcClassDefinitionNode bcClass : classes)
		{
			BcTypeNode classType = bcClass.getClassType();
			if (name.equals(classType.getName()) && (packageName == null || packageName.equals(classType.getQualifier())))
			{
				return bcClass;
			}
		}

		return null;
	}

	private void write(File outputDir, List<BcClassDefinitionNode> classes) throws IOException
	{
		for (BcClassDefinitionNode bcClass : classes)
		{
			writeClassDefinition(bcClass, outputDir);
		}
	}

	protected abstract void writeClassDefinition(BcClassDefinitionNode bcClass, File outputDir) throws IOException;
	
	protected void postWrite(File outputDir) throws IOException
	{
	}

	protected void writeBlockOpen(WriteDestination dest)
	{
		dest.writeln("{");
		dest.incTab();
	}

	protected void writeBlockClose(WriteDestination dest)
	{
		dest.decTab();
		dest.writeln("}");
	}

	private void writeEmptyBlock()
	{
		writeEmptyBlock(dest);
	}

	protected void writeBlankLine(WriteDestination dest)
	{
		dest.writeln();
	}

	private void writeEmptyBlock(WriteDestination dest)
	{
		writeBlockOpen(dest);
		writeBlockClose(dest);
	}

	protected boolean shouldWriteClassToFile(BcClassDefinitionNode bcClass, File file)
	{
		BcMetadata metadata = bcClass.getMetadata();
		if (metadata != null)
		{
			if (metadata.contains("NoConversion"))
			{
				return false;
			}

			if (metadata.contains("ConvertOnce"))
			{
				if (file.exists())
				{
					return false;
				}
			}
		}

		return true;
	}
	
	protected boolean shouldIgnoreDeclaration(BcMetadata metadata)
	{
		return metadata != null && metadata.contains("IgnoreConversion");
	}

	protected void writeDestToFile(ListWriteDestination src, File file) throws IOException
	{
		if (needUpldateFile(file, src))
		{
			FileUtils.moveToTrash(file);
			writeFile(file, src);
		}
		else
		{
			System.out.println("Up to date: " + file.getName());
		}
	}

	private boolean needUpldateFile(File file, ListWriteDestination src) throws IOException
	{
		if (file.exists())
		{
			List<String> oldLines = readFile(file);
			List<String> newLines = src.getLines();

			if (oldLines.size() != newLines.size())
			{
				return true;
			}

			for (int i = 0; i < oldLines.size(); ++i)
			{
				if (!oldLines.get(i).equals(newLines.get(i)))
				{
					return true;
				}
			}

			return false;
		}

		return true;
	}

	private List<String> readFile(File file) throws IOException
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			List<String> lines = new ArrayList<String>();

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
			{
				reader.close();
			}
		}
	}

	private void writeFile(File file, ListWriteDestination src) throws IOException
	{
		PrintStream stream = null;
		try
		{
			stream = new PrintStream(file);
			List<String> lines = src.getLines();
			{
				for (String line : lines)
				{
					stream.println(line);
				}
			}
		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}
	}

	private void pushDest(WriteDestination newDest)
	{
		destStack.push(dest);
		dest = newDest;
	}

	private void popDest()
	{
		dest = destStack.pop();
	}

	// /////////////////////////////////////////////////////////////
	// Helpers

	private BcFunctionDeclaration findFunction(String name, int argsCount)
	{
		return findFunction(BcGlobal.lastBcClass, name, argsCount);
	}

	private BcFunctionDeclaration findFunction(BcClassDefinitionNode bcClass, String name, int argsCount)
	{
		BcFunctionDeclaration classFunc = bcClass.findFunction(name, argsCount);
		if (classFunc != null)
		{
			return classFunc;
		}

		return findGlobalFunction(name);
	}

	private BcFunctionDeclaration findGlobalFunction(String name)
	{
		for (BcFunctionDeclaration bcFunc : BcGlobal.bcGlobalFunctions)
		{
			if (bcFunc.getName().equals(name))
			{
				return bcFunc;
			}
		}

		return null;
	}

	private BcVariableDeclaration findDeclaredVar(String name)
	{
		failConversionUnless(BcGlobal.declaredVars != null, "Declared vars can't be 'null': %s", name);

		for (BcVariableDeclaration var : BcGlobal.declaredVars)
		{
			if (var.getIdentifier().equals(name))
				return var;
		}

		return null;
	}

	private void addToImport(BcTypeNode bcType)
	{
		if (canBeClass(bcType))
		{
			failConversionUnless(BcGlobal.lastBcClass != null, "Try to add import type without class: %s", bcType.getName());
			BcGlobal.lastBcClass.addToImport(bcType);
		}
	}

	protected List<BcVariableDeclaration> collectFieldsWithInitializer(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> bcFields = bcClass.getFields();
		List<BcVariableDeclaration> bcInitializedFields = new ArrayList<BcVariableDeclaration>();

		for (BcVariableDeclaration bcField : bcFields)
		{
			if (bcField.hasInitializer() && !isSafeInitialized(bcClass, bcField))
			{
				bcInitializedFields.add(bcField);
			}
		}

		return bcInitializedFields;
	}

	protected boolean isSafeInitialized(BcClassDefinitionNode bcClass, BcVariableDeclaration bcField)
	{
		if (bcField.isStatic() || bcField.getType().isIntegral())
		{
			return true;
		}

		Node initializerNode = bcField.getInitializerNode();
		if (initializerNode instanceof MemberExpressionNode)
		{
			MemberExpressionNode memberNode = (MemberExpressionNode) initializerNode;
			if (memberNode.selector instanceof CallExpressionNode)
			{
				CallExpressionNode callNode = (CallExpressionNode) memberNode.selector;
				ArgumentListNode args = callNode.args;
				if (args == null || args.items.isEmpty())
				{
					return true;
				}

				ObjectList<Node> argItems = args.items;
				for (Node argItem : argItems)
				{
					if (argItem instanceof MemberExpressionNode)
					{
						IdentifierNode argIdentifier = BcNodeHelper.tryExtractIdentifier((MemberExpressionNode) argItem);
						if (argIdentifier != null)
						{
							BcVariableDeclaration bcUsedField = bcClass.findField(getCodeHelper().extractIdentifier(argIdentifier));
							if (bcUsedField != null && !bcUsedField.isStatic())
							{
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}

	protected BcCodeHelper getCodeHelper()
	{
		return codeHelper;
	}

	protected String getClassName(BcClassDefinitionNode bcClass)
	{
		return classType(bcClass.getClassType());
	}

	protected String getBaseClassName(BcClassDefinitionNode bcClass)
	{
		if (bcClass.hasExtendsType())
		{
			return classType(bcClass.getExtendsType());
		}

		return classType(BcTypeNode.typeObject);
	}

	protected BcTypeNode getBaseClassType(BcClassDefinitionNode bcClass)
	{
		if (bcClass.hasExtendsType())
		{
			return bcClass.getExtendsType();
		}

		return BcTypeNode.create(BcTypeNode.typeObject);
	}

	protected List<BcFunctionTypeNode> extractFunctionTypes(BcMetadata metadata)
	{
		List<BcMetadataNode> functions = metadata.children("FunctionType");
		List<BcFunctionTypeNode> functionTypes = new ArrayList<BcFunctionTypeNode>();

		for (BcMetadataNode funcMetadata : functions)
		{
			String callbackName = funcMetadata.attribute("callback");
			failConversionUnless(callbackName != null, "'callback' attribute is missing for 'FunctionType' metadata");

			BcFunctionDeclaration func = new BcFunctionDeclaration(callbackName);

			String returnTypeString = funcMetadata.attribute("returns");
			if (returnTypeString != null)
			{
				BcTypeNode returnType = createBcType(returnTypeString);
				func.setReturnType(returnType.createTypeInstance()); // TODO: handle qualified types
			}

			String paramsString = funcMetadata.attribute("params");
			if (paramsString != null)
			{
				String[] tokens = paramsString.split("\\s*,\\s*");
				for (String token : tokens)
				{
					int index = token.indexOf(":");
					failConversionUnless(index != -1, "Can't parse param for 'FunctionType' metadata: %s", paramsString);

					String name = token.substring(0, index);
					failConversionUnless(name.length() > 0, "Can't parse param for 'FunctionType' metadata: %s", paramsString);

					String type = token.substring(index + 1);
					failConversionUnless(type.length() > 0, "Can't parse param for 'FunctionType' metadata: %s", paramsString);

					func.addParam(new BcFuncParam(createBcType(type), getCodeHelper().identifier(name), false));
				}
			}

			boolean useByDefault = false;
			String defaultFlagString = funcMetadata.attribute("useByDefault");
			if (defaultFlagString != null)
			{
				useByDefault = Boolean.parseBoolean(defaultFlagString);
			}

			BcFunctionTypeNode funcType = new BcFunctionTypeNode(func);
			funcType.setUseByDefault(useByDefault);

			String attachedParam = funcMetadata.attribute("attach");
			if (attachedParam != null)
			{
				funcType.setAttachedParam(attachedParam);
			}

			functionTypes.add(funcType);
		}

		return functionTypes;
	}

	protected BcFunctionTypeNode getDefaultFunctionType()
	{
		return BcGlobal.lastBcClass != null && BcGlobal.lastBcClass.hasDefaultFunctionType() ? BcGlobal.lastBcClass.getDefaultFunctionType() : new BcFunctionTypeNode();
	}

	public BcTypeNode evaluateType(Node node)
	{
		return evaluateType(node, false);
	}

	public BcTypeNode evaluateType(Node node, boolean returnFuncType)
	{
		BcTypeNode type = evaluateTypeHelper(node);
		if (type instanceof BcFunctionTypeNode)
		{
			BcFunctionTypeNode funcType = (BcFunctionTypeNode) type;
			if (!funcType.isComplete())
			{
				funcType = getDefaultFunctionType();
			}

			return returnFuncType ? funcType : (funcType.hasReturnType() ? funcType.getReturnType() : BcTypeNode.create("void"));
		}

		return type;
	}

	private BcTypeNode evaluateTypeHelper(Node node)
	{
		if (node instanceof MemberExpressionNode)
		{
			return evaluateMemberExpression((MemberExpressionNode) node);
		}

		if (node instanceof IdentifierNode)
		{
			IdentifierNode identifier = (IdentifierNode) node;
			return findIdentifierType(getCodeHelper().extractIdentifier(identifier));
		}

		if (node instanceof LiteralNumberNode)
		{
			LiteralNumberNode numberNode = (LiteralNumberNode) node;
			return numberNode.value.indexOf('.') != -1 ? createBcType("float") : createBcType("int");
		}

		if (node instanceof LiteralStringNode)
		{
			return createBcType(BcTypeNode.typeString);
		}

		if (node instanceof LiteralBooleanNode)
		{
			return createBcType(getCodeHelper().literalBool());
		}

		if (node instanceof LiteralNullNode)
		{
			return BcNullType.instance();
		}

		if (node instanceof ListNode)
		{
			ListNode listNode = (ListNode) node;
			failConversionUnless(listNode.items.size() == 1, "Can't evaluate ListNode's type");

			return evaluateType(listNode.items.get(0));
		}

		if (node instanceof ThisExpressionNode)
		{
			failConversionUnless(BcGlobal.lastBcClass != null, "Can't evaluate 'this' expression's type: class is missing");
			return BcGlobal.lastBcClass.getClassType();
		}

		if (node instanceof SuperExpressionNode)
		{
			failConversionUnless(BcGlobal.lastBcClass != null, "Can't evaluate 'super' expression's type: class is missing");
			return BcGlobal.lastBcClass.getExtendsType();
		}

		if (node instanceof GetExpressionNode)
		{
			GetExpressionNode get = (GetExpressionNode) node;
			return evaluateType(get.expr);
		}

		if (node instanceof ArgumentListNode)
		{
			ArgumentListNode args = (ArgumentListNode) node;
			failConversionUnless(args.size() == 1, "Can't evaluate argument list type");
			return evaluateType(args.items.get(0));
		}

		if (node instanceof BinaryExpressionNode)
		{
			BinaryExpressionNode binaryNode = (BinaryExpressionNode) node;
			
			boolean returnFuncType = binaryNode.op == Tokens.LOGICALAND_TOKEN || 
					binaryNode.op == Tokens.LOGICALOR_TOKEN || 
					binaryNode.op == Tokens.EQUALS_TOKEN ||
					binaryNode.op == Tokens.NOTEQUALS_TOKEN;
			
			BcTypeNode lhsType = evaluateType(binaryNode.lhs, returnFuncType);
			BcTypeNode rhsType = evaluateType(binaryNode.rhs, returnFuncType);

			if (binaryNode.op == Tokens.LOGICALAND_TOKEN || 
				binaryNode.op == Tokens.LOGICALOR_TOKEN || 
				binaryNode.op == Tokens.EQUALS_TOKEN ||
				binaryNode.op == Tokens.NOTEQUALS_TOKEN ||
				binaryNode.op == Tokens.GREATERTHAN_TOKEN ||
				binaryNode.op == Tokens.GREATERTHANOREQUALS_TOKEN ||
				binaryNode.op == Tokens.LESSTHAN_TOKEN ||
				binaryNode.op == Tokens.LESSTHANOREQUALS_TOKEN ||
				binaryNode.op == Tokens.IS_TOKEN ||
				binaryNode.op == Tokens.IN_TOKEN)
			{
				return createBcType(BcTypeNode.typeBoolean);
			}

			if (binaryNode.op == Tokens.AS_TOKEN)
			{
				return extractBcType(binaryNode.rhs);
			}

			if (typeEquals(lhsType, BcTypeNode.typeString) || typeEquals(rhsType, BcTypeNode.typeString))
			{
				return createBcType(BcTypeNode.typeString);
			}

			if (typeEquals(lhsType, "Number") || typeEquals(rhsType, "Number"))
			{
				return createBcType("Number");
			}

			if (typeEquals(lhsType, "float") || typeEquals(rhsType, "float"))
			{
				return createBcType("float");
			}

			if (typeEquals(lhsType, "long") || typeEquals(rhsType, "long"))
			{
				return createBcType("long");
			}

			if (typeEquals(lhsType, "int") && typeEquals(rhsType, "int"))
			{
				return createBcType("int");
			}

			if (typeEquals(lhsType, "uint") && typeEquals(rhsType, "uint"))
			{
				return createBcType("uint");
			}

			if (typeEquals(lhsType, "uint") && typeEquals(rhsType, "int") || 
				typeEquals(lhsType, "int") && typeEquals(rhsType, "uint"))
			{
				return createBcType("long");
			}

			failConversion("Can't evaluate node's type: %s", node.getClass());
		}

		if (node instanceof UnaryExpressionNode)
		{
			UnaryExpressionNode unary = (UnaryExpressionNode) node;
			if (unary.expr instanceof MemberExpressionNode)
			{
				if (unary.op == Tokens.NOT_TOKEN)
				{
					return createBcType(BcTypeNode.typeBoolean);
				}
				return evaluateMemberExpression((MemberExpressionNode) unary.expr);
			}
			else if (unary.expr instanceof ListNode)
			{
				return evaluateType(unary.expr);
			}
			else
			{
				failConversion("Can't evaluate unary expression's type: %s", unary.expr.getClass());
			}
		}

		if (node instanceof CallExpressionNode)
		{
			CallExpressionNode callExpr = (CallExpressionNode) node;
			if (callExpr.expr instanceof MemberExpressionNode)
			{
				return extractBcType(callExpr.expr);
			}
			else
			{
				failConversion("Can't evaluate 'call' expression's type: %d", callExpr.expr.getClass());
			}
		}

		if (node instanceof LiteralArrayNode)
		{
			return createBcType(BcTypeNode.typeArray);
		}

		if (node instanceof ConditionalExpressionNode)
		{
			ConditionalExpressionNode conditional = (ConditionalExpressionNode) node;
			BcTypeNode thenType = evaluateType(conditional.thenexpr);
			failConversionUnless(thenType != null, "Can't evaluate 'then' conditional type");

			String classNull = getCodeHelper().literalNull();

			if (!typeEquals(thenType, classNull))
			{
				return thenType;
			}

			BcTypeNode elseType = evaluateType(conditional.elseexpr);
			failConversionUnless(elseType != null, "Conditional expression 'else' is 'null'");

			if (!typeEquals(elseType, classNull))
			{
				return elseType;
			}

			return createBcType(BcTypeNode.typeObject);
		}

		if (node instanceof LiteralVectorNode)
		{
			LiteralVectorNode literalVector = (LiteralVectorNode) node;
			return extractBcType(literalVector.type);
		}
		
		if (node instanceof LiteralObjectNode)
		{
			return createBcType(BcTypeNode.typeObject);
		}
		
		if (node instanceof LiteralXMLNode)
		{
			return createBcType(BcTypeNode.typeXML);
		}

		failConversion("Unable to evaluate node's type: %s", node.getClass());
		return null;
	}

	private BcTypeNode evaluateMemberExpression(MemberExpressionNode node)
	{
		BcClassDefinitionNode baseClass = BcGlobal.lastBcClass;
		boolean hasCallTarget = node.base != null;
		if (hasCallTarget)
		{
			if (node.base instanceof MemberExpressionNode)
			{
				failConversionUnless(node.base instanceof MemberExpressionNode, "Can't evaluate member expression. Base node is supposed to be a MemberExpressionNode: %s", node.base.getClass());
				BcTypeNode baseType = evaluateMemberExpression((MemberExpressionNode) node.base);

				failConversionUnless(baseType != null, "Can't evaluate member expression. Base type is 'null'");
				baseClass = baseType.getClassNode();

				failConversionUnless(baseClass != null, "Can't evaluate member expression. Base class type is 'null'");
			}
			else if (node.base instanceof ThisExpressionNode)
			{
				// we're good
			}
			else if (node.base instanceof SuperExpressionNode)
			{
				baseClass = baseClass.getExtendsType().getClassNode(); // get supertype
			}
			else if (node.base instanceof ListNode)
			{
				ListNode list = (ListNode) node.base;
				failConversionUnless(list.size() == 1, "Can't evaluate ListNode. List size is supposed to have a single item: %d", list.size());

				Node firstItem = list.items.get(0);
				if (firstItem instanceof MemberExpressionNode)
				{
					BcTypeNode baseType = evaluateMemberExpression((MemberExpressionNode) firstItem);

					failConversionUnless(baseType != null, "Can't evaluate ListNode. Base type is 'null'");
					baseClass = baseType.getClassNode();

					failConversionUnless(baseClass != null, "Can't evaluate ListNode. Base class type is 'null'");
				}
				else if (firstItem instanceof BinaryExpressionNode)
				{
					BcTypeNode baseType = evaluateType(firstItem);

					failConversionUnless(baseType != null, "Can't evaluate BinaryExpressionNode. Base type is 'null'");
					baseClass = baseType.getClassNode();

					failConversionUnless(baseClass != null, "Can't evaluate BinaryExpressionNode. Base class is 'null'");
				}
				else
				{
					failConversion("Can't evaluate ListNode. Unexpected node type: %s", firstItem.getClass());
				}
			}
			else
			{
				failConversion("Can't evaluate node's type: %s", node.base.getClass());
			}
		}

		if (node.selector instanceof SelectorNode)
		{
			SelectorNode selector = (SelectorNode) node.selector;
			if (selector.expr instanceof IdentifierNode)
			{
				IdentifierNode identifier = (IdentifierNode) selector.expr;
				int argsCount = argsCount(selector);
				BcTypeNode identifierType = findIdentifierType(baseClass, identifier, hasCallTarget, argsCount);
				if (identifierType != null)
				{
					return identifierType;
				}
				else
				{
					if (classEquals(baseClass, BcTypeNode.typeXML) || classEquals(baseClass, BcTypeNode.typeXMLList))
					{
						if (identifier.isAttr())
						{
							return createBcType(BcTypeNode.typeString);
						}

						return createBcType(BcTypeNode.typeXMLList); // dirty hack
					}
					else if (getCodeHelper().extractIdentifier(identifier).equals(BcCodeHelper.thisCallMarker))
					{
						return BcGlobal.lastBcClass.getClassType(); // this referes to the current class
					}
					else if (classEquals(baseClass, BcTypeNode.typeObject))
					{
						return createBcType(BcTypeNode.typeObject);
					}
				}
			}
			else if (selector.expr instanceof ArgumentListNode)
			{
				BcTypeNode baseClassType = baseClass.getClassType();
				if (baseClassType instanceof BcVectorTypeNode)
				{
					BcVectorTypeNode bcVector = (BcVectorTypeNode) baseClassType;
					return bcVector.getGeneric();
				}
				else if (typeEquals(baseClassType, BcTypeNode.typeXMLList))
				{
					return createBcType(BcTypeNode.typeXMLList);
				}
				else
				{
					return createBcType(BcTypeNode.typeObject); // no generics
				}
			}
			else
			{
				failConversion("Can't evaluate MemberExpressionNode. Selector's expression is unsupported: %s", selector.expr.getClass());
			}
		}
		else
		{
			failConversion("Can't evaluate MemeberExpressionNode. Selector's node is unsupported: %s", node.selector.getClass());
		}

		return null;
	}

	private BcMetadata findMetadata(Node node)
	{
		return BcGlobal.bcMetadataMap.get(node);
	}

	private BcTypeNode findIdentifierType(BcClassDefinitionNode baseClass, IdentifierNode identifier, boolean hasCallTarget, int argsCount)
	{
		if (identifier.isAttr())
		{
			return createBcType(BcTypeNode.typeString); // hack
		}

		BcTypeName typeName = getCodeHelper().extractTypeName(identifier);

		// check if it's class
		BcClassDefinitionNode bcClass = findClass(typeName);
		if (bcClass != null)
		{
			return bcClass.getClassType();
		}

		if (BcCodeHelper.isBasicType(typeName))
		{
			return createBcType(typeName);
		}

		// search as identifier
		String name = typeName.getName();

		// search for local variable
		if (!hasCallTarget && BcGlobal.lastBcFunction != null)
		{
			BcVariableDeclaration bcVar = BcGlobal.lastBcFunction.findVariable(name);
			if (bcVar != null) return bcVar.getType();
		}
		// search for function
		BcFunctionDeclaration bcFunc = baseClass.findFunction(name, argsCount);
		if (bcFunc != null || 
		   (argsCount != -1 && (bcFunc = baseClass.findFunction(name, -1)) != null) || 
		   (bcFunc = findGlobalFunction(name)) != null)
		{
			return new BcFunctionTypeNode(bcFunc);
		}
		// search for field
		BcVariableDeclaration bcField = baseClass.findField(name);
		if (bcField != null)
		{
			return bcField.getType();
		}
		
		if (identifier.name.equals("arguments"))
		{
			return BcArgumentsType.instance();
		}

		return null;
	}

	private BcTypeNode extractBcType(Node node)
	{
		BcTypeNode bcType = BcNodeHelper.extractBcType(node);
		if (bcType instanceof BcVectorTypeNode)
		{
			BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;

			BcClassDefinitionNode vectorClass = findClass(BcTypeNode.typeVector);
			BcClassDefinitionNode vectorGenericClass = vectorType.createGenericClass(vectorClass);
			vectorGenericClass.setClassType(bcType);

			vectorType.setClassNode(vectorGenericClass);
			BcTypeNode genericType = vectorType.getGeneric();
			if (genericType instanceof BcFunctionTypeNode)
			{
				vectorType.setGeneric(getDefaultFunctionType());
			}
			
			return vectorType;
		}
		
		if (bcType instanceof BcFunctionTypeNode)
		{
			return getDefaultFunctionType();
		}

		return bcType;
	}

	private boolean isTypeQualified(Node type)
	{
		return BcNodeHelper.isTypeQualified(type);
	}

	private BcArgumentsList getArgs(ArgumentListNode args)
	{
		return args.items == null ? new BcArgumentsList() : getArgs(args.items);
	}

	private BcArgumentsList getArgs(ObjectList<Node> items)
	{
		BcArgumentsList argsList = new BcArgumentsList(items.size());

		for (Node arg : items)
		{
			ListWriteDestination argDest = new ListWriteDestination();
			pushDest(argDest);

			process(arg);

			popDest();
			argsList.add(argDest);
		}

		return argsList;
	}

	private void writeNewLiteralArray(ObjectList<Node> args)
	{
		WriteDestination elementDest = new ListWriteDestination();
		pushDest(elementDest);
		int elementIndex = 0;
		for (Node elementNode : args)
		{
			process(elementNode);
			if (++elementIndex < args.size())
			{
				dest.write(", ");
			}
		}
		popDest();

		dest.writef(construct(type(BcTypeNode.typeArray), elementDest));
	}

	private void writeNewLiteralVector(BcVectorTypeNode vectorType, ObjectList<Node> args)
	{
		if (args == null)
		{
			dest.write(construct(vectorType.createTypeInstance()));
		}
		else
		{
			// check if vector is initialized with literal array (in that case we should cast each arg to generic type)
			if (args.size() == 1 && args.size() == 1 && args.get(0) instanceof LiteralArrayNode)
			{
				LiteralArrayNode arrayNode = (LiteralArrayNode) args.get(0);
				BcTypeNode genericType = vectorType.getGeneric();

				ArgumentListNode elementlist = arrayNode.elementlist;
				BcArgumentsList argsList = new BcArgumentsList(elementlist.size());

				for (Node elementNode : elementlist.items)
				{
					ListWriteDestination argDest = new ListWriteDestination();
					pushDest(argDest);
					process(elementNode);
					popDest();

					BcTypeNode argType = evaluateType(elementNode);
					if (argType != genericType)
					{
						argsList.add(cast(argDest, genericType));
					}
					else
					{
						argsList.add(argDest);
					}
				}

				dest.write(constructLiteralVector(vectorType, argsList));
			}
			else
			{
				dest.write(constructLiteralVector(vectorType, getArgs(args)));
			}
		}
	}
	
	private void writeLiteralObject(LiteralObjectNode node)
	{
		failConversionUnless(node.block == null, "Literal objects with blocks are not supported yet");
		
		ArgumentListNode fieldlist = node.fieldlist;
		failConversionUnless(fieldlist != null, "Literal object is expected to have fields list");
	
		int itemsCount = fieldlist.items.size();
		Object[] args = new Object[2 * itemsCount]; // name + value
		
		int itemIndex = 0;
		for (Node arg : fieldlist.items)
		{
			if (arg instanceof LiteralFieldNode)
			{		
				LiteralFieldNode field = (LiteralFieldNode) arg;
				
				failConversionUnless(field.block == null, "Literal objects with blocks not supported");
				failConversionUnless(field.name != null, "Literal field should have name");
				failConversionUnless(field.value != null, "Literal field should have value");
				
				ListWriteDestination nameDest = new ListWriteDestination();
				pushDest(nameDest);
				process(field.name);
				popDest();
				
				args[itemIndex++] = nameDest;
				
				ListWriteDestination valueDest = new ListWriteDestination();
				pushDest(valueDest);
				process(field.value);
				popDest();
				
				args[itemIndex++] = valueDest;
			}
		}
		
		dest.write(staticCall(type(BcTypeNode.typeObject), "createLiteralObject", args));		
	}
	
	private void writeLiteralXML(LiteralXMLNode node) 
	{
		String text = codeHelper.literalString(((LiteralStringNode)node.list.items.get(0)).value);
		dest.write(construct(BcTypeNode.typeXML, text));
	}

	protected boolean classEquals(BcClassDefinitionNode classNode, String name)
	{
		return typeEquals(classNode.getClassType(), name);
	}

	protected boolean typeOneOf(BcTypeNode type, String... names)
	{
		for (String name : names)
		{
			if (typeEquals(type, name))
			{
				return true;
			}
		}
		return false;
	}

	protected boolean typeEquals(BcTypeNode type, String name)
	{
		if (name.equals(BcTypeNode.typeVector) && type instanceof BcVectorTypeNode)
		{
			return true;
		}

		if (name.equals(BcTypeNode.typeFunction) && type instanceof BcFunctionTypeNode)
		{
			return true;
		}

		return type.getName().equals(name);
	}

	private BcTypeNode createBcType(String name)
	{
		return createBcType(name, null);
	}

	private BcTypeNode createBcType(BcTypeName typeName)
	{
		return createBcType(typeName.getName(), typeName.getQualifier());
	}

	private BcTypeNode createBcType(String name, String qualifier)
	{
		if (qualifier == null)
		{
			qualifier = tryFindPackage(name);
		}

		BcTypeNode type = BcTypeNode.create(name, qualifier);
		if (type instanceof BcFunctionTypeNode)
		{
			return BcGlobal.lastBcClass != null && BcGlobal.lastBcClass.hasDefaultFunctionType() ? BcGlobal.lastBcClass.getDefaultFunctionType() : type;
		}

		return type;
	}
	
	private BcArgumentsList createArgsList(BcFunctionDeclaration function, ObjectList<Node> args) 
	{
		BcArgumentsList argsList = new BcArgumentsList(args.size());
		
		boolean hasRestParams = function.hasRestParams();
		List<BcFuncParam> params = function.getParams();
		failConversionUnless(hasRestParams || params.size() >= args.size() , "Function args and params count doesn't match: %d >= %d. Function: %s", params.size(), args.size(), function);

		int argIndex = 0;
		for (Node arg : args)
		{
			WriteDestination argDest = new ListWriteDestination();
			pushDest(argDest);
			process(arg);
			popDest();

			BcTypeNode argType = evaluateType(arg);
			failConversionUnless(argType != null, "Unable to evaluate args's type: %s", argDest);
			
			// duh
			if (argType instanceof BcArgumentsType)
			{
				failConversionUnless(argIndex == 0, "Unexpected 'arguments' usage: %s", args);
				failConversionUnless(BcGlobal.lastBcFunction != null, "Unable to detect enclosing function: %s", function);
				return createArgsList(function, BcGlobal.lastBcFunction);
			}
			
			BcTypeNode paramType = argIndex >= params.size() && hasRestParams ? 
					BcTypeNode.create(BcTypeNode.typeObject) : 
					params.get(argIndex).getType();

			if (needExplicitCast(argType, paramType))
			{
				argsList.add(cast(argDest, argType, paramType));
			}
			else
			{
				argsList.add(argDest);
			}
			
			++argIndex;
		}

		if (function.getName().equals("hasOwnProperty") && argsList.size() == 1)
		{
			String argString = argsList.get(0).toString();
			if (argString.startsWith("\"@"))
			{
				argsList.set(0, getCodeHelper().literalString(argString.substring(2, argString.length() - 1)));
			}
		}
		return argsList;
	}

	private BcArgumentsList createArgsList(BcFunctionDeclaration targetFunction, BcFunctionDeclaration enclosingFunction) 
	{
		if (targetFunction.hasRestParams())
		{
			int paramsCount = targetFunction.paramsCount();
			if (paramsCount == 1)
			{
				BcArgumentsList list = new BcArgumentsList(enclosingFunction.paramsCount());
				List<BcFuncParam> args = enclosingFunction.getParams();
				for (BcFuncParam arg : args) {
					list.add(codeHelper.identifier(arg.getIdentifier()));
				}
				return list;
			}
			
		}
		
		throw new NotImplementedException();
	}

	private String tryFindPackage(String typeName)
	{
		if (BcCodeHelper.isIntegralType(typeName) || typeName.equals(getCodeHelper().literalNull()))
		{
			return null;
		}

		failConversionUnless(BcGlobal.lastBcImportList != null, "Can't detect type's qualifier: import list is null. Type: '%s'", typeName);

		String packageFromImport = BcGlobal.lastBcImportList.findPackage(typeName);
		if (packageFromImport != null)
		{
			return packageFromImport;
		}

		return null;
	}
	
	private int argsCount(SelectorNode selector) 
	{
		if (selector instanceof GetExpressionNode)
		{
			return 0;
		}

		if (selector instanceof CallExpressionNode)
		{
			CallExpressionNode call = (CallExpressionNode) selector;
			ArgumentListNode args = call.args;
			if (args == null)
			{
				return 0;
			}
			return args.items.size();
		}
		
		if (selector instanceof SetExpressionNode)
		{
			SetExpressionNode set = (SetExpressionNode) selector;
			if (set.args == null)
			{
				return 0;
			}
			return set.args.items.size();
		}
		
		if (selector instanceof ApplyTypeExprNode)
		{
			return -1;
		}
		
		if (selector instanceof IncrementNode)
		{
			return -1;
		}
		
		assert false;
		return -1;
	}

	private boolean canBeClass(String name)
	{
		return canBeClass(name, null);
	}

	private boolean canBeClass(String name, String packageName)
	{
		return findClass(name, packageName) != null;
	}

	protected boolean canBeClass(BcTypeNode type)
	{
		return canBeClass(type.getName(), type.getQualifier());
	}

	protected String typeDefault(BcTypeNode type)
	{
		if (type.isIntegral())
		{
			if (typeEquals(type, BcTypeNode.typeBoolean))
			{
				return "false";
			}

			return "0";
		}

		return getCodeHelper().literalNull();
	}

	private boolean needExplicitCast(BcTypeNode fromType, BcTypeNode toType)
	{
		if (fromType.isIntegral() && toType.isIntegral())
		{
			if ((typeEquals(fromType, "float") || typeEquals(fromType, "Number") || typeEquals(fromType, "long")) && (typeEquals(toType, "int") || typeEquals(toType, "uint")))
			{
				return true;
			}

			if (typeEquals(fromType, "int") && typeEquals(toType, "uint"))
			{
				return true;
			}

			if (typeEquals(fromType, "uint") && typeEquals(toType, "int"))
			{
				return true;
			}
		}

		if (typeEquals(fromType, BcTypeNode.typeObject))
		{
			return true;
		}

		if (toType.isIntegral() && typeEquals(fromType, BcTypeNode.typeString))
		{
			return true;
		}

		if (!toType.isIntegral() && typeEquals(fromType, BcTypeNode.typeObject) && !typeEquals(toType, BcTypeNode.typeObject))
		{
			return true;
		}

		return false;
	}

	protected boolean isPlatformClass(BcClassDefinitionNode bcClass)
	{
		return BcGlobal.bcPlatformClasses.contains(bcClass);
	}

	protected boolean isKindOfClass(BcClassDefinitionNode childClass, BcClassDefinitionNode parentClass)
	{
		BcClassDefinitionNode bcClass = childClass;
		while (bcClass.hasExtendsType())
		{
			if (bcClass == parentClass)
			{
				return true;
			}
			bcClass = bcClass.getExtendsType().getClassNode();
		}

		return false;
	}

	private String cast(Object expression, BcTypeNode fromType, BcTypeNode toType)
	{
		if (toType.isIntegral() && typeEquals(fromType, BcTypeNode.typeString))
		{
			return parseString(expression, toType);
		}

		if (toType.isClass())
		{
			if (fromType.isClass())
			{
				return castClass(expression, fromType, toType);
			}
			else if (fromType.isInterface())
			{
				return castInterface(expression, fromType, toType);
			}
			else
			{
				failConversion("Can't make a class cast from '%s' to '%s': %s", fromType.getName(), toType.getName(), expression);
			}
		}

		return cast(expression, toType);
	}

	/* code helper */

	public static final String TYPE_PREFIX = "As";

	private static final BcArgumentsList emptyInitializer = new BcArgumentsList();

	public abstract String construct(String type, Object initializer);
	public abstract String operatorIs(Object lhs, Object rhs);

	public abstract String thisSelector(BcClassDefinitionNode bcClass, Object selector);
	public abstract String superSelector(BcClassDefinitionNode bcClass, Object selector);

	public abstract String toString(Object expr);

	protected abstract String vectorType(BcVectorTypeNode vectorType);
	protected abstract String restType(BcRestTypeNode type);
	protected abstract String wildCardType(BcWildcardTypeNode type);
	
	public abstract String constructVector(BcVectorTypeNode vectorType, BcArgumentsList args);
	public abstract String constructLiteralVector(BcVectorTypeNode vectorType, BcArgumentsList args);

	public String type(BcTypeNodeInstance bcTypeInstance)
	{
		String typeName = type(bcTypeInstance.getType());
		if (bcTypeInstance.isQualified())
		{
			return bcTypeInstance.getQualified() + "." + typeName;
		}

		return typeName;
	}

	public String type(BcTypeNode bcType)
	{
		if (bcType instanceof BcFunctionTypeNode)
		{
			BcFunctionTypeNode funcType = (BcFunctionTypeNode) bcType;
			bcType = funcType.isComplete() ? funcType : getDefaultFunctionType();
		}
		
		if (bcType instanceof BcRestTypeNode)
		{
			return restType((BcRestTypeNode) bcType);
		}
		
		if (bcType instanceof BcWildcardTypeNode)
		{
			return wildCardType((BcWildcardTypeNode) bcType);
		}
		
		String typeName = bcType.getName();
		if (bcType instanceof BcVectorTypeNode)
		{
			return vectorType((BcVectorTypeNode) bcType);
		}
		
		return type(typeName);
	}

	public String type(String name)
	{
		if (name.equals(BcTypeNode.typeFunction))
		{
			name = getDefaultFunctionType().getName();
		}

		String basic = BcCodeHelper.findBasicType(name);
		if (basic != null)
		{
			return basic;
		}

		return classType(name);
	}

	protected String classType(BcTypeNode type)
	{
		return classType(type.getName());
	}
	
	protected String classType(String name)
	{
		if (name.startsWith(TYPE_PREFIX))
		{
			return name;
		}
		return TYPE_PREFIX + name;
	}

	public String construct(BcTypeNodeInstance typeInstance)
	{
		return construct(typeInstance, emptyInitializer);
	}

	public String construct(BcTypeNodeInstance typeInstance, BcArgumentsList argsList)
	{
		BcTypeNode type = typeInstance.getType();
		if (type instanceof BcVectorTypeNode)
		{
			return constructVector((BcVectorTypeNode) type, argsList);
		}
		return construct(type(typeInstance), argsList);
	}

	public String parseString(Object expr, BcTypeNode exprType)
	{
		String typeString = type(exprType);
		typeString = Character.toUpperCase(typeString.charAt(0)) + typeString.substring(1);
		return staticCall(classType(BcTypeNode.typeString), "parse" + typeString, expr);
	}

	public String varDecl(BcTypeNodeInstance typeInstance, String identifier)
	{
		return String.format("%s %s", type(typeInstance), getCodeHelper().identifier(identifier));
	}

	public String paramsDef(List<BcFuncParam> params)
	{
		StringBuilder buffer = new StringBuilder();

		int paramIndex = 0;
		for (BcVariableDeclaration bcParam : params)
		{
			buffer.append(paramDecl(bcParam.getTypeInstance(), bcParam.getIdentifier()));
			if (++paramIndex < params.size())
			{
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}

	public String argsDef(List<BcFuncParam> params)
	{
		StringBuilder buffer = new StringBuilder();

		int paramIndex = 0;
		for (BcVariableDeclaration bcParam : params)
		{
			buffer.append(getCodeHelper().identifier(bcParam.getIdentifier()));
			if (++paramIndex < params.size())
			{
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}

	public String paramDecl(BcTypeNodeInstance typeInstance, String identifier)
	{
		return varDecl(typeInstance, identifier);
	}

	public String selector(BcClassDefinitionNode bcClass, Object funcExp)
	{
		return funcExp.toString();
	}

	public String memberSelector(Object target, Object selector)
	{
		return String.format("%s.%s", target, selector);
	}

	public String staticSelector(Object target, Object selector)
	{
		return memberSelector(target, selector);
	}

	public String staticCall(Object target, Object selector, Object... args)
	{
		return staticSelector(target, String.format("%s(%s)", selector, BcStringUtils.commaSeparated(args)));
	}

	public String memberCall(Object target, Object selector, Object... args)
	{
		return memberSelector(target, String.format("%s(%s)", selector, BcStringUtils.commaSeparated(args)));
	}

	public String indexerGetter(Object expr)
	{
		failConversionUnless(expr != null, "Indexer getter's expression can't be 'null'");
		return String.format("[%s]", expr);
	}

	public String indexerSetter(Object expr, Object value)
	{
		failConversionUnless(expr != null, "Indexer setter's expression can't be 'null'");
		return String.format("[%s] = %s", expr, value);
	}

	public String propertyGetter(Object expr)
	{
		return indexerGetter(expr);
	}

	public String propertySetter(Object expr, Object value)
	{
		return indexerSetter(expr, value);
	}

	public String cast(Object expr, BcTypeNode type)
	{
		return String.format("(%s)(%s)", type(type), expr);
	}

	public String castClass(Object expr, BcTypeNode fromType, BcTypeNode toType)
	{
		return cast(expr, toType);
	}

	public String castInterface(Object expr, BcTypeNode fromType, BcTypeNode toType)
	{
		return cast(expr, toType);
	}

	public String catchClause(ListWriteDestination paramDest)
	{
		return String.format("catch (%s)", paramDest);
	}

	public String throwStatment(Object expr)
	{
		return "throw " + expr;
	}

	protected void failConversion(String format, Object... args)
	{
		failConversionUnless(false, format, args);
	}

	protected void failConversionUnless(boolean condition, String format, Object... args)
	{
		if (!condition)
		{
			String message = new Formatter().format(format, args).toString();
			String className = BcGlobal.lastBcClass != null ? BcGlobal.lastBcClass.toString() : null;
			String functionName = BcGlobal.lastBcFunction != null ? BcGlobal.lastBcFunction.toString() : null;
			String fullErrorMessage = String.format("Conversion failed:\n\treason: %s\n\tclass: %s\n\tfunction: %s", message, className, functionName);
			throw new ConverterException(fullErrorMessage);
		}
	}
	
	private InputStream preprocess(File file) throws IOException 
	{
		try {
			List<String> lines = readFile(file);
			List<String> preprocessedLines = new Preprocessor().process(lines);
			return toInputStream(preprocessedLines);
		} catch (PreprocessorException e) {
			throw new IOException(e.getMessage());
		}
	}

	private InputStream toInputStream(List<String> lines)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bos);
		for (String line : lines) 
		{
			out.println(line);
		}
		out.close();
		byte[] byteArray = bos.toByteArray();
		return new ByteArrayInputStream(byteArray);
	}
}