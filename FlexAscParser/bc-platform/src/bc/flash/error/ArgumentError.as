package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class ArgumentError extends bc.flash.error.Error
	{
		public function ArgumentError(message : String = "")
		{
			super(message);
		}
	}
}
