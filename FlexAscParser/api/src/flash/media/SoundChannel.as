package flash.media 
{
	import flash.media.SoundTransform;
	
	public final class SoundChannel /* extends EventDispatcher */ 
	{
		public function get leftPeak() : Number { return null; }

		public function get position() : Number { return null; }

		public function get rightPeak() : Number { return null; }

		public function get soundTransform() : SoundTransform { return null; }

		public function set soundTransform(sndTransform : SoundTransform) : void { }

		public function stop() : void { }
	}
}
