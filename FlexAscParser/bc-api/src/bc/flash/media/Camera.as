package flash.media
{
	import flash.events;
	
	public class Camera extends EventDispatcher implements IEventDispatcher
	{
		public function setMode(arg1:int, arg2:int, arg3:Number, arg4:Boolean=false) : void { throw new NotImplementedError(); }
		public function setQuality(arg1:int, arg2:int) : void { throw new NotImplementedError(); }
		public function setKeyFrameInterval(arg1:int) : void { throw new NotImplementedError(); }
		public function setLoopback(arg1:Boolean=false) : void { throw new NotImplementedError(); }
		public function setMotionLevel(arg1:int, arg2:int=0) : void { throw new NotImplementedError(); }
		public function setCursor(arg1:Boolean) : void { throw new NotImplementedError(); }
		public function get loopback() : Boolean { throw new NotImplementedError(); }
		public function get muted() : Boolean { throw new NotImplementedError(); }
		public function get width() : int { throw new NotImplementedError(); }
		public function get motionTimeout() : int { throw new NotImplementedError(); }
		public function get bandwidth() : int { throw new NotImplementedError(); }
		public function get index() : int { throw new NotImplementedError(); }
		public function get fps() : Number { throw new NotImplementedError(); }
		public function get position() : String { throw new NotImplementedError(); }
		public function get name() : String { throw new NotImplementedError(); }
		public function get quality() : int { throw new NotImplementedError(); }
		public function get keyFrameInterval() : int { throw new NotImplementedError(); }
		public function get currentFPS() : Number { throw new NotImplementedError(); }
		public function get motionLevel() : int { throw new NotImplementedError(); }
		public function get activityLevel() : Number { throw new NotImplementedError(); }
		public function get height() : int { throw new NotImplementedError(); }
	}
}