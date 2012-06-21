package bc.flash.errors
{
	import bc.flash.Error;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class NotImplementedError extends bc.flash.Error
	{
		public function NotImplementedError(message : String = "")
		{
			super(message);
		}
	}
}
