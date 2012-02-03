import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import macromedia.asc.parser.ApplyTypeExprNode;
import macromedia.asc.parser.ArgumentListNode;
import macromedia.asc.parser.BinaryExpressionNode;
import macromedia.asc.parser.BreakStatementNode;
import macromedia.asc.parser.CallExpressionNode;
import macromedia.asc.parser.CaseLabelNode;
import macromedia.asc.parser.ClassDefinitionNode;
import macromedia.asc.parser.CoerceNode;
import macromedia.asc.parser.ConditionalExpressionNode;
import macromedia.asc.parser.DeleteExpressionNode;
import macromedia.asc.parser.DoStatementNode;
import macromedia.asc.parser.ExpressionStatementNode;
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
import macromedia.asc.parser.MemberExpressionNode;
import macromedia.asc.parser.MetaDataNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.PackageDefinitionNode;
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
import bc.code.FileWriteDestination;
import bc.code.ListWriteDestination;
import bc.code.WriteDestination;
import bc.help.BcCodeCs;
import bc.help.BcNodeHelper;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcInterfaceDefinitionNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVariableDeclaration;
import bc.lang.BcVectorTypeNode;

public class Main2
{
	private static FileWriteDestination src;
	private static ListWriteDestination impl;
	
	private static WriteDestination dest;
	private static Stack<WriteDestination> destStack;
	
	private static String packageName;
	private static BcClassDefinitionNode lastBcClass;
	private static BcFunctionDeclaration lastBcFunction;
	
	private static final String internalFieldInitializer = "__internalInitializeFields";
	
	private static final String classObject = "Object";
	private static final String classString = "String";
	private static final String classVector = "Vector";
	private static final String classArray = "Array";
	private static final String classDictionary = "Dictionary";
	private static final String classFunction = "Function";
	private static final String classXML = "XML";
	private static final String classXMLList = "XMLList";
	private static final String classBoolean = "Boolean";
	
	private static List<BcVariableDeclaration> declaredVars;
	
	private static List<BcClassDefinitionNode> bcClasses;
	private static List<BcClassDefinitionNode> bcApiClasses;
	private static List<BcFunctionDeclaration> bcGlobalFunctions;
	
	private static boolean needFieldsInitializer;
	
	public static void main(String[] args)
	{
		BcFunctionDeclaration.thisCallMarker = BcCodeCs.thisCallMarker;
		BcFunctionDeclaration.superCallMarker = BcCodeCs.superCallMarker;
		
		
		File outputDir = new File(args[0]);

		String[] filenames = new String[args.length - 1];
		System.arraycopy(args, 1, filenames, 0, filenames.length);
		
		try
		{
			bcClasses = new ArrayList<BcClassDefinitionNode>();
			bcGlobalFunctions = new ArrayList<BcFunctionDeclaration>();
			collect(new File("api/src")); // collect api classes
			bcApiClasses = bcClasses;
			
			bcClasses = new ArrayList<BcClassDefinitionNode>();
			collect(filenames);
			process();
			write(outputDir, bcApiClasses);
			write(outputDir, bcClasses);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void collect(String... filenames) throws IOException
	{		
		for (int i = 0; i < filenames.length; ++i)
		{
			collect(new File(filenames[i]));
		}
	}
	
	private static void collect(File file) throws IOException
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
	
	private static void collectSource(File file) throws IOException
	{
		ContextStatics statics = new ContextStatics();
		Context cx = new Context(statics);
		FileInputStream in = new FileInputStream(file);
		Parser parser = new Parser(cx, in, file.getPath());

		ProgramNode programNode = parser.parseProgram();
		in.close();
		
//		NodePrinter printer = new NodePrinter();
//		printer.evaluate(cx, programNode);

		packageName = null;
		
		for (Node node : programNode.statements.items)
		{
			dest = new ListWriteDestination();
			destStack = new Stack<WriteDestination>();
			
			if (node instanceof InterfaceDefinitionNode)
			{
				BcInterfaceDefinitionNode bcInterface = collect((InterfaceDefinitionNode) node);
				bcClasses.add(bcInterface);
			}
			else if (node instanceof ClassDefinitionNode)
			{
				BcClassDefinitionNode bcClass = collect((ClassDefinitionNode) node);
				bcClasses.add(bcClass);
			}
			else if (node instanceof PackageDefinitionNode)
			{
				if (packageName == null)
				{
					PackageDefinitionNode packageNode = (PackageDefinitionNode) node;
					String packageNameString = packageNode.name.id.pkg_part;
					if (packageNameString.length() > 0)
					{
						packageName = packageNameString;
					}
				}
			}
			else if (node instanceof MetaDataNode || node instanceof ImportDirectiveNode)
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

	private static BcInterfaceDefinitionNode collect(InterfaceDefinitionNode interfaceDefinitionNode)
	{
		String interfaceDeclaredName = BcCodeCs.identifier(interfaceDefinitionNode.name);
		
		declaredVars = new ArrayList<BcVariableDeclaration>();
		
		BcTypeNode interfaceType = BcTypeNode.create(interfaceDeclaredName);
		BcInterfaceDefinitionNode bcInterface = new BcInterfaceDefinitionNode(interfaceType);
		bcInterface.setPackageName(packageName);
		bcInterface.setDeclaredVars(declaredVars);
		
		lastBcClass = bcInterface;
		
		
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
					assert false;
				}
			}		
		}
		lastBcClass = null;
		
		return bcInterface;
	}
	
	private static BcClassDefinitionNode collect(ClassDefinitionNode classDefinitionNode)
	{
		String classDeclaredName = BcCodeCs.identifier(classDefinitionNode.name);
		declaredVars = new ArrayList<BcVariableDeclaration>();
		
		BcTypeNode classType = BcTypeNode.create(classDeclaredName);
		BcClassDefinitionNode bcClass = new BcClassDefinitionNode(classType);
		bcClass.setPackageName(packageName);
		bcClass.setDeclaredVars(declaredVars);
		
		lastBcClass = bcClass;		

		// super type
		Node baseclass = classDefinitionNode.baseclass;
		if (baseclass == null)
		{
			BcTypeNode typeObject = BcTypeNode.create(classObject);
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
			}
			else if (node instanceof ExpressionStatementNode)
			{
				assert false : "Top level ExpressionStatementNode";
			}
			else
			{
				assert false : node.getClass();
			}
		}		
		
		lastBcClass = null;
		declaredVars = null;
		
		return bcClass;
	}

