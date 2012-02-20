package bc.flash.media
{
	import bc.flash.error.NotImplementedError;

	import flash.media.SoundTransform;

	[ConvertOnce]
	public final class SoundChannel /* extends EventDispatcher */
	{
		private var mSoundTransform : SoundTransform;
		
		public function get leftPeak() : Number
		{
			throw new NotImplementedError();
		}

		public function get position() : Number
		{
			throw new NotImplementedError();
		}

		public function get rightPeak() : Number
		{
			throw new NotImplementedError();
		}

		public function get soundTransform() : SoundTransform
		{
			return mSoundTransform;
		}

		public function set soundTransform(sndTransform : SoundTransform) : void
		{
			mSoundTransform = sndTransform;
		}

		public function stop() : void
		{
			throw new NotImplementedError();
		}
	}
}
