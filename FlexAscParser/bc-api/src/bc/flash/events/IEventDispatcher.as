package flash.events
{
	public interface IEventDispatcher
	{
		public function addEventListener(arg1:String, arg2:Function, arg3:Boolean=false, arg4:int=0, arg5:Boolean=false) : void { throw new NotImplementedError(); }
		public function hasEventListener(arg1:String) : Boolean { throw new NotImplementedError(); }
		public function removeEventListener(arg1:String, arg2:Function, arg3:Boolean=false) : void { throw new NotImplementedError(); }
		public function willTrigger(arg1:String) : Boolean { throw new NotImplementedError(); }
		public function dispatchEvent(arg1:Event) : Boolean { throw new NotImplementedError(); }
	}
}