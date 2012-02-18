package bc.flash.error
{
	/**
	 * @author weee
	 */
	[NoConversion]
	public class ArgumentError extends Error
	{
		public function ArgumentError(message : String = "")
		{
			super(message);
		}
	}
}
