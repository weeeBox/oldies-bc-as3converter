package bc.lang;

import java.util.List;

public class BcFunctionTypeNode extends BcTypeNode
{
	private BcFunctionDeclaration func;
	private boolean useByDefault;

	public BcFunctionTypeNode()
	{
		super("Function");		
	}
	
	public BcFunctionTypeNode(BcFunctionDeclaration func)
	{
		super(func.getName());
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
	
	public void setUseByDefault(boolean useByDefault)
	{
		this.useByDefault = useByDefault;
	}
	
	public boolean isUseByDefault()
	{
		return useByDefault;
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
		assert isComplete();
		return func.hasReturnType();
	}

	public void setReturnType(BcTypeNode returnType)
	{
		func.setReturnType(returnType);
	}
}
