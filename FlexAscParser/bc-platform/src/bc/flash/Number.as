package bc.flash
{
	import bc.flash.errors.NotImplementedError;

	/**
	 * @author weee
	 */
	[ConvertOnce]
	public final class Number extends Object
	{
		public static const NaN : Number = 0.0/0.0; 
		public static const NEGATIVE_INFINITY : Number = -1.0/0.0;
		public static const POSITIVE_INFINITY : Number = 1.0/0.0;
		public static const MIN_VALUE : Number = 1.401298464324817E-45;
		public static const MAX_VALUE : Number = 3.4028234663852886E38;

		private function Number()
		{
		}

		public static function toExponential(number : Number, fractionDigits : Number = 0) : String
		{
			throw new NotImplementedError();
		}

		public static function toFixed(number : Number, fractionDigits : Number = 0) : String
		{
			throw new NotImplementedError();
		}

		public static function toPrecision(number : Number, precision : Number = 0) : String
		{
			throw new NotImplementedError();
		}

		public static function toString(number : Number, radix:Number = 10) : String
		{
			throw new NotImplementedError();
		}

		public static function valueOf(number : Number) : Number
		{
			throw new NotImplementedError();
		}
	}
}
