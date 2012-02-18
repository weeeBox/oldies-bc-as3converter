package bc.flash.error
{
	/**
	 * @author weee
	 */
	[NoConversion]
	public class AbstractMethodError extends Error
	{
		public function AbstractMethodError(message : String = "")
		{
			super(message);
		}
	}
}
