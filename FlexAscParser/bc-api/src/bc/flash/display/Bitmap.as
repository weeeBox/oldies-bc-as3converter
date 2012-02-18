package bc.flash.display 
{
	public class Bitmap extends DisplayObject 
	{
		private var mBitmapData : BitmapData;
		private var mPixelSnapping : String;
		private var mSmoothing : Boolean;
		
		public function Bitmap(bitmapData : BitmapData = null, pixelSnapping : String = "auto", smoothing : Boolean = false) : void 
		{
			mBitmapData = bitmapData;
			mPixelSnapping = pixelSnapping;
			mSmoothing = smoothing;
		}

		public function get bitmapData() : BitmapData { return mBitmapData; }

		public function set bitmapData(value : BitmapData) : void { mBitmapData = value; }

		public function get pixelSnapping() : String { return mPixelSnapping; }

		public function set pixelSnapping(value : String) : void { mPixelSnapping = value; }

		public function get smoothing() : Boolean { return mSmoothing; }

		public function set smoothing(value : Boolean) : void { mSmoothing = value; }
	}
}
