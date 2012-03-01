package bc.flash.display 
{
	import bc.flash.core.RenderSupport;
	
	public class Bitmap extends DisplayObject 
	{
		private var mBitmapData : BitmapData;
		private var mPixelSnapping : String;
		private var mSmoothing : Boolean;
		
		public function Bitmap(bitmapData : BitmapData = null, pixelSnapping : String = "auto", smoothing : Boolean = false) 
		{	
			mBitmapData = bitmapData;
			mPixelSnapping = pixelSnapping;
			mSmoothing = smoothing;
		}
		
		public override function render(support : RenderSupport, alpha : Number) : void 
		{
			if (mBitmapData != null)
			{
				support.drawBitmap(mBitmapData, alpha);
			}			
		}
		
		public function get bitmapData() : BitmapData { return mBitmapData; }

		public function set bitmapData(value : BitmapData) : void { mBitmapData = value; }

		public function get pixelSnapping() : String { return mPixelSnapping; }

		public function set pixelSnapping(value : String) : void { mPixelSnapping = value; }

		public function get smoothing() : Boolean { return mSmoothing; }

		public function set smoothing(value : Boolean) : void { mSmoothing = value; }
	}
}
