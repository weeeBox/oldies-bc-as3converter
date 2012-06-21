package bc.flash
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class ArgumentError extends bc.flash.Error
	{
		public function ArgumentError(message : String = "")
		{
			super(message);
		}
	}
}
