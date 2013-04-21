package bc.test.objects.dynamics
{
	public class DynamicMethodCallTest
	{
		private var mObject:Object;
		
		public function testDynamicMethod() : void
		{
			var methodName:String = "someMethod";
			var obj:Object = new Object();
			
			obj[methodName]();
			mObject[methodName]();
			this.mObject[methodName]();
			getObject()[methodName]();
			this.getObject()[methodName]();
			
			obj[methodName]("arg");
			mObject[methodName]("arg");
			this.mObject[methodName]("arg");
			getObject()[methodName]("arg");
			this.getObject()[methodName]("arg");
		}
		
		public function getObject():Object
		{
			return mObject;
		}
	}
}