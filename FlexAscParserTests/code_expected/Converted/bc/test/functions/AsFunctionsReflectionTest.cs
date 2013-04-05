using System;
 
using bc.flash;
using bc.test.functions;
 
namespace bc.test.functions
{
	public class AsFunctionsReflectionTest : AsObject
	{
		public AsFunction func;
		public AsFunctionsReflectionTest obj;
		public virtual void testAssignment()
		{
			func = __function("someFunction");
			func = obj.__function("someFunction");
			func = this.__function("someFunction");
			acceptFunction(__function("someFunction"));
		}
		public virtual void acceptFunction(AsFunction func)
		{
		}
		public virtual void someFunction()
		{
		}
		public virtual void someFunctionWithArg(int arg)
		{
		}
	}
}
