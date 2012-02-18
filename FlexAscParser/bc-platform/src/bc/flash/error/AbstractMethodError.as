package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class AbstractMethodError extends Error
	{
		public function AbstractMethodError(message : String = "")
		{
			super(message);
		}
	}
}
