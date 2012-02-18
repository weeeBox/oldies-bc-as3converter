package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class RangeError extends Error
	{
		public function RangeError(message : String = "")
		{
			super(message);
		}
	}
}
