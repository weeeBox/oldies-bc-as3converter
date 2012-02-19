package bc.flash.display 
{
	public dynamic class MovieClip extends Sprite 
	{
		/* [Inspectable(environment="none")] */
		/* public function addFrameScript(...args : *) : void; */

		public function get currentFrame() : int { implementMe(); }

		/* [Version("10")] */
		public function get currentFrameLabel() : String { implementMe(); }

		public function get currentLabel() : String { implementMe(); }

		public function get currentLabels() : Array { implementMe(); }

		/* public function get currentScene() : Scene { implementMe(); } */

		public function get enabled() : Boolean { implementMe(); }

		public function set enabled(value : Boolean) : void { implementMe(); }

		public function get framesLoaded() : int { implementMe(); }

		public function gotoAndPlay(frame : Object, scene : String = null) : void { implementMe(); }

		public function gotoAndStop(frame : Object, scene : String = null) : void { implementMe(); }

		public function nextFrame() : void { implementMe(); }

		/* public function nextScene() : void { implementMe(); } */

		public function play() : void { implementMe(); }

		public function prevFrame() : void { implementMe(); }

		public function prevScene() : void { implementMe(); }

		/* public function get scenes() : Array { implementMe(); } */

		public function stop() : void { implementMe(); }

		public function get totalFrames() : int { implementMe(); }

		/* public function get trackAsMenu() : Boolean; */

		/* public function set trackAsMenu(value : Boolean) : void; */
	}
}
