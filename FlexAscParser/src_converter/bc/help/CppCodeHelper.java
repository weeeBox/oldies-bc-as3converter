package bc.help;

import bc.lang.BcTypeNode;
import bc.lang.BcVectorTypeNode;

public class CppCodeHelper extends BcCodeHelper
{
	private static final String PREFIX_REF = "_ref";
	
	private static final String IS_OPERATOR = "ASIS";
	
	@Override
	public String construct(String type, Object initializer)
	{
		return "foo";
	}

	@Override
	public String operatorIs(Object lhs, Object rhs)
	{
		return String.format("%s(%s, %s)", IS_OPERATOR, lhs, rhs);
	}

	@Override
	public String literalNull()
	{
		return "NULL";
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
	public String memberSelector(Object target, Object selector)
	{
		return String.format("%s->%s", target, selector);
	}
	
	@Override
	public String staticSelector(Object target, Object selector)
	{
		return String.format("%s::%s", target, selector);
	}
	
	public String typeRef(BcTypeNode bcType)
	{
		return typeRef(bcType.getName());
	}

	public String typeRef(String type)
	{
		if (isBasicType(type))
		{
			return type(type);
		}
		return String.format("%s%s", type(type), PREFIX_REF);
	}
	
	@Override
	public String paramDecl(BcTypeNode type, String identifier)
	{
		if (isBasicType(type))
		{
			return String.format("%s %s", type(type), identifier(identifier));
		}
		return String.format("const %s& %s", typeRef(type), identifier(identifier));
	}
}
