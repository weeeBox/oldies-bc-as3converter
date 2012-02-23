package bc.flash.display 
{
	import bc.flash.core.RenderSupport;
	import bc.flash.utils.transformCoords;
	import bc.flash.geom.Point;
	import bc.flash.geom.Matrix;
	import bc.flash.geom.Rectangle;
	
	public class Bitmap extends DisplayObject 
	{
		private var mBitmapData : BitmapData;
		private var mPixelSnapping : String;
		private var mSmoothing : Boolean;
		
		private static var sHelperMatrix : Matrix = new Matrix();
		private static var sHelperPoint : Point = new Point();
		
		public function Bitmap(bitmapData : BitmapData = null, pixelSnapping : String = "auto", smoothing : Boolean = false) 
		{	
			mBitmapData = bitmapData;
			mPixelSnapping = pixelSnapping;
			mSmoothing = smoothing;
			
			if (bitmapData != null)
			{
				width = bitmapData.width;
				height = bitmapData.height;
			}
		}
		
		override public function render(support : RenderSupport, alpha : Number) : void
		{
			super.render(support, alpha);
		}
		
		/** @inheritDoc */
		public override function getBounds(targetSpace : DisplayObject, resultRect : Rectangle = null) : Rectangle
		{
			if (resultRect == null) resultRect = new Rectangle();

			getTransformationMatrix(targetSpace, sHelperMatrix);
			
			sHelperPoint.x = x;
			sHelperPoint.y = y;
			transformCoords(sHelperMatrix, 0.0, 0.0, sHelperPoint);
			resultRect.x = sHelperPoint.x;
			resultRect.y = sHelperPoint.y;
			
			sHelperPoint.x = x + mBitmapData.width;
			sHelperPoint.y = y + mBitmapData.height;
			transformCoords(sHelperMatrix, 0.0, 0.0, sHelperPoint);
			resultRect.width = sHelperPoint.x; 
			resultRect.height = sHelperPoint.y;

			return resultRect;
		}

		public function get bitmapData() : BitmapData { return mBitmapData; }

		public function set bitmapData(value : BitmapData) : void { mBitmapData = value; }

		public function get pixelSnapping() : String { return mPixelSnapping; }

		public function set pixelSnapping(value : String) : void { mPixelSnapping = value; }

		public function get smoothing() : Boolean { return mSmoothing; }

		public function set smoothing(value : Boolean) : void { mSmoothing = value; }
	}
}
