package bc.flash.media
{
	public final class SoundTransform extends Object
	{
		private var mVolume : Number;
		private var mPanning : Number;
		
		private var mLeftToLeft : Number;
		private var mLeftToRight : Number;
		private var mRightToLeft : Number;
		private var mRightToRight : Number;
		
		function SoundTransform(vol : Number = 1, panning : Number = 0) : void
		{
			mVolume = vol;
			mPanning = panning;
		}

		public function get leftToLeft() : Number
		{
			return mLeftToLeft;
		}

		public function set leftToLeft(leftToLeft : Number) : void
		{
			mLeftToLeft = leftToLeft;
		}

		public function get leftToRight() : Number
		{
			return mLeftToRight;
		}

		public function set leftToRight(leftToRight : Number) : void
		{
			mLeftToRight = leftToRight;
		}

		public function get pan() : Number
		{
			return mPanning;
		}

		public function set pan(panning : Number) : void
		{
			mPanning = panning;
		}

		public function get rightToLeft() : Number
		{
			return mRightToLeft;
		}

		public function set rightToLeft(rightToLeft : Number) : void
		{
			mRightToLeft = rightToLeft;
		}

		public function get rightToRight() : Number
		{
			return mRightToRight;
		}

		public function set rightToRight(rightToRight : Number) : void
		{
			mRightToRight = rightToRight;
		}

		public function get volume() : Number
		{
			return mVolume;
		}

		public function set volume(volume : Number) : void
		{
			mVolume = volume;
		}
	}
}
