package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class IllegalOperationError extends bc.flash.error.Error
	{
		public function IllegalOperationError(message : String = null)
		{
			super(message);
		}
	}
}
