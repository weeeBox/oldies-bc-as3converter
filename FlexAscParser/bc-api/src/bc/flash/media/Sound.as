package bc.flash.media
{
	import bc.flash.errors.NotImplementedError;
	import bc.flash.events.EventDispatcher;
	import bc.flash.net.URLRequest;

	[ConvertOnce]
	public class Sound extends EventDispatcher
	{
		private var mRequest : URLRequest;
		private var mContext : SoundLoaderContext;
		
		public function Sound(request : URLRequest = null, context : SoundLoaderContext = null) : void
		{
			mRequest = request;
			mContext = context;
		}

		public function get bytesLoaded() : uint
		{
			throw new NotImplementedError();
		}

		public function get bytesTotal() : int
		{
			throw new NotImplementedError();
		}

		public function close() : void
		{
			throw new NotImplementedError();
		}

		/* public function extract(target : ByteArray, length : Number, startPosition : Number = -1) : Number; */
		/* public function get id3() : ID3Info; */
		/* public function get isBuffering() : Boolean; */
		/* public function get isURLInaccessible() : Boolean; */
		
		public function get length() : Number
		{
			throw new NotImplementedError();
		}

		public function load(request : URLRequest, context : SoundLoaderContext = null) : void
		{
			mRequest = request;
			mContext = context;
			
			throw new NotImplementedError();
		}

		public function play(startTime : Number = 0, loops : int = 0, sndTransform : SoundTransform = null) : SoundChannel
		{
			throw new NotImplementedError();
		}

		public function get url() : String
		{
			return mRequest.url;
		}
	}
}
