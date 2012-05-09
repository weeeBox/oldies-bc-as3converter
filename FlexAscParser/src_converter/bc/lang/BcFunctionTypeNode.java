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

	public boolean isComplete()
	{
		return func != null;
	}
	
	public BcFunctionDeclaration getFunc()
	{
		return func;
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
}
