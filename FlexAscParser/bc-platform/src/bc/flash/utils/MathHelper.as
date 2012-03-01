package bc.flash.utils
{
	/**
	 * @author weee
	 */
	 
	public class MathHelper
	{
		private static const epsilon : Number = 0.00001;
		
		public static function epsilonEquals(a : Number, b : Number) : Boolean
		{
			return Math.abs(a - b) < epsilon;		
		}
	}
}
