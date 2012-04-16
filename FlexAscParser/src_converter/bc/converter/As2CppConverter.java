package bc.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bc.code.ListWriteDestination;
import bc.code.WriteDestination;
import bc.help.CppCodeHelper;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFuncParam;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcTypeNode;
import bc.lang.BcVariableDeclaration;
import bc.lang.BcVectorTypeNode;

public class As2CppConverter extends As2WhateverConverter
{
	private static final String classInitialiseName = "__internalInitialise";
	private static final String classGcName = "__internalGc";
	private static final String classGcNeeded = "__internalGcNeeded";
	private static final String classConstructName = "__internalConstruct";
	private static final String classAllocName = "__alloc";

	private static final String interfaceBoxName = "__internalBox";
	private static final String interfaceUnboxName = "__internalUnbox";
	
	private static final String classStaticInitializerName = "__internalStaticInitializer";
	private static final String classStaticInitializedName = "__internalStaticInitialized";
	private static final String classStaticInitFuncName = "__internalStaticInit";
	
	private String lastVisiblityModifier;
	private ListWriteDestination hdr;
	private ListWriteDestination impl;

	public As2CppConverter()
	{
		super(new CppCodeHelper());
	}
	
	@Override
	protected void writeClassDefinition(BcClassDefinitionNode bcClass, File outputDir) throws IOException
	{
		String className = getClassName(bcClass);
		String classExtends = getBaseClassName(bcClass);
		
		File hdrFile = new File(outputDir, className + ".h");
		File implFile = new File(outputDir, className + ".cpp");
		
		outputDir.mkdirs();
		
		hdr = new ListWriteDestination();
		impl = new ListWriteDestination();
		
		lastVisiblityModifier = null;
		
		String defGuardName = className + "_h__";
		
		hdr.writeln("#ifndef " + defGuardName);
		hdr.writeln("#define " + defGuardName);
		hdr.writeln();
		
		hdr.writeln("#include \"AsBase.h\"");
		hdr.writeln();
		
		hdr.writelnf("#include \"%s.h\"", classExtends);
		hdr.writeln();
		
		impl.writelnf("#include \"%s.h\"", className);
		
		List<BcTypeNode> headerTypes = getHeaderTypedefs(bcClass);
		List<BcTypeNode> implTypes = getImplementationTypedefs(bcClass);
		
		writeHeaderTypes(hdr, headerTypes);
		writeImplementationTypes(impl, headerTypes);
		writeImplementationTypes(impl, implTypes);
		
		hdr.writeln();
		
		hdr.writelnf("class %s : public %s", className, classExtends);
		hdr.writeln("{");
		hdr.incTab();
		
		writeTypename(bcClass);
		writeFields(bcClass);
		writeFunctions(bcClass);
		writeConstruct(bcClass);
		writeClassInit(bcClass);
		writeClassStaticInit(bcClass);
		writeClassDefaultConstructor(bcClass);
		writeClassInternalGc(bcClass);
		
		// generate interface boxers accessors if any
		if (bcClass.hasInterfaces())
		{
			writeBoxingInterfacesAccessors(bcClass);
		}
		
		hdr.decTab();
		hdr.writeln("};");
		
		hdr.writeln();
		impl.writeln();

		// generate interface boxers if any
		if (bcClass.hasInterfaces())
		{
			writeBoxingInterfaces(bcClass);
		}
		
		hdr.writeln("#endif // " + defGuardName);
		
		if (shouldWriteClassToFile(bcClass, hdrFile))
			writeDestToFile(hdr, hdrFile);
		
		if (shouldWriteClassToFile(bcClass, implFile))
			writeDestToFile(impl, implFile);
	}

	private void writeHeaderTypes(WriteDestination dst, List<BcTypeNode> types)
	{
		for (BcTypeNode bcType : types)
		{
			if (bcType instanceof BcVectorTypeNode)
			{
				BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;
				String genericName = type(vectorType.getGeneric());
				String typeName = type(bcType);
				
				dst.writelnf("typedef %s<%s> %s;", type(getCodeHelper().VECTOR_TYPE), getCodeHelper().typeRef(genericName), typeName);
				dst.writelnf("typedef %s::Ref %s;", typeName, getCodeHelper().typeRef(typeName));
			}
			else
			{
				String typeName = type(bcType);
				dst.writelnf("ASTYPEDEF(%s)", typeName);				
			}
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
		hdr.writelnf("ASOBJ(%s, %s);", className, baseClassName);
	}
	
	private void writeFields(BcClassDefinitionNode bcClass)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields();
		impl.writeln();
		
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
				else if (!bcField.hasInitializer())
				{
					impl.writelnf("%s %s::%s(0);", type, className, name);
				}
			}
			if (bcField.isConst() && !canBeClass)
			{
				hdr.write("const ");
			}
			hdr.writef("%s %s", type, name);
			if (isConst && bcField.isIntegralInitializerFlag())
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
			hdr.writef("%s %s(", type, name);
			
