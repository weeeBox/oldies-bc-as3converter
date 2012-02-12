package bc.flash.ui
{
	/**
	 * @author weee
	 */
	public final class Mouse extends Object
	{
		private static var mCursor : String;
		private static var mHidden : Boolean;

		/* [Version("10")] */
		public static function get cursor() : String
		{
			return mCursor;
		}

		/* [Version("10")] */
		public static function set cursor(value : String) : void
		{
			mCursor = value;
		}

		public static function hide() : void
		{
			mHidden = true;
		}

		/* [API("670")] */
		/* public static function registerCursor(name : String, cursor : MouseCursorData) : void; */
		
		public static function show() : void
		{
			mHidden = false;
		}
		
		/* [Version("10.1")] */
		/* public static function get supportsCursor() : Boolean; */

		/* [API("670")] */
		/* public static function get supportsNativeCursor() : Boolean; */

		/* [API("670")] */
		/* public static function unregisterCursor(name : String) : void; */
	}
}
