package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class NotImplementedError extends Error
	{
		public function NotImplementedError(message : String = "")
		{
			super(message);
		}
	}
}
