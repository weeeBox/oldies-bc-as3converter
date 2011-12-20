public class Sprite extends DisplayObjectContainer
{
	public function get buttonMode() : Boolean;

	public function set buttonMode(value : Boolean) : void;

	public function get dropTarget() : DisplayObject;

	public function get graphics() : Graphics;

	public function get hitArea() : Sprite;

	public function set hitArea(value : Sprite) : void;

	public function get soundTransform() : SoundTransform;

	public function set soundTransform(sndTransform : SoundTransform) : void;

	public function startDrag() : void;

	public function startDrag(lockCenter : Boolean) : void;

	public function startDrag(lockCenter : Boolean, bounds : Rectangle) : void;

	public function stopDrag() : void;

	public function get useHandCursor() : Boolean;

	public function set useHandCursor(value : Boolean) : void;
}