package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcFunctionTypeNode extends BcTypeNode
{
	private List<BcFuncParam> params;
	private BcTypeNode returnType;

	public BcFunctionTypeNode()
	{
		super("Function");
		params = new ArrayList<BcFuncParam>();
	}

	public void addParam(BcFuncParam param)
	{
		params.add(param);
	}
	
	public List<BcFuncParam> getParams() 
	{
		return params;
	}
	
	public void setReturnType(BcTypeNode returnType)
	{
		this.returnType = returnType;
	}
	
	public BcTypeNode getReturnType()
	{
		return returnType;
	}
	
	public boolean hasReturnType()
	{
		return returnType != null;
	}
}
