package bc.flash.error
{
	/**
	 * @author weee
	 */
	[NoConversion]
	public class AbstractClassError extends Error
	{
		public function AbstractClassError(message : String = "")
		{
			super(message);
		}		
	}
}
