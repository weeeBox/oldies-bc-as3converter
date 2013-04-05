using System;
 
using bc.flash;
 
namespace bc.test.functions
{
	public class AsFunctionsTypeTest : AsObject
	{
		private AsFunction mFunc;
		public virtual void testFunctionAssignment()
		{
			mFunc = __function("functionDefaultCallback");
		}
		public virtual void testFunctionUsage()
		{
			mFunc.Invoke("This is string", "This is another string");
		}
		public virtual void testFunctionArgument()
		{
			accept(__function("functionDefaultCallback"));
		}
		public virtual void testFunctionApply()
		{
			AsArray args = new AsArray("This is string", "This is another string");
			__function("functionDefaultCallback").Apply(null, args);
		}
		public virtual void argumentsFunction(String a, String b)
		{
			mFunc.Apply(null, a, b);
		}
		public virtual void argumentsCastFunction(Object a, String b)
		{
			mFunc.Apply(null, a, b);
		}
		private void accept(AsFunction func)
		{
			func.Invoke("This is string", "This is another string");
		}
		private void functionDefaultCallback(String a, String b)
		{
		}
	}
}
