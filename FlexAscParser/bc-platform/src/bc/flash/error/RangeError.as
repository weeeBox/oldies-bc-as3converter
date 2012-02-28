package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class RangeError extends bc.flash.error.Error
	{
		public function RangeError(message : String = "")
		{
			super(message);
		}
	}
}
