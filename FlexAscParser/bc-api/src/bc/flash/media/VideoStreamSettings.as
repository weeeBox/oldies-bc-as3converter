package flash.media
{
	public class VideoStreamSettings extends Object
	{
		public function setMode(arg1:int, arg2:int, arg3:Number) : void { throw new NotImplementedError(); }
		public function setQuality(arg1:int, arg2:int) : void { throw new NotImplementedError(); }
		public function setKeyFrameInterval(arg1:int) : void { throw new NotImplementedError(); }
		public function get width() : int { throw new NotImplementedError(); }
		public function get height() : int { throw new NotImplementedError(); }
		public function get keyFrameInterval() : int { throw new NotImplementedError(); }
		public function get codec() : String { throw new NotImplementedError(); }
		public function get quality() : int { throw new NotImplementedError(); }
		public function get fps() : Number { throw new NotImplementedError(); }
		public function get bandwidth() : int { throw new NotImplementedError(); }
	}
}