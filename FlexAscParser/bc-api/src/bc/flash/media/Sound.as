package bc.flash.media 
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.events.EventDispatcher;
	import bc.flash.net.URLRequest;

	public class Sound extends EventDispatcher 
	{
		public function Sound(stream : URLRequest = null, context : SoundLoaderContext = null) : void { implementMe(); }

		public function get bytesLoaded() : uint { implementMe(); }

		public function get bytesTotal() : int { implementMe(); }

		public function close() : void { implementMe(); }

		/* public function extract(target : ByteArray, length : Number, startPosition : Number = -1) : Number; */

		/* public function get id3() : ID3Info; */

		/* public function get isBuffering() : Boolean; */

		/* public function get isURLInaccessible() : Boolean; */

		public function get length() : Number { implementMe(); }

		public function load(stream : URLRequest, context : SoundLoaderContext = null) : void { implementMe(); }

		public function play(startTime : Number = 0, loops : int = 0, sndTransform : SoundTransform = null) : SoundChannel { implementMe(); }

		public function get url() : String { implementMe(); }
	}
}
