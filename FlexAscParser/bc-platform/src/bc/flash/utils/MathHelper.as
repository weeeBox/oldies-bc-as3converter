package bc.flash.utils
{
	/**
	 * @author weee
	 */
	 
	public class MathHelper
	{
		private static const epsilon : Number = 0.00001;
		
		public static const MAX_NUMBER : Number = 0x7fffffff;
		
		public static function toRadians(degrees : Number) : Number
		{
			return 0.0174532925199433 * degrees;
		}
		
		public static function toDegrees(radians : Number) : Number
		{
			return 57.2957795130823209 * radians;
		}
		
		public static function epsilonZero(a : Number) : Boolean
		{
			return Math.abs(a) < epsilon;
		}
		
		public static function epsilonEquals(a : Number, b : Number) : Boolean
		{
			return Math.abs(a - b) < epsilon;		
		}
	}
}
