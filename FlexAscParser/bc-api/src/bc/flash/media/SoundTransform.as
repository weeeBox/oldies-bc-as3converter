package bc.flash.media 
{
	import bc.flash.error.NotImplementedError;
	public final class SoundTransform extends Object 
	{
		function SoundTransform(vol : Number = 1, panning : Number = 0) : void { implementMe(); }

		public function get leftToLeft() : Number { implementMe(); }

		public function set leftToLeft(leftToLeft : Number) : void { implementMe(); }

		public function get leftToRight() : Number { implementMe(); }

		public function set leftToRight(leftToRight : Number) : void { implementMe(); }

		public function get pan() : Number { implementMe(); }

		public function set pan(panning : Number) : void { implementMe(); }

		public function get rightToLeft() : Number { implementMe(); }

		public function set rightToLeft(rightToLeft : Number) : void { implementMe(); }

		public function get rightToRight() : Number { implementMe(); }

		public function set rightToRight(rightToRight : Number) : void { implementMe(); }

		public function get volume() : Number { implementMe(); }

		public function set volume(volume : Number) : void { implementMe(); }
	}
}
