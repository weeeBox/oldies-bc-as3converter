package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class AbstractClassError extends bc.flash.error.Error
	{
		public function AbstractClassError(message : String = "")
		{
			super(message);
		}		
	}
}
