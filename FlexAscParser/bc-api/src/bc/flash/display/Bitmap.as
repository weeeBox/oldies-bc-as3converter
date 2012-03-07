package bc.flash.display 
{
	import bc.flash.utils.MathHelper;
	import bc.flash.core.RenderSupport;
	import bc.flash.geom.Matrix;
	import bc.flash.geom.Point;
	import bc.flash.geom.Rectangle;
	import bc.flash.utils.transformCoords;

	import flash.display.BitmapData;
	
	[ConvertOnce]
	public class Bitmap extends DisplayObject 
	{
		private var mBitmapData : flash.display.BitmapData;
		private var mPixelSnapping : String;
		private var mSmoothing : Boolean;
		
		/** Helper objects. */
		private static var sHelperPoint : Point = new Point();
		private static var sHelperMatrix : Matrix = new Matrix();
		private static var sPosition : Point = new Point();
		
		public function Bitmap(bitmapData : flash.display.BitmapData = null, pixelSnapping : String = "auto", smoothing : Boolean = false) 
		{	
			mBitmapData = bitmapData;
			mPixelSnapping = pixelSnapping;
			mSmoothing = smoothing;
		}
		
		public override function render(support : RenderSupport, alpha : Number) : void 
		{
		}
		
		/** @inheritDoc */
		public override function getBounds(targetSpace : DisplayObject, resultRect : Rectangle = null) : Rectangle 
		{
			if (resultRect == null) resultRect = new Rectangle();

			if (targetSpace == this) // optimization
			{
				resultRect.x = x;
				resultRect.y = y;
				resultRect.width = mBitmapData.width;
				resultRect.height = mBitmapData.height;

				return resultRect;
			}

			var minX : Number = MathHelper.MAX_NUMBER;
			var maxX : Number = -MathHelper.MAX_NUMBER;
			var minY : Number = MathHelper.MAX_NUMBER;
			var maxY : Number = -MathHelper.MAX_NUMBER;

			getTransformationMatrix(targetSpace, sHelperMatrix);

			// top left
			sPosition.x = x;
			sPosition.y = y;

			transformCoords(sHelperMatrix, sPosition.x, sPosition.y, sHelperPoint);
			minX = minX < sHelperPoint.x ? minX : sHelperPoint.x;
			maxX = maxX > sHelperPoint.x ? maxX : sHelperPoint.x;
			minY = minY < sHelperPoint.y ? minY : sHelperPoint.y;
			maxY = maxY > sHelperPoint.y ? maxY : sHelperPoint.y;

			// top right
			sPosition.x += bitmapData.width;

			transformCoords(sHelperMatrix, sPosition.x, sPosition.y, sHelperPoint);
			minX = minX < sHelperPoint.x ? minX : sHelperPoint.x;
			maxX = maxX > sHelperPoint.x ? maxX : sHelperPoint.x;
			minY = minY < sHelperPoint.y ? minY : sHelperPoint.y;
			maxY = maxY > sHelperPoint.y ? maxY : sHelperPoint.y;

			// bottom right
			sPosition.y += bitmapData.height;

			transformCoords(sHelperMatrix, sPosition.x, sPosition.y, sHelperPoint);
			minX = minX < sHelperPoint.x ? minX : sHelperPoint.x;
			maxX = maxX > sHelperPoint.x ? maxX : sHelperPoint.x;
			minY = minY < sHelperPoint.y ? minY : sHelperPoint.y;
			maxY = maxY > sHelperPoint.y ? maxY : sHelperPoint.y;

			// bottom left
			sPosition.x = x;

			transformCoords(sHelperMatrix, sPosition.x, sPosition.y, sHelperPoint);
			minX = minX < sHelperPoint.x ? minX : sHelperPoint.x;
			maxX = maxX > sHelperPoint.x ? maxX : sHelperPoint.x;
			minY = minY < sHelperPoint.y ? minY : sHelperPoint.y;
			maxY = maxY > sHelperPoint.y ? maxY : sHelperPoint.y;

			resultRect.x = minX;
			resultRect.y = minY;
			resultRect.width = maxX - minX;
			resultRect.height = maxY - minY;

			return resultRect;
		}

		public function get bitmapData() : flash.display.BitmapData { return mBitmapData; }

		public function set bitmapData(value : flash.display.BitmapData) : void { mBitmapData = value; }

		public function get pixelSnapping() : String { return mPixelSnapping; }

		public function set pixelSnapping(value : String) : void { mPixelSnapping = value; }

		public function get smoothing() : Boolean { return mSmoothing; }

		public function set smoothing(value : Boolean) : void { mSmoothing = value; }
	}
}
