package flash.events
{
	public class TimerEvent extends Event
	{
		public function TimerEvent(arg1:String, arg2:Boolean=false, arg3:Boolean=false) { throw new NotImplementedError(); }
		public function updateAfterEvent() : void { throw new NotImplementedError(); }
		public function clone() : Event { throw new NotImplementedError(); }
		public function toString() : String { throw new NotImplementedError(); }
	}
}