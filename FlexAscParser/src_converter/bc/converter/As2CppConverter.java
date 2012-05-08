package bc.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bc.code.ListWriteDestination;
import bc.code.WriteDestination;
import bc.cpp.BcCppDefine;
import bc.cpp.BcCppDefines;
import bc.cpp.BcCppDefinesReader;
import bc.help.BcCodeHelper;
import bc.help.BcStringUtils;
import bc.help.BcVariableFilter;
import bc.help.CppCodeHelper;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcTypeNode;
import bc.lang.BcVariableDeclaration;
import bc.lang.BcVectorTypeNode;

public class As2CppConverter extends As2WhateverConverter
{
	private static final String defineClass = "AS_CLASS";
	private static final String defineRef = "AS_REF";
	private static final String defineVector = "AS_VECTOR_REF";
	private static final String definePrimitiveVector = "AS_VECTOR_PRIMITIVE_REF";
	private static final String defineForeach = "AS_FOREACH";
	private static final String definePrimitiveForeach = "AS_PRIMITIVE_FOREACH";
	private static final String defineForeachEnd = "AS_FOREACH_END";
	
	private static final String defineObject = "AS_OBJ";
	
	private static final String classCreate = "_as_create_";
	private static final String classConstructor = "_as_construct_";
	private static final String classInitFields = "_as_init_fields_";
	private static final String classStaticInit = "_as_static_init_";
	private static final String classStaticInitializedFlag = "_as_static_initialized_";
	private static final String classStaticInitializer = "_as_static_initializer_";
	private static final String classStaticInitializerClass = "AsStaticRefInitializer";
	private static final String classGcMark = "_as_gc_mark";
	private static final String classGcMarkNeeded = "_as_gc_mark_needed";
	
	private static final String classInterfaceWrapper = "_as_interface_";
	private static final String classInterfaceBox = "_as_box_";
	private static final String classInterfaceUnbox = "_as_unbox";
	
	private static final String defineGcMark = "AS_GC_MARK";
	private static final String defineInterface = "AS_INTERFACE";
	private static final String defineInterfaceRef = "AS_INTERFACE_REF";
	
	
	private String lastVisiblityModifier;
	private ListWriteDestination hdr;
	private ListWriteDestination impl;
	private BcCppDefines defines;

	private static Set<String> definedTypes; // this types shouldn't be redefined
	static
	{
		definedTypes = new HashSet<String>();
		definedTypes.add("Object");
		definedTypes.add("String");
	}
	
	public As2CppConverter()
	{
		super(new CppCodeHelper());
		
		readCppDefines();
	}

	protected void readCppDefines()
	{
		try
		{
			defines = BcCppDefinesReader.read(new File("cpp_defines"));
		}
		catch (IOException e)
		{
			System.err.println("Unable to read defines: " + e);
		}
	}
	
	@Override
	protected void postProcess(BcClassDefinitionNode bcClass)
	{
		if (!bcClass.isInterface() && !bcClass.hasConstructors())
		{
			BcFunctionDeclaration defaultConstructor = new BcFunctionDeclaration(bcClass.getName());
			defaultConstructor.setConstructorFlag(true);
			defaultConstructor.setDeclaredVars(new ArrayList<BcVariableDeclaration>());
			
			ListWriteDestination body = new ListWriteDestination();
			body.writeBlockOpen();
			body.writeBlockClose();
			
			defaultConstructor.setBody(body);
			
			bcClass.add(defaultConstructor);
		}
	}
	
	@Override
	protected void writeForeach(WriteDestination dest, Object loopVarName, BcTypeNode loopVarType, Object collection, BcTypeNode collectionType, Object body)
	{
		assert collectionType instanceof BcVectorTypeNode;
		BcVectorTypeNode vectorType = (BcVectorTypeNode) collectionType;
		
		String defineName = vectorType.getGeneric().isIntegral() ? definePrimitiveForeach : defineForeach;
		
		dest.writelnf("%s(%s, %s, %s)", defineName, type(loopVarType), loopVarName, collection);
		dest.writeln(body);
		dest.writeln(defineForeachEnd);
	}
	
