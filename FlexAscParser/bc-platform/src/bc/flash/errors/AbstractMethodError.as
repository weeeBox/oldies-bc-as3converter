package bc.flash.errors
{
	import bc.flash.Error;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class AbstractMethodError extends bc.flash.Error
	{
		public function AbstractMethodError(message : String = "")
		{
			super(message);
		}
	}
}
