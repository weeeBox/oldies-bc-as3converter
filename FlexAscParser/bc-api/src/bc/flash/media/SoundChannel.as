package bc.flash.media 
{
	import bc.flash.error.NotImplementedError;

	import flash.media.SoundTransform;
	
	public final class SoundChannel /* extends EventDispatcher */ 
	{
		public function get leftPeak() : Number { implementMe(); }

		public function get position() : Number { implementMe(); }

		public function get rightPeak() : Number { implementMe(); }

		public function get soundTransform() : SoundTransform { implementMe(); }

		public function set soundTransform(sndTransform : SoundTransform) : void { implementMe(); }

		public function stop() : void { implementMe(); }
	}
}