	@Override
	protected void writeClassDefinition(BcClassDefinitionNode bcClass, File outputDir) throws IOException
	{
		String className = getClassName(bcClass);
		String classExtends = getBaseClassName(bcClass);
		
		File hdrFile = new File(outputDir, className + ".h");
		File implFile = new File(outputDir, className + ".cpp");
		
		outputDir.mkdirs();
		
		boolean isInterface = bcClass.isInterface();
		
		hdr = new ListWriteDestination();
		impl = new ListWriteDestination();
		
		lastVisiblityModifier = null;
		
		String defGuardName = className + "_h__";
		
		hdr.writeln("#ifndef " + defGuardName);
		hdr.writeln("#define " + defGuardName);
		hdr.writeln();
		
		hdr.writeln(getCodeHelper().include("AsBc.h"));
		hdr.writeln();
		
		hdr.writelnf(getCodeHelper().include(classExtends + ".h"));
		if (bcClass.hasInterfaces())
		{
			List<BcTypeNode> interfaces = bcClass.getInterfaces();
			for (BcTypeNode bcInterface : interfaces)
			{
				hdr.writeln(getCodeHelper().include(type(bcInterface) + ".h"));
			}
		}
		
		hdr.writeln();
		
		impl.writelnf("#include \"%s.h\"", className);
		
		List<BcTypeNode> headerTypes = getHeaderTypedefs(bcClass);
		List<BcTypeNode> implTypes = getImplementationTypedefs(bcClass);
		
		writeHeaderTypes(hdr, headerTypes);
		writeImplementationTypes(impl, implTypes);
		
		hdr.writeln();
		
		hdr.writelnf("%s(%s);", isInterface ? defineRef : defineClass, className);
		hdr.writeln();
		
		hdr.writelnf("class %s : public %s", className, classExtends);
		hdr.writeln("{");
		hdr.incTab();
		
		writeTypename(bcClass);
		writeFields(bcClass);
		writeFunctions(bcClass);
		
		if (!isInterface)
		{
			hdr.writeln();
			
			writeConstructors(bcClass);
			writeFieldsInit(bcClass);
			writeClassStaticInit(bcClass);
			writeClassGcMarkMethod(bcClass);
			writeClassDefaultConstructor(bcClass);
			
			// generate interface boxers accessors if any
			if (bcClass.hasInterfaces())
			{
				writeBoxingInterfaces(bcClass);
			}
		}
		
		hdr.decTab();
		hdr.writeln("};");

		if (bcClass.isInterface())
		{
			hdr.writeln();
			writeDefine(hdr, defineInterfaceRef, true, className);
		}
		
		hdr.writeln();
		hdr.writeln("#endif // " + defGuardName);
		
		if (shouldWriteClassToFile(bcClass, hdrFile))
			writeDestToFile(hdr, hdrFile);
		
		if (!isInterface)
		{
			if (shouldWriteClassToFile(bcClass, implFile))
				writeDestToFile(impl, implFile);
		}
	}

	private void writeHeaderTypes(WriteDestination dst, List<BcTypeNode> types)
	{
		ListWriteDestination includeDest = new ListWriteDestination();
		ListWriteDestination defineDest = new ListWriteDestination();
		
		boolean vectorIncluded = false; // hack
		
		for (BcTypeNode bcType : types)
		{
			if (bcType instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;
				String genericName = type(vectorType.getGeneric());
				
				if (!vectorIncluded)
				{
					vectorIncluded = true;
					includeDest.writeln(getCodeHelper().include(type(bcType) + ".h"));
				}
				
				if (vectorType.getGeneric().isIntegral())
				{
					defineDest.writelnf("%s(%s);", definePrimitiveVector, genericName);
				}
				else
				{
					defineDest.writelnf("%s(%s);", defineVector, getCodeHelper().type(vectorType.getGeneric()));
				}
			}
			else
			{
				if (definedTypes.contains(bcType.getName()))
				{
					includeDest.writeln(getCodeHelper().include(type(bcType) + ".h"));
				}
				else
				{
					String defineName = bcType.getClassNode().isInterface() ? defineRef : defineClass;
					defineDest.writelnf("%s(%s);", defineName, type(bcType));
				}
			}
		}
		
		if (includeDest.linesCount() > 0)
		{
			dst.writeln(includeDest);
			dst.writeln();
		}
		if (defineDest.linesCount() > 0)
		{
			dst.writeln(defineDest);
		}
	}
	
