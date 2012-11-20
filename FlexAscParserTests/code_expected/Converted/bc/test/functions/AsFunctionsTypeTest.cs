using System;
 
using bc.flash;
 
namespace bc.test.functions
{
	public class AsFunctionsTypeTest : AsObject
	{
		private AsFunctionDefaultCallback mFunc;
		public AsFunctionsTypeTest()
		{
			accept(functionDefaultCallback);
			AsArray args = new AsArray("This is string");
			functionDefaultCallback((String)(args[0]));
		}
		private void accept(AsFunctionDefaultCallback func)
		{
			func("This is string");
		}
		private void functionDefaultCallback(String _string)
		{
		}
	}
}
