package bc.flash.media 
{
	import bc.flash.error.NotImplementedError;

	import flash.media.SoundTransform;
	
	public final class SoundChannel /* extends EventDispatcher */ 
	{
		public function get leftPeak() : Number { throw new NotImplementedError(); }

		public function get position() : Number { throw new NotImplementedError(); }

		public function get rightPeak() : Number { throw new NotImplementedError(); }

		public function get soundTransform() : SoundTransform { throw new NotImplementedError(); }

		public function set soundTransform(sndTransform : SoundTransform) : void { throw new NotImplementedError(); }

		public function stop() : void { throw new NotImplementedError(); }
	}
}
