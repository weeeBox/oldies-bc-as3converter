package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class AbstractMethodError extends bc.flash.error.Error
	{
		public function AbstractMethodError(message : String = "")
		{
			super(message);
		}
	}
}
