package bc.help;

import bc.code.ListWriteDestination;
import bc.lang.BcArgumentsList;
import bc.lang.BcClassDefinitionNode;
import bc.lang.BcFunctionDeclaration;
import bc.lang.BcTypeNode;
import bc.lang.BcVectorTypeNode;

public class CppCodeHelper extends BcCodeHelper
{
	private static final String PREFIX_REF = "_ref";
	private static final String PREFIX_VECTOR = "_V_";
	private static final String NEW = "AS_NEW";
	private static final String NEW_VECTOR = "AS_NEW_VECTOR";
	private static final String NEW_PRIMITIVE_VECTOR = "AS_NEW_PRIMITIVES_VECTOR";
	private static final String UNBOX = "AS_UNBOX";
	private static final String SELECTOR = "AS_SELECTOR";
	
	private static final String STRING_LITERAL = "ASL";
	
	private static final String IS_OPERATOR = "AS_IS";
	
	@Override
	public String construct(String type, Object initializer)
	{
		return String.format("%s(%s,(%s))", NEW, type(type), initializer);
	}

	@Override
	public String operatorIs(Object lhs, Object rhs)
	{
		return String.format("%s(%s, %s)", IS_OPERATOR, lhs, rhs);
	}

	@Override
	public String literalNull()
	{
		return "AS_NULL";
	}
	
	@Override
	public String literalString(String value)
	{
		return String.format("%s(\"%s\")", STRING_LITERAL, BcStringUtils.replaceEscapes(value));
	}

	@Override
	protected String vectorType(BcVectorTypeNode vectorType)
	{
		return type(vectorType.getName());
	}

	@Override
	public String constructVector(BcVectorTypeNode vectorType, BcArgumentsList args)
	{	
		BcTypeNode genericType = vectorType.getGeneric();
		String defineName = genericType.isIntegral() ? NEW_PRIMITIVE_VECTOR : NEW_VECTOR;

		return String.format("%s(%s,(%s))", defineName, type(genericType), args);
	}
	
	@Override
	public String constructLiteralVector(BcVectorTypeNode vectorType, BcArgumentsList args)
	{	
		BcArgumentsList initializer = new BcArgumentsList(1);
		initializer.add(args.size());
		
		ListWriteDestination dest = new ListWriteDestination();
		dest.write(constructVector(vectorType, initializer));
		
		for (Object arg : args)
		{
			dest.writef(" << %s", arg);
		}
		
		return dest.toString();
	}
	
	@Override
	public String cast(Object expr, BcTypeNode type)
	{
		return String.format("(%s)(%s)", typeRef(type), expr);
	}
	
	@Override
	public String castClass(Object expr, BcTypeNode fromType, BcTypeNode toType)
	{
		return expr.toString();
	}
	
	@Override
	public String castInterface(Object expr, BcTypeNode fromType, BcTypeNode toType)
	{
		return String.format("%s(%s, %s)", UNBOX, type(toType), expr);
	}

	@Override
	public String selector(BcClassDefinitionNode bcClass, Object funcExp)
	{
		return String.format("%s(%s, %s)", SELECTOR, type(bcClass.getClassType()), funcExp);
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
	
	public String typePtr(BcTypeNode bcType)
	{
		 return typePtr(bcType.getName());
	}
	
	public String typePtr(String type)
	{
		return type(type) + "*";
	}
	
	public String typeRef(BcTypeNode bcType)
	{
		if (bcType instanceof BcVectorTypeNode)
		{
			BcVectorTypeNode vectorType = (BcVectorTypeNode) bcType;
			return PREFIX_VECTOR + type(vectorType.getGeneric()) + PREFIX_REF;
		}
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
	
	@Override
	public String varDecl(BcTypeNode type, String identifier)
	{
		return String.format("%s %s", typeRef(type), identifier(identifier));
	}
	
	public String include(String filename)
	{
		return String.format("#include \"%s\"", filename);
	}
}
