package bc.lang;

import java.util.ArrayList;
import java.util.List;

import bc.help.BcCodeCpp;

import macromedia.asc.parser.Node;

public class BcClassDefinitionNode extends BcNode
{
	private BcTypeNode classType;
	
	private boolean hasReferenceVars;
	
	private String packageName;
	
	private List<BcVariableDeclaration> fields;
	private List<BcFunctionDeclaration> functions;
	private List<BcFunctionDeclaration> constructors;
	
	private List<BcVariableDeclaration> declaredVars;
	
	private BcTypeNode extendsType;
	private List<BcTypeNode> interfaces;

	private List<Node> statements;
	
	public BcClassDefinitionNode(BcTypeNode classType)
	{
		this.classType = classType;
		fields = new ArrayList<BcVariableDeclaration>();
		functions = new ArrayList<BcFunctionDeclaration>();
		constructors = new ArrayList<BcFunctionDeclaration>();
		statements = new ArrayList<Node>();
		interfaces = new ArrayList<BcTypeNode>();
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
	
	public void setPackageName(String packageName) 
	{
		this.packageName = packageName;
	}
	
	public String getPackageName() 
	{
		return packageName;
	}
	
	public BcTypeNode getExtendsType()
	{
		return extendsType;
	}
	
	public void setExtendsType(BcTypeNode extendsType)
	{
		this.extendsType = extendsType;
	}
	
	public boolean hasExtendsType()
	{
		return extendsType != null;
	}
	
	public void addInterface(BcTypeNode interfaceType)
	{
		interfaces.add(interfaceType);
	}
	
	public List<BcTypeNode> getInterfaces()
	{
		return interfaces;
	}
	
	public boolean hasInterfaces()
	{
		return interfaces != null;
	}
	
	public void setDeclaredVars(List<BcVariableDeclaration> declaredVars)
	{
		this.declaredVars = declaredVars;
	}
	
	public List<BcVariableDeclaration> getDeclaredVars()
	{
		return declaredVars;
	}
	
	public void add(BcVariableDeclaration var)
	{
		if (BcCodeCpp.canBeClass(var.getType()))
		{
			hasReferenceVars = true;
		}
		fields.add(var);
	}
	
	public boolean hasReferenceVars()
	{
		return hasReferenceVars;
	}

	public void add(BcFunctionDeclaration func)
	{
		functions.add(func);
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

	public List<BcVariableDeclaration> getFields()
	{
		return fields;
	}
	
	public List<BcFunctionDeclaration> getFunctions()
	{
		return functions;
	}
	
	public List<BcFunctionDeclaration> getConstructors()
	{
		return constructors;
	}
	
	public boolean hasConstructors()
	{
		return constructors.size() > 0;
	}

	public BcVariableDeclaration findField(String name)
	{
		return findField(this, name);
	}
	
	private static BcVariableDeclaration findField(BcClassDefinitionNode bcClass, String name)
	{
		List<BcVariableDeclaration> fields = bcClass.getFields();
		for (BcVariableDeclaration bcField : fields)
		{
			if (bcField.getIdentifier().equals(name))
			{
				return bcField;
			}
		}
		
		if (bcClass.hasExtendsType())
		{
			BcClassDefinitionNode bcSuperClass = bcClass.getExtendsType().getClassNode();
			return findField(bcSuperClass, name);
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

	public BcClassDefinitionNode clone()
	{
		BcClassDefinitionNode bcClass = new BcClassDefinitionNode(classType);
		bcClass.extendsType = extendsType;
		
		bcClass.fields = fields;
		bcClass.functions = functions;
		bcClass.constructors = constructors;
		bcClass.declaredVars = declaredVars;
		bcClass.interfaces = interfaces;
		bcClass.statements = statements;
		
		return bcClass;
	}
}
