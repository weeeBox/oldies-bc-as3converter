package bc.lang;

import java.util.ArrayList;
import java.util.List;

import bc.help.BcFunctionFilter;
import bc.help.BcVariableFilter;

import macromedia.asc.parser.Node;

public class BcClassDefinitionNode extends BcDeclaration
{
	private BcTypeNode classType;
	private String classPackage;
	
	private boolean isFinal;
	
	private List<BcVariableDeclaration> fields;
	private List<BcFunctionDeclaration> functions;
	private List<BcFunctionDeclaration> constructors;
	
	private List<BcVariableDeclaration> declaredVars;
	private List<BcTypeNode> additionalImports;
	
	private BcTypeNodeInstance extendsType;
	private List<BcTypeNodeInstance> interfaces;

	private List<Node> statements;
	private List<BcFunctionTypeNode> functionTypes;
	private BcFunctionTypeNode defaultFunctionType;
	
	public BcClassDefinitionNode(BcTypeNode classType)
	{
		assert !classType.hasClassNode() || classType.getName().equals("Vector"); // hack: we create a class for each generic version
		classType.setClassNode(this);
		
		this.classType = classType;
		fields = new ArrayList<BcVariableDeclaration>();
		functions = new ArrayList<BcFunctionDeclaration>();
		constructors = new ArrayList<BcFunctionDeclaration>();
		statements = new ArrayList<Node>();
		interfaces = new ArrayList<BcTypeNodeInstance>();
		additionalImports = new ArrayList<BcTypeNode>();
		functionTypes = new ArrayList<BcFunctionTypeNode>();
		declaredVars = new ArrayList<BcVariableDeclaration>();
	}
	
	public boolean isFinal()
	{
		return isFinal;
	}
	
	public void setFinal(boolean isFinal)
	{
		this.isFinal = isFinal;
	}
	
	public void addStatement(Node statement)
	{
		statements.add(statement);
	}
	
	public List<Node> getStatements()
	{
		return statements;
	}
	
	public String getName()
	{
		return classType.getName();
	}

	public void setClassType(BcTypeNode classType)
	{
		this.classType = classType;
	}
	
	public BcTypeNode getClassType()
	{
		return classType;
	}
	
	public String getPackageName() 
	{
		if (classType.hasQualifier())
		{
			return classType.getQualifier();
		}
		
		return classPackage;
	}
	
	public void setPackageName(String packageName)
	{
		this.classPackage = packageName;
	}
	
	public BcTypeNode getExtendsType()
	{
		return extendsType.getType();
	}
	
	public void setExtendsType(BcTypeNodeInstance extendsType)
	{
		this.extendsType = extendsType;
	}
	
	public boolean hasExtendsType()
	{
		return extendsType != null;
	}
	
	public BcClassDefinitionNode getExtendsClass()
	{
		return hasExtendsType() ? extendsType.getType().getClassNode() : null;
	}
	
	public void addFunctionType(BcFunctionTypeNode node)
	{
		functionTypes.add(node);
		node.getFunc().setOwner(this);
		
		if (node.isUseByDefault())
		{
			assert defaultFunctionType == null;
			defaultFunctionType = node;
		}
	}
	
	public BcFunctionTypeNode getDefaultFunctionType()
	{
		return defaultFunctionType;
	}
	
	public boolean hasDefaultFunctionType()
	{
		return defaultFunctionType != null;
	}
	
	public BcFunctionTypeNode findFunctionType(String name)
	{
		for (BcFunctionTypeNode funcType : functionTypes) 
		{
			if (funcType.getName().equals(name))
			{
				return funcType;
			}
		}
		return extendsType != null ? extendsType.getType().getClassNode().findFunctionType(name) : null;
	}
	
	public boolean hasFunctionTypes()
	{
		return functionTypes.size() > 0;
	}
	
	public List<BcFunctionTypeNode> getFunctionTypes() 
	{
		return functionTypes;
	}
	
	public void addInterface(BcTypeNodeInstance interfaceType)
	{
		interfaces.add(interfaceType);
	}
	
	public List<BcTypeNodeInstance> getInterfaces()
	{
		return interfaces;
	}
	
	public boolean hasInterfaces()
	{
		return interfaces.size() > 0;
	}
	
	public List<BcVariableDeclaration> getDeclaredVars()
	{
		return declaredVars;
	}
	
	public void add(BcVariableDeclaration var)
	{
		fields.add(var);
	}
	
	public void add(BcFunctionDeclaration func)
	{
		functions.add(func);
		func.setOwner(this);
		
		if (func.isConstructor())
		{
			constructors.add(func);
		}
		
		if (func.hasDefaultParams())
		{
			for (int paramsCount = func.getDefaultParamsCount() - 1; paramsCount >= 0; --paramsCount)
			{
				BcFunctionDeclaration overridenFunc = func.createOverridenWithNumDefParams(paramsCount);
				add(overridenFunc);
			}
		}		
	}

	public void addToImport(BcTypeNode bcType)
	{
		if (!additionalImports.contains(bcType))
		{
			additionalImports.add(bcType);
		}
	}
	