	private static BcVariableDeclaration collect(VariableDefinitionNode node)
	{
		assert node.list.items.size() == 1 : node.list.items.size();
		VariableBindingNode varBindNode = (VariableBindingNode) node.list.items.get(0);
		
		BcTypeNode bcType = extractBcType(varBindNode.variable.type);
		String bcIdentifier = BcCodeCs.identifier(varBindNode.variable.identifier);	
		BcVariableDeclaration bcVar = new BcVariableDeclaration(bcType, bcIdentifier);
		bcVar.setConst(node.kind == Tokens.CONST_TOKEN);
		bcVar.setModifiers(BcNodeHelper.extractModifiers(varBindNode.attrs));		

		bcVar.setInitializerNode(varBindNode.initializer);
		
		declaredVars.add(bcVar);
		
		return bcVar;
	}

	
	private static BcFunctionDeclaration collect(FunctionDefinitionNode functionDefinitionNode)
	{
		FunctionNameNode functionNameNode = functionDefinitionNode.name;
		String name = BcCodeCs.identifier(functionNameNode.identifier);
		BcFunctionDeclaration bcFunc = new BcFunctionDeclaration(name);
		
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
				BcFuncParam bcParam = new BcFuncParam(extractBcType(param.type), BcCodeCs.identifier(param.identifier));
				
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
	
	private static void process()
	{
		Collection<BcTypeNode> values = BcTypeNode.uniqueTypes.values();
		for (BcTypeNode type : values)
		{
			process(type);
		}
				
		process(new ArrayList<BcClassDefinitionNode>(bcApiClasses));
		process(bcClasses);
	}

	private static void process(List<BcClassDefinitionNode> classes) 
	{
		for (BcClassDefinitionNode bcClass : classes)
		{
			bcMembersTypesStack = new Stack<BcTypeNode>();
			
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

	private static void process(BcInterfaceDefinitionNode bcInterface)
	{
		declaredVars = bcInterface.getDeclaredVars();
	}
	
	private static void process(BcClassDefinitionNode bcClass)
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
			process(bcFunc);
		}
		
		lastBcClass = null;
		declaredVars = null;
	}
	
	private static void process(BcVariableDeclaration bcVar)
	{
		BcTypeNode varType = bcVar.getType();
		String varId = bcVar.getIdentifier();
		
		dest.writef("%s %s", BcCodeCs.type(varType), BcCodeCs.identifier(varId));
		
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
			assert initializerType != null;
			
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
	
	
	private static void process(Node node)
	{
		assert node != null;
		
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
				node instanceof LiteralArrayNode)
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
			assert false : node.getClass();
	}
	
	private static void process(StatementListNode statementsNode)
	{
		writeBlockOpen(dest);
		
		ObjectList<Node> items = statementsNode.items;
		for (Node node : items)
		{
			process(node);
		}
		
		writeBlockClose(dest);
	}
	
	private static void process(ArgumentListNode node)
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
	
	private static void process(FunctionCommonNode node)
	{
		System.err.println("Fix me!!! FunctionCommonNode");
		assert false;
	}
	
	private static BcVariableDeclaration process(VariableDefinitionNode node)
	{
		VariableBindingNode varBindNode = (VariableBindingNode) node.list.items.get(0);
		
		BcTypeNode varType = extractBcType(varBindNode.variable.type);
		addToImport(varType);
		
		String bcIdentifier = BcCodeCs.identifier(varBindNode.variable.identifier);	
		BcVariableDeclaration bcVar = new BcVariableDeclaration(varType, bcIdentifier);
		
		bcVar.setConst(node.kind == Tokens.CONST_TOKEN);
		bcVar.setModifiers(BcNodeHelper.extractModifiers(varBindNode.attrs));		
		
		dest.writef("%s %s", BcCodeCs.type(varType), BcCodeCs.identifier(bcIdentifier));
		
		if (varBindNode.initializer != null)
		{
			ListWriteDestination initializer = new ListWriteDestination();
			pushDest(initializer);
			process(varBindNode.initializer);
			popDest();
			bcVar.setInitializer(initializer);
			bcVar.setIntegralInitializerFlag(BcNodeHelper.isIntegralLiteralNode(varBindNode.initializer));
			
			BcTypeNode initializerType = evaluateType(varBindNode.initializer);
			assert initializerType != null;
			
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
	
	private static BcTypeNode lastBcMemberType;
	private static Stack<BcTypeNode> bcMembersTypesStack;
	
	private static void process(MemberExpressionNode node)
	{
		bcMembersTypesStack.push(lastBcMemberType);
		lastBcMemberType = null;
		
		Node base = node.base;
		SelectorNode selector = node.selector;
		
		ListWriteDestination memberDest = new ListWriteDestination();
		pushDest(memberDest);
		
		if (base != null)
		{
			ListWriteDestination baseExpr = new ListWriteDestination();
			pushDest(baseExpr);
			process(base);
			popDest();
			
			lastBcMemberType = evaluateType(base);
			assert lastBcMemberType != null;
			
			boolean staticCall = false;
			
			if (base instanceof MemberExpressionNode)
			{
				IdentifierNode identifierNode = BcNodeHelper.tryExtractIdentifier((MemberExpressionNode)base);
				staticCall = identifierNode != null && canBeClass(identifierNode.name);
			}
			
			if (staticCall)
			{
				dest.write(BcCodeCs.type(baseExpr.toString()));
			}
			else
			{
				dest.write(baseExpr);
			}
		}
		
		
		BcTypeNode baseType = lastBcMemberType;
		
		ListWriteDestination selectorDest = new ListWriteDestination();
		if (selector != null)
		{
			pushDest(selectorDest);
			process(selector);
			popDest();
		}
		
		boolean stringCall = false;
		boolean needDot = true;
		if (base != null && typeEquals(baseType, classString))
		{
			String selectorCode = selectorDest.toString();
			stringCall = !selectorCode.equals("ToString()") && !selectorCode.equals("Length");
			needDot = !stringCall;
		}
		
		if (base != null && needDot)
		{
			if (selector instanceof GetExpressionNode || 
				selector instanceof CallExpressionNode || 
				selector instanceof SetExpressionNode ||
				selector instanceof DeleteExpressionNode ||
				selector instanceof IncrementNode)
			{
				if (selector.getMode() != Tokens.LEFTBRACKET_TOKEN || selector instanceof DeleteExpressionNode)
				{
					dest.write(".");
				}
			}
			else
			{
				assert false;
			}
		}
		
		if (stringCall)	
		{
			popDest(); // member dest

			assert selector instanceof CallExpressionNode;
			CallExpressionNode callExpr = (CallExpressionNode) selector;
			
			ListWriteDestination argsDest = new ListWriteDestination();
			if (callExpr.args != null)
			{
				pushDest(argsDest);
				process(callExpr.args);
				popDest();
			}
			
			IdentifierNode funcIndentifierNode = BcNodeHelper.tryExtractIdentifier(selector);
			assert funcIndentifierNode != null;
			
			String funcName = BcCodeCs.identifier(funcIndentifierNode);
//			if (funcName.equals("charAt"))
//			{
//				dest.writef("%s[%s]", memberDest, argsDest);
//			}
			if (callExpr.args != null)
			{
				dest.writef("AsString.%s(%s, %s)", funcName, memberDest, argsDest);
			}
			else
			{
				dest.writef("AsString.%s(%s)", funcName, memberDest);
			}
		}
		else
		{
			dest.write(selectorDest);
			popDest(); // member dest
			dest.write(memberDest);
		}
		
		
		lastBcMemberType = bcMembersTypesStack.pop();
	}
	
	private static void process(SelectorNode node)
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
			assert false : node.getClass();
	}
	
	private static void process(DeleteExpressionNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();
		
		assert node.getMode() == Tokens.LEFTBRACKET_TOKEN;
		dest.writef("remove(%s)", expr);
	}
	
	private static void process(GetExpressionNode node)
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
				assert bcClass != null;
			}
			else
			{
				assert lastBcClass != null;
				bcClass = lastBcClass;
				needLocalVars = true;
			}
			
			BcClassDefinitionNode bcStaticClass = findClass(identifier);
			if (bcStaticClass != null)
			{
				lastBcMemberType = bcStaticClass.getClassType();
				assert lastBcMemberType != null;
				
				addToImport(lastBcMemberType);
			}
			else
			{
				BcVariableDeclaration localVar = needLocalVars ? findLocalVar(identifier) : null;
				if (localVar != null)
				{
					lastBcMemberType = localVar.getType();
					assert lastBcMemberType != null;
				}
				else
				{
					BcFunctionDeclaration bcFunc = bcClass.findGetterFunction(identifier);				
					if (bcFunc != null)
					{
						BcTypeNode funcType = bcFunc.getReturnType();
						assert funcType != null : identifier;
						
						lastBcMemberType = funcType;
						if (classEquals(bcClass, classString) && identifier.equals("length"))
						{
							// keep String.length property as a "Lenght" property
							identifier = Character.toUpperCase(identifier.charAt(0)) + identifier.substring(1);
						}
						else
						{
							identifier = BcCodeCs.getter(identifier);
							getterCalled = true;
						}
							
					}
					else
					{
						BcVariableDeclaration bcVar = bcClass.findField(identifier);
						if (bcVar != null)
						{
							lastBcMemberType = bcVar.getType();
							assert lastBcMemberType != null;
						}
						else
						{
							BcFunctionDeclaration bcFunction = bcClass.findFunction(identifier); // check if it's a function type
							if (bcFunction != null)
							{
								System.err.println("Warning! Function type: " + identifier);
								lastBcMemberType = BcTypeNode.create(classFunction);
							}
							else if (classEquals(bcClass, classXML))
							{
								IdentifierNode identifierNode = (IdentifierNode) node.expr;
								if (identifierNode.isAttr())
								{
									lastBcMemberType = BcTypeNode.create(classString);
								}
								else
								{
									lastBcMemberType = BcTypeNode.create(classXMLList);
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
			assert lastBcMemberType != null;
			if (lastBcMemberType instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) lastBcMemberType;
				lastBcMemberType = vectorType.getGeneric();
				assert lastBcMemberType != null;
			}
			else if (typeEquals(lastBcMemberType, classXMLList))
			{
				lastBcMemberType = BcTypeNode.create(classXML);
			}
			else
			{
				lastBcMemberType = BcTypeNode.create(classObject);
			}
		}
		else
		{
			assert false;
		}
		
		if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
		{
			dest.writef("[%s]", identifier);
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

	private static BcTypeNode findIdentifierType(String name)
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
	
	private static BcVariableDeclaration findVariable(String name)
	{
		return findVariable(lastBcClass, name);
	}

	private static BcVariableDeclaration findVariable(BcClassDefinitionNode bcClass, String name) 
	{
		BcVariableDeclaration bcVar = findLocalVar(name);
		if (bcVar != null)
		{
			return bcVar;
		}
		
		return bcClass.findField(name);
	}
	
	private static BcVariableDeclaration findLocalVar(String name)
	{
		if (lastBcFunction == null)
		{
			return null;
		}
		
		return lastBcFunction.findVariable(name);
	}

	private static void process(CallExpressionNode node)
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
		assert type != null : node.expr.getClass();
		
		if (node.expr instanceof IdentifierNode)
		{
			if (lastBcMemberType == null)
			{
				if (!(identifier.equals(BcCodeCs.thisCallMarker) && identifier.equals(BcCodeCs.thisCallMarker)))
				{
					BcFunctionDeclaration bcFunc = findFunction(identifier);
					if (bcFunc != null)
					{
						calledFunction = bcFunc;
						
						if (bcFunc.hasReturnType())
						{
							lastBcMemberType = bcFunc.getReturnType();
							assert lastBcMemberType != null;
						}
						
						isGlobalCalled = bcFunc.isGlobal();
					}
					else if (node.is_new)
					{
						BcClassDefinitionNode bcNewClass = findClass(identifier);
						assert bcNewClass != null : bcNewClass;
						BcTypeNode bcClassType = bcNewClass.getClassType();
						assert bcClassType != null : identifier;
						
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
				assert bcClass != null;
				
				BcFunctionDeclaration bcFunc = bcClass.findFunction(identifier);
				if (bcFunc != null)
				{
					calledFunction = bcFunc;
					
					lastBcMemberType = bcFunc.getReturnType();
					if (classEquals(bcClass, classString))
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
							assert false : identifier;
						}
					}
					else
					{
						assert false : identifier;
					}
				}
			}
		}
		else if (node.expr instanceof MemberExpressionNode)
		{
			lastBcMemberType = evaluateMemberExpression((MemberExpressionNode) node.expr);
			assert lastBcMemberType != null;
		}
		else
		{
			assert false : node;
		}
		
		ListWriteDestination argsDest = new ListWriteDestination();
		if (node.args != null)
		{
			pushDest(argsDest);
			
			if (calledFunction != null && !isCast)
			{
				List<BcFuncParam> params = calledFunction.getParams();
				ObjectList<Node> args = node.args.items;
				
				assert params.size() >= args.size();
				
				int argIndex = 0;
				for (Node arg : args)
				{
					WriteDestination argDest = new ListWriteDestination();
					pushDest(argDest);
					process(arg);
					popDest();					
					
					BcTypeNode argType = evaluateType(arg);
					assert argType != null;
					
					BcTypeNode paramType = params.get(argIndex).getType();
					
					if (needExplicitCast(argType, paramType))
					{
						dest.write(cast(argDest, argType, paramType));
					}
					else
					{
						dest.write(argDest);
					}
					
					if (++argIndex < args.size())
					{
						dest.write(", ");
					}
				}
			}
			else
			{
				process(node.args);
			}
			
			popDest();
		}
		
		if (node.is_new)
		{
			if (type instanceof BcVectorTypeNode)
			{
				ObjectList<Node> args = node.args != null ? node.args.items : null;
				writeNewLiteralVector((BcVectorTypeNode) type, args);
			}
			else
			{
				dest.write(BcCodeCs.construct(type, argsDest));
			}
		}
		else if (node.expr instanceof MemberExpressionNode && ((MemberExpressionNode) node.expr).selector instanceof ApplyTypeExprNode)
		{
			assert type instanceof BcVectorTypeNode : type;
			Node argNode = node.args.items.get(0);
			
			if (argNode instanceof LiteralArrayNode)
			{
				LiteralArrayNode arrayNode = (LiteralArrayNode) argNode;
				ArgumentListNode elementlist = arrayNode.elementlist;
				
				writeNewLiteralVector((BcVectorTypeNode) type, elementlist.items);
			}
			else
			{
				BcTypeNode argType = evaluateType(argNode);
				assert argType != null;
				
				ListWriteDestination argDest = new ListWriteDestination();
				pushDest(argDest);
				process(argNode);
				popDest();
				
				dest.write(cast(argDest, argType, type));
			}			
		}
		else
		{
			if (isCast)
			{			
				assert node.args != null;
				assert node.args.size() == 1;
				
				Node argNode = node.args.items.get(0);
				
				BcTypeNode argType = evaluateType(argNode);
				assert argType != null;
				
				dest.writef("(%s)", cast(argsDest, argType, type));
			}
			else if (isGlobalCalled)
			{
				dest.writef("AsGlobal.%s(%s)", identifier, argsDest);
			}
			else
			{
				dest.writef("%s(%s)", identifier, argsDest);
			}
		}
	}

	private static void process(SetExpressionNode node)
	{
		ListWriteDestination exprDest = new ListWriteDestination();
		pushDest(exprDest);
		process(node.expr);
		popDest();
		
		String identifier = exprDest.toString();
		
		boolean setterCalled = false;
		if (node.expr instanceof IdentifierNode)
		{
			BcClassDefinitionNode bcClass;
			if (lastBcMemberType != null)
			{
				bcClass = lastBcMemberType.getClassNode();
				assert bcClass != null;
			}
			else
			{
				assert lastBcClass != null;
				bcClass = lastBcClass;
			}
			
			BcFunctionDeclaration bcFunc = bcClass.findSetterFunction(identifier);
			if (bcFunc != null)
			{
				List<BcFuncParam> funcParams = bcFunc.getParams();
				BcTypeNode setterType = funcParams.get(0).getType();
				setterCalled = true;
				
				identifier = BcCodeCs.setter(identifier);
				lastBcMemberType = setterType;
			}
			else
			{
				BcVariableDeclaration bcVar = findVariable(bcClass, identifier);
				if (bcVar != null)
				{
					lastBcMemberType = bcVar.getType();
					assert lastBcMemberType != null;
				}
				else
				{
					BcFunctionDeclaration bcFunction = bcClass.findFunction(identifier); // check if it's a function type
					if (bcFunction != null)
					{
						System.err.println("Warning! Function type: " + identifier);
						lastBcMemberType = BcTypeNode.create(classFunction);
					}
					else if (classEquals(bcClass, classXML))
					{
						IdentifierNode identifierNode = (IdentifierNode) node.expr;
						if (identifierNode.isAttribute())
						{
							lastBcMemberType = BcTypeNode.create(classString);
						}
						else
						{
							assert false : identifierNode.name;
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
			assert lastBcMemberType != null;
			if (lastBcMemberType instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) lastBcMemberType;
				lastBcMemberType = vectorType.getGeneric();
				assert lastBcMemberType != null;
			}
			else
			{
				lastBcMemberType = BcTypeNode.create(classObject);
			}
		}
		else
		{
			assert false;
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
				dest.writef("[%s](%s)", identifier, argsDest);
			}
			else
			{
				dest.writef("%s(%s)", identifier, argsDest);
			}
		}
		else
		{
			assert node.args.size() == 1 : node.args.size();
			
			Node argNode = node.args.items.get(0);
			
			BcTypeNode argType = evaluateType(argNode);
			if (argType == null)
			{
				evaluateType(argNode);
			}
			assert argType != null : argNode;
			
			boolean needCast = needExplicitCast(argType, selectorType);
			
			if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
			{
				if (needCast)
				{
					dest.writef("[%s] = %s", identifier, cast(argsDest, argType, selectorType));
				}
				else
				{
					dest.writef("[%s] = %s", identifier, argsDest);
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
	
	private static void process(ApplyTypeExprNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();
		
		String typeName = BcCodeCs.identifier((IdentifierNode)node.expr);
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
				typeBuffer.append(BcCodeCs.type(genericType));
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

	private static void process(IncrementNode node)
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
	
	private static void process(IdentifierNode node)
	{
		if (node.isAttr())
		{
			dest.write("attributeValue(\"");
			dest.write(BcCodeCs.identifier(node));
			dest.write("\")");
		}
		else
		{
			dest.write(BcCodeCs.identifier(node));
		}
	}
	
	private static void process(VariableBindingNode node)
	{
		System.err.println("Fix me!!! VariableBindingNode");
	}
	
	private static void processLiteral(Node node)
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
			dest.write(BcCodeCs.NULL);
		}
		else if (node instanceof LiteralBooleanNode)
		{
			LiteralBooleanNode booleanNode = (LiteralBooleanNode) node;
			dest.write(booleanNode.value ? "true" : "false");
		}
		else if (node instanceof LiteralStringNode)
		{			
			LiteralStringNode stringNode = (LiteralStringNode) node;
			dest.writef("\"%s\"", replaceEscapes(stringNode.value));
		}
		else if (node instanceof LiteralRegExpNode)
		{
			assert false : "LiteralRegExpNode";
		}
		else if (node instanceof LiteralArrayNode)
		{
			LiteralArrayNode arrayNode = (LiteralArrayNode) node;
			writeNewLiteralArray(dest, arrayNode.elementlist.items);			
		}
		else if (node instanceof LiteralObjectNode)
		{
			assert false : "LiteralObjectNode";
		}
		else 
		{
			assert false : node.getClass();
		}
	}
	
	private static String replaceEscapes(String str)
	{
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\b", "\\\b").replace("\f", "\\\f").replace("\n", "\\\n").replace("\r", "\\\r").replace("\t", "\\\t");
	}
	
	private static void process(IfStatementNode node)
	{
		ListWriteDestination condDest = new ListWriteDestination();
		pushDest(condDest);
		process(node.condition);
		popDest();	
		
		String condString = condDest.toString();
		
		assert node.condition instanceof ListNode : node.condition;
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

	private static String createSafeConditionString(String condString, ListNode listNode) 
	{
		assert listNode.size() == 1 : listNode.size();
		Node condition = listNode.items.get(0);
		
		BcTypeNode conditionType = evaluateType(condition);
		if (!typeEquals(conditionType, classBoolean))
		{
			if (typeEquals(conditionType, "int") || typeEquals(conditionType, "uint"))
			{
				return String.format("(%s) != 0", condString);
			}
			return String.format("(%s) != null", condString);
		}
		else
		{
			return String.format("%s", condString);
		}		
	}
	
	private static void process(ConditionalExpressionNode node)
	{
		ListWriteDestination condDest = new ListWriteDestination();
		pushDest(condDest);
		process(node.condition);
		popDest();
		
		ListWriteDestination thenDest = new ListWriteDestination();
		pushDest(thenDest);
		process(node.thenexpr);
		popDest();
		
		ListWriteDestination elseDest = new ListWriteDestination();
		pushDest(elseDest);
		process(node.elseexpr);
		popDest();
		
		dest.writef("((%s) ? (%s) : (%s))", condDest, thenDest, elseDest);
	}
	
	private static void process(WhileStatementNode node)
	{
		ListWriteDestination exprDest = new ListWriteDestination();
		pushDest(exprDest);
		process(node.expr);
		popDest();
		
		String condString = exprDest.toString();
		
		assert node.expr instanceof ListNode : node.expr;
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
	
	private static void process(ForStatementNode node)
	{
		boolean isForEach = node.test instanceof HasNextNode;
		if (isForEach)
		{
			// get iterable collection expression
			assert node.initialize != null;
			assert node.initialize instanceof ListNode : node.initialize.getClass();
			
			ListNode list = (ListNode) node.initialize;
			assert list.items.size() == 2 : list.items.size();
			
			StoreRegisterNode register = (StoreRegisterNode) list.items.get(1);
			CoerceNode coerce = (CoerceNode) register.expr;
			
			ListWriteDestination collection = new ListWriteDestination();
			pushDest(collection);
			process(coerce.expr);
			popDest();
			
			BcTypeNode collectionType = evaluateType(coerce.expr);
			assert collectionType != null;
			
			assert node.statement != null;
			assert node.statement instanceof StatementListNode : node.statement.getClass();
			
			StatementListNode statements = (StatementListNode) node.statement;
			assert statements.items.size() == 2;
			
			// get iteration
			ExpressionStatementNode child1 = (ExpressionStatementNode) statements.items.get(0);
			MemberExpressionNode child2 = (MemberExpressionNode) child1.expr;
			SetExpressionNode child3 = (SetExpressionNode) child2.selector;
			String loopVarName = null;
			if (child3.expr instanceof QualifiedIdentifierNode)
			{
				QualifiedIdentifierNode identifier = (QualifiedIdentifierNode) child3.expr;
				loopVarName = BcCodeCs.identifier(identifier);
			}
			else if (child3.expr instanceof IdentifierNode)
			{
				IdentifierNode identifier = (IdentifierNode) child3.expr;
				loopVarName = BcCodeCs.identifier(identifier);
			}
			else
			{
				assert false : child3.expr.getClass();
			}
			
			BcVariableDeclaration loopVar = findDeclaredVar(loopVarName);
			assert loopVar != null : loopVarName;
			
			String loopVarString = String.format("%s %s", BcCodeCs.type(loopVar.getType()), loopVarName);
			String loopVarStringGenerated = loopVarString + " = " + typeDefault(loopVar.getType()) + ";";
			
			ListWriteDestination listDest = (ListWriteDestination) dest;
			if (listDest.peekLine().trim().equals(loopVarStringGenerated))
			{
				listDest.popLine();
			}
			
			// get loop body
			dest.writelnf("foreach (%s in %s)", loopVarString, collection);
			Node bodyNode = statements.items.get(1);
			if (bodyNode != null)
			{
				assert bodyNode instanceof StatementListNode : bodyNode.getClass();
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
	
	private static void process(DoStatementNode node)
	{
	}
	
	private static void process(SwitchStatementNode node)
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
					
					writeBlockOpen(dest);
				}
				else if (statement instanceof BreakStatementNode)
				{
					process(statement);
					writeBlockClose(dest);
				}
				else 
				{
					process(statement);
				}
			}
		}
		
		writeBlockClose(dest);
	}
	
	private static void process(TryStatementNode node)
	{
	}
	
	private static void process(ThrowStatementNode node)
	{
		
	}
	
	private static void process(BinaryExpressionNode node)
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
				lshString = String.format("(%s != null)", lshString);
			}
			
			if (!typeEquals(rshType, classBoolean))
			{
				rshString = String.format("(%s != null)", rshString);
			}
			
			dest.writef("(%s %s %s)", lshString, Tokens.tokenToString[-node.op], rshString);
		}
		else if (node.op == Tokens.IS_TOKEN)
		{
			dest.write(BcCodeCs.operatorIs(ldest, rdest));
		}
		else if (node.op == Tokens.AS_TOKEN)
		{
			assert false;
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
	
	private static void process(UnaryExpressionNode node)
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
				MemberExpressionNode memberNode = (MemberExpressionNode) node.expr;
				BcTypeNode memberType = evaluateMemberExpression(memberNode);
				if (!typeEquals(memberType, classBoolean))
				{
					dest.writef("(%s == null)", expr);
				}
				else
				{
					dest.writef("!(%s)", expr);
				}
			}
			else
			{
				assert false : node.expr;
			}
			break;
		}
		case Tokens.MINUS_TOKEN:
			dest.writef("-%s", expr);
			break;
			
		default:
			assert false : node.op;
		}
	}
	
	private static void process(ReturnStatementNode node)
	{
		assert !node.finallyInserted;
		
		dest.write("return");
		if (node.expr != null)
		{
			dest.write(" ");
			process(node.expr);
		}
		dest.writeln(";");
	}
	
	private static void process(BreakStatementNode node)
	{
		dest.write("break");
		if (node.id != null)
		{
			String id = BcCodeCs.identifier(node.id);
			dest.write(" " + id);
		}
		dest.writeln(";");
	}
	
	private static void process(ThisExpressionNode node)
	{
		dest.write("this");
	}
	
	private static void process(SuperExpressionNode node)
	{
		String extendsClass = BcCodeCs.type(lastBcClass.getExtendsType());
		dest.write(extendsClass);
	}
	
	private static void process(SuperStatementNode node)
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
		
		dest.writelnf("%s(%s);", BcCodeCs.superCallMarker, argsDest);
	}
	
	private static void process(BcFunctionDeclaration bcFunc)
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
	
	private static void process(ExpressionStatementNode node)
	{
		process(node.expr);
		dest.writeln(";");
	}
	
	private static void process(ListNode node)
	{
		ObjectList<Node> items = node.items;
		for (Node item : items)
		{
			process(item);
		}
	}
	
	private static void process(BcTypeNode typeNode)
	{
		if (!typeNode.isIntegral() && !typeNode.hasClassNode())
		{
			BcClassDefinitionNode classNode = findClass(typeNode.getNameEx());
			assert classNode != null : typeNode.getNameEx();
			typeNode.setClassNode(classNode);
		}
	}
	
	private static BcClassDefinitionNode findClass(String name)
	{
		BcClassDefinitionNode bcClass = findClass(bcApiClasses, name);
		if (bcClass != null)
		{
			return bcClass;
		}
		
		return findClass(bcClasses, name);
	}

	private static BcClassDefinitionNode findClass(List<BcClassDefinitionNode> classes, String name) 
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
	
	private static void write(File outputDir, List<BcClassDefinitionNode> classes) throws IOException
	{
		for (BcClassDefinitionNode bcClass : classes)
		{
			writeClassDefinition(bcClass, outputDir);
		}
	}
	
	private static void writeImports(WriteDestination dest, List<String> imports)	
	{
		List<String> sortedImports = new ArrayList<String>(imports);
		Collections.sort(sortedImports);
		
		for (String importString : sortedImports)
		{
			dest.writelnf("using %s;", importString);
		}
	}

	private static void writeInterfaceFunctions(BcClassDefinitionNode bcClass)
	{
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			String type = bcFunc.hasReturnType() ? BcCodeCs.typeRef(bcFunc.getReturnType()) : "void";
			String name = BcCodeCs.identifier(bcFunc.getName());
			
			if (bcFunc.isConstructor())
			{
				continue;
			}
			
			src.writef("%s %s(", type, name);
			
			StringBuilder paramsBuffer = new StringBuilder();
			StringBuilder argsBuffer = new StringBuilder();
			List<BcFuncParam> params = bcFunc.getParams();
			int paramIndex = 0;
			for (BcFuncParam bcParam : params)
			{
				String paramType = BcCodeCs.type(bcParam.getType());
				String paramName = BcCodeCs.identifier(bcParam.getIdentifier());
				paramsBuffer.append(String.format("%s %s", paramType, paramName));
				argsBuffer.append(paramName);
				if (++paramIndex < params.size())
				{
					paramsBuffer.append(", ");
					argsBuffer.append(", ");
				}
			}
			
			src.write(paramsBuffer);
			src.writeln(");");
		}
	}

	private static void writeClassDefinition(BcClassDefinitionNode bcClass, File outputDir) throws IOException
	{
		boolean isInterface = bcClass instanceof BcInterfaceDefinitionNode;
		
		String className = getClassName(bcClass);
		
		String packageName = bcClass.getPackageName();
		String subPath = packageName.replace(".", "/");
		
		File srcFileDir = new File(outputDir, subPath);
		if (!srcFileDir.exists())
		{
			boolean successed = srcFileDir.mkdirs();
			assert successed : srcFileDir.getAbsolutePath();
		}
		
		src = new FileWriteDestination(new File(srcFileDir, className + ".cs"));		
		impl = new ListWriteDestination();
		
		src.writeln("using System;");
		writeBlankLine(src);
		
		writeImports(src, getImports(bcClass));
		writeBlankLine(src);
		
		src.writeln("namespace " + BcCodeCs.namespace(bcClass.getPackageName()));
		writeBlockOpen(src);
		
		if (isInterface)
		{
			src.writelnf("public interface %s", className);
			writeBlockOpen(src);
			writeInterfaceFunctions(bcClass);
			writeBlockClose(src);
		}
		else
		{
			src.writef("public class %s", className);
			
			boolean hasExtendsType = bcClass.hasExtendsType();
			boolean hasInterfaces = bcClass.hasInterfaces();
			
			if (hasExtendsType || hasInterfaces)
			{
				src.write(" : ");
			}
			
			if (hasExtendsType)
			{
				src.write(BcCodeCs.type(bcClass.getExtendsType()));
				if (hasInterfaces)
				{
					src.write(", ");
				}
			}
			
			if (hasInterfaces)
			{
				List<BcTypeNode> interfaces = bcClass.getInterfaces();
				int interfaceIndex= 0;
				for (BcTypeNode bcInterface : interfaces) 
				{					
					String interfaceType = BcCodeCs.type(bcInterface);
					src.write(++interfaceIndex == interfaces.size() ? interfaceType : (interfaceType + ", "));
				}
			}
			
			List<BcVariableDeclaration> bcInitializedFields = collectFieldsWithInitializer(bcClass);
			needFieldsInitializer = bcInitializedFields.size() > 0;
			
			src.writeln();
			writeBlockOpen(src);
			
			writeFields(bcClass);
			if (needFieldsInitializer)
			{
				writeFieldsInitializer(bcClass, bcInitializedFields);
			}
			writeFunctions(bcClass);
			
			writeBlockClose(src);
		}		
		
		writeBlockClose(src);
		
		src.close();
	}

	private static void writeFields(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields();
		
		for (BcVariableDeclaration bcField : fields)
		{
			String type = BcCodeCs.type(bcField.getType());
			String name = BcCodeCs.identifier(bcField.getIdentifier());
						
			src.write(bcField.getVisiblity() + " ");
			
			if (bcField.isConst())
			{
				if (canBeClass(bcField.getType()))
				{
					src.write("static ");
				}
				else
				{
					src.write("const ");
				}
			}
			else if (bcField.isStatic())
			{
				src.write("static ");
			}			
			
			src.writef("%s %s", type, name);
			if (bcField.hasInitializer() && isSafeInitialized(bcClass, bcField))
			{
				src.writef(" = %s", bcField.getInitializer());
			}
			src.writeln(";");
		}
	}
	
	private static void writeFieldsInitializer(BcClassDefinitionNode bcClass, List<BcVariableDeclaration> bcFields) 
	{
		src.writelnf("private void %s()", internalFieldInitializer);
		writeBlockOpen(src);
		
		for (BcVariableDeclaration bcVar : bcFields) 
		{
			String name = BcCodeCs.identifier(bcVar.getIdentifier());
			src.writelnf("%s = %s;", name, bcVar.getInitializer());
		}
		
		writeBlockClose(src);
	}
	
	private static void writeFunctions(BcClassDefinitionNode bcClass)
	{
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			src.write(bcFunc.getVisiblity() + " ");
			if (bcFunc.isConstructor())
			{
				src.write(getClassName(bcClass));
			}			
			else
			{
				if (bcFunc.isStatic())
				{
					src.write("static ");
				}
				else if (!bcFunc.isPrivate())
				{
					src.write("virtual ");
				}
				
				String type = bcFunc.hasReturnType() ? BcCodeCs.type(bcFunc.getReturnType()) : "void";
				String name = BcCodeCs.identifier(bcFunc.getName());			
				
				if (bcFunc.isGetter())
				{
					name = BcCodeCs.getter(name);
				}
				else if (bcFunc.isSetter())
				{
					name = BcCodeCs.setter(name);
				}
				src.writef("%s %s", type, name);
			}
			
			StringBuilder paramsBuffer = new StringBuilder();
			List<BcFuncParam> params = bcFunc.getParams();
			int paramIndex = 0;
			for (BcFuncParam bcParam : params)
			{
				String paramType = BcCodeCs.type(bcParam.getType());
				String paramName = BcCodeCs.identifier(bcParam.getIdentifier());
				paramsBuffer.append(String.format("%s %s", paramType, paramName));
				if (++paramIndex < params.size())
				{
					paramsBuffer.append(", ");
				}
			}
			src.writelnf("(%s)", paramsBuffer);
			
			ListWriteDestination body = bcFunc.getBody();
			if (bcFunc.isConstructor())
			{
				writeConstructorBody(body);
			}
			else
			{
				src.writeln(body);
			}
		}
	}

	private static void writeConstructorBody(ListWriteDestination body) 
	{
		List<String> lines = body.getLines();
		String firstLine = lines.get(1).trim();
		if (firstLine.startsWith(BcCodeCs.thisCallMarker))
		{
			firstLine = firstLine.replace(BcCodeCs.thisCallMarker, "this");
			if (firstLine.endsWith(";"))
			{
				firstLine = firstLine.substring(0, firstLine.length() - 1);
			}
			
			src.writeln(" : " + firstLine);
			lines.remove(1);
		}
		else if (firstLine.startsWith(BcCodeCs.superCallMarker))
		{
			firstLine = firstLine.replace(BcCodeCs.superCallMarker, "base");
			if (firstLine.endsWith(";"))
			{
				firstLine = firstLine.substring(0, firstLine.length() - 1);
			}
			
			src.writeln(" : " + firstLine);
			lines.remove(1);
		}
		
		if (needFieldsInitializer)
		{
			lines.add(1, String.format("\t%s();", internalFieldInitializer));
		}
		
		src.writeln(new ListWriteDestination(lines));
	}
	
	private static void writeBlockOpen(WriteDestination dest)
	{
		dest.writeln("{");
		dest.incTab();
	}
	
	private static void writeBlockClose(WriteDestination dest)
	{
		dest.decTab();
		dest.writeln("}");
	}
	
	private static void writeEmptyBlock()
	{
		writeEmptyBlock(dest);
	}
	
	private static void writeBlankLine(WriteDestination dest)
	{
		dest.writeln();
	}
	
	private static void writeBlankLine()
	{
		src.writeln();
		impl.writeln();
	}
	
	private static void writeEmptyBlock(WriteDestination dest)
	{
		writeBlockOpen(dest);
		writeBlockClose(dest);
	}
	
	private static void pushDest(WriteDestination newDest)
	{
		destStack.push(dest);
		dest = newDest;
	}
	
	private static void popDest()
	{
		dest = destStack.pop();
	}
	
	///////////////////////////////////////////////////////////////
	// Helpers
	
	private static BcFunctionDeclaration findFunction(String name)
	{
		BcFunctionDeclaration classFunc = lastBcClass.findFunction(name);
		if (classFunc != null)
		{
			return classFunc;
		}
		
		return findGlobalFunction(name);
	}
	
	private static BcFunctionDeclaration findGlobalFunction(String name)
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
	
	private static BcVariableDeclaration findDeclaredVar(String name)
	{
		assert declaredVars != null;
		
		for (BcVariableDeclaration var : declaredVars)
		{
			if (var.getIdentifier().equals(name))
				return var;
		}
		
		return null;
	}
	
	private static List<String> getImports(BcClassDefinitionNode bcClass)
	{
		List<String> imports = new ArrayList<String>();
		
		if (bcClass.hasExtendsType())
		{
			tryAddUniqueNamespace(imports, bcClass.getExtendsType());
		}
		
		if (bcClass.hasInterfaces())
		{
			List<BcTypeNode> interfaces = bcClass.getInterfaces();
			for (BcTypeNode bcInterface : interfaces)
			{
				tryAddUniqueNamespace(imports, bcInterface);
			}
		}
		
		List<BcVariableDeclaration> classVars = bcClass.getDeclaredVars();
		for (BcVariableDeclaration bcVar : classVars)
		{
			BcTypeNode type = bcVar.getType();
			tryAddUniqueNamespace(imports, type);
		}
		
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			if (bcFunc.hasReturnType())
			{
				BcTypeNode returnType = bcFunc.getReturnType();
				tryAddUniqueNamespace(imports, returnType);
			}
			
			List<BcFuncParam> params = bcFunc.getParams();
			for (BcFuncParam param : params)
			{
				BcTypeNode type = param.getType();
				tryAddUniqueNamespace(imports, type);
			}
		}
		
		List<BcTypeNode> additionalImports = bcClass.getAdditionalImports();
		for (BcTypeNode bcType : additionalImports) 
		{
			tryAddUniqueNamespace(imports, bcType);
		}
		
		return imports;
	}
	
	private static void tryAddUniqueNamespace(List<String> imports, BcTypeNode type)
	{
		if (canBeClass(type))
		{
			BcClassDefinitionNode classNode = type.getClassNode();
			assert classNode != null : type.getName();
			
			String packageName = classNode.getPackageName();
			assert packageName != null : classNode.getName();
			
			if (!imports.contains(packageName))
			{
				imports.add(packageName);
			}
			
			if (type instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) type;
				BcTypeNode generic = vectorType.getGeneric();
				if (generic != null)
				{
					tryAddUniqueNamespace(imports, generic);
				}
			}
		}
	}
	
	private static void addToImport(BcTypeNode bcType) 
	{
		if (canBeClass(bcType))
		{
			assert lastBcClass != null;
			lastBcClass.addToImport(bcType);
		}
	}
	
	private static List<BcVariableDeclaration> collectFieldsWithInitializer(BcClassDefinitionNode bcClass)
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

	private static boolean isSafeInitialized(BcClassDefinitionNode bcClass, BcVariableDeclaration bcField) 
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
							BcVariableDeclaration bcUsedField = bcClass.findField(BcCodeCs.identifier(argIdentifier));
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
	
	private static String getClassName(BcClassDefinitionNode bcClass)
	{
		return BcCodeCs.type(bcClass.getName());
	}
	
	public static BcTypeNode evaluateType(Node node)
	{
		if (node instanceof MemberExpressionNode)
		{
			return evaluateMemberExpression((MemberExpressionNode) node);
		}
		
		if (node instanceof IdentifierNode)
		{
			IdentifierNode identifier = (IdentifierNode) node;
			return findIdentifierType(BcCodeCs.identifier(identifier));
		}
		
		if (node instanceof LiteralNumberNode)
		{
			LiteralNumberNode numberNode = (LiteralNumberNode) node;
			return numberNode.value.indexOf('.') != -1 ? BcTypeNode.create("float") : BcTypeNode.create("int");			
		}
		
		if (node instanceof LiteralStringNode)
		{
			return BcTypeNode.create(classString);
		}
		
		if (node instanceof LiteralBooleanNode)
		{
			return BcTypeNode.create("BOOL");
		}
		
		if (node instanceof LiteralNullNode)
		{
			return BcTypeNode.create("null");
		}
		
		if (node instanceof ListNode)
		{
			ListNode listNode = (ListNode) node;
			assert listNode.items.size() == 1;
			
			return evaluateType(listNode.items.get(0));
		}
		
		if (node instanceof ThisExpressionNode)
		{
			assert lastBcClass != null;
			return lastBcClass.getClassType();
		}
		
		if (node instanceof SuperExpressionNode)
		{
			assert lastBcClass != null;
			assert lastBcClass.hasExtendsType();
			
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
			assert args.size() == 1;
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
				return BcTypeNode.create(classBoolean);
			}
			
			if (typeEquals(lhsType, classString) || typeEquals(rhsType, classString))
			{
				return BcTypeNode.create(classString);
			}
			
			if (typeEquals(lhsType, "Number") || typeEquals(rhsType, "Number"))
			{
				return BcTypeNode.create("Number");
			}

			if (typeEquals(lhsType, "float") || typeEquals(rhsType, "float"))
			{
				return BcTypeNode.create("float");
			}
			
			if (typeEquals(lhsType, "long") || typeEquals(rhsType, "long"))
			{
				return BcTypeNode.create("long");
			}
			
			if (typeEquals(lhsType, "int") && typeEquals(rhsType, "int"))
			{
				return BcTypeNode.create("int");
			}
			
			if (typeEquals(lhsType, "uint") && typeEquals(rhsType, "uint"))
			{
				return BcTypeNode.create("uint");
			}
			
			if (typeEquals(lhsType, "uint") && typeEquals(rhsType, "int") || 
				typeEquals(lhsType, "int") && typeEquals(rhsType, "uint"))
			{
				return BcTypeNode.create("long");
			}
			
			assert false;
		}
		
		if (node instanceof UnaryExpressionNode)
		{
			UnaryExpressionNode unary = (UnaryExpressionNode) node;
			if (unary.expr instanceof MemberExpressionNode)
			{
				if (unary.op == Tokens.NOT_TOKEN)
				{
					return BcTypeNode.create(classBoolean);
				}				
				return evaluateMemberExpression((MemberExpressionNode) unary.expr);
			}
			else
			{
				assert false;
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
				assert false;
			}
		}
		
		if (node instanceof LiteralArrayNode)
		{
			return BcTypeNode.create(classArray);
		}
		
		assert false : node;
		return null;
	}

