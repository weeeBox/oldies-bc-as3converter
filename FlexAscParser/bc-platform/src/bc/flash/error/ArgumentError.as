package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class ArgumentError extends Error
	{
		public function ArgumentError(message : String = "")
		{
			super(message);
		}
	}
}
