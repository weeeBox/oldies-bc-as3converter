using System;
 
using bc.flash;
 
namespace bc.test.functions
{
	public class AsFunctionsTypeTest : AsObject
	{
		private AsFunctionDefaultCallback mFunc;
		public virtual void testFunctionAssignment()
		{
			mFunc = functionDefaultCallback;
		}
		public virtual void testFunctionUsage()
		{
			mFunc("This is string", "This is another string");
		}
		public virtual void testFunctionArgument()
		{
			accept(functionDefaultCallback);
		}
		public virtual void testFunctionApply()
		{
			AsArray args = new AsArray("This is string", "This is another string");
			functionDefaultCallback((String)(args[0]), (String)(args[1]));
		}
		public virtual void argumentsFunction(String a, String b)
		{
			mFunc(a, b);
		}
		public virtual void argumentsCastFunction(Object a, String b)
		{
			mFunc((String)(a), b);
		}
		private void accept(AsFunctionDefaultCallback func)
		{
			func("This is string", "This is another string");
		}
		private void functionDefaultCallback(String a, String b)
		{
		}
	}
}
