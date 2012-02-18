package bc.flash.error
{
	/**
	 * @author weee
	 */
	[NoConversion]
	public class NotImplementedError extends Error
	{
		public function NotImplementedError(message : String = "")
		{
			super(message);
		}
	}
}
