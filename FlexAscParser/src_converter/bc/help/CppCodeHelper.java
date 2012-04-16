package bc.help;

import bc.lang.BcVectorTypeNode;

public class CppCodeHelper extends BcCodeHelper
{

	@Override
	public String construct(String type, Object initializer)
	{
		return "foo";
	}

	@Override
	public String operatorIs(Object lhs, Object rhs)
	{
		return "foo";
	}

	@Override
	public String literalNull()
	{
		return "NULL";
	}

	@Override
	protected String classType(String name)
	{
		return "foo";
	}

	@Override
	protected String vectorType(BcVectorTypeNode vectorType)
	{
		return "foo";
	}

	@Override
	protected String constructVector(BcVectorTypeNode vectorType, Object initializer)
	{
		return "foo";
	}

	@Override
	protected String staticCall(String type, String method, Object args)
	{
		return String.format("%s::%s(%s)", type, method, args);
	}

}
