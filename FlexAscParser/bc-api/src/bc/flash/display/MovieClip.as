package bc.flash.display 
{
	public dynamic class MovieClip extends Sprite 
	{
		/* [Inspectable(environment="none")] */
		/* public function addFrameScript(...args : *) : void; */

		public function get currentFrame() : int { throw new NotImplementedError(); }

		/* [Version("10")] */
		public function get currentFrameLabel() : String { throw new NotImplementedError(); }

		public function get currentLabel() : String { throw new NotImplementedError(); }

		public function get currentLabels() : Array { throw new NotImplementedError(); }

		/* public function get currentScene() : Scene { throw new NotImplementedError(); } */

		public function get enabled() : Boolean { throw new NotImplementedError(); }

		public function set enabled(value : Boolean) : void { throw new NotImplementedError(); }

		public function get framesLoaded() : int { throw new NotImplementedError(); }

		public function gotoAndPlay(frame : Object, scene : String = null) : void { throw new NotImplementedError(); }

		public function gotoAndStop(frame : Object, scene : String = null) : void { throw new NotImplementedError(); }

		public function nextFrame() : void { throw new NotImplementedError(); }

		/* public function nextScene() : void { throw new NotImplementedError(); } */

		public function play() : void { throw new NotImplementedError(); }

		public function prevFrame() : void { throw new NotImplementedError(); }

		public function prevScene() : void { throw new NotImplementedError(); }

		/* public function get scenes() : Array { throw new NotImplementedError(); } */

		public function stop() : void { throw new NotImplementedError(); }

		public function get totalFrames() : int { throw new NotImplementedError(); }

		/* public function get trackAsMenu() : Boolean; */

		/* public function set trackAsMenu(value : Boolean) : void; */
	}
}
