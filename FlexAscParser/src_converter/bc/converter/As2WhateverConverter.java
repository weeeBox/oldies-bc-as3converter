package bc.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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
import macromedia.asc.parser.LiteralNullNode;
import macromedia.asc.parser.LiteralNumberNode;
import macromedia.asc.parser.LiteralObjectNode;
import macromedia.asc.parser.LiteralRegExpNode;
import macromedia.asc.parser.LiteralStringNode;
import macromedia.asc.parser.LiteralVectorNode;
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
import bc.help.BcCodeHelper;
import bc.help.BcNodeHelper;
import bc.help.BcStringUtils;
import bc.lang.BcArgumentsList;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcFunctionTypeNode;
import bc.lang.BcImportList;
import bc.lang.BcInterfaceDefinitionNode;
import bc.lang.BcMetadata;
import bc.lang.BcMetadataNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVariableDeclaration;
import bc.lang.BcVectorTypeNode;
import bc.lang.BcWildcardTypeNode;

public abstract class As2WhateverConverter
{
	private WriteDestination dest;
	private Stack<WriteDestination> destStack;
	
	private String lastBcPath;
	private BcClassDefinitionNode lastBcClass;
	private BcFunctionDeclaration lastBcFunction;
	private BcImportList lastBcImportList;
	
	protected static final String internalFieldInitializer = "__internalInitializeFields";
	
	protected static final String classGlobal = "Global";
	protected static final String classObject = "Object";
	protected static final String classString = "String";
	protected static final String classVector = "Vector";
	protected static final String classArray = "Array";
	protected static final String classDictionary = "Dictionary";
	protected static final String classFunction = "Function";
	protected static final String classXML = "XML";
	protected static final String classXMLList = "XMLList";
	protected static final String classBoolean = "Boolean";
	
	private List<BcVariableDeclaration> declaredVars;
	
	private List<BcClassDefinitionNode> bcPlatformClasses;
	private List<BcClassDefinitionNode> bcApiClasses;
	private List<BcClassDefinitionNode> bcClasses;
	private List<BcFunctionDeclaration> bcGlobalFunctions;
	
	private Map<DefinitionNode, BcMetadata> bcMetadataMap;
	
	protected boolean needFieldsInitializer;
	private BcCodeHelper codeHelper;

	protected abstract void writeForeach(WriteDestination dest, Object loopVarName, BcTypeNode loopVarType, Object collection, BcTypeNode collectionType, Object body);
	
	public As2WhateverConverter(BcCodeHelper codeHelper)
	{
		bcGlobalFunctions = new ArrayList<BcFunctionDeclaration>();
		bcMetadataMap = new HashMap<DefinitionNode, BcMetadata>();
		
		this.codeHelper = codeHelper;
	}
	
	public void convert(File outputDir, String... filenames) throws IOException
	{
		bcPlatformClasses = collect("bc-platform/src");
		bcApiClasses = collect("bc-api/src");
		bcClasses = collect(filenames);
		lastBcPath = null;		
		
		process();
		
		write(new File(outputDir, "Platform"), bcPlatformClasses);
		write(new File(outputDir, "Api"), bcApiClasses);
		write(new File(outputDir, "Converted"), bcClasses);
	}
	
	private List<BcClassDefinitionNode> collect(String... filenames) throws IOException
	{		
		bcClasses = new ArrayList<BcClassDefinitionNode>();
		for (int i = 0; i < filenames.length; ++i)
		{
			collect(new File(filenames[i]));
		}
		return bcClasses;
	}
	
	private void collect(File file) throws IOException
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
						return !filename.equals(".svn");
					
