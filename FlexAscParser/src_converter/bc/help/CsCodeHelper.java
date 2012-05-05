package bc.help;

import bc.lang.BcArgumentsList;
import bc.lang.BcVectorTypeNode;

public class CsCodeHelper extends BcCodeHelper
{
	private static final String NEW = "new";
	private static final String IS = "is";
	
	protected static final String VECTOR_BC_TYPE = "Vector";
	protected static final String NULL = "null";
	
	@Override
	protected String classType(String name)
	{
		if (name.equals("String"))
		{
			return name;
		}
		
		return super.classType(name);
	}
	
	@Override
	public String construct(String type, Object initializer)
	{
		return NEW + " " + type(type) + "(" + initializer + ")";
	}
	
	@Override
	protected String vectorType(BcVectorTypeNode vectorType)
	{
		String genericName = type(vectorType.getGeneric());
		return type(VECTOR_BC_TYPE) + "<" + genericName + ">";
	}
	
	@Override
	public String constructVector(BcVectorTypeNode vectorType, BcArgumentsList args)
	{
		return NEW + " " + type(VECTOR_BC_TYPE) + "<" + type(vectorType.getGeneric()) + ">" + "(" + args + ")";
	}
	
	@Override
	public String constructLiteralVector(BcVectorTypeNode vectorType, BcArgumentsList args)
	{
		return constructVector(vectorType, args);
	}
	
	@Override
	public String operatorIs(Object lhs, Object rhs)
	{
		return String.format("%s %s %s", lhs, IS, type(rhs.toString()));
	}
	
	@Override
	public String literalNull()
	{
		return NULL;
	}
}
