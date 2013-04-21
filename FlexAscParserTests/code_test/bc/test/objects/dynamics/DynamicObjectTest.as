package bc.test.objects.dynamics
{
	public class DynamicObjectTest
	{
		public function testDynamics() : void
		{
			var arr:Array = null;
			
			arr[0].someMethod();
			
			var val:Object
			val = arr[0].someField;
			val = arr[0].someMethod();
			val = arr[0].someMethod("arg1", "arg2", 0);
			val = arr[0].someField.someMethod();
			val = arr[0].someField.someMethod("arg1", "arg2", 0);
			val = arr[0].someField.anotherField.someMethod();
			val = arr[0].someField.anotherField.someMethod("arg1", "arg2", 0); 
		}
	}
}