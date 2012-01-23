import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import bc.builtin.BuiltinClasses;
import bc.code.FileWriteDestination;
import bc.code.ListWriteDestination;
import bc.code.WriteDestination;
import bc.help.BcCodeCs;
import bc.help.BcNodeHelper;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcFunctionTypeNode;
import bc.lang.BcInterfaceDefinitionNode;
import bc.lang.BcMemberExpressionNode;
import bc.lang.BcTypeNode;
import bc.lang.BcVariableDeclaration;
import bc.lang.BcVectorTypeNode;

public class Main2
{
	private static FileWriteDestination src;
	private static ListWriteDestination impl;
	
	private static WriteDestination dest;
	private static Stack<WriteDestination> destStack;
	
	private static BcClassDefinitionNode lastBcClass;
	private static BcFunctionDeclaration lastBcFunction;
	private static BcTypeNode lastBcMemberType;
	
	private static final String classObject = "Object";
	private static final String classString = "String";
	private static final String classVector = "Vector";
	private static final String classDictionary = "Dictionary";
	private static final String classXML = "XML";
	private static final String classXMLList = "XMLList";
	
	private static List<BcVariableDeclaration> declaredVars;
	
	private static List<BcClassDefinitionNode> bcClasses;
	private static Map<String, BcClassDefinitionNode> builtinClasses; // builtin classes
	private static Map<String, BcFunctionDeclaration> bcBuitinFunctions; // top level functions	
	
	// FIXME: filter class names
	private static String[] builtinClassesNames = 
	{
		"Object",
		"Array",
		"Dictionary",
		"Sprite",
		"Rectangle",
		"Stage",
		"Event",
		"MouseEvent",
		"KeyboardEvent",		
		"DisplayObject",
		"DisplayObjectContainer",
		"MovieClip",
		"TextField",
		"TextFormat",
		"DropShadowFilter",
		"Bitmap",
		"Sound",
		"SoundTransform",
		"SoundChannel",
		"ColorTransform",
		"BitmapData",
		"ByteArray",
		"Class",
		"RegExp",
		"URLRequest",
		"Loader",
		"URLLoader",
		"IOErrorEvent",
		"*",
		"SharedObject",
		"Shape",
		"Graphics",
		"BlurFilter",
		"Point",
		"Math",
		"StageAlign",
		"StageScaleMode",
		"StageQuality",
		"StageDisplayState",
		"PixelSnapping",
		"TextFieldAutoSize",
		"Mouse",
		"Keyboard",
	};
	
