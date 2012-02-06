package flash.display 
{
	import flash.Array;
	import flash.display.Sprite;
	
	public dynamic class MovieClip extends Sprite 
	{
		/* [Inspectable(environment="none")] */
		/* public function addFrameScript(...args : *) : void; */

		public function get currentFrame() : int { return 0; }

		/* [Version("10")] */
		public function get currentFrameLabel() : String { return null; }

		public function get currentLabel() : String { return null; }

		public function get currentLabels() : Array { return null; }

		/* public function get currentScene() : Scene { return null; } */

		public function get enabled() : Boolean { return false; }

		public function set enabled(value : Boolean) : void { }

		public function get framesLoaded() : int { return 0; }

		public function gotoAndPlay(frame : Object, scene : String = null) : void { }

		public function gotoAndStop(frame : Object, scene : String = null) : void { }

		public function nextFrame() : void { }

		/* public function nextScene() : void { } */

		public function play() : void { }

		public function prevFrame() : void { }

		public function prevScene() : void { }

		/* public function get scenes() : Array { return null; } */

		public function stop() : void { }

		public function get totalFrames() : int { return 0; }

		/* public function get trackAsMenu() : Boolean; */

		/* public function set trackAsMenu(value : Boolean) : void; */
	}
}
