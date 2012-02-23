	package bc.flash.display
{
	import bc.flash.error.NotImplementedError;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class BcRenderSupport
	{
		public static function beginFill(color : uint, alpha : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function clear() : void
		{
			throw new NotImplementedError();
		}

		public static function curveTo(controlX : Number, controlY : Number, anchorX : Number, anchorY : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function drawCircle(x : Number, y : Number, radius : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function drawEllipse(x : Number, y : Number, width : Number, height : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function drawRect(x : Number, y : Number, width : Number, height : Number) : void
		{
			throw new NotImplementedError();
		}
		
		public static function drawBitmap(bitmap : Object, x : Number, y : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function drawRoundRect(x : Number, y : Number, width : Number, height : Number, ellipseWidth : Number, ellipseHeight : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function endFill() : void
		{
			throw new NotImplementedError();
		}

		public static function lineStyle(thickness : Number, color : uint, alpha : Number, pixelHinting : Boolean, scaleMode : String, caps : String, joints : String, miterLimit : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function lineTo(x : Number, y : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function moveTo(x : Number, y : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function scale(scaleX : Number, scaleY : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function rotate(a : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function translate(x : Number, y : Number) : void
		{
			throw new NotImplementedError();
		}

		public static function pushMatrix() : void
		{
			throw new NotImplementedError();
		}

		public static function popMatrix() : void
		{
			throw new NotImplementedError();
		}		
	}
}