	public List<BcVariableDeclaration> getFields()
	{
		return fields;
	}
	
	public List<BcVariableDeclaration> getFields(BcVariableFilter filter)
	{
		List<BcVariableDeclaration> result = new ArrayList<BcVariableDeclaration>();
		for (BcVariableDeclaration field : fields)
		{
			if (filter.accept(field))
				result.add(field);
		}
		return result;
	}
	
	public List<BcFunctionDeclaration> getFunctions()
	{
		return functions;
	}
	
	public List<BcFunctionDeclaration> getFunctions(BcFunctionFilter filter)
	{
		List<BcFunctionDeclaration> result = new ArrayList<BcFunctionDeclaration>();
		for (BcFunctionDeclaration func : functions)
		{
			if (filter.accept(func))
				result.add(func);
		}
		
		return result;
	}
	
	public void setFunctions(List<BcFunctionDeclaration> functions) 
	{
		this.functions = functions;
	}
	
	public List<BcFunctionDeclaration> getConstructors()
	{
		return constructors;
	}
	
	public List<BcTypeNode> getAdditionalImports() 
	{
		return additionalImports;
	}
	
	public boolean hasConstructors()
	{
		return constructors.size() > 0;
	}

	public BcVariableDeclaration findField(final String name)
	{
		return findField(this, new BcVariableDeclarationFilter() {
			
			@Override
			public boolean accept(BcVariableDeclaration bcVar) 
			{
				return bcVar.getIdentifier().equals(name);
			}
		});
	}
	
	public BcVariableDeclaration findFunctionField(final String name)
	{
		final BcTypeNode functionType = BcTypeNode.create("Function"); 
		
		return findField(this, new BcVariableDeclarationFilter() {
			
			@Override
			public boolean accept(BcVariableDeclaration bcVar) 
			{
				return bcVar.getType() == functionType && bcVar.getIdentifier().equals(name);
			}
		});
	}
	
	private static BcVariableDeclaration findField(BcClassDefinitionNode bcClass, BcVariableDeclarationFilter searchCriteria)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields();
		for (BcVariableDeclaration bcField : fields)
		{
			if (searchCriteria.accept(bcField))
			{
				return bcField;
			}
		}
		
		if (bcClass.hasExtendsType())
		{
			BcClassDefinitionNode bcSuperClass = bcClass.getExtendsType().getClassNode();
			return findField(bcSuperClass, searchCriteria);
		}
		
		return null;
	}

	private static final int FIND_NORMAL = 0;
	private static final int FIND_GETTER = 1;
	private static final int FIND_SETTER = 2;
	
	public BcFunctionDeclaration findFunction(String name)
	{
		return findFunction(this, name, FIND_NORMAL);
	}
	
	public BcFunctionDeclaration findGetterFunction(String name)
	{
		return findFunction(this, name, FIND_GETTER);
	}
	
	public BcFunctionDeclaration findSetterFunction(String name)
	{
		return findFunction(this, name, FIND_SETTER);
	}
	
	private static BcFunctionDeclaration findFunction(BcClassDefinitionNode bcClass, String name, int mode)
	{
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		for (BcFunctionDeclaration bcFunc : functions)
		{
			if (bcFunc.getName().equals(name))
			{
				if (bcFunc.isConstructor())
				{
					continue;
				}
				
				if (mode == FIND_GETTER)
				{
					if (!bcFunc.isGetter())
					{
						continue;
					}
				}
				else if (mode == FIND_SETTER)
				{
					if (!bcFunc.isSetter())
					{
						continue;
					}
				}
				
				return bcFunc;
			}
		}
		
		if (bcClass.hasExtendsType())
		{
			BcClassDefinitionNode bcSuperClass = bcClass.getExtendsType().getClassNode();
			assert bcSuperClass != null : bcClass.getExtendsType().getName();
			
			return findFunction(bcSuperClass, name, mode);
		}
		
		return null;
	}
	
	public boolean isInterface()
	{
		return false;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classType == null) ? 0 : classType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BcClassDefinitionNode other = (BcClassDefinitionNode) obj;
		if (classType == null)
		{
			if (other.classType != null)
				return false;
		}
		else if (!classType.equals(other.classType))
			return false;
		return true;
	}

	public BcClassDefinitionNode clone(BcTypeNode classType)
	{
		BcClassDefinitionNode bcClass = new BcClassDefinitionNode(classType);
		bcClass.extendsType = extendsType;
		bcClass.importList = importList;
		
		bcClass.fields = fields;
		bcClass.functions = functions;
		bcClass.constructors = constructors;
		bcClass.declaredVars = declaredVars;
		bcClass.interfaces = interfaces;
		bcClass.statements = statements;
		bcClass.metadata = metadata;
		bcClass.classPackage = classPackage;
		
		return bcClass;
	}
	
	private static interface BcVariableDeclarationFilter
	{
		boolean accept(BcVariableDeclaration bcVar);
	}
	
	@Override
	public String toString()
	{
		return getClassType().getQualifiedName();
	}
}
