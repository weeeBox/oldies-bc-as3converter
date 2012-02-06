package _as_.flash.media 
{
	import flash.media.SoundTransform;
	import flash.media.SoundChannel;
	
	public class Sound /* extends EventDispatcher */ 
	{
		/* function Sound(stream : URLRequest = null, context : SoundLoaderContext = null) : void; */

		/* public function get bytesLoaded() : uint; */

		/* public function get bytesTotal() : int; */

		public function close() : void;

		/* [Version("10")] */
		/* public function extract(target : ByteArray, length : Number, startPosition : Number = -1) : Number; */

		/* public function get id3() : ID3Info; */

		/* public function get isBuffering() : Boolean; */

		/* public function get isURLInaccessible() : Boolean; */

		public function get length() : Number { return 0; }

		/* public function load(stream : URLRequest, context : SoundLoaderContext = null) : void; */

		public function play(startTime : Number = 0, loops : int = 0, sndTransform : SoundTransform = null) : SoundChannel { return null; }

		/* public function get url() : String; */
	}
}