	private void writeImplementationTypes(WriteDestination dst, List<BcTypeNode> types)
	{
		for (BcTypeNode type : types)
		{
			if (type instanceof BcVectorTypeNode)
			{
				System.err.println("Fix me!!! Vector in implementation");
			}
			else
			{
				String typeName = type(type);
				dst.writelnf("#include \"%s.h\"", typeName);
			}
		}
	}
	
	private void writeTypename(BcClassDefinitionNode bcClass)
	{
		String className = getClassName(bcClass);
		String baseClassName = getBaseClassName(bcClass);
		
		writeVisiblity("public", true);
		hdr.writelnf("%s(%s, %s);", defineObject, className, baseClassName);
	}
	
	private void writeFields(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields();
		if (fields.size() > 0)
		{
			impl.writeln();
		}
		
		boolean forceVisiblity = true;
		String className = getClassName(bcClass);
		
		for (BcVariableDeclaration bcField : fields)
		{
			String type = getCodeHelper().typeRef(bcField.getType());
			String name = getCodeHelper().identifier(bcField.getIdentifier());
			boolean canBeClass = canBeClass(bcField.getType());
			boolean isConst = bcField.isConst();
			
			writeVisiblity(bcField.getVisiblity(), forceVisiblity);
			forceVisiblity = false;
			
			if (bcField.isStatic())
			{
				hdr.write("static ");
				if (canBeClass)
				{
					impl.writelnf("%s %s::%s(true);", type, className, name);
				}
				else if (!canBeInitializedInHeader(bcField))
				{
					impl.writelnf("%s %s::%s(0);", type, className, name);
				}
			}
			if (isConst && !canBeClass)
			{
				hdr.write("const ");
			}
			
			hdr.write(getCodeHelper().varDecl(bcField.getType(), name));
			if (canBeInitializedInHeader(bcField))
			{
				hdr.writef(" = %s", bcField.getInitializer());
			}
			
			hdr.writeln(";");
		}
	}
	
