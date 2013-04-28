package bc.flash.events
{
	import bc.flash.errors.NotImplementedError;
	import bc.flash.events.Event;
	
	public class TextEvent : Event
	{
		public static const LINK:String = "link";
		public static const TEXT_INPUT:String = "textInput";
		
		public function TextEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false, text:String = "")
		{
			throw new NotImplementedError();
		}
		
		override public function clone():Event
		{
			throw new NotImplementedError();
		}
		
		override public function toString():String
		{
			throw new NotImplementedError();
		}
		
		public function get text():String { throw new NotImplementedError(); }
		public function set text(value:String):void { throw new NotImplementedError(); }
	}
}