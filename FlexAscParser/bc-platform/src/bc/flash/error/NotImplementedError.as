package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class NotImplementedError extends bc.flash.error.Error
	{
		public function NotImplementedError(message : String = "")
		{
			super(message);
		}
	}
}
