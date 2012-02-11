package _as_.flash.media 
{
	import flash.media.SoundTransform;
	
	public final class SoundChannel /* extends EventDispatcher */ 
	{
		public function get leftPeak() : Number { return 0; }

		public function get position() : Number { return 0; }

		public function get rightPeak() : Number { return 0; }

		public function get soundTransform() : SoundTransform { return null; }

		public function set soundTransform(sndTransform : SoundTransform) : void { }

		public function stop() : void { }
	}
}
