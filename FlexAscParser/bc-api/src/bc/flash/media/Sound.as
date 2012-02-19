package bc.flash.media 
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.events.EventDispatcher;
	import bc.flash.net.URLRequest;

	public class Sound extends EventDispatcher 
	{
		public function Sound(stream : URLRequest = null, context : SoundLoaderContext = null) : void { throw new NotImplementedError(); }

		public function get bytesLoaded() : uint { throw new NotImplementedError(); }

		public function get bytesTotal() : int { throw new NotImplementedError(); }

		public function close() : void { throw new NotImplementedError(); }

		/* public function extract(target : ByteArray, length : Number, startPosition : Number = -1) : Number; */

		/* public function get id3() : ID3Info; */

		/* public function get isBuffering() : Boolean; */

		/* public function get isURLInaccessible() : Boolean; */

		public function get length() : Number { throw new NotImplementedError(); }

		public function load(stream : URLRequest, context : SoundLoaderContext = null) : void { throw new NotImplementedError(); }

		public function play(startTime : Number = 0, loops : int = 0, sndTransform : SoundTransform = null) : SoundChannel { throw new NotImplementedError(); }

		public function get url() : String { throw new NotImplementedError(); }
	}
}
