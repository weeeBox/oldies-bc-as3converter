package bc.flash.errors
{
	import bc.flash.Error;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class IllegalOperationError extends bc.flash.Error
	{
		public function IllegalOperationError(message : String = null)
		{
			super(message);
		}
	}
}
