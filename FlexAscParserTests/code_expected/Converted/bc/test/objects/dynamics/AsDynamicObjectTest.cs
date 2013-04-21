using System;
 
using bc.flash;
 
namespace bc.test.objects.dynamics
{
	public class AsDynamicObjectTest : AsObject
	{
		public virtual void testDynamics()
		{
			AsArray arr = null;
			arr[0].someMethod();
			Object val = null;
			val = (Object)(arr[0].getOwnProperty("someField"));
			val = arr[0].someMethod();
			val = arr[0].someMethod("arg1", "arg2", 0);
			val = arr[0].getOwnProperty("someField").someMethod();
			val = arr[0].getOwnProperty("someField").someMethod("arg1", "arg2", 0);
			val = arr[0].getOwnProperty("someField").getOwnProperty("anotherField").someMethod();
			val = arr[0].getOwnProperty("someField").getOwnProperty("anotherField").someMethod("arg1", "arg2", 0);
		}
	}
}
