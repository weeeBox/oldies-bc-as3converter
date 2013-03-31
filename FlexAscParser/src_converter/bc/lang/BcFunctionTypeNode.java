package bc.lang;

import java.util.List;

public class BcFunctionTypeNode extends BcTypeNode
{
	private BcFunctionDeclaration func;
	private boolean useByDefault;
	private String attachedParam;

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
	
	@Override
	public boolean isFunction()
	{
		return true;
	}
	
	@Override
	public BcFunctionTypeNode asFunction()
	{
		return this;
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
	
	public boolean isGetter()
	{
		return func != null && func.isGetter();
	}
	
	public boolean isSetter()
	{
		return func != null && func.isSetter();
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

	public void setReturnType(BcTypeNodeInstance returnType)
	{
		func.setReturnType(returnType);
	}

	public String getAttachedParam()
	{
		return attachedParam;
	}
	
	public void setAttachedParam(String attachedParam)
	{
		this.attachedParam = attachedParam;
	}
	
	public boolean hasAttachedParam()
	{
		return attachedParam != null;
	}
}
