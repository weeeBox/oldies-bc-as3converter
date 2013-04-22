package bc.flash.events
{
	import bc.flash.errors.NotImplementedError;

	public class ProgressEvent extends Event
	{
		public function ProgressEvent(arg1:String, arg2:Boolean=false, arg3:Boolean=false, arg4:Number=0, arg5:Number=0)
		{
			throw new NotImplementedError();
		}
		public function clone() : Event
		{
			throw new NotImplementedError();
		}
		public function toString() : String
		{
			throw new NotImplementedError();
		}
		public function get bytesTotal() : Number
		{
			throw new NotImplementedError();
		}
		public function set bytesTotal(value:Number) : void
		{
			throw new NotImplementedError();
		}
		public function get bytesLoaded() : Number
		{
			throw new NotImplementedError();
		}
		public function set bytesLoaded(value:Number) : void
		{
			throw new NotImplementedError();
		}
	}
}