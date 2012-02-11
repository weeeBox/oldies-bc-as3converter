package bc.flash.display 
{
	import bc.flash.media.SoundTransform;

	public class Sprite extends DisplayObjectContainer 
	{
		public function get buttonMode() : Boolean { return false; }

		public function set buttonMode(value : Boolean) : void { }

		/* public function get dropTarget() : DisplayObject; */

		public function get graphics() : Graphics { return null; }

		/* public function get hitArea() : Sprite; */

		/* public function set hitArea(value : Sprite) : void; */

		public function get soundTransform() : SoundTransform { return null; }

		public function set soundTransform(sndTransform : SoundTransform) : void { }

		/* public function startDrag(lockCenter : Boolean = false, bounds : Rectangle = null) : void; */

		/* [API("667")] */
		/* public function startTouchDrag(touchPointID : int, lockCenter : Boolean = false, bounds : Rectangle = null) : void; */

		/* public function stopDrag() : void; */

		/* [API("667")] */
		/* public function stopTouchDrag(touchPointID : int) : void; */

		public function get useHandCursor() : Boolean { return false; }

		public function set useHandCursor(value : Boolean) : void { }
	}
}
