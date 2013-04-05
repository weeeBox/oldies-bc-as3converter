package bc.converter;

import static bc.help.BcNodeFactory.callExpression;
import static bc.help.BcNodeFactory.getExpression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import macromedia.asc.parser.ArgumentListNode;
import macromedia.asc.parser.CallExpressionNode;
import macromedia.asc.parser.GetExpressionNode;
import macromedia.asc.parser.LiteralStringNode;
import macromedia.asc.parser.MemberExpressionNode;
import macromedia.asc.parser.SelectorNode;
import bc.code.ListWriteDestination;
import bc.code.WriteDestination;
import bc.help.BcCodeHelper;
import bc.help.BcGlobal;
import bc.help.BcNodeFactory;
import bc.help.BcNodeHelper;
import bc.help.Cast;
import bc.help.CsCodeHelper;
import bc.lang.BcArgumentsList;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcFunctionTypeNode;
import bc.lang.BcImportList;
import bc.lang.BcInterfaceDefinitionNode;
import bc.lang.BcRestTypeNode;
import bc.lang.BcTypeNode;
import bc.lang.BcTypeNodeInstance;
import bc.lang.BcUntypedTypeNode;
import bc.lang.BcVariableDeclaration;
import bc.lang.BcVectorTypeNode;
import bc.utils.string.StringUtils;

public class As2CsConverter extends As2WhateverConverter
{
	private static Map<String, SelectorNode> STRING_SELECTOR_LOOKUP;
	
	static
	{
		STRING_SELECTOR_LOOKUP = new HashMap<String, SelectorNode>();
		STRING_SELECTOR_LOOKUP.put("length", getExpression("Length"));
		STRING_SELECTOR_LOOKUP.put("toString", callExpression("ToString"));
		STRING_SELECTOR_LOOKUP.put("toLowerCase", callExpression("ToLower"));
		STRING_SELECTOR_LOOKUP.put("toUpperCase", callExpression("ToUpper"));
		STRING_SELECTOR_LOOKUP.put("replace", callExpression("Replace"));
	}

	
	private ListWriteDestination src;
	
	public As2CsConverter()
	{
		super(new CsCodeHelper());
	}
	
	@Override
	protected void process(MemberExpressionNode node) 
	{
		preprocess(node);
		super.process(node);
	}

	private boolean preprocess(MemberExpressionNode node)
	{
		if (preprocessFuncType(node))
			return true;
		
		if (node.base != null)
		{
			BcTypeNodeInstance baseTypeInstance = evaluateTypeInstance(node.base, true);
			failConversionUnless(baseTypeInstance != null, "Unable to evaluate base type");
			
			BcTypeNode type = baseTypeInstance.getType();
			BcFunctionTypeNode funcType = Cast.tryCast(type, BcFunctionTypeNode.class);
			if (funcType != null && funcType.hasReturnType())
			{
				type = funcType.getReturnType();
			}
			
			if (type.isIntegral())
			{
				BcNodeFactory.turnToStaticTypeDelegateCall(node, baseTypeInstance);
				return true;
			}
			
			if (typeEquals(type, BcTypeNode.typeString))
			{
				BcNodeFactory.turnToStaticTypeDelegateCall(node, baseTypeInstance, STRING_SELECTOR_LOOKUP);
				return true;
			}
		}
		
		return false;
	}

	private boolean preprocessFuncType(MemberExpressionNode node)
	{
		SelectorNode selector = node.selector;
		
		if (selector.isGetExpression())
		{
			BcTypeNode nodeType = evaluateType(node, true);
			failConversionUnless(nodeType != null);
			
			if (nodeType.isFunction())
			{
				BcFunctionTypeNode funcType = (BcFunctionTypeNode) nodeType;
				if (!funcType.isComplete())
				{
					return false;
				}
				
				if (funcType.isGetter())
				{
					// TODO: handle assigning getter function to Function type
					return false;
				}
				
				String funcName = BcNodeHelper.tryExtractIdentifier(selector);
				failConversionUnless(funcName != null);
				
				ArgumentListNode args =	BcNodeFactory.args(new LiteralStringNode(funcName));
				BcNodeFactory.turnSelectorToCall(node, "__function", args);
				return true;
			}
		}
		
		if (selector.isCallExpression())
		{
			if (selector.expr.isIdentifier())
			{
				String identifier = BcNodeHelper.tryExtractIdentifier(selector.expr);
				failConversionUnless(identifier != null);
				
				// TODO: code duplication
				BcClassDefinitionNode classNode;
				if (node.base != null)
				{
					BcTypeNode baseType = evaluateType(node.base);
					failConversionUnless(baseType != null);
					
					if (baseType.isVoid())
					{
						return false;
					}
					
					classNode = baseType.getClassNode();
					failConversionUnless(classNode != null);
				}
				else
				{
					classNode = BcGlobal.lastBcClass;
					failConversionUnless(classNode != null);
				}
				
				BcVariableDeclaration var = findVariable(classNode, identifier);
				if (var != null && var.getType().isFunction())
				{
					CallExpressionNode call = (CallExpressionNode) selector;
					
					GetExpressionNode delegateSelector = new GetExpressionNode(call.expr);
					node.base = new MemberExpressionNode(node.base, delegateSelector, -1);
					node.selector = BcNodeFactory.callExpression("invoke", call.args);
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	protected void writeForeach(WriteDestination dest, Object loopVarName, BcTypeNodeInstance loopVarTypeInstance, Object collection, BcTypeNodeInstance collectionTypeInstance, Object body)
	{
		final String collectionTemp = "__" + loopVarName + "s_";
		dest.writelnf("%s %s = %s;", type(collectionTypeInstance), collectionTemp, collection);
		dest.writelnf("if (%s != %s)", collectionTemp, getCodeHelper().literalNull());
		dest.writeBlockOpen();
		dest.writelnf("foreach (%s %s in %s)", type(loopVarTypeInstance), loopVarName, collectionTemp);		
		dest.writeln(body);		
		dest.writeBlockClose();
	}
	
	private void writeImports(WriteDestination dest, List<String> imports)	
	{
		List<String> sortedImports = new ArrayList<String>(imports);
		Collections.sort(sortedImports);
		
		for (String importString : sortedImports)
		{
			dest.writelnf("using %s;", importString);
		}
	}
	
	private void writeUsings(ListWriteDestination dest, List<BcTypeNode> types)
	{
		for (BcTypeNode type : types)
		{
			String classType = classType(type);
			dest.writelnf("using %s = %s.%s;", classType, type.getQualifier(), classType);
		}
	}

	private void writeInterfaceFunctions(BcClassDefinitionNode bcClass)
	{
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			String type = bcFunc.hasReturnType() ? type(bcFunc.getReturnType()) : "void";
			String name = getCodeHelper().identifier(bcFunc.getName());
			
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
				String paramType = type(bcParam.getTypeInstance());
				String paramName = getCodeHelper().identifier(bcParam.getIdentifier());
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

	@Override
	protected void writeClassDefinition(BcClassDefinitionNode bcClass, File outputDir) throws IOException
	{
		boolean isInterface = bcClass instanceof BcInterfaceDefinitionNode;
		
		String className = getClassName(bcClass);
		
		String packageName = bcClass.getPackageName();
		String subPath = packageName.replace(".", "/");
		
		File srcFileDir = new File(outputDir, subPath);
		if (!srcFileDir.exists())
		{
			boolean successed = srcFileDir.mkdirs();
			failConversionUnless(successed, "Can't make output dir: %s", srcFileDir.getAbsolutePath());
		}
		
		File outputFile = new File(srcFileDir, className + ".cs");
		
		if (!shouldWriteClassToFile(bcClass, outputFile))
		{
			return;
		}
		
		src = new ListWriteDestination();		
		
		src.writeln("using System;");
		writeBlankLine(src);
		
		CsImportsData importsData = getImports(bcClass);
		
		writeImports(src, importsData.getNamespaces());
		writeUsings(src, importsData.getUsingTypes(bcClass.getImportList()));
		writeBlankLine(src);
		
		src.writeln("namespace " + getCodeHelper().namespace(bcClass.getPackageName()));
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
			if (bcClass.isFinal())
			{
				src.writef("public sealed class %s", className);
			}
			else
			{
				src.writef("public class %s", className);
			}
			
			boolean hasExtendsType = bcClass.hasExtendsType();
			boolean hasInterfaces = bcClass.hasInterfaces();
			
			if (hasExtendsType || hasInterfaces)
			{
				src.write(" : ");
			}
			
			if (hasExtendsType)
			{
				src.write(classType(bcClass.getExtendsType()));
				if (hasInterfaces)
				{
					src.write(", ");
				}
			}
			
			if (hasInterfaces)
			{
				List<BcTypeNodeInstance> interfaces = bcClass.getInterfaces();
				int interfaceIndex= 0;
				for (BcTypeNodeInstance bcInterface : interfaces) 
				{					
					String interfaceType = type(bcInterface);
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
		
		writeDestToFile(src, outputFile);
	}

	private void writeFields(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields();
		
		for (BcVariableDeclaration bcField : fields)
		{
			String type = type(bcField.getTypeInstance());
			String name = getCodeHelper().identifier(bcField.getIdentifier());
						
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
	
	private void writeFieldsInitializer(BcClassDefinitionNode bcClass, List<BcVariableDeclaration> bcFields) 
	{
		src.writelnf("private void %s()", internalFieldInitializer);
		writeBlockOpen(src);
		
		for (BcVariableDeclaration bcVar : bcFields) 
		{
			String name = getCodeHelper().identifier(bcVar.getIdentifier());
			src.writelnf("%s = %s;", name, bcVar.getInitializer());
		}
		
		writeBlockClose(src);
	}
	
	private void writeFunctions(BcClassDefinitionNode bcClass)
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
				else if (bcFunc.isOverride())
				{
					src.write("override ");
				}
				else if (!bcFunc.isPrivate() && !bcClass.isFinal())
				{
					src.write("virtual ");
				}
				
				String type = bcFunc.hasReturnType() ? type(bcFunc.getReturnTypeInstance()) : "void";
				String name = getCodeHelper().identifier(bcFunc.getName());			
				
				src.writef("%s %s", type, name);
			}
			
			src.writelnf("(%s)", paramsDef(bcFunc.getParams()));
			
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

	private void writeConstructorBody(ListWriteDestination body) 
	{
		List<String> lines = body.getLines();
		String firstLine = lines.get(1).trim();
		if (firstLine.startsWith(BcCodeHelper.thisCallMarker))
		{
			firstLine = firstLine.replace(BcCodeHelper.thisCallMarker, "this");
			if (firstLine.endsWith(";"))
			{
				firstLine = firstLine.substring(0, firstLine.length() - 1);
			}
			
			src.writeln(" : " + firstLine);
			lines.remove(1);
		}
		else if (firstLine.startsWith(BcCodeHelper.superCallMarker))
		{
			firstLine = firstLine.replace(BcCodeHelper.superCallMarker, "base");
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
	
	private CsImportsData getImports(BcClassDefinitionNode bcClass)
	{
		CsImportsData importsData = new CsImportsData();
		
		if (bcClass.hasExtendsType())
		{
			tryAddUniqueNamespace(importsData, bcClass.getExtendsType());
		}
		
		if (bcClass.hasInterfaces())
		{
			List<BcTypeNodeInstance> interfaces = bcClass.getInterfaces();
			for (BcTypeNodeInstance bcInterface : interfaces)
			{
				tryAddUniqueNamespace(importsData, bcInterface.getType());
			}
		}
		
		List<BcVariableDeclaration> classVars = bcClass.getDeclaredVars();
		for (BcVariableDeclaration bcVar : classVars)
		{
			BcTypeNode type = bcVar.getType();
			tryAddUniqueNamespace(importsData, type);
		}
		
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			if (bcFunc.hasReturnType())
			{
				BcTypeNode returnType = bcFunc.getReturnType();
				tryAddUniqueNamespace(importsData, returnType);
			}
			
			List<BcFuncParam> params = bcFunc.getParams();
			for (BcFuncParam param : params)
			{
				BcTypeNode type = param.getType();
				tryAddUniqueNamespace(importsData, type);
			}
		}
		
		List<BcTypeNode> additionalImports = bcClass.getAdditionalImports();
		for (BcTypeNode bcType : additionalImports) 
		{
			tryAddUniqueNamespace(importsData, bcType);
		}
		
		return importsData;
	}
		
	private void tryAddUniqueNamespace(CsImportsData importsData, BcTypeNode type)
	{
		if (type instanceof BcUntypedTypeNode)
		{
			return;
		}
		
		if (type.isFunction())
		{
			return;
		}
		
		if (canBeClass(type))
		{
			importsData.addType(type);
			
			if (type instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) type;
				BcTypeNode generic = vectorType.getGeneric();
				if (generic != null)
				{
					tryAddUniqueNamespace(importsData, generic);
				}
			}
		}
	}
	
	/* code helper */
	
	private static final String NEW = "new";
	private static final String IS = "is";
	private static final String AS = "as";
	
	protected static final String VECTOR_BC_TYPE = "Vector";
		
	@Override
	public String thisSelector(BcClassDefinitionNode bcClass, Object selector)
	{
		return memberSelector("this", selector);
	}
	
	@Override
	public String superSelector(BcClassDefinitionNode bcClass, Object selector)
	{
		return memberSelector("base", selector);
	}
	
	@Override
	public String createTypeName(String name)
	{
		// XXX we use Object and String classes as-is
		if (name.equals("Object") || name.equals("String"))
		{
			return name;
		}
		
		return super.createTypeName(name);
	}
	
	@Override
	public String objectSelector(Object target, Object selector)
	{
		return String.format("((%s)%s).%s", createClassName(BcTypeNode.typeObject), target, selector);
	}
	
	@Override
	public String castString(Object expr, BcTypeNode fromType) 
	{
		return String.format("(%s)(%s)", createTypeName(BcTypeNode.typeString), expr);
	}
	
	@Override
	public String construct(String type, Object initializer)
	{
		return NEW + " " + type + "(" + initializer + ")";
	}
	
	@Override
	protected String vectorType(BcVectorTypeNode vectorType)
	{
		String genericName = type(vectorType.getGenericTypeInstance());
		return createClassName(VECTOR_BC_TYPE) + "<" + genericName + ">";
	}
	
	@Override
	protected String restType(BcRestTypeNode type)
	{
		BcTypeNodeInstance restType = type.getRestTypeInstance();
		return String.format("params %s[]", type(restType));
	}
	
	@Override
	protected String wildCardType(BcUntypedTypeNode type)
	{
		return "Object";
	}
	
	@Override
	public String constructVector(BcVectorTypeNode vectorType, BcArgumentsList args)
	{
		return NEW + " " + createClassName(VECTOR_BC_TYPE) + "<" + type(vectorType.getGenericTypeInstance()) + ">" + "(" + args + ")";
	}
	
	@Override
	public String constructLiteralVector(BcVectorTypeNode vectorType, BcArgumentsList args)
	{
		return staticCall(createClassName(VECTOR_BC_TYPE) + "<" + type(vectorType.getGenericTypeInstance()) + ">", "create", args);
	}
	
	@Override
	public String operatorIs(Object lhs, Object rhs, BcTypeNodeInstance fromTypeInstance, BcTypeNodeInstance toTypeInstance) 
	{
		return operatorHelper(IS, lhs, toTypeInstance);
	}

	@Override
	public String operatorAs(Object lhs, Object rhs, BcTypeNodeInstance fromTypeInstance, BcTypeNodeInstance toTypeInstance)
	{
		return operatorHelper(AS, lhs, toTypeInstance);
	}
	
	@Override
	public String toString(Object expr)
	{
		return memberCall(expr, "ToString");
	}
	
	private String operatorHelper(String operator, Object expr, BcTypeNodeInstance toTypeInstance)
	{
		if (toTypeInstance.isIntegral())
		{
			String name = type(toTypeInstance);
			String methodName = operator + StringUtils.capitalize(name);
			return String.format("%s.%s(%s)", createClassName(BcTypeNode.typeNumber), methodName, expr);
		}
		return String.format("%s %s %s", expr, operator, type(toTypeInstance));
	}
	
	private class CsImportsData
	{
		private List<String> namespaces;
		private List<BcTypeNode> uniqueTypes;

		public CsImportsData()
		{
			namespaces = new ArrayList<String>();
			uniqueTypes = new ArrayList<BcTypeNode>();
		}
		
		public void addType(BcTypeNode type)
		{
			BcClassDefinitionNode classNode = type.getClassNode();
			failConversionUnless(classNode != null, "Can't add type to workspace: %s", type.getName());
			
			String packageName = classNode.getPackageName();
			failConversionUnless(packageName != null, "Can't get class package: %s", classNode.getName());
			
			if (!namespaces.contains(packageName))
			{
				namespaces.add(packageName);
			}
			
			if (!uniqueTypes.contains(type))
			{
				uniqueTypes.add(type);
			}
		}
		
		public List<String> getNamespaces()
		{
			return namespaces;
		}
		
		public List<BcTypeNode> getUsingTypes(BcImportList classImportList)
		{
			List<BcTypeNode> usingTypes = new ArrayList<BcTypeNode>();
			for (String namespace : namespaces)
			{
				List<BcTypeNode> packageTypes = BcTypeNode.typesForPackage(namespace);
				for (BcTypeNode uniqueType : uniqueTypes)
				{
					String typeName = uniqueType.getName();
					String typeQualifier = uniqueType.getQualifier();
					
					for (BcTypeNode packageType : packageTypes)
					{
						if (typeName.equals(packageType.getName()) && !typeQualifier.equals(packageType.getQualifier()))
						{
							BcTypeNode duplicateType;
							if ((duplicateType = findDuplicateType(usingTypes, uniqueType.getName())) != null)
							{
								if (!classImportList.containsType(duplicateType))
								{
									usingTypes.remove(duplicateType);
								}
								
								if (classImportList.containsType(uniqueType))
								{
									usingTypes.add(uniqueType);
								}
							}
							else
							{
								usingTypes.add(uniqueType);
							}
						}
					}
				}
			}
			return usingTypes;
		}
		
		private BcTypeNode findDuplicateType(List<BcTypeNode> types, String typeName)
		{
			for (BcTypeNode type : types)
			{
				if (typeName.equals(type.getName()))
				{
					return type;
				}
			}
			return null;
		}
	}
}