	private static BcTypeNode evaluateMemberExpression(MemberExpressionNode node)
	{
		BcClassDefinitionNode baseClass = lastBcClass;		
		boolean hasCallTarget = node.base != null;
		if (hasCallTarget)
		{
			if (node.base instanceof MemberExpressionNode)
			{
				assert node.base instanceof MemberExpressionNode;			
				BcTypeNode baseType = evaluateMemberExpression((MemberExpressionNode) node.base);
				
				assert baseType != null;
				baseClass = baseType.getClassNode();

				assert baseClass != null;
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
				assert list.size() == 1 : list.size();
				
				assert list.items.get(0) instanceof MemberExpressionNode;
				BcTypeNode baseType = evaluateMemberExpression((MemberExpressionNode) list.items.get(0));
				
				assert baseType != null;
				baseClass = baseType.getClassNode();

				assert baseClass != null;
			}
			else
			{
				assert false;
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
					if (classEquals(baseClass, classXML))				
					{
						return BcTypeNode.create(classXMLList); // dirty hack
					}
					else if (BcCodeCs.identifier(identifier).equals(BcCodeCs.thisCallMarker))
					{
						return lastBcClass.getClassType(); // this referes to the current class
					}
					else if (classEquals(baseClass, classObject))
					{
						return BcTypeNode.create(classObject);
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
				else 
				{
					return BcTypeNode.create(classObject); // no generics
				}
			}
			else
			{
				assert false;
			}
		}			
		else
		{
			assert false;
		}
		
		return null;
	}

	private static BcTypeNode findIdentifierType(BcClassDefinitionNode baseClass, IdentifierNode identifier, boolean hasCallTarget)
	{
		if (identifier.isAttr())
		{
			return BcTypeNode.create(classString); // hack
		}
		
		String name = BcCodeCs.identifier(identifier);
		
		// check if it's class
		BcClassDefinitionNode bcClass = findClass(name);
		if (bcClass != null)
		{
			return bcClass.getClassType();
		}
		
		if (BcCodeCs.isBasicType(name))
		{			
			return BcTypeNode.create(name);
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
			if (bcFunc.hasReturnType())
			{
				return bcFunc.getReturnType();
			}
			return BcTypeNode.create("void");
		}
		// search for field
		BcVariableDeclaration bcField = baseClass.findField(name);
		if (bcField != null)
		{
			return bcField.getType();
		}				
		
		return null;
	}
	
	private static BcTypeNode extractBcType(Node node)
	{
		BcTypeNode bcType = BcNodeHelper.extractBcType(node);
		if (bcType instanceof BcVectorTypeNode)
		{
			BcClassDefinitionNode vectorGenericClass = findClass(classVector).clone();
			vectorGenericClass.setClassType(bcType);
			bcApiClasses.add(vectorGenericClass);
			
			bcType.setClassNode(vectorGenericClass);
		}
		
		BcTypeNode.add(bcType.getNameEx(), bcType);
		
		return bcType;
	}
	
	private static void writeNewLiteralArray(WriteDestination dest, ObjectList<Node> args)
	{
		WriteDestination elementDest = new ListWriteDestination();
		pushDest(elementDest);
		int elementIndex = 0;
		for (Node elementNode : args)
		{
			process(elementNode);
			if (++elementIndex < args.size())
			{
				elementDest.write(", ");
			}
		}
		popDest();
		
		dest.writef("new %s(%s)", BcCodeCs.type(classArray), elementDest);		
	}
	
	private static void writeNewLiteralVector(BcVectorTypeNode vectorType, ObjectList<Node> args)
	{
		if (args == null)
		{
			dest.write(BcCodeCs.construct(vectorType));
		}
		else
		{
			// check if vector is initialized with literal array (in that case we should cast each arg to generic type)
			if (args.size() == 1 && args.size() == 1 && args.get(0) instanceof LiteralArrayNode)
			{
				LiteralArrayNode arrayNode = (LiteralArrayNode) args.get(0);
				BcTypeNode genericType = vectorType.getGeneric();
				
				ArgumentListNode elementlist = arrayNode.elementlist;
				
				WriteDestination initDest = new ListWriteDestination();
				pushDest(initDest);
				for (Node elementNode : elementlist.items)
				{
					ListWriteDestination argDest = new ListWriteDestination();
					pushDest(argDest);
					process(elementNode);
					popDest();
					
					BcTypeNode argType = evaluateType(elementNode);
					if (argType != genericType)
					{
						dest.writef("%s(%s)", BcCodeCs.type(genericType), argDest);
					}
					else
					{
						dest.write(argDest);
					}
				}
				popDest();
				
				dest.write(BcCodeCs.construct(vectorType, initDest));
			}
			else
			{
				ListWriteDestination argsDest = new ListWriteDestination();
				pushDest(argsDest);
				
				int itemIndex = 0;
				for (Node arg : args)
				{
					process(arg);
					
					if (++itemIndex < args.size())
					{
						dest.write(", ");
					}
				}
				
				popDest();
				
				dest.write(BcCodeCs.construct(vectorType, argsDest));
			}
		}
	}
	
	private static boolean classEquals(BcClassDefinitionNode classNode, String name)
	{
		return typeEquals(classNode.getClassType(), name);
	}
	
	private static boolean typeEquals(BcTypeNode type, String name)
	{
		if (name.equals(classVector) && type instanceof BcVectorTypeNode)
		{
			return true;
		}
		
		return type == BcTypeNode.create(name);
	}

	private static boolean canBeClass(String name) 
	{
		return findClass(name) != null;
	}
	
	private static boolean canBeClass(BcTypeNode type) 
	{
		return canBeClass(type.getName());
	}
	
	private static String typeDefault(BcTypeNode type)
	{
		if (type.isIntegral())
		{
			if (typeEquals(type, classBoolean))
			{
				return "false";
			}
			
			return "0";
		}
		
		return BcCodeCs.NULL;
	}
	
	private static boolean typeDerivesFrom(BcTypeNode type, BcTypeNode superType)
	{
		if (!canBeClass(type))
		{
			return false;
		}
		
		if (!canBeClass(superType))
		{
			return false;
		}
		
		return false;
	}
	
	private static boolean needExplicitCast(BcTypeNode fromType, BcTypeNode toType)
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
	
	private static String cast(Object expression, BcTypeNode fromType, BcTypeNode toType) 
	{
		if (toType.isIntegral() && typeEquals(fromType, classString))
		{
			return BcCodeCs.parseString(expression, toType);
		}
		
		return BcCodeCs.cast(expression, toType);
	}
}
