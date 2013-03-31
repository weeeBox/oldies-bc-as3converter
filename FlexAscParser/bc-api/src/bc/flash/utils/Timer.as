package flash.utils
{
	import flash.events;
	
	public class Timer extends EventDispatcher implements IEventDispatcher
	{
		public function Timer(arg1:Number, arg2:int=0) { throw new NotImplementedError(); }
		public function start() : void { throw new NotImplementedError(); }
		public function stop() : void { throw new NotImplementedError(); }
		public function reset() : void { throw new NotImplementedError(); }
		public function get delay() : Number { throw new NotImplementedError(); }
		public function set delay(value:Number) : void { throw new NotImplementedError(); }
		public function get currentCount() : int { throw new NotImplementedError(); }
		public function get repeatCount() : int { throw new NotImplementedError(); }
		public function set repeatCount(value:int) : void { throw new NotImplementedError(); }
		public function get running() : Boolean { throw new NotImplementedError(); }
	}
}