using System;
 
using bc.flash;
using bc.flash.errors;
 
namespace bc.flash
{
	public sealed class AsNumber : AsObject
	{
		public static float NaN = (0.0f / 0.0f);
		public static float NEGATIVE_INFINITY = (-1.0f / 0.0f);
		public static float POSITIVE_INFINITY = (1.0f / 0.0f);
		public static float MIN_VALUE = 1.401298464324817E-45f;
		public static float MAX_VALUE = 3.4028234663852886E38f;
		public AsNumber(AsObject _value)
		{
			throw new AsNotImplementedError();
		}
		public String toExponential(uint fractionDigits)
		{
			throw new AsNotImplementedError();
		}
		public String toFixed(uint fractionDigits)
		{
			throw new AsNotImplementedError();
		}
		public String toPrecision(uint precision)
		{
			throw new AsNotImplementedError();
		}
		public String toString(float radix)
		{
			throw new AsNotImplementedError();
		}
		public String toString()
		{
			return toString(10);
		}
		public float valueOf()
		{
			throw new AsNotImplementedError();
		}
	}
}