			impl.writeln();
			impl.writef("%s %s::%s(", type, getClassName(bcClass), name);
			
			StringBuilder paramsBuffer = new StringBuilder();
			List<BcFuncParam> params = bcFunc.getParams();
			int paramIndex = 0;
			for (BcFuncParam bcParam : params)
			{
				paramsBuffer.append(getCodeHelper().paramDecl(bcParam.getType(), bcParam.getIdentifier()));
				if (++paramIndex < params.size())
				{
					paramsBuffer.append(", ");
				}
			}
			
			hdr.write(paramsBuffer);
			impl.write(paramsBuffer);
			hdr.writeln(");");
			impl.writeln(")");
			
			impl.writeln(bcFunc.getBody());
		}
	}
	
	private void writeConstruct(BcClassDefinitionNode bcClass)
	{
		List<BcFunctionDeclaration> constructors = bcClass.getConstructors();
		if (constructors.isEmpty())
		{
			return;
		}
		
		String className = getClassName(bcClass);
		
		writeVisiblity("private", true);
		
		for (BcFunctionDeclaration bcFunc : constructors)
		{
			String constructFuncName = classConstructName + className;
			
			hdr.writef("void %s(", constructFuncName);
			impl.writeln();
			impl.writef("void %s::%s(", className, constructFuncName);
			
			StringBuilder paramsBuffer = new StringBuilder();
			List<BcFuncParam> params = bcFunc.getParams();
			int paramIndex = 0;
			for (BcVariableDeclaration bcParam : params)
			{
				paramsBuffer.append(getCodeHelper().paramDecl(bcParam.getType(), bcParam.getIdentifier()));
				if (++paramIndex < params.size())
				{
					paramsBuffer.append(", ");
				}
			}
			
			hdr.write(paramsBuffer);
			impl.write(paramsBuffer);
			hdr.writeln(");");
			impl.writeln(")");
			
			ListWriteDestination body = bcFunc.getBody();
			List<String> bodyLines = body.getLines();
			String constructorLine = bodyLines.get(1);
			
			String superConstructFuncName = classConstructName + getBaseClassName(bcClass);
			if (constructorLine.contains(getCodeHelper().superCallMarker))
			{
				String newConstructorLine = constructorLine.replace(getCodeHelper().superCallMarker, superConstructFuncName);
				bodyLines.set(1, newConstructorLine);				
			}
			else if (constructorLine.contains(getCodeHelper().thisCallMarker))
			{
				String thisConstructFuncName = classConstructName + getClassName(bcClass);
				String newConstructorLine = constructorLine.replace(getCodeHelper().thisCallMarker, thisConstructFuncName);
				bodyLines.set(1, newConstructorLine);
			}
			else
			{
				bodyLines.add(1, "\t" + superConstructFuncName + "();");
			}
			if (!bcFunc.isOverridenConstructor())
			{
				bodyLines.add(2, "\t" + classInitialiseName + className + "();");
			}
			impl.writeln(body);
		}
	}
	
	private void writeClassInit(BcClassDefinitionNode bcClass)
	{
		String className = getClassName(bcClass);
		
		String initializeName = classInitialiseName + className;
		
		writeVisiblity("public", false);
		
		hdr.writelnf("void %s();", initializeName);
		
		impl.writeln();
		impl.writelnf("void %s::%s()", className, initializeName);
		writeBlockOpen(impl);
		
		List<BcVariableDeclaration> fields = bcClass.getDeclaredVars();
		for (BcVariableDeclaration field : fields)
		{
			if (field.isConst())
			{
				continue;
			}
			
			if (field.hasInitializer())
			{
				impl.writelnf("%s = %s;", getCodeHelper().identifier(field.getIdentifier()), field.getInitializer());
			}
		}
		
		writeBlockClose(impl);
	}
	
	private void writeClassStaticInit(BcClassDefinitionNode bcClass)
	{
		String classType = getClassName(bcClass);
		String superClassType = getBaseClassName(bcClass);
		
		String initializerName = classStaticInitializerName + classType;
		String initializedFlagName = classStaticInitializedName + classType;
		
		writeVisiblity("private", true);
		hdr.writelnf("static StaticInit %s;", initializerName);
		hdr.writelnf("static BOOL %s;", initializedFlagName);
		
		writeVisiblity("public", true);
		hdr.writelnf("static void %s();", classStaticInitFuncName);
		
		impl.writeln();
		impl.writelnf("StaticInit %s::%s(&%s::%s);", classType, initializerName, classType, classStaticInitFuncName);
		impl.writelnf("BOOL %s::%s = false;", classType, initializedFlagName);
		impl.writeln();
		impl.writelnf("void %s::%s()", classType, classStaticInitFuncName);
		writeBlockOpen(impl);
		impl.writelnf("if (!%s)", initializedFlagName);
		writeBlockOpen(impl);
		impl.writelnf("%s = true;", initializedFlagName);
		
		// super class initialization
		impl.writelnf("%s::%s();", superClassType, classStaticInitFuncName);
		
		// initialize static members
		List<BcVariableDeclaration> fields = bcClass.getDeclaredVars();
		for (BcVariableDeclaration field : fields)
		{
			if (field.isConst())
			{
				continue;
			}
			
			if (!field.hasInitializer())
			{
				continue;
			}
			
			if (field.isIntegralInitializerFlag())
			{
				continue;
			}
			
			impl.writelnf("%s = %s;", getCodeHelper().identifier(field.getIdentifier()), field.getInitializer());
		}
		
		writeBlockClose(impl);		
		writeBlockClose(impl);
	}
	
	private void writeClassDefaultConstructor(BcClassDefinitionNode bcClass)
	{
		String className = getClassName(bcClass);
		
		writeVisiblity("protected", true);
		hdr.writelnf("%s();", className);
		
		impl.writeln();
		impl.writef("%s::%s()", className, className);
		
		List<BcVariableDeclaration> fields = bcClass.getDeclaredVars();
		List<BcVariableDeclaration> initializedFields = new ArrayList<BcVariableDeclaration>();
		for (BcVariableDeclaration field : fields)
		{
			if (field.isStatic())
			{
				continue;
			}
			
			if (field.hasInitializer())
			{
				continue;
			}
			
			initializedFields.add(field);
		}

		if (initializedFields.size() > 0)
		{
			impl.writeln(" :");
			
			int fieldIndex = 0;
			for (BcVariableDeclaration field : initializedFields)
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
				if (++fieldIndex < initializedFields.size())
				{
					impl.write(",");
				}			
				impl.writeln();
			}
		}
		else
		{
			impl.writeln();
		}
		
		writeBlockOpen(impl);
		writeBlockClose(impl);
	}
	
	private void writeClassInternalGc(BcClassDefinitionNode bcClass)
	{
		if (bcClass.hasReferenceVars())
		{
			String className = getClassName(bcClass);
			String baseClassName = getBaseClassName(bcClass);
			
			writeVisiblity("public", true);
			
			hdr.writelnf("void %s();", classGcName);
			
			impl.writeln();
			impl.writelnf("void %s::%s()", className, classGcName);
			writeBlockOpen(impl);

			impl.writelnf("if(%s())", classGcNeeded);
			writeBlockOpen(impl);
			impl.writelnf("%s::%s();", baseClassName, classGcName);
			
			List<BcVariableDeclaration> fields = bcClass.getDeclaredVars();
			for (BcVariableDeclaration field : fields)
			{
				if (field.isStatic())
				{
					continue;
				}
				
				if (!canBeClass(field.getType()))
				{
					continue;
				}
				
				String identifier = getCodeHelper().identifier(field.getIdentifier());
				impl.writelnf("if (%s != %s) %s->%s();", identifier, getCodeHelper().literalNull(), identifier, classGcName);
			}
			
			writeBlockClose(impl);
			writeBlockClose(impl);
		}
	}
	
	private void writeBoxingInterfacesAccessors(BcClassDefinitionNode bcClass)
	{
		hdr.writeln();
		writeVisiblity("public", true);
		
		List<BcTypeNode> interfaces = bcClass.getInterfaces();
		for (BcTypeNode bcInterface : interfaces)
		{
			BcClassDefinitionNode interfaceClass = bcInterface.getClassNode();
			assert interfaceClass != null : bcInterface.getName();
			
			writeBoxingInterfaceAccessor(bcClass, interfaceClass);
		}
	}
	
	private void writeBoxingInterfaceAccessor(BcClassDefinitionNode bcClass, BcClassDefinitionNode interfaceClass)
	{
		String className = getClassName(bcClass);
		String interfaceName = getClassName(interfaceClass);
		String interfaceRef = getCodeHelper().typeRef(interfaceName);
		String boxName = interfaceBoxName + interfaceName; 
		
		hdr.writelnf("%s %s();", interfaceRef, boxName);
		impl.writelnf("%s %s::%s()", interfaceRef, className, boxName);
		writeBlockOpen(impl);
		impl.writelnf("return %s(new %s(this));", interfaceRef, className + "_" + interfaceClass.getName());
		writeBlockClose(impl);
	}
	
	private void writeBoxingInterfaces(BcClassDefinitionNode bcClass)
	{
		hdr.writeln("/////////////////////////////////////////////////////////////////////////////");
		hdr.writeln("// interface boxing");
		hdr.writeln("/////////////////////////////////////////////////////////////////////////////");		
		impl.writeln("/////////////////////////////////////////////////////////////////////////////");
		impl.writeln("// interface boxing");
		impl.writeln("/////////////////////////////////////////////////////////////////////////////");		
		
		List<BcTypeNode> interfaces = bcClass.getInterfaces();
		for (BcTypeNode bcInterface : interfaces)
		{
			BcClassDefinitionNode interfaceClass = bcInterface.getClassNode();
			assert interfaceClass != null : bcInterface.getName();
			
			writeBoxingInterface(bcClass, interfaceClass);
		}
		
		hdr.writeln();
	}
	
	private void writeBoxingInterface(BcClassDefinitionNode bcClass, BcClassDefinitionNode interfaceClass)
	{
		lastVisiblityModifier = null;
		
		impl.writelnf("// %s", interfaceClass.getName());
		impl.writeln();
		
		String className = getClassName(bcClass);
		String classRef = getCodeHelper().typeRef(className);
		
		String interfaceName = getClassName(bcClass) + "_" + interfaceClass.getName();
		String interfaceBaseName = getClassName(interfaceClass);
		
		hdr.writelnf("#include \"%s.h\"", interfaceBaseName);
		hdr.writeln();
		
		hdr.writelnf("class %s : public %s", interfaceName, interfaceBaseName);
		hdr.writeln("{");
		hdr.incTab();
		
		// delegate
		writeVisiblity("private", true);
		hdr.writelnf("%s m_base;", classRef);
		
		// constructor
		writeVisiblity("public", true);
		hdr.writelnf("%s(const %s& base);", interfaceName, classRef);
		impl.writelnf("%s::%s(const %s& base) : m_base(base)", interfaceName, interfaceName, classRef);
		writeBlockOpen(impl);
		writeBlockClose(impl);
		
		// functions
		writeVisiblity("public", true);
		List<BcFunctionDeclaration> functions = interfaceClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			String type = bcFunc.hasReturnType() ? getCodeHelper().typeRef(bcFunc.getReturnType()) : "void";
			String name = getCodeHelper().identifier(bcFunc.getName());
			
			hdr.writef("%s %s(", type, name);
			
			impl.writeln();
			impl.writef("%s %s::%s(", type, interfaceName, name);
			
			StringBuilder paramsBuffer = new StringBuilder();
			StringBuilder argsBuffer = new StringBuilder();
			List<BcFuncParam> params = bcFunc.getParams();
			int paramIndex = 0;
			for (BcFuncParam bcParam : params)
			{
				argsBuffer.append(getCodeHelper().paramDecl(bcParam.getType(), bcParam.getIdentifier()));
				if (++paramIndex < params.size())
				{
					paramsBuffer.append(", ");
					argsBuffer.append(", ");
				}
			}
			
			hdr.write(paramsBuffer);
			impl.write(paramsBuffer);
			hdr.writeln(");");
			impl.writeln(")");
			
			writeBlockOpen(impl);
			
			if (bcFunc.hasReturnType())
			{
				impl.write("return ");
			}
			impl.writelnf("m_base->%s(%s);", bcFunc.getName(), argsBuffer);
			
			writeBlockClose(impl);
		}
		
		// internalGc

		impl.writeln();
		
		writeVisiblity("public", true);
		hdr.writelnf("void %s();", classGcName);
		impl.writelnf("void %s::%s()", interfaceName, classGcName);
		writeBlockOpen(impl);
		impl.writelnf("if(%s())", classGcNeeded);
		writeBlockOpen(impl);
		impl.writelnf("%s::%s();", interfaceBaseName, classGcName);
		impl.writelnf("m_base->%s();", classGcName);
		writeBlockClose(impl);		
		writeBlockClose(impl);		
		
		hdr.decTab();
		hdr.writeln("};");
	}
	
	private List<BcTypeNode> getHeaderTypedefs(BcClassDefinitionNode bcClass)
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
	
	private void writeVisiblity(String visiblity, boolean force)
	{
		if (force || !visiblity.equals(lastVisiblityModifier))
		{
			if (lastVisiblityModifier != null)
			{
				hdr.writeln();
			}
			hdr.decTab();
			hdr.writeln(visiblity + ":");
			hdr.incTab();
			lastVisiblityModifier = visiblity;
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
}
