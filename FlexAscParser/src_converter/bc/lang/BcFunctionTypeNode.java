package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcFunctionTypeNode extends BcTypeNode
{
	private List<BcTypeNode> args;
	private BcTypeNode returnType;

	public BcFunctionTypeNode()
	{
		super("Function");
		args = new ArrayList<BcTypeNode>();
	}

	public void add(BcTypeNode arg)
	{
		args.add(arg);
	}
	
	public List<BcTypeNode> getArgs()
	{
		return args;
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
