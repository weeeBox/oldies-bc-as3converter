package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class IllegalOperationError extends Error
	{
		public function IllegalOperationError(message : String)
		{
			super(message);
		}
	}
}
