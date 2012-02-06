package as.flash.events 
{
	import flash.events.Event;
	
	public class KeyboardEvent extends Event 
	{
		public static const KEY_DOWN : String = "keyDown";
		public static const KEY_UP : String = "keyUp";

		/* function KeyboardEvent(type : String, bubbles : Boolean = true, cancelable : Boolean = false, charCodeValue : uint = 0, keyCodeValue : uint = 0, keyLocationValue : uint = 0, ctrlKeyValue : Boolean = false, altKeyValue : Boolean = false, shiftKeyValue : Boolean = false) : void; */

		public function get altKey() : Boolean { return false; }

		public function set altKey(value : Boolean) : void { }

		public function get charCode() : uint { return 0; }

		public function set charCode(value : uint) : void { }

		/* public function clone() : Event; */

		public function get ctrlKey() : Boolean { return false; }

		public function set ctrlKey(value : Boolean) : void { }

		public function get keyCode() : uint { return 0; }

		public function set keyCode(value : uint) : void { }

		public function get keyLocation() : uint { return 0; }

		public function set keyLocation(value : uint) : void { }

		public function get shiftKey() : Boolean { return false; }

		public function set shiftKey(value : Boolean) : void { }

		/* public function toString() : String; */

		/* public function updateAfterEvent() : void; */
	}
}