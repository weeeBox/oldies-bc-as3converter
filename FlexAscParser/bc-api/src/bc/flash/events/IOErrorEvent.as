package bc.flash.events 
{
	/**
	 * @author weee
	 */
	public class IOErrorEvent extends Event
	{
		public static const IO_ERROR : String = "ioError";		
		public static const NETWORK_ERROR : String = "networkError";		
		public static const DISK_ERROR : String = "diskError";		
		public static const VERIFY_ERROR : String = "verifyError";
		
		public function IOErrorEvent(type:String, bubbles:Boolean=false)
		{
			super(type, bubbles);
		}
	}
}