	private void writeFunctions(BcClassDefinitionNode bcClass)
	{
		boolean forceVisiblity = true;
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			String type = bcFunc.hasReturnType() ? getCodeHelper().typeRef(bcFunc.getReturnType()) : "void";
			String name = getCodeHelper().identifier(bcFunc.getName());
			
			if (bcFunc.isConstructor())
			{
				continue;
			}
			
			writeVisiblity(bcFunc.getVisiblity(), forceVisiblity);
			forceVisiblity = false;
			
			if (bcFunc.isStatic())
			{
				hdr.write("static ");
			}
			else
			{
				hdr.write("virtual ");
			}
			String params = getCodeHelper().paramsDef(bcFunc.getParams());
	
			if (bcClass.isInterface())
			{
				hdr.writelnf("%s %s(%s) = 0;", type, name, params);
			}
			else
			{
				hdr.writelnf("%s %s(%s);", type, name, params);
			}
			
			impl.writeln();
			impl.writelnf("%s %s::%s(%s)", type, getClassName(bcClass), name, params);			
			impl.writeln(bcFunc.getBody());
		}
	}
	
	private void writeConstructors(BcClassDefinitionNode bcClass)
	{
		List<BcFunctionDeclaration> constructors = bcClass.getConstructors();
		if (constructors.isEmpty())
		{
			return;
		}

		String className = getClassName(bcClass);
		
		writeVisiblity("public", true);
		for (BcFunctionDeclaration bcFunc : constructors)
		{
			String params = getCodeHelper().paramsDef(bcFunc.getParams());
			String args = getCodeHelper().argsDef(bcFunc.getParams());
			
			String type = getCodeHelper().type(className);
			String typeRef = getCodeHelper().typeRef(className);
			String create = classCreate + type;
			String constructor = classConstructor + type;
			
			hdr.writelnf("static %s %s(%s);", typeRef, create, params);
			
			impl.writeln();
			impl.writelnf("%s %s::%s(%s)", typeRef, className, create, params);
			impl.writeBlockOpen();
			impl.writelnf("%s* __instance = new %s();", type, type);
			impl.writelnf("__instance->%s(%s);", constructor, args);
			impl.writeln("return __instance;");
			impl.writeBlockClose();
		}
		
		writeVisiblity("protected", true);
		for (BcFunctionDeclaration bcFunc : constructors)
		{
			String params = getCodeHelper().paramsDef(bcFunc.getParams());
			
			String type = getCodeHelper().type(className);
			String constructor = classConstructor + type;
			
			hdr.writelnf("void %s(%s);", constructor, params);
			
			impl.writeln();
			if (bcFunc.isOverridenConstructor())
			{
				impl.write("inline ");
			}
			impl.writelnf("void %s::%s(%s)", className, constructor, params);
			
			ListWriteDestination body = bcFunc.getBody();
			List<String> bodyLines = body.getLines();
			String constructorLine = bodyLines.get(1);
			
			if (constructorLine.contains(BcCodeHelper.superCallMarker))
			{
				if (!typeEquals(getBaseClassType(bcClass), classObject))
				{
					String constructorCall = classConstructor + getBaseClassName(bcClass);
					String newConstructorLine = constructorLine.replace(BcCodeHelper.superCallMarker, constructorCall);
					bodyLines.set(1, newConstructorLine);
				}
			}
			else if (constructorLine.contains(BcCodeHelper.thisCallMarker))
			{
				String newConstructorLine = constructorLine.replace(BcCodeHelper.thisCallMarker, constructor);
				bodyLines.set(1, newConstructorLine);
			}
			else
			{
				if (!typeEquals(getBaseClassType(bcClass), classObject))
				{
					bodyLines.add(1, String.format("\t%s();", classConstructor + getBaseClassName(bcClass)));
				}
			}
			if (!bcFunc.isOverridenConstructor())
			{
				List<BcVariableDeclaration> initFields = getInitFields(bcClass);
				if (initFields.size() > 0)
				{
					bodyLines.add(1, String.format("\t%s();", classInitFields + className));
				}
			}
			impl.writeln(body);
		}
	}
	
	private void writeFieldsInit(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = getInitFields(bcClass);
		if (fields.size() > 0)
		{		
			String className = getClassName(bcClass);
			String initFieldsName = classInitFields + className;
			
			writeVisiblity("protected", false);
			hdr.writelnf("void %s();", initFieldsName);
			
			impl.writeln();
			impl.writelnf("void %s::%s()", className, initFieldsName);
			writeBlockOpen(impl);
			for (BcVariableDeclaration field : fields)
			{
				impl.writelnf("%s = %s;", getCodeHelper().identifier(field.getIdentifier()), field.getInitializer());
			}
			writeBlockClose(impl);
		}
	}
	
	private void writeClassStaticInit(BcClassDefinitionNode bcClass)
	{
		String className = getClassName(bcClass);
		String superClassName = getBaseClassName(bcClass);
		String staticInitFlagName = classStaticInitializedFlag + className;
		String staticInitFuncName = classStaticInit + className;
		String staticInitializer = classStaticInitializer + className;
		
		writeVisiblity("public", false);
		hdr.writelnf("static void %s();", staticInitFuncName);
		
		writeVisiblity("private", false);
		hdr.writelnf("static bool %s;", staticInitFlagName);
		hdr.writelnf("static %s %s;", classStaticInitializerClass, staticInitializer);
		
		impl.writeln();
		impl.writelnf("bool %s::%s = false;", className, staticInitFlagName);
		impl.writelnf("%s %s::%s(%s);", classStaticInitializerClass, className, staticInitializer, staticInitFuncName);
		impl.writelnf("void %s::%s()", className, staticInitFuncName);
		impl.writeBlockOpen();
		
		impl.writelnf("if (!%s)", staticInitFlagName);
		impl.writeBlockOpen();
		
		impl.writelnf("%s = true;", staticInitFlagName);
		
		if (!typeEquals(getBaseClassType(bcClass), classObject))
		{
			impl.writelnf("%s();", classStaticInit + superClassName);
		}
				
		// initialize static members
		List<BcVariableDeclaration> fields = bcClass.getFields(new BcVariableFilter()
		{
			@Override
			public boolean accept(BcVariableDeclaration field)
			{
				return field.isStatic() && !canBeInitializedInHeader(field);
			}
		});
		
		if (fields.size() > 0)
		{
			List<BcTypeNode> uniqueTypes = getTypesForStaticInitialization(bcClass);
			if (uniqueTypes.size() > 0)
			{
				impl.writeln();
				for (BcTypeNode type : uniqueTypes)
				{
					String typeName = type(type);
					String funcName = classStaticInit + typeName;
					
					impl.writelnf("%s::%s();", typeName, funcName);
				}
			}
			
			impl.writeln();
			for (BcVariableDeclaration field : fields)
			{
				impl.writelnf("%s = %s;", getCodeHelper().identifier(field.getIdentifier()), field.getInitializer());
			}
		}
		
		impl.writeBlockClose();
		impl.writeBlockClose();
	}
	
	private void writeClassDefaultConstructor(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields(new BcVariableFilter()
		{
			@Override
			public boolean accept(BcVariableDeclaration field)
			{
				if (field.isStatic())
				{
					return false;
				}
				
				if (field.hasInitializer() && field.getType().isIntegral())
				{
					return false;
				}
				
				return true;
			}
		});
		
		if (fields.size() > 0)
		{
			String className = getClassName(bcClass);
			
			hdr.writeln();
			writeVisiblity("protected", true);
			hdr.writelnf("%s();", className);
			
			impl.writeln();
			impl.writelnf("%s::%s() : ", className, className);
			
			int fieldIndex = 0;
			for (BcVariableDeclaration field : fields)
			{
				impl.write("  ");
				
				if (canBeClass(field.getType()))
				{
					impl.writef("%s(false)", getCodeHelper().identifier(field.getIdentifier()));
				}
				else
				{
					impl.writef("%s(0)", getCodeHelper().identifier(field.getIdentifier()));
				}
				if (++fieldIndex < fields.size())
				{
					impl.write(",");
				}			
				impl.writeln();
			}
			
			writeBlockOpen(impl);
			writeBlockClose(impl);
		}
	}
	
	private void writeClassGcMarkMethod(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields(new BcVariableFilter()
		{
			@Override
			public boolean accept(BcVariableDeclaration field)
			{
				return !field.isStatic() && !field.getType().isIntegral();
			}
		});
		
		if (fields.size() > 0)
		{
			String className = getClassName(bcClass);
			String baseClassName = getBaseClassName(bcClass);

			writeVisiblity("public", false);
			hdr.writelnf("void %s();", classGcMark);
			
			impl.writeln();
			impl.writelnf("void %s::%s()", className, classGcMark);
			impl.writeBlockOpen();

			impl.writelnf("if (%s())", classGcMarkNeeded);
			impl.writeBlockOpen();
			impl.writelnf("%s::%s();", baseClassName, classGcMark);
			
			for (BcVariableDeclaration field : fields)
			{
				String identifier = getCodeHelper().identifier(field.getIdentifier());
				impl.writelnf("%s(%s)", defineGcMark, identifier);
			}
			
			impl.writeBlockClose();
			impl.writeBlockClose();
		}
	}
	
	private void writeBoxingInterfaces(BcClassDefinitionNode bcClass)
	{
		List<BcTypeNode> interfaces = bcClass.getInterfaces();
		for (BcTypeNode bcInterface : interfaces)
		{
			BcClassDefinitionNode interfaceClass = bcInterface.getClassNode();
			assert interfaceClass != null : bcInterface.getName();
			
			writeBoxingInterface(bcClass, interfaceClass);
		}
	}
	
	private void writeBoxingInterface(BcClassDefinitionNode bcClass, BcClassDefinitionNode interfaceClass)
	{
		lastVisiblityModifier = null;
		
		String className = getClassName(bcClass);
		String baseName = getClassName(interfaceClass);
		String targetFieldName = "m_target";
		
		hdr.writeln();
		
		hdr.decTab();
		hdr.writelnf("/* %s interface wrapper */", baseName);
		hdr.incTab();
		
		writeVisiblity("public", true);
		
		resetVisiblity();
		
		String interfaceWrapperName = classInterfaceWrapper + baseName;
		hdr.writelnf("class %s : public %s", interfaceWrapperName, baseName);
		hdr.writeBlockOpen();
		
		// interface macro
		writeVisiblity("public", true);
		writeDefine(hdr, defineInterface, className, baseName);
		
		// functions
		writeVisiblity("public", true);
		List<BcFunctionDeclaration> functions = interfaceClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			String type = bcFunc.hasReturnType() ? getCodeHelper().typeRef(bcFunc.getReturnType()) : "void";
			String name = getCodeHelper().identifier(bcFunc.getName());
			
			String params = getCodeHelper().paramsDef(bcFunc.getParams());
			String args = getCodeHelper().argsDef(bcFunc.getParams());
			
			hdr.writef("%s %s(%s) { ", type, name, params);			
			if (bcFunc.hasReturnType())
			{
				hdr.write("return ");
			}
			hdr.writelnf("%s->%s(%s); }", targetFieldName, name, args);
		}
		
		hdr.writeBlockClose(true);
		
		// box/unbox methods	
		String boxName = classInterfaceBox + baseName;
		hdr.writeln();
		hdr.writelnf("inline %s %s() { return new %s(this); }", getCodeHelper().typeRef(baseName), boxName, interfaceWrapperName);
		hdr.writelnf("inline static %s* %s(%s* obj) { return ((%s*)obj)->%s; }", className, classInterfaceUnbox, baseName, interfaceWrapperName, targetFieldName);
	}
	
	private List<BcTypeNode> getHeaderTypedefs(BcClassDefinitionNode bcClass)
	{
		List<BcTypeNode> includes = new ArrayList<BcTypeNode>();
		
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
	
	private void resetVisiblity()
	{
		lastVisiblityModifier = null;
	}
	
	private void writeVisiblity(String visiblity, boolean force)
	{
		if (force || !visiblity.equals(lastVisiblityModifier))
		{
			if (lastVisiblityModifier != null)
			{
				hdr.writeln();
			}
			writeVisiblity(hdr, visiblity);
			lastVisiblityModifier = visiblity;
		}
	}

	protected void writeVisiblity(WriteDestination dest, String visiblity)
	{
		dest.decTab();
		dest.writeln(visiblity + ":");
		dest.incTab();
	}
	
	private void writeDefine(WriteDestination dest, String defineName, String...args)
	{
		writeDefine(dest, defineName, false, args);
	}
	
	private void writeDefine(WriteDestination dest, String defineName, boolean addSemicolon, String...args)
	{
		BcCppDefine define = defines.find(defineName);
		if (define != null)
		{
			define.write(dest, args);
		}
		else
		{
			String format = addSemicolon ? "%s(%s);" : "%s(%s)";
			dest.writelnf(format, defineName, BcStringUtils.commaSeparated(args));
		}
	}
	
	private List<BcTypeNode> getImplementationTypedefs(BcClassDefinitionNode bcClass)
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
		
		List<BcTypeNode> additionalImports = bcClass.getAdditionalImports();
		for (BcTypeNode bcType : additionalImports) 
		{
			tryAddUniqueType(includes, bcType);
		}
		
		return includes;
	}
	
	private void tryAddUniqueType(List<BcTypeNode> types, BcTypeNode type)
	{
		if (canBeClass(type))
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
	
	protected CppCodeHelper getCodeHelper()
	{
		return (CppCodeHelper) super.getCodeHelper();
	}

	private List<BcVariableDeclaration> getInitFields(BcClassDefinitionNode bcClass)
	{
		return bcClass.getFields(new BcVariableFilter()
		{
			@Override
			public boolean accept(BcVariableDeclaration field)
			{
				return !field.isStatic() && !field.isConst() && field.hasInitializer();
			}
		});
	}
	
	private List<BcTypeNode> getTypesForStaticInitialization(BcClassDefinitionNode bcClass)
	{
		List<BcTypeNode> uniqueClasses = new ArrayList<BcTypeNode>();
		
		List<BcTypeNode> types = bcClass.getAdditionalImports();		
		for (BcTypeNode type : types)
		{
			if (type.isClass() && !isPlatformClass(type.getClassNode()) && !isKindOfClass(type.getClassNode(), bcClass))
			{
				if (!uniqueClasses.contains(type))
				{
					uniqueClasses.add(type);
				}
			}
		}
		return uniqueClasses;
	}
	
	private boolean canBeInitializedInHeader(BcVariableDeclaration field)
	{
		return field.isStatic() && field.isConst() && field.getType().isIntegral() && field.hasInitializer() && field.isIntegralInitializerFlag();
	}
}
