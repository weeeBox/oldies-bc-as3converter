package bc.flash.error
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class AbstractClassError extends Error
	{
		public function AbstractClassError(message : String = "")
		{
			super(message);
		}		
	}
}
