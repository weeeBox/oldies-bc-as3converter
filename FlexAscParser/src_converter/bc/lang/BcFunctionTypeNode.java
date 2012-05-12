package bc.lang;

import java.util.List;

public class BcFunctionTypeNode extends BcTypeNode
{
	private BcFunctionDeclaration func;

	public BcFunctionTypeNode()
	{
		super("Function");		
	}
	
	public BcFunctionTypeNode(BcFunctionDeclaration func)
	{
		super("Function");
		this.func = func;
		
		if (func.hasReturnType())
		{
			BcTypeNode returnType = func.getReturnType();
			if (returnType.isClass())
			{
				setClassNode(returnType.getClassNode());
			}
		}
	}

	@Override
	public String getName()
	{
		return isComplete() ? func.getName() : super.getName();
	}
	
	public boolean isComplete()
	{
		return func != null;
	}
	
	public BcFunctionDeclaration getFunc()
	{
		return func;
	}
	
	public void addParam(BcFuncParam param)
	{
		func.addParam(param);
	}
	
	public List<BcFuncParam> getParams() 
	{
		assert isComplete();
		return func.getParams();
	}
	
	public BcTypeNode getReturnType()
	{
		assert isComplete();
		return func.getReturnType();
	}
	
	public boolean hasReturnType()
	{
		return isComplete() && func.hasReturnType();
	}

	public void setReturnType(BcTypeNode returnType)
	{
		func.setReturnType(returnType);
	}
}
