package bc.flash.ui
{
	public final class Multitouch extends Object
	{
		private static var mInputMode : String = MultitouchInputMode.NONE;
		
		public static function get inputMode() : String
		{
			return mInputMode;			
		}

		public static function set inputMode(value : String) : void
		{
			mInputMode = value;			
		}

		/* [API("675")] */
		/* public static function get mapTouchToMouse() : Boolean; */

		/* [API("675")] */
		/* public static function set mapTouchToMouse(value : Boolean) : void; */

		/* public static function get maxTouchPoints() : int; */

		/* public static function get supportedGestures() : Vector.<String>; */

		/* public static function get supportsGestureEvents() : Boolean; */

		/* public static function get supportsTouchEvents() : Boolean; */
	}
}
