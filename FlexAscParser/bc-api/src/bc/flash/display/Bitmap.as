package bc.flash.display 
{
	import bc.flash.error.NotImplementedError;
	import flash.display.DisplayObject;
	import flash.display.BitmapData;
	
	public class Bitmap extends DisplayObject 
	{
		function Bitmap(bitmapData : BitmapData = null, pixelSnapping : String = "auto", smoothing : Boolean = false) : void {}

		public function get bitmapData() : BitmapData { throw new NotImplementedError(); }

		public function set bitmapData(value : BitmapData) : void { throw new NotImplementedError(); }

		public function get pixelSnapping() : String { throw new NotImplementedError(); }

		public function set pixelSnapping(value : String) : void { throw new NotImplementedError(); }

		public function get smoothing() : Boolean { throw new NotImplementedError(); }

		public function set smoothing(value : Boolean) : void { throw new NotImplementedError(); }
	}
}
