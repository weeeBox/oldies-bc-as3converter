package bc.flash.events 
{
	import bc.flash.errors.NotImplementedError;
	public class MouseEvent extends Event 
	{
		public static const CLICK : String = "click";
		public static const DOUBLE_CLICK : String = "doubleClick";
		public static const MOUSE_DOWN : String = "mouseDown";
		public static const MOUSE_MOVE : String = "mouseMove";
		public static const MOUSE_OUT : String = "mouseOut";
		public static const MOUSE_OVER : String = "mouseOver";
		public static const MOUSE_UP : String = "mouseUp";
		public static const MOUSE_WHEEL : String = "mouseWheel";
		public static const ROLL_OUT : String = "rollOut";
		public static const ROLL_OVER : String = "rollOver";
		private var mX : Number;
		private var mY : Number;

		public function MouseEvent(type:String, x : Number, y : Number, bubbles:Boolean=false)
		{
			super(type, bubbles);
			mX = x;
			mY = y;
		}

		/* function MouseEvent(type : String, bubbles : Boolean = true, cancelable : Boolean = false, localX : Number = undefined, localY : Number = undefined, relatedObject : InteractiveObject = null, ctrlKey : Boolean = false, altKey : Boolean = false, shiftKey : Boolean = false, buttonDown : Boolean = false, delta : int = 0) : void; */

		/* public function get altKey() : Boolean; */

		/* public function set altKey(value : Boolean) : void; */

		/* public function get buttonDown() : Boolean; */

		/* public function set buttonDown(value : Boolean) : void; */

		/* public function clone() : Event; */

		/* public function get ctrlKey() : Boolean; */

		/* public function set ctrlKey(value : Boolean) : void; */

		/* public function get delta() : int; */

		/* public function set delta(value : int) : void; */

		/* [Version("10")] */
		/* public function get isRelatedObjectInaccessible() : Boolean; */

		/* [Version("10")] */
		/* public function set isRelatedObjectInaccessible(value : Boolean) : void; */

		/* public function get localX() : Number; */

		/* public function set localX(value : Number) : void; */

		/* public function get localY() : Number; */

		/* public function set localY(value : Number) : void; */

		/* public function get relatedObject() : InteractiveObject; */

		/* public function set relatedObject(value : InteractiveObject) : void; */

		/* public function get shiftKey() : Boolean; */

		/* public function set shiftKey(value : Boolean) : void; */

		public function get stageX() : Number 
		{
			return mX; 
		}

		public function get stageY() : Number 
		{ 
			return mY; 
		}

		/* public function toString() : String; */

		/* public function updateAfterEvent() : void; */
	}
}