	static
	{
		builtinClasses = new HashMap<String, BcClassDefinitionNode>();
		bcBuitinFunctions = new HashMap<String, BcFunctionDeclaration>();
		
		for (String className : builtinClassesNames)
		{
			builtinClasses.put(className, new BcClassDefinitionNode(BcTypeNode.create(className)));
		}
		
		try
		{
			List<BcClassDefinitionNode> classes = BuiltinClasses.load(new File("as_classes"));
			for (BcClassDefinitionNode bcClass : classes)
			{
				if (bcClass.getName().equals(BuiltinClasses.TOPLEVEL_DUMMY_CLASS_NAME))
				{
					List<BcFunctionDeclaration> bcTopLevelFunctions = bcClass.getFunctions();
					for (BcFunctionDeclaration bcFunc : bcTopLevelFunctions)
					{
						bcBuitinFunctions.put(bcFunc.getName(), bcFunc);
					}
				}
				else
				{
					builtinClasses.put(bcClass.getName(), bcClass);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		File outputDir = new File(args[0]);

		String[] filenames = new String[args.length - 1];
		System.arraycopy(args, 1, filenames, 0, filenames.length);
		
		try
		{
			collect(filenames);
			process();
			write(outputDir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void collect(String[] filenames) throws IOException
	{
		bcClasses = new ArrayList<BcClassDefinitionNode>();
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
			else if (node instanceof ImportDirectiveNode || 
					 node instanceof PackageDefinitionNode ||
					 node instanceof MetaDataNode)
			{
				// nothing
			}
		}
	}

	private static BcInterfaceDefinitionNode collect(InterfaceDefinitionNode interfaceDefinitionNode)
	{
		String interfaceDeclaredName = interfaceDefinitionNode.name.name;
		
		declaredVars = new ArrayList<BcVariableDeclaration>();
		
		BcTypeNode interfaceType = BcTypeNode.create(interfaceDeclaredName);
		BcInterfaceDefinitionNode bcInterface = new BcInterfaceDefinitionNode(interfaceType);
		bcInterface.setDeclaredVars(declaredVars);
		
		lastBcClass = bcInterface;
		
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
		lastBcClass = null;
		
		return bcInterface;
	}
	
	private static BcClassDefinitionNode collect(ClassDefinitionNode classDefinitionNode)
	{
		String classDeclaredName = classDefinitionNode.name.name;
		declaredVars = new ArrayList<BcVariableDeclaration>();
		
		BcTypeNode classType = BcTypeNode.create(classDeclaredName);
		BcClassDefinitionNode bcClass = new BcClassDefinitionNode(classType);
		bcClass.setDeclaredVars(declaredVars);
		
		lastBcClass = bcClass;		

		// super type
		Node baseclass = classDefinitionNode.baseclass;
		if (baseclass != null)
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
		String bcIdentifier = BcNodeHelper.extractBcIdentifier(varBindNode.variable.identifier);	
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
		String name = functionNameNode.identifier.name;
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
				BcFuncParam bcParam = new BcFuncParam(extractBcType(param.type), param.identifier.name);
				
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
				
		for (BcClassDefinitionNode bcClass : bcClasses)
		{
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
		List<BcVariableDeclaration> oldDeclaredVars = declaredVars;
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
		
		dest.writef("%s %s", BcCodeCs.type(varType.getName()), BcCodeCs.identifier(varId));
		
		Node initializer = bcVar.getInitializerNode();
		if (initializer != null)
		{
			ListWriteDestination initializerDest = new ListWriteDestination();
			pushDest(initializerDest);
			process(initializer);
			popDest();
			bcVar.setInitializer(initializerDest);
			bcVar.setIntegralInitializerFlag(BcNodeHelper.isIntegralLiteralNode(initializer));
			
			dest.write(" = " + initializerDest);
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
	}
	
	private static BcVariableDeclaration process(VariableDefinitionNode node)
	{
		VariableBindingNode varBindNode = (VariableBindingNode) node.list.items.get(0);
		
		BcTypeNode varType = extractBcType(varBindNode.variable.type);
		
		String bcIdentifier = BcNodeHelper.extractBcIdentifier(varBindNode.variable.identifier);	
		BcVariableDeclaration bcVar = new BcVariableDeclaration(varType, bcIdentifier);
		
		bcVar.setConst(node.kind == Tokens.CONST_TOKEN);
		bcVar.setModifiers(BcNodeHelper.extractModifiers(varBindNode.attrs));		
		
		dest.writef("%s %s", BcCodeCs.type(varType.getName()), BcCodeCs.identifier(bcIdentifier));
		
		if (varBindNode.initializer != null)
		{
			ListWriteDestination initializer = new ListWriteDestination();
			pushDest(initializer);
			process(varBindNode.initializer);
			popDest();
			bcVar.setInitializer(initializer);
			bcVar.setIntegralInitializerFlag(BcNodeHelper.isIntegralLiteralNode(varBindNode.initializer));
			
			dest.write(" = " + initializer);
		}
		dest.writeln(";");
		
		declaredVars.add(bcVar);
		
		return bcVar;
	}
	
	// dirty hack: we need to check the recursion depth
	
	private static MemberExpressionNode rootMemberNode;
	
	private static void process(MemberExpressionNode node)
	{
		lastBcMemberType = null;
		String baseIdentifier = null;
		
		Node base = node.base;
		SelectorNode selector = node.selector;
		
		if (rootMemberNode == null)
		{
			rootMemberNode = node;
		}
		
		if (base != null)
		{
			process(base);

			boolean staticCall = false;
			
			if (base instanceof MemberExpressionNode)
			{
				baseIdentifier = BcNodeHelper.tryExtractIdentifier((MemberExpressionNode)base);
				if (baseIdentifier != null)
				{
					staticCall = BcCodeCs.canBeClass(baseIdentifier);
					
					BcTypeNode baseType = findIdentifierType(baseIdentifier);
					if (baseType != null)
					{
						lastBcMemberType = baseType;
					}
				}
			} 
			else if (base instanceof ThisExpressionNode)
			{
				lastBcMemberType = lastBcClass.getClassType();
			}
			else if (base instanceof SuperExpressionNode)
			{
				lastBcMemberType = lastBcClass.getExtendsType();
			}
			else if (base instanceof ListNode)
			{
				ListNode list = (ListNode) base;
				assert list.items.size() == 1 : list.items.size();
				
				Node item = list.items.get(0);
				if (item instanceof MemberExpressionNode)
				{
					MemberExpressionNode member = (MemberExpressionNode) item;
					assert member.base == null : member.base.getClass();
					
					assert member.selector instanceof CallExpressionNode : member.selector.getClass();
					CallExpressionNode call = (CallExpressionNode) member.selector;
					
					assert call.expr instanceof IdentifierNode : call.expr.getClass();
					IdentifierNode identifier = (IdentifierNode) call.expr;
					
					BcClassDefinitionNode castClass = findClass(identifier.name);
					assert castClass != null : identifier.name;
					
					lastBcMemberType = castClass.getClassType();
				}
			}
			else
			{
				assert false;
			}
			
			if (selector instanceof GetExpressionNode || 
				selector instanceof CallExpressionNode || 
				selector instanceof SetExpressionNode)
			{
				if (selector.getMode() == Tokens.DOT_TOKEN)
				{
					dest.write(staticCall ? "::" : "->");
				}
				else
				{
					dest.write("->");
				}
			}
		}
		
		if (selector != null)
		{
			process(selector);
		}
		
		rootMemberNode = null;
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
		dest.writef("->remove(%s)", expr);
	}
	
	private static void process(GetExpressionNode node)
	{
		ListWriteDestination exprDest = new ListWriteDestination();
		pushDest(exprDest);
		process(node.expr);
		popDest();
		
		String expr = exprDest.toString();
		
		if (lastBcMemberType != null)
		{
			BcTypeNode memberType = lastBcMemberType;
			lastBcMemberType = null;
			
			if (node.expr instanceof IdentifierNode)
			{
				String identifier = expr;
				
				BcClassDefinitionNode bcClass = memberType.getClassNode();
				if (bcClass != null)
				{
					BcFunctionDeclaration bcFunc = bcClass.findGetterFunction(identifier);
					if (bcFunc != null)
					{
						BcTypeNode funcType = bcFunc.getReturnType();
						assert funcType != null : identifier;
						
						expr = identifier + "()";
						lastBcMemberType = funcType;
					}
					else
					{
						BcVariableDeclaration bcField = bcClass.findField(identifier);
						if (bcField != null)
						{
							BcTypeNode fieldType = bcField.getType();
							lastBcMemberType = fieldType;
						}
					}
				}
			}
		}
		
		if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
		{
			ListWriteDestination argsDest = new ListWriteDestination();
			pushDest(argsDest);
			process(node.expr);
			popDest();
			
			dest.writef("%s[%s]", expr, argsDest);
		}
		else
		{
			dest.write(expr);
		}
	}

	private static BcTypeNode findIdentifierType(String name)
	{
		if (BcCodeCs.canBeClass(name))
		{
			BcClassDefinitionNode bcClass = findClass(name);
			if (bcClass != null)
			{
				return bcClass.getClassType();
			}
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
		BcVariableDeclaration bcVar = findLocalVar(name);
		if (bcVar != null)
		{
			return bcVar;
		}
		
		return lastBcClass.findField(name);
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
		
		String expr = exprDest.toString();
		
		if (lastBcMemberType != null)
		{
			BcTypeNode memberType = lastBcMemberType;
			lastBcMemberType = null;
			
			if (node.expr instanceof IdentifierNode)
			{
				String identifier = expr;
				
				BcClassDefinitionNode bcClass = memberType.getClassNode();
				if (bcClass != null)
				{
					BcFunctionDeclaration bcFunc = bcClass.findFunction(identifier);
					if (bcFunc != null)
					{
						lastBcMemberType = bcFunc.getReturnType();
					}
				}
			}
		}
		
		ListWriteDestination argsDest = new ListWriteDestination();
		if (node.args != null)
		{
			pushDest(argsDest);
			process(node.args);
			popDest();
		}
		
		BcTypeNode type = extractBcType(node.expr);
		assert type != null : node.expr.getClass();
		
		if (node.is_new)
		{
			if (type instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) type;
				
				ObjectList<Node> args;
				if (node.args != null && (args = node.args.items).size() == 1 && args.get(0) instanceof LiteralArrayNode)
				{
					LiteralArrayNode arrayNode = (LiteralArrayNode) args.get(0);
					
					ArgumentListNode elementlist = arrayNode.elementlist;
					
					WriteDestination initDest = new ListWriteDestination();
					pushDest(initDest);
					for (Node elementNode : elementlist.items)
					{
						initDest.write(".a(");
						process(elementNode);
						initDest.write(")");
					}
					popDest();
					
					dest.write(BcCodeCs.construct(vectorType, elementlist.size()) + initDest);
				}
				else
				{
					dest.write(BcCodeCs.construct(vectorType, argsDest));
				}
			}
			else
			{
				dest.write(BcCodeCs.construct(type, argsDest.toString()));
			}
		}
		else if (node.expr instanceof MemberExpressionNode && ((MemberExpressionNode) node.expr).selector instanceof ApplyTypeExprNode)
		{
			assert type instanceof BcVectorTypeNode;
			BcVectorTypeNode bcVector = (BcVectorTypeNode) type;
			
			Node argNode = node.args.items.get(0);
			
			if (argNode instanceof LiteralArrayNode)
			{
				LiteralArrayNode arrayNode = (LiteralArrayNode) argNode;
				
				ArgumentListNode elementlist = arrayNode.elementlist;
				
				WriteDestination initDest = new ListWriteDestination();
				pushDest(initDest);
				for (Node elementNode : elementlist.items)
				{
					initDest.write(" << ");
					process(elementNode);
				}
				popDest();
				dest.write(BcCodeCs.construct(bcVector, elementlist.size()) + initDest);
			}
			else
			{
				dest.write(BcCodeCs.construct(type, 1) + " << " + argsDest);
			}			
		}
		else
		{
			if (node.getMode() == Tokens.EMPTY_TOKEN && node.args != null && node.args.items.size() == 1)
			{
				if (BcCodeCs.canBeClass(type) || BcCodeCs.isBasicType(type))
				{
					dest.writef("((%s)(%s))", BcCodeCs.type(exprDest.toString()), argsDest);
				}
				else
				{
					dest.writef("%s(%s)", exprDest, argsDest);
				}
			}
			else
			{
				dest.writef("%s(%s)", exprDest, argsDest);
			}
		}
	}
	
	private static void process(SetExpressionNode node)
	{
		ListWriteDestination exprDest = new ListWriteDestination();
		pushDest(exprDest);
		process(node.expr);
		popDest();
		
		String expr = exprDest.toString();
		boolean setterCalled = false;
		
		if (lastBcMemberType != null)
		{
			BcTypeNode memberType = lastBcMemberType;
			lastBcMemberType = null;
			
			if (node.expr instanceof IdentifierNode)
			{
				String identifier = expr;
				
				BcClassDefinitionNode bcClass = memberType.getClassNode();
				if (bcClass != null)
				{
					BcFunctionDeclaration bcFunc = bcClass.findSetterFunction(identifier);
					if (bcFunc != null)
					{
						List<BcFuncParam> funcParams = bcFunc.getParams();
						BcTypeNode setterType = funcParams.get(0).getType();
						setterCalled = true;
						
						lastBcMemberType = setterType;
					}
				}
			}
		}

		ListWriteDestination argsDest = new ListWriteDestination();
		pushDest(argsDest);
		process(node.args);
		popDest();
		
		if (setterCalled)
		{
			if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
			{
				dest.writef("[%s](%s)", exprDest, argsDest);
			}
			else
			{
				dest.writef("%s(%s)", exprDest, argsDest);
			}
		}
		else
		{
			if (node.getMode() == Tokens.LEFTBRACKET_TOKEN)
			{
				dest.writef("[%s] = %s", exprDest, argsDest);
			}
			else
			{
				dest.writef("%s = %s", exprDest, argsDest);
			}
		}
	}
	
	private static void process(ApplyTypeExprNode node)
	{
		ListWriteDestination expr = new ListWriteDestination();
		pushDest(expr);
		process(node.expr);
		popDest();
		
		String typeName = ((IdentifierNode)node.expr).name;
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
			dest.write("attribute(\"");
			dest.write(node.name);
			dest.write("\")");
		}
		else
		{
			dest.write(node.name);
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
			dest.write(BcCodeCs.construct(classString, String.format("\"%s\"", stringNode.value)));
		}
		else if (node instanceof LiteralRegExpNode)
		{
			assert false : "LiteralRegExpNode";
		}
		else if (node instanceof LiteralArrayNode)
		{
			LiteralArrayNode arrayNode = (LiteralArrayNode) node;
			ArgumentListNode elementlist = arrayNode.elementlist;
			BcTypeNode type = null;
			
			WriteDestination elementDest = new ListWriteDestination();
			pushDest(elementDest);
			int elementIndex = 0;
			for (Node elementNode : elementlist.items)
			{
				process(elementNode);
				if (++elementIndex < elementlist.items.size())
				{
					elementDest.write(", ");
				}
			}
			popDest();
			
			dest.writef("__NEWARRAY(Foo, %s)", elementDest);
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
	
	private static void process(IfStatementNode node)
	{
		ListWriteDestination condDest = new ListWriteDestination();
		pushDest(condDest);
		process(node.condition);
		popDest();
		dest.writelnf("if(%s)", condDest);
		
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
		
		dest.writelnf("while(%s)", exprDest);
		
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
				loopVarName = BcCodeCs.identifier(identifier.name);
			}
			else if (child3.expr instanceof IdentifierNode)
			{
				IdentifierNode identifier = (IdentifierNode) child3.expr;
				loopVarName = BcCodeCs.identifier(identifier.name);
			}
			else
			{
				assert false : child3.expr.getClass();
			}
			
			BcVariableDeclaration loopVar = findDeclaredVar(loopVarName);
			assert loopVar != null : loopVarName;
			
			// get loop body
			dest.writelnf("for (%s::Iterator __it = %s->__internalIterator(); __it.hasNext();)", BcCodeCs.type(collectionType), collection);
			Node bodyNode = statements.items.get(1);
			if (bodyNode != null)
			{
				assert bodyNode instanceof StatementListNode : bodyNode.getClass();
				StatementListNode statementsNode = (StatementListNode) bodyNode;
				
				writeBlockOpen(dest);
				
				dest.writelnf("%s = __it.next();", loopVarName);
				
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

		if (node.op == Tokens.IS_TOKEN)
		{
			dest.write(BcCodeCs.operatorIs(ldest, rdest));
		}
		else if (node.op == Tokens.AS_TOKEN)
		{
			assert false;
		}
		else
		{
			dest.writef("%s %s %s", ldest, Tokens.tokenToString[-node.op], rdest);
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
			dest.writef("!(%s)", expr);
			break;
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
		BcClassDefinitionNode builtinClass = findBuiltinClass(name);
		if (builtinClass != null)
		{
			return builtinClass;
		}
		
		for (BcClassDefinitionNode bcClass : bcClasses)
		{
			if (bcClass.getClassType().getName().equals(name))
			{
				return bcClass;
			}
		}
		
		return null;
	}
	
	private static BcClassDefinitionNode findBuiltinClass(String name)
	{
		return builtinClasses.get(name);
	}
	
	private static BcFunctionDeclaration findBuiltinFunc(String name)
	{
		return bcBuitinFunctions.get(name);
	}
	
	private static BcFunctionTypeNode findFunctionType(String identifier)
	{
		BcTypeNode foundType = findVariableType(identifier);
		if (foundType instanceof BcFunctionTypeNode)
		{
			return (BcFunctionTypeNode) foundType;
		}
		return null;
	}

	private static BcTypeNode findVariableType(String indentifier)
	{
		if (indentifier.equals("this"))
		{
			return BcTypeNode.create(lastBcClass.getName());
		}
		
		BcTypeNode foundType = null;
		if (lastBcFunction != null)
		{
			foundType = findVariableType(lastBcFunction.getDeclaredVars(), indentifier);
			if (foundType != null)
			{
				return foundType;
			}
		}
		
		return findVariableType(lastBcClass.getDeclaredVars(), indentifier);
	}

	private static BcTypeNode findVariableType(List<BcVariableDeclaration> vars, String indentifier)
	{
		for (BcVariableDeclaration var : vars)
		{
			if (var.getIdentifier().equals(indentifier)) 
			{
				return var.getType();
			}
		}
		return null;
	}

	private static void write(File outputDir) throws IOException
	{
		for (BcClassDefinitionNode bcClass : bcClasses)
		{
			if (bcClass.isInterface())
			{
				writeIntefaceDefinition(bcClass, outputDir);
			}
			else
			{
				writeClassDefinition(bcClass, outputDir);
			}
		}
	}
	
	private static void writeIntefaceDefinition(BcClassDefinitionNode bcClass, File outputDir) throws IOException
	{
		String className = getClassName(bcClass);
		String classExtends = getBaseClassName(bcClass);
		
		src = new FileWriteDestination(new File(outputDir, className + ".cs"));
		impl = null;
		
		List<BcTypeNode> headerTypes = getHeaderTypedefs(bcClass);
		
		src.writeln("namespace bc");
		writeBlockOpen(src);
		
		src.writelnf("public interface %s : %s", className, classExtends);
		writeBlockOpen(src);
		
		writeInterfaceFunctions(bcClass);

		writeBlockClose(src);
		writeBlockClose(src);
		
		src.close();
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
		String className = getClassName(bcClass);
		String classExtends = getBaseClassName(bcClass);
		
		src = new FileWriteDestination(new File(outputDir, className + ".cs"));
		impl = new ListWriteDestination();
		
		List<BcTypeNode> headerTypes = getHeaderTypedefs(bcClass);
		List<BcTypeNode> implTypes = getImplementationTypedefs(bcClass);
		
		src.writeln("namespace bc");
		writeBlockOpen(src);
		
		src.writelnf("public class %s : %s", className, classExtends);
		writeBlockOpen(src);
		
		writeFields(bcClass);
		writeFunctions(bcClass);
		
		writeBlockClose(src);
		writeBlockClose(src);
		
		src.close();
	}

	private static void writeHeaderTypes(WriteDestination dst, List<BcTypeNode> types)
	{
		for (BcTypeNode bcType : types)
		{
			if (bcType instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;
				String genericName = BcCodeCs.type(vectorType.getGeneric());
				String typeName = BcCodeCs.type(bcType);
				
				dst.writelnf("typedef %s<%s> %s;", BcCodeCs.type(BcCodeCs.VECTOR_TYPE), BcCodeCs.type(genericName), typeName);
				dst.writelnf("typedef %s::Ref %s;", typeName, BcCodeCs.type(typeName));
			}
			else
			{
				String typeName = BcCodeCs.type(bcType);
				dst.writelnf("__TYPEREF_DEF(%s)", typeName);				
			}
		}
	}
	
	private static void writeImplementationTypes(WriteDestination dst, List<BcTypeNode> types)
	{
		for (BcTypeNode type : types)
		{
			if (type instanceof BcVectorTypeNode)
			{
				System.err.println("Fix me!!! Vector in implementation");
			}
			else
			{
				String typeName = BcCodeCs.type(type);
				dst.writelnf("#include \"%s.h\"", typeName);
			}
		}
	}
	
	private static void writeFields(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields();
		impl.writeln();
		
		for (BcVariableDeclaration bcField : fields)
		{
			String type = BcCodeCs.type(bcField.getType());
			String name = BcCodeCs.identifier(bcField.getIdentifier());
						
			src.write(bcField.getVisiblity() + " ");
			
			if (bcField.isStatic())
			{
				src.write("static ");
			}
			
			if (bcField.isConst())
			{
				src.write("const ");
			}
			src.writef("%s %s", type, name);
			if (bcField.hasInitializer())
			{
				src.writef(" = %s", bcField.getInitializer());
			}
			src.writeln(";");
		}
	}
	
	private static void writeFunctions(BcClassDefinitionNode bcClass)
	{
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			String type = bcFunc.hasReturnType() ? BcCodeCs.type(bcFunc.getReturnType()) : "void";
			String name = BcCodeCs.identifier(bcFunc.getName());			
			
			src.write(bcFunc.getVisiblity() + " ");
			if (!bcFunc.isConstructor())
			{
				if (bcFunc.isStatic())
				{
					src.write("static ");
				}
				else
				{
					src.write("virtual ");
				}
				src.write(type + " ");
			}
			src.writef("%s(", name);
			
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
			
			src.write(paramsBuffer);
			src.writeln(")");
			src.writeln(bcFunc.getBody());
		}
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
		return lastBcClass.findFunction(name);
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
	
	private static List<BcTypeNode> getHeaderTypedefs(BcClassDefinitionNode bcClass)
	{
		List<BcTypeNode> includes = new ArrayList<BcTypeNode>();
		
		tryAddUniqueType(includes, bcClass.getClassType());
		
		if (bcClass.hasInterfaces())
		{
			List<BcTypeNode> interfaces = bcClass.getInterfaces();
			for (BcTypeNode bcInterface : interfaces)
			{
				tryAddUniqueType(includes, bcInterface);
			}
		}
		
		List<BcVariableDeclaration> classVars = bcClass.getDeclaredVars();
		for (BcVariableDeclaration bcVar : classVars)
		{
			BcTypeNode type = bcVar.getType();
			tryAddUniqueType(includes, type);
		}
		
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			if (bcFunc.hasReturnType())
			{
				BcTypeNode returnType = bcFunc.getReturnType();
				tryAddUniqueType(includes, returnType);
			}
			
			List<BcFuncParam> params = bcFunc.getParams();
			for (BcFuncParam param : params)
			{
				BcTypeNode type = param.getType();
				tryAddUniqueType(includes, type);
			}
		}
		
		return includes;
	}
	
	private static List<BcTypeNode> getImplementationTypedefs(BcClassDefinitionNode bcClass)
	{
		List<BcTypeNode> includes = new ArrayList<BcTypeNode>();
		
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			List<BcVariableDeclaration> funcVars = bcFunc.getDeclaredVars();
			for (BcVariableDeclaration var : funcVars)
			{
				BcTypeNode type = var.getType();
				tryAddUniqueType(includes, type);
			}
		}
		
		return includes;
	}
	
	private static void tryAddUniqueType(List<BcTypeNode> types, BcTypeNode type)
	{
		if (BcCodeCs.canBeClass(type))
		{
			if (type instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) type;
				tryAddUniqueType(types, vectorType.getGeneric());
			}
			if (!types.contains(type))
			{
				types.add(type);
			}
		}
	}
	
	private static String getClassName(BcClassDefinitionNode bcClass)
	{
		return BcCodeCs.type(bcClass.getName());
	}
	
	private static String getBaseClassName(BcClassDefinitionNode bcClass)
	{
		if (bcClass.hasExtendsType())
		{
			return BcCodeCs.type(bcClass.getExtendsType());
		}
		
		return BcCodeCs.type(classObject);
	}
	
	public static BcTypeNode evaluateType(Node node)
	{
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
		
		if (node instanceof MemberExpressionNode)
		{
			return evaluateMemberExpression((MemberExpressionNode) node);
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
		
//		MemberExpressionNode expr = null;
//		if (node instanceof TypeExpressionNode)
//		{
//			TypeExpressionNode typeNode = (TypeExpressionNode) node;
//			expr = (MemberExpressionNode) typeNode.expr;
//		}
//		else if (node instanceof MemberExpressionNode)
//		{
//			expr = (MemberExpressionNode) node;
//		}
//		else
//		{
//			BcDebug.implementMe(node);
//		}
//		
//		if (expr.selector instanceof GetExpressionNode)
//		{
//			GetExpressionNode selector = (GetExpressionNode) expr.selector;
//			String name = ((IdentifierNode)selector.expr).name;
//			if (name.equals("Function"))
//			{
//				assert false;
//				return new BcFunctionTypeNode();
//			}
//			
//			return BcTypeNode.create(name);
//		}
//		
//		if (expr.selector instanceof ApplyTypeExprNode)
//		{
//			ApplyTypeExprNode selector = (ApplyTypeExprNode) expr.selector;
//			String typeName = ((IdentifierNode)selector.expr).name;
//			
//			ListNode typeArgs = selector.typeArgs;
//			assert typeArgs.size() == 1;
//			
//			BcTypeNode genericType = extractBcType(typeArgs.items.get(0));
//			return new BcVectorTypeNode(typeName, genericType);
//		}
//		
//		assert false : expr.selector.getClass();
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
					if (baseClass.getName().equals(classXML))				
					{
						return BcTypeNode.create(classXMLList); // dirty hack
					}
					else if (BcCodeCs.identifier(identifier).equals(BcCodeCs.thisCallMarker))
					{
						return lastBcClass.getClassType(); // this referes to the current class
					}
					else if (baseClass.getName().endsWith(classObject))
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
		
		String name = BcNodeHelper.extractBcIdentifier(identifier);
		
		// check if it's class
		if (BcCodeCs.canBeClass(name))
		{
			BcClassDefinitionNode bcClass = findClass(name);
			if (bcClass != null)
			{
				return bcClass.getClassType();
			}
		}
		else if (BcCodeCs.isBasicType(name))
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
		if (bcFunc != null || (bcFunc = findBuiltinFunc(name)) != null)
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
			BcClassDefinitionNode vectorGenericClass = findBuiltinClass(classVector).clone();
			vectorGenericClass.setClassType(bcType);
			builtinClasses.put(bcType.getNameEx(), vectorGenericClass);
			
			bcType.setClassNode(vectorGenericClass);
		}
		
		BcTypeNode.add(bcType.getNameEx(), bcType);
		
		return bcType;
	}
	
	private static void generateFunctor(WriteDestination dest, String className)
	{
//		BcClassDefinitionNode bcClass = new BcClassDefinitionNode("Functor");
//		bcClass.setExtendsType(new BcTypeNode("Object"));
//		
//		List<String> targetModifiers = new ArrayList<String>();
//		targetModifiers.add("private");
//		
//		BcVariableDeclaration targetVar = new BcVariableDeclaration(new BcTypeNode("Object"), new BcIdentifierNode("target"));
//		targetVar.setModifiers(targetModifiers);
//		bcClass.add(targetVar);
//		
//		List<String> operatorModifiers = new ArrayList<String>();
//		operatorModifiers.add("public");
//		
//		BcFunctionDeclaration operatorFunc = new BcFunctionDeclaration("operator()");
//		operatorFunc.setModifiers(operatorModifiers);
	}
}
