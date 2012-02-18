package bc.flash.error
{
	/**
	 * @author weee
	 */
	[NoConversion]
	public class RangeError extends Error
	{
		public function RangeError(message : String = "")
		{
			super(message);
		}
	}
}
