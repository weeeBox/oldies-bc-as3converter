public dynamic class MovieClip extends Sprite
{
	public function get currentFrame() : int;

	public function get currentFrameLabel() : String;

	public function get currentLabel() : String;

	public function get currentLabels() : Array;

	public function get currentScene() : Scene;

	public function get enabled() : Boolean;

	public function set enabled(value : Boolean) : void;

	public function get framesLoaded() : int;

	public function gotoAndPlay(frame : Object) : void;

	public function gotoAndPlay(frame : Object, scene : String) : void;

	public function gotoAndStop(frame : Object) : void;

	public function gotoAndStop(frame : Object, scene : String) : void;

	public function nextFrame() : void;

	public function nextScene() : void;

	public function play() : void;

	public function prevFrame() : void;

	public function prevScene() : void;

	public function get scenes() : Array;

	public function stop() : void;

	public function get totalFrames() : int;

	public function get trackAsMenu() : Boolean;

	public function set trackAsMenu(value : Boolean) : void;
}