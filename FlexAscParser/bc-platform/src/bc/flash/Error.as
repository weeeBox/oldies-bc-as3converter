package bc.flash
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class Error
	{
		public var message : String;
		public var name : String;

		public function Error(message : String = "") : void
		{
			this.message = message;		
		}		
	}
}
