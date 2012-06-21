package bc.flash.events {
	import bc.flash.errors.NotImplementedError;
	/**
	 * @author weee
	 */
	public class ErrorEvent extends Event 
	{
		public static const ERROR : String = "error";
		
		private var mId:int;

		public function ErrorEvent(type : String, bubbles : Boolean = false, cancelable : Boolean = false, text : String = "", id : int = 0) : void
		{
			super(type, bubbles);
			mId = id;
		}

		public function clone() : Event
		{
			throw new NotImplementedError();
		}
		
		public function get errorID() : int
		{
			return mId;
		}

		public override function toString() : String
		{
			throw new NotImplementedError();
		}
	}
}
