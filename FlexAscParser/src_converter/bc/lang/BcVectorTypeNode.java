package bc.lang;

import java.util.ArrayList;
import java.util.List;

import bc.error.NotImplementedException;

public class BcVectorTypeNode extends BcTypeNode
{
	private BcTypeNodeInstance genericTypeInstance;
	
	protected BcVectorTypeNode(BcTypeName name, BcTypeNodeInstance genericTypeInstance)
	{
		super(name);
		this.genericTypeInstance = genericTypeInstance;
	}
	
	public void setGeneric(BcTypeNode generic) 
	{
		this.genericTypeInstance = generic.createTypeInstance(); // TODO: use type instance
	}
	
	public BcTypeNode getGeneric()
	{
		return genericTypeInstance != null ? genericTypeInstance.getType() : null;
	}
	
	public BcTypeNodeInstance getGenericTypeInstance() 
	{
		return genericTypeInstance;
	}
	
	@Override
	public String getNameEx()
	{
		return getName() + "<" + genericTypeInstance.getName() + ">";
	}

	@Override
	public String toString()
	{
		return super.toString() + "<" + getGeneric() + ">";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((genericTypeInstance == null) ? 0 : genericTypeInstance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BcVectorTypeNode other = (BcVectorTypeNode) obj;
		if (genericTypeInstance == null)
		{
			if (other.genericTypeInstance != null)
				return false;
		}
		else if (!genericTypeInstance.equals(other.genericTypeInstance))
			return false;
		return true;
	}
	
	public BcClassDefinitionNode createGenericClass(BcClassDefinitionNode origin)
	{
		BcClassDefinitionNode bcClass = origin.clone(this);
		
		List<BcFunctionDeclaration> functions = bcClass.getFunctions();
		List<BcFunctionDeclaration> newFunctions = new ArrayList<BcFunctionDeclaration>(functions.size());
		for (BcFunctionDeclaration bcFunc : functions) 
		{
			newFunctions.add(cloneGeneric(bcFunc));
		}
		bcClass.setFunctions(newFunctions);
		
		// TODO: implement fields
		
		return bcClass;
	}

	private BcFunctionDeclaration cloneGeneric(BcFunctionDeclaration bcFunc) 
	{
		BcFunctionDeclaration newFunc = bcFunc;
		if (bcFunc.hasReturnType() && isGenericType(bcFunc.getReturnType()))
		{
			newFunc = bcFunc.clone();
			newFunc.setReturnType(genericTypeInstance);
		}
		
		List<BcFuncParam> params = bcFunc.getParams();
		for (BcFuncParam param : params) 
		{
			if (isGenericType(param.getType()))
			{
				throw new NotImplementedException();
			}
		}
		
		return newFunc;
	}

	private boolean isGenericType(BcTypeNode type) 
	{
		return type instanceof BcGenericTypeNode;
	}
}
