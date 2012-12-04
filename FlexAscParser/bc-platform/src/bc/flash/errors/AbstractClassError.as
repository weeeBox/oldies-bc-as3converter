package bc.flash.errors
{
	import bc.flash.Error;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class AbstractClassError extends bc.flash.Error
	{
		public function AbstractClassError(message : String = "")
		{
			super(message);
		}		
	}
}
