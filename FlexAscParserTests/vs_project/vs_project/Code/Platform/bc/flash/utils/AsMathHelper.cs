using System;
 
using bc.flash;
 
namespace bc.flash.utils
{
	public class AsMathHelper : AsObject
	{
		private static float epsilon = 0.00001f;
		public static float MAX_NUMBER = 0x7fffffff;
		public static float toRadians(float degrees)
		{
			return (0.0174532925199433f * degrees);
		}
		public static float toDegrees(float radians)
		{
			return (57.2957795130823209f * radians);
		}
		public static bool epsilonZero(float a)
		{
			return (AsMath.abs(a) < epsilon);
		}
		public static bool epsilonEquals(float a, float b)
		{
			return (AsMath.abs((a - b)) < epsilon);
		}
	}
}