					return filename.endsWith(".as");
				}
			});
			
			for (File child : files) 
			{
				collect(child);
			}
		}
		else
		{
			collectSource(file);
		}
		
	}
	
	private void collectSource(File file) throws IOException
	{
		lastBcPath = file.getPath();
		lastBcImportList = new BcImportList();
		
		ContextStatics statics = new ContextStatics();
		Context cx = new Context(statics);
		FileInputStream in = new FileInputStream(file);		
		Parser parser = new Parser(cx, in, lastBcPath);

		ProgramNode programNode = parser.parseProgram();
		in.close();
		
		bcMetadataMap.clear();		
		
		for (Node node : programNode.statements.items)
		{
			if (node instanceof InterfaceDefinitionNode)
			{
				bcClasses.add(collect((InterfaceDefinitionNode) node));
			}
			else if (node instanceof ClassDefinitionNode)
			{
				bcClasses.add(collect((ClassDefinitionNode) node));
			}
			else if (node instanceof MetaDataNode)
			{
				MetaDataNode metadata = (MetaDataNode) node;
				if (metadata.def != null)
				{
					BcMetadata bcMetadata = BcNodeHelper.extractBcMetadata(metadata);
					failConversionUnless(bcMetadata != null, "Can't parse top level metadata");
					
					bcMetadataMap.put(metadata.def, bcMetadata);
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
				String packageName = packageIdentifierNode.pkg_part;
				
				boolean added = lastBcImportList.add(typeName, packageName);
				failConversionUnless(added, "Duplicate import directive: %s.%s", packageName, typeName);
			}
			else if (node instanceof PackageDefinitionNode)
			{
				// nothing
			}
			else if (node instanceof FunctionDefinitionNode)
			{
				BcFunctionDeclaration bcFunc = collect((FunctionDefinitionNode)node);
				bcFunc.setGlobal();
				bcGlobalFunctions.add(bcFunc);
			}
		}
	}

	private BcInterfaceDefinitionNode collect(InterfaceDefinitionNode interfaceDefinitionNode)
	{
		String interfaceDeclaredName = getCodeHelper().identifier(interfaceDefinitionNode.name);
		
		declaredVars = new ArrayList<BcVariableDeclaration>();
		
		BcTypeNode interfaceType = createBcType(interfaceDeclaredName);
		BcInterfaceDefinitionNode bcInterface = new BcInterfaceDefinitionNode(interfaceType);
		
		String packageName = BcNodeHelper.tryExtractPackageName(interfaceDefinitionNode);
		
		bcInterface.setPackageName(packageName);
		bcInterface.setDeclaredVars(declaredVars);
		bcInterface.setImportList(lastBcImportList);
		
		lastBcClass = bcInterface;
		
		BcMetadata metadata = findMetadata(interfaceDefinitionNode);
		if (metadata != null)
		{
			collectClassMetadata(bcInterface, metadata);
		}
		
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
		lastBcClass = null;
		
		return bcInterface;
	}
	
	private BcClassDefinitionNode collect(ClassDefinitionNode classDefinitionNode)
	{
		String classDeclaredName = getCodeHelper().identifier(classDefinitionNode.name);
		declaredVars = new ArrayList<BcVariableDeclaration>();
		
		BcTypeNode classType = createBcType(classDeclaredName);
		BcClassDefinitionNode bcClass = new BcClassDefinitionNode(classType);
		bcClass.setFinal(BcNodeHelper.isFinal(classDefinitionNode));
		
		String packageName = BcNodeHelper.tryExtractPackageName(classDefinitionNode);
		
		bcClass.setPackageName(packageName);
		bcClass.setDeclaredVars(declaredVars);
		bcClass.setImportList(lastBcImportList);
		
		lastBcClass = bcClass;
		
		// collect metadata
		BcMetadata classMetadata = findMetadata(classDefinitionNode);
		if (classMetadata != null)
		{
			collectClassMetadata(bcClass, classMetadata);
		}

		// super type
		Node baseclass = classDefinitionNode.baseclass;
		if (baseclass == null)
		{
			BcTypeNode typeObject = createBcType(classObject);
			if (classType != typeObject)
			{
				bcClass.setExtendsType(typeObject);
			}
		}
		else
		{
			BcTypeNode bcSuperType = extractBcType(baseclass);
			bcClass.setExtendsType(bcSuperType);
		}
		
		// interfaces
		if (classDefinitionNode.interfaces != null)
		{
			for (Node interfaceNode : classDefinitionNode.interfaces.items)
			{
				BcTypeNode interfaceType = extractBcType(interfaceNode);
				bcClass.addInterface(interfaceType);
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
				bcClass.add(collect((VariableDefinitionNode)node));
			}
			else if (node instanceof MetaDataNode)
			{
				MetaDataNode metadata = (MetaDataNode) node;
				if (metadata.def != null)
				{
					BcMetadata bcMetadata = BcNodeHelper.extractBcMetadata(metadata);
					failConversionUnless(bcMetadata != null, "Failed to extract metadata");
					
					bcMetadataMap.put(metadata.def, bcMetadata);
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
		
		lastBcClass = null;
		declaredVars = null;
		
		return bcClass;
	}

	protected void collectClassMetadata(BcClassDefinitionNode bcClass, BcMetadata bcMetadata)
	{
		bcClass.setMetadata(bcMetadata);
		
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
		String bcIdentifier = getCodeHelper().identifier(varBindNode.variable.identifier);	
		BcVariableDeclaration bcVar = new BcVariableDeclaration(bcType, bcIdentifier);
		bcVar.setConst(node.kind == Tokens.CONST_TOKEN);
		bcVar.setModifiers(BcNodeHelper.extractModifiers(varBindNode.attrs));		

		bcVar.setInitializerNode(varBindNode.initializer);
		
		declaredVars.add(bcVar);
		
		return bcVar;
	}
	
	private BcFunctionDeclaration collect(FunctionDefinitionNode functionDefinitionNode)
	{
		FunctionNameNode functionNameNode = functionDefinitionNode.name;
		String name = getCodeHelper().identifier(functionNameNode.identifier);
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
		else if(functionNameNode.kind == Tokens.SET_TOKEN)
		{
			bcFunc.setSetter();
		}
		
		boolean isConstructor = lastBcClass != null && name.equals(lastBcClass.getName());
		bcFunc.setConstructorFlag(isConstructor);
	
		bcFunc.setModifiers(BcNodeHelper.extractModifiers(functionDefinitionNode.attrs));		
	
		ArrayList<BcVariableDeclaration> declaredVars = new ArrayList<BcVariableDeclaration>();
		bcFunc.setDeclaredVars(declaredVars);
		
		// get function params
		ParameterListNode parameterNode = functionDefinitionNode.fexpr.signature.parameter;
		if (parameterNode != null)
		{
			ObjectList<ParameterNode> params = parameterNode.items;
			for (ParameterNode param : params)
			{
				BcTypeNode paramType = extractBcType(param);
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
				
				BcFuncParam bcParam = new BcFuncParam(paramType, getCodeHelper().identifier(paramName));				
				if (param.init != null)
				{
					bcParam.setDefaultInitializer(param.init);
				}
				
				bcFunc.addParam(bcParam);
				declaredVars.add(bcParam);
			}
		}
		
		// get function return type
		Node returnTypeNode = functionDefinitionNode.fexpr.signature.result;
		if (returnTypeNode != null)
		{
			BcTypeNode bcReturnType = extractBcType(returnTypeNode);
			bcFunc.setReturnType(bcReturnType);
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
				
		process(new ArrayList<BcClassDefinitionNode>(bcPlatformClasses));
		process(bcApiClasses);
		process(bcClasses);
		postProcess(bcClasses);
	}

	private void process(List<BcClassDefinitionNode> classes) 
	{
		for (BcClassDefinitionNode bcClass : classes)
		{
			if (bcClass.hasMetadata() && bcClass.getMetadata().contains("IgnoreConversion"))
			{
				System.err.println("Skipped class: " + bcClass.getName());
				continue;
			}
			
			bcMembersTypesStack = new Stack<BcTypeNode>();
			dest = new ListWriteDestination();
			destStack = new Stack<WriteDestination>();
			
			if (bcClass.isInterface())
			{
				process((BcInterfaceDefinitionNode)bcClass);
			}
			else
			{
				process(bcClass);
			}
		}
	}

	private void process(BcInterfaceDefinitionNode bcInterface)
	{
		declaredVars = bcInterface.getDeclaredVars();
	}
	
	private void process(BcClassDefinitionNode bcClass)
	{
		System.out.println("Process: " + bcClass.getName());
		
		declaredVars = bcClass.getDeclaredVars();
		lastBcClass = bcClass;

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
		
		lastBcClass = null;
		declaredVars = null;
	}
	
	private void process(BcVariableDeclaration bcVar)
	{
		BcTypeNode varType = bcVar.getType();
		String varId = bcVar.getIdentifier();
		
		dest.write(varDecl(varType, varId));
		
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
			failConversionUnless(initializerType != null, "Unable to evaluate initializer type: %s = %s", varDecl(varType, varId), initializerDest);
			
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
			process((MemberExpressionNode)node);
		else if (node instanceof SelectorNode)
			process((SelectorNode)node);
		else if (node instanceof IdentifierNode)
			process((IdentifierNode)node);
		else if (node instanceof ExpressionStatementNode)
			process((ExpressionStatementNode)node);
		else if (node instanceof VariableBindingNode)
			process((VariableBindingNode)node);
		else if (node instanceof VariableDefinitionNode)
			process((VariableDefinitionNode)node);
		else if (node instanceof LiteralNumberNode ||
				node instanceof LiteralBooleanNode ||
				node instanceof LiteralNullNode ||
				node instanceof LiteralObjectNode ||
				node instanceof LiteralStringNode ||
				node instanceof LiteralRegExpNode ||
				node instanceof LiteralArrayNode ||
				node instanceof LiteralVectorNode)
			processLiteral(node);
		else if (node instanceof BinaryExpressionNode)
			process((BinaryExpressionNode)node);
		else if (node instanceof UnaryExpressionNode)
			process((UnaryExpressionNode)node);
		else if (node instanceof IfStatementNode)
			process((IfStatementNode)node);
		else if (node instanceof ConditionalExpressionNode)
			process((ConditionalExpressionNode)node);
		else if (node instanceof WhileStatementNode)
			process((WhileStatementNode)node);
		else if (node instanceof ForStatementNode)
			process((ForStatementNode)node);
		else if (node instanceof DoStatementNode)
			process((DoStatementNode)node);
		else if (node instanceof SwitchStatementNode)
			process((SwitchStatementNode)node);		
		else if (node instanceof TryStatementNode)
			process((TryStatementNode)node);
		else if (node instanceof CatchClauseNode)
			process((CatchClauseNode)node);
		else if (node instanceof FinallyClauseNode)
			process((FinallyClauseNode)node);
		else if (node instanceof ParameterNode)
			process((ParameterNode)node);
		else if (node instanceof ReturnStatementNode)
			process((ReturnStatementNode)node);
		else if (node instanceof BreakStatementNode)
			process((BreakStatementNode)node);
		else if (node instanceof ThisExpressionNode) 
			process((ThisExpressionNode)node);
		else if (node instanceof SuperExpressionNode) 
			process((SuperExpressionNode)node);
		else if (node instanceof ThrowStatementNode)
			process((ThrowStatementNode)node);
		else if (node instanceof SuperStatementNode)
			process((SuperStatementNode)node);
		else if (node instanceof ListNode)
			process((ListNode)node);
		else if (node instanceof StatementListNode)
			process((StatementListNode)node);
		else if (node instanceof ArgumentListNode)
			process((ArgumentListNode)node);
		else if (node instanceof FunctionCommonNode)
			process((FunctionCommonNode)node);
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
	
	private void process(FunctionCommonNode node)
	{		
		failConversion("Unexpected function common node");
	}
	
	private BcVariableDeclaration process(VariableDefinitionNode node)
	{
		VariableBindingNode varBindNode = (VariableBindingNode) node.list.items.get(0);
		
		BcTypeNode varType = extractBcType(varBindNode.variable);
		addToImport(varType);
		
		String bcIdentifier = getCodeHelper().identifier(varBindNode.variable.identifier);	
		BcVariableDeclaration bcVar = new BcVariableDeclaration(varType, bcIdentifier);
		
		bcVar.setConst(node.kind == Tokens.CONST_TOKEN);
		bcVar.setModifiers(BcNodeHelper.extractModifiers(varBindNode.attrs));		
		
		dest.write(varDecl(varType, bcIdentifier));
		
		if (varBindNode.initializer != null)
		{
			ListWriteDestination initializer = new ListWriteDestination();
			pushDest(initializer);
			process(varBindNode.initializer);
			popDest();
			
			bcVar.setInitializer(initializer);
			bcVar.setIntegralInitializerFlag(BcNodeHelper.isIntegralLiteralNode(varBindNode.initializer));
			
			BcTypeNode initializerType = evaluateType(varBindNode.initializer);
			failConversionUnless(initializerType != null, "Unable to evaluate initializer's type: %s = %s", varDecl(varType, bcIdentifier), initializer);
			
			if (needExplicitCast(initializerType, varType))
			{
				dest.writef(" = %s", cast(initializer, initializerType, varType));
			}
			else
			{
				dest.write(" = " + initializer);
			}
		}
		else if (lastBcFunction != null)
		{
			dest.write(" = " + typeDefault(varType));
		}
		dest.writeln(";");
		
		declaredVars.add(bcVar);
		
		return bcVar;
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
		
		Node base = node.base;
		SelectorNode selector = node.selector;
		
		// base expression
		ListWriteDestination baseDest = new ListWriteDestination();
		if (base != null)
		{
			pushDest(baseDest);
			
			lastBcMemberType = evaluateType(base);
			failConversionUnless(lastBcMemberType != null, "Unable to evaluate base member's type");
			
			baseType = lastBcMemberType;
			
			IdentifierNode identifierNode = BcNodeHelper.tryExtractIdentifier(base);
			if (identifierNode != null && canBeClass(identifierNode.name)) // is call?
			{
				staticCall = true;
				
				ListWriteDestination baseExpr = new ListWriteDestination();
				pushDest(baseExpr);
				process(base);
				popDest();
				
				dest.write(type(baseExpr.toString()));
			}
			else
			{
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
				objectAsDictionaryCall = !typeOneOf(baseType, classVector, classDictionary, classArray, classString, classXMLList);				
			}
						
			if (typeEquals(baseType, classString) && getCodeHelper().boolSetting(BcCodeHelper.SETTING_DELEGATE_STRINGS_CALLS))
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
		else if (stringCall)	
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
			
			String funcName = getCodeHelper().identifier(funcIndentifierNode);
			if (callExpr.args != null)
			{
				dest.write(staticCall("AsString", funcName, baseDest, argsDest));
			}
			else
			{
				dest.write(staticCall("AsString", funcName, baseDest));				
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
						failConversionUnless(lastBcClass != null, "Try to use 'this' without of a class: base=%s selector=%s", baseDest, selectorDest);
						dest.write(thisSelector(lastBcClass, selectorDest));
					}
					else if (base instanceof SuperExpressionNode)
					{
						failConversionUnless(lastBcClass != null, "Try to use 'super' without of a class: base=%s selector=%s", baseDest, selectorDest);
						dest.write(superSelector(lastBcClass, selectorDest));
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
			process((DeleteExpressionNode)node);
		else if (node instanceof GetExpressionNode)
			process((GetExpressionNode)node);
		else if (node instanceof CallExpressionNode)
			process((CallExpressionNode)node);
		else if (node instanceof SetExpressionNode)
			process((SetExpressionNode)node);
		else if (node instanceof ApplyTypeExprNode)
			process((ApplyTypeExprNode)node);
		else if (node instanceof IncrementNode)
			process((IncrementNode)node);
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
				failConversionUnless(lastBcClass != null, "'get' expression might appear outside of the class: %s", expr);
				bcClass = lastBcClass;
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
						if (classEquals(bcClass, classString) && identifier.equals("length") && getCodeHelper().boolSetting(BcCodeHelper.SETTING_DELEGATE_STRINGS_CALLS))
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
							BcFunctionDeclaration bcFunction = bcClass.findFunction(identifier); // check if it's a function type
							if (bcFunction != null)
							{
								System.err.println("Warning! Function type: " + identifier);
								lastBcMemberType = createBcType(classFunction);
							}
							else if (classEquals(bcClass, classXML) || classEquals(bcClass, classXMLList))
							{
								IdentifierNode identifierNode = (IdentifierNode) node.expr;
								if (identifierNode.isAttr())
								{
									lastBcMemberType = createBcType(classString);
								}
								else
								{
									lastBcMemberType = createBcType(classXMLList);
									accessingDynamicXMLList = true;
									System.err.println("Warning! Dynamic XMLList: " + identifier);
								}
							}
							else
							{
								IdentifierNode identifierNode = (IdentifierNode) node.expr;
								if (!identifierNode.isAttr())
								{
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
			else if (typeEquals(lastBcMemberType, classXMLList))
			{
				lastBcMemberType = createBcType(classXML);
			}
			else
			{
				lastBcMemberType = createBcType(classObject);
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
		
		BcFunctionDeclaration bcFunc = findFunction(name);
		if (bcFunc != null)
		{
			return bcFunc.getReturnType();
		}
		
		return null;
	}
	
	private BcVariableDeclaration findVariable(String name)
	{
		return findVariable(lastBcClass, name);
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
		if (lastBcFunction == null)
		{
			return null;
		}
		
		return lastBcFunction.findVariable(name);
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
		
		BcTypeNode type = extractBcType(node.expr);
		failConversionUnless(type != null, "Can't detect expression's type: ", exprDest);
		
		if (node.expr instanceof IdentifierNode)
		{
			if (lastBcMemberType == null)
			{
				if (!(identifier.equals(BcCodeHelper.thisCallMarker) && identifier.equals(BcCodeHelper.thisCallMarker)))
				{
					BcFunctionDeclaration bcFunc = findFunction(identifier);
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
							addToImport(BcTypeNode.create(classGlobal));
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
						isCast = type.isIntegral() || canBeClass(type);
						if (isCast)
						{
							addToImport(type);
						}
					}
				}
			}
			else
			{
				BcClassDefinitionNode bcClass = lastBcMemberType.getClassNode();
				failConversionUnless(bcClass != null, "Class type is undefined: " + exprDest);
				
				BcFunctionDeclaration bcFunc = bcClass.findFunction(identifier);
				if (bcFunc != null)
				{
					calledFunction = bcFunc;
					
					lastBcMemberType = bcFunc.getReturnType();
					if (classEquals(bcClass, classString) && getCodeHelper().boolSetting(BcCodeHelper.SETTING_DELEGATE_STRINGS_CALLS))
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
					BcVariableDeclaration bcFuncVar = findVariable(bcClass, identifier);
					if (bcFuncVar != null)
					{
						if (typeEquals(bcFuncVar.getType(), classFunction))
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
			if (calledFunction != null && !isCast)
			{
				argsList = new BcArgumentsList(node.args.size());
				
				List<BcFuncParam> params = calledFunction.getParams();
				ObjectList<Node> args = node.args.items;
				
				failConversionUnless(params.size() >= args.size(), "Function args and params count doesn't match: %d >= %d", params.size(), args.size());
				
				int argIndex = 0;
				for (Node arg : args)
				{
					WriteDestination argDest = new ListWriteDestination();
					pushDest(argDest);
					process(arg);
					popDest();					
					
					BcTypeNode argType = evaluateType(arg);
					failConversionUnless(argType != null, "Unable to evaluate args's type: %s", argDest);
					
					BcTypeNode paramType = params.get(argIndex++).getType();
					
					if (needExplicitCast(argType, paramType))
					{
						argsList.add(cast(argDest, argType, paramType));
					}
					else
					{
						argsList.add(argDest);
					}
				}
				
				if (calledFunction.getName().equals("hasOwnProperty") && argsList.size() == 1)
				{
					String argString = argsList.get(0).toString();
					if (argString.startsWith("\"@"))
					{
						argsList.set(0, getCodeHelper().literalString(argString.substring(2, argString.length() - 1)));
					}
				}
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
			dest.write(construct(type, argsList));
		}
		else if (node.expr instanceof MemberExpressionNode && ((MemberExpressionNode) node.expr).selector instanceof ApplyTypeExprNode)
		{
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
				
				if (typeEquals(type, classString))
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
				dest.writef(staticSelector(type(classGlobal), String.format("%s(%s)", identifier, argsList)));
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
				failConversionUnless(lastBcClass != null, "'set' expression might appear outside of a class: %s", exprDest);
				bcClass = lastBcClass;
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
					BcFunctionDeclaration bcFunction = bcClass.findFunction(identifier); // check if it's a function type
					if (bcFunction != null)
					{
						System.err.println("Warning! Function type: " + identifier);
						lastBcMemberType = createBcType(classFunction);
					}
					else if (classEquals(bcClass, classXML))
					{
						IdentifierNode identifierNode = (IdentifierNode) node.expr;
						if (identifierNode.isAttribute())
						{
							lastBcMemberType = createBcType(classString);
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
				lastBcMemberType = createBcType(classObject);
				addToDictionary = true;
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
				failConversionUnless(argType != null, "Can't evaluated arg's type: %d", argsDest);
				
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
		
		String typeName = getCodeHelper().identifier((IdentifierNode)node.expr);
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
			dest.write(getCodeHelper().identifier(node));
			dest.write("\")");
		}
		else
		{
			dest.write(getCodeHelper().identifier(node));
		}
	}
	
	private void process(VariableBindingNode node)
	{
		System.err.println("Fix me!!! VariableBindingNode");
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
			writeNewLiteralArray(arrayNode.elementlist.items);			
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
			failConversion("Literal objects are not supported yet");
		}
		else 
		{
			failConversion("Unexpected literal node: %d", node.getClass());
		}
	}
	
	private void process(IfStatementNode node)
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
		
		if (node.elseactions != null)
		{
			ListWriteDestination elseDest = new ListWriteDestination();
			pushDest(elseDest);
			process(node.elseactions);
			popDest();
			dest.writeln("else");
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
		if (!typeEquals(conditionType, classBoolean))
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
			failConversionUnless(lastBcClass != null, "For each is defined outside of a class");
			
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
			failConversionUnless(collectionType != null, "Can't evaluate for each collection's type: %s", collection);
			
			lastBcClass.addToImport(collectionType);
			
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
				loopVarName = getCodeHelper().identifier(identifier);
			}
			else if (child3.expr instanceof IdentifierNode)
			{
				IdentifierNode identifier = (IdentifierNode) child3.expr;
				loopVarName = getCodeHelper().identifier(identifier);
			}
			else
			{
				failConversion("Unexpected for each statement node: %s", child3.expr.getClass());
			}
			
			BcVariableDeclaration loopVar = findDeclaredVar(loopVarName);
			failConversionUnless(loopVar != null, "Unable to find for each loop var: %s", loopVarName);
			
			BcTypeNode loopVarType = loopVar.getType();
			String loopVarString = varDecl(loopVarType, loopVarName);
			String loopVarStringGenerated = loopVarString + " = " + typeDefault(loopVar.getType()) + ";";
			
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
			
			writeForeach(dest, loopVarName, loopVarType, collection, collectionType, bodyDest);
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
					CaseLabelNode caseLabel = (CaseLabelNode)statement;
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
		addToImport(type);
		
		String identifier = getCodeHelper().identifier(node.identifier);
		
		declaredVars.add(new BcVariableDeclaration(type, identifier));		
		dest.write(paramDecl(type, identifier));
	}
	
	private void process(FinallyClauseNode node)
	{
		failConversion("'finally' statement is not supported yet. Sorry");
	}
	
	private void process(ThrowStatementNode node)
	{
		ListWriteDestination throwDest = new ListWriteDestination();
		pushDest(throwDest);
		process(node.expr);
		popDest();
		
		dest.writelnf("%s;", throwStatment(throwDest));
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
			
			if (!typeEquals(lshType, classBoolean))
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
			
			if (!typeEquals(rshType, classBoolean))
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
				if (!typeEquals(memberType, classBoolean))
				{
					dest.writef("(%s)", getCodeHelper().isNull(expr));
				}
				else
				{
					dest.writef("!(%s)", expr);
				}
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
			
			failConversionUnless(lastBcFunction != null, "'return' statemnt outside of a function: return %s", exprDest);
			failConversionUnless(lastBcFunction.hasReturnType(), "'return' statement with expression inside 'void' function: return %s", exprDest);
			
			BcTypeNode returnValueType = evaluateType(node.expr);
			failConversionUnless(returnValueType != null, "Unable to evaluate return type from expression: %s", exprDest);
			
			BcTypeNode returnType = lastBcFunction.getReturnType();
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
			String id = getCodeHelper().identifier(node.id);
			dest.write(" " + id);
		}
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
		List<BcVariableDeclaration> oldDeclaredVars = declaredVars;
		lastBcFunction = bcFunc;
		declaredVars = bcFunc.getDeclaredVars();

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
		declaredVars = oldDeclaredVars;
		lastBcFunction = null;
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
			return findClass(classObject);
		}
		
		return findClass(type.getNameEx());
	}
	
	private BcClassDefinitionNode findClass(String name)
	{
		BcClassDefinitionNode bcClass;
		if ((bcClass = findClass(bcPlatformClasses, name)) != null)
		{
			return bcClass;
		}
		
		if ((bcClass = findClass(bcApiClasses, name)) != null)
		{
			return bcClass;
		}
		
		return findClass(bcClasses, name);
	}

	private BcClassDefinitionNode findClass(List<BcClassDefinitionNode> classes, String name) 
	{
		for (BcClassDefinitionNode bcClass : classes)
		{
			if (bcClass.getClassType().getName().equals(name))
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
	
	protected void writeDestToFile(ListWriteDestination src, File file) throws IOException 
	{
		if (needUpldateFile(file, src))
		{
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
	
	///////////////////////////////////////////////////////////////
	// Helpers
	
	private BcFunctionDeclaration findFunction(String name)
	{
		return findFunction(lastBcClass, name);
	}
	
	private BcFunctionDeclaration findFunction(BcClassDefinitionNode bcClass, String name)
	{
		BcFunctionDeclaration classFunc = bcClass.findFunction(name);
		if (classFunc != null)
		{
			return classFunc;
		}
		
		return findGlobalFunction(name);
	}
	
	private BcFunctionDeclaration findGlobalFunction(String name)
	{
		for (BcFunctionDeclaration bcFunc : bcGlobalFunctions) 
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
		failConversionUnless(declaredVars != null, "Declared vars can't be 'null': %s", name);
		
		for (BcVariableDeclaration var : declaredVars)
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
			failConversionUnless(lastBcClass != null, "Try to add import type without class: %s", bcType.getName());
			lastBcClass.addToImport(bcType);
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
						IdentifierNode argIdentifier = BcNodeHelper.tryExtractIdentifier((MemberExpressionNode)argItem);
						if (argIdentifier != null)
						{
							BcVariableDeclaration bcUsedField = bcClass.findField(getCodeHelper().identifier(argIdentifier));
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
		return type(bcClass.getName());
	}
	
	protected String getBaseClassName(BcClassDefinitionNode bcClass)
	{
		if (bcClass.hasExtendsType())
		{
			return type(bcClass.getExtendsType());
		}
		
		return type(classObject);
	}
	
	protected BcTypeNode getBaseClassType(BcClassDefinitionNode bcClass)
	{
		if (bcClass.hasExtendsType())
		{
			return bcClass.getExtendsType();
		}
		
		return BcTypeNode.create(classObject);
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
				func.setReturnType(returnType);
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
					
					func.addParam(new BcFuncParam(createBcType(type), getCodeHelper().identifier(name)));
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
		return lastBcClass != null && lastBcClass.hasDefaultFunctionType() ? lastBcClass.getDefaultFunctionType() : new BcFunctionTypeNode();
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
			return findIdentifierType(getCodeHelper().identifier(identifier));
		}
		
		if (node instanceof LiteralNumberNode)
		{
			LiteralNumberNode numberNode = (LiteralNumberNode) node;
			return numberNode.value.indexOf('.') != -1 ? createBcType("float") : createBcType("int");			
		}
		
		if (node instanceof LiteralStringNode)
		{
			return createBcType(classString);
		}
		
		if (node instanceof LiteralBooleanNode)
		{
			return createBcType("BOOL");
		}
		
		if (node instanceof LiteralNullNode)
		{
			return createBcType(getCodeHelper().literalNull());
		}
		
		if (node instanceof ListNode)
		{
			ListNode listNode = (ListNode) node;
			failConversionUnless(listNode.items.size() == 1, "Can't evaluate ListNode's type");
			
			return evaluateType(listNode.items.get(0));
		}
		
		if (node instanceof ThisExpressionNode)
		{
			failConversionUnless(lastBcClass != null, "Can't evaluate 'this' expression's type: class is missing");
			return lastBcClass.getClassType();
		}
		
		if (node instanceof SuperExpressionNode)
		{
			failConversionUnless(lastBcClass != null, "Can't evaluate 'super' expression's type: class is missing");
			return lastBcClass.getExtendsType();
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
			BcTypeNode lhsType = evaluateType(binaryNode.lhs);
			BcTypeNode rhsType = evaluateType(binaryNode.rhs);
			
			if (binaryNode.op == Tokens.LOGICALAND_TOKEN || 
				binaryNode.op == Tokens.LOGICALOR_TOKEN || 
				binaryNode.op == Tokens.EQUALS_TOKEN ||
				binaryNode.op == Tokens.NOTEQUALS_TOKEN ||
				binaryNode.op == Tokens.GREATERTHAN_TOKEN ||
				binaryNode.op == Tokens.GREATERTHANOREQUALS_TOKEN ||
				binaryNode.op == Tokens.LESSTHAN_TOKEN ||
				binaryNode.op == Tokens.LESSTHANOREQUALS_TOKEN ||
				binaryNode.op == Tokens.IS_TOKEN)
			{
				return createBcType(classBoolean);
			}
			
			if (binaryNode.op == Tokens.AS_TOKEN)
			{
				return extractBcType(binaryNode.rhs);
			}
			
			if (typeEquals(lhsType, classString) || typeEquals(rhsType, classString))
			{
				return createBcType(classString);
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
					return createBcType(classBoolean);
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
			return createBcType(classArray);
		}
		
		if (node instanceof ConditionalExpressionNode)
		{
			ConditionalExpressionNode conditional = (ConditionalExpressionNode) node;
			BcTypeNode thenType = evaluateType(conditional.thenexpr);
			failConversionUnless(thenType != null, "Conditional expression 'then' is 'null'");
			
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
			
			return createBcType(classObject);
		}
		
		if (node instanceof LiteralVectorNode)
		{
			LiteralVectorNode literalVector = (LiteralVectorNode) node;
			return extractBcType(literalVector.type);
		}
		
		failConversion("Unable to evaluate node's type: %s", node.getClass());
		return null;
	}

	private BcTypeNode evaluateMemberExpression(MemberExpressionNode node)
	{
		BcClassDefinitionNode baseClass = lastBcClass;		
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
				BcTypeNode identifierType = findIdentifierType(baseClass, identifier, hasCallTarget);
				if (identifierType != null)
				{					
					return identifierType;
				}
				else 
				{
					if (classEquals(baseClass, classXML) || classEquals(baseClass, classXMLList))				
					{
						if (identifier.isAttr())
						{
							return createBcType(classString);
						}
						
						return createBcType(classXMLList); // dirty hack
					}
					else if (getCodeHelper().identifier(identifier).equals(BcCodeHelper.thisCallMarker))
					{
						return lastBcClass.getClassType(); // this referes to the current class
					}
					else if (classEquals(baseClass, classObject))
					{
						return createBcType(classObject);
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
				else if (typeEquals(baseClassType, classXMLList))
				{
					return createBcType(classXMLList);
				}
				else 
				{
					return createBcType(classObject); // no generics
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
		return bcMetadataMap.get(node);
	}
	
	private BcTypeNode findIdentifierType(BcClassDefinitionNode baseClass, IdentifierNode identifier, boolean hasCallTarget)
	{
		if (identifier.isAttr())
		{
			return createBcType(classString); // hack
		}
		
		String name = getCodeHelper().identifier(identifier);
		
		// check if it's class
		BcClassDefinitionNode bcClass = findClass(name);
		if (bcClass != null)
		{
			return bcClass.getClassType();
		}
		
		if (BcCodeHelper.isBasicType(name))
		{			
			return createBcType(name);
		}
		
		// search for local variable
		if (!hasCallTarget && lastBcFunction != null)
		{
			BcVariableDeclaration bcVar = lastBcFunction.findVariable(name);
			if (bcVar != null) return bcVar.getType();
		}				
		// search for function
		BcFunctionDeclaration bcFunc = baseClass.findFunction(name);
		if (bcFunc != null || (bcFunc = findGlobalFunction(name)) != null)
		{
			return new BcFunctionTypeNode(bcFunc); 
		}
		// search for field
		BcVariableDeclaration bcField = baseClass.findField(name);
		if (bcField != null)
		{
			return bcField.getType();
		}				
		
		return null;
	}
	
	private BcTypeNode extractBcType(Node node)
	{
		BcTypeNode bcType = BcNodeHelper.extractBcType(node);
		if (bcType instanceof BcVectorTypeNode)
		{
			BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;
			
			BcClassDefinitionNode vectorGenericClass = findClass(classVector).clone();
			vectorGenericClass.setClassType(bcType);
			bcPlatformClasses.add(vectorGenericClass);
			
			vectorType.setClassNode(vectorGenericClass);
			BcTypeNode genericType = vectorType.getGeneric();
			if (genericType instanceof BcFunctionTypeNode)
			{
				vectorType.setGeneric(getDefaultFunctionType());
			}
		}
		else if (bcType instanceof BcFunctionTypeNode)
		{
			return getDefaultFunctionType();
		}
		
		BcTypeNode.add(bcType.getNameEx(), bcType);
		
		return bcType;
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
		
		dest.writef(construct(type(classArray), elementDest));		
	}
	
	private void writeNewLiteralVector(BcVectorTypeNode vectorType, ObjectList<Node> args)
	{
		if (args == null)
		{
			dest.write(construct(vectorType));
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
		if (name.equals(classVector) && type instanceof BcVectorTypeNode)
		{
			return true;
		}
		
		return type == createBcType(name);
	}

	private BcTypeNode createBcType(String name) 
	{
		BcTypeNode type = BcTypeNode.create(name);
		if (type instanceof BcFunctionTypeNode)
		{
			return lastBcClass != null && lastBcClass.hasDefaultFunctionType() ? lastBcClass.getDefaultFunctionType() : type;
		}
		
		return type;
	}

	private boolean canBeClass(String name) 
	{
		return findClass(name) != null;
	}
	
	protected boolean canBeClass(BcTypeNode type) 
	{
		return canBeClass(type.getName());
	}
	
	protected String typeDefault(BcTypeNode type)
	{
		if (type.isIntegral())
		{
			if (typeEquals(type, classBoolean))
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
		
		if (typeEquals(fromType, classObject))
		{
			return true;
		}
		
		if (toType.isIntegral() && typeEquals(fromType, classString))
		{
			return true;
		}
		
		if (!toType.isIntegral() && typeEquals(fromType, classObject) && !typeEquals(toType, classObject))
		{
			return true;
		}
		
		return false;
	}
	
	protected boolean isPlatformClass(BcClassDefinitionNode bcClass)
	{
		return bcPlatformClasses.contains(bcClass);
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
		if (toType.isIntegral() && typeEquals(fromType, classString))
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
	public abstract String constructVector(BcVectorTypeNode vectorType, BcArgumentsList args);
	public abstract String constructLiteralVector(BcVectorTypeNode vectorType, BcArgumentsList args);
	
	public String type(BcTypeNode bcType)
	{
		if (bcType instanceof BcFunctionTypeNode)
		{
			BcFunctionTypeNode funcType = (BcFunctionTypeNode) bcType;
			bcType = funcType.isComplete() ? funcType : getDefaultFunctionType();
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
		if (name.equals(classFunction))
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
	
	
	protected String classType(String name)
	{
		if (name.startsWith(TYPE_PREFIX))
		{
			return name; 
		}
		return TYPE_PREFIX + name;
	}
	
	public String construct(BcTypeNode type)
	{
		return construct(type, emptyInitializer);
	}
	
	public String construct(BcTypeNode type, BcArgumentsList argsList)
	{
		if (type instanceof BcVectorTypeNode)
		{
			return constructVector((BcVectorTypeNode)type, argsList);
		}
		return construct(type.getName(), argsList);
	}
	
	
	public String parseString(Object expr, BcTypeNode exprType)
	{
		String typeString = type(exprType);
		typeString = Character.toUpperCase(typeString.charAt(0)) + typeString.substring(1);
		return staticCall("AsString", "parse" + typeString, expr);
	}
	
	public String varDecl(BcTypeNode type, String identifier)
	{
		return String.format("%s %s", type(type), getCodeHelper().identifier(identifier));
	}
	
	public String paramsDef(List<BcFuncParam> params)
	{
		StringBuilder buffer = new StringBuilder();
		
		int paramIndex = 0;
		for (BcVariableDeclaration bcParam : params)
		{
			buffer.append(paramDecl(bcParam.getType(), bcParam.getIdentifier()));
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
	
	public String paramDecl(BcTypeNode type, String identifier)
	{
		return varDecl(type, identifier);
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
	
	protected void failConversion(String format, Object...args)
	{
		failConversionUnless(false, format, args);
	}
	
	protected void failConversionUnless(boolean condition, String format, Object... args)
	{
		if (!condition)
		{
			String message = new Formatter().format(format, args).toString();
			String className = lastBcClass != null ? lastBcClass.getName() : null;
			String functionName = lastBcFunction != null ? lastBcFunction.getName() : null;
			throw new ConverterException(String.format("Conversion failed:\n\treason: %s\n\tclass: %s\n\tfunction: %s", message, className, functionName));
		}
	}
}