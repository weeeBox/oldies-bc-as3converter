package bc.flash.error
{
	/**
	 * @author weee
	 */
	[NoConversion]
	public class IllegalOperationError extends Error
	{
		public function IllegalOperationError(message : String)
		{
			super(message);
		}
	}
}
