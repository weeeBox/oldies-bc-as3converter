package _as_.flash.events
{
	public class EventDispatcher extends Object implements IEventDispatcher, IEventDispatcher
	{
		function EventDispatcher(target : IEventDispatcher = null) : void
		{
		}

		public function addEventListener(type : String, listener : Function, useCapture : Boolean = false, priority : int = 0, useWeakReference : Boolean = false) : void
		{
		}

		public function dispatchEvent(event : Event) : Boolean
		{
			return false;
		}

		public function hasEventListener(type : String) : Boolean
		{
			return false;
		}

		public function removeEventListener(type : String, listener : Function, useCapture : Boolean = false) : void
		{
		}

		public function willTrigger(type : String) : Boolean
		{
			return false;
		}
	}
}
