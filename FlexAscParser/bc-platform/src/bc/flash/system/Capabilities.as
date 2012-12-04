package bc.flash.system
{
	import bc.flash.errors.NotImplementedError;
	/**
	 * @author weee
	 */
	public final class Capabilities extends Object
	{
		public static function get _internal() : uint
		{
			throw new NotImplementedError();
		}

		public static function get avHardwareDisable() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get cpuArchitecture() : String
		{
			throw new NotImplementedError();
		}

		public static function get hasAccessibility() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasAudio() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasAudioEncoder() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasEmbeddedVideo() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasIME() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasMP3() : Boolean
		{
			throw new NotImplementedError();
		}

		[API("674")]
		public static function hasMultiChannelAudio(type : String) : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasPrinting() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasScreenBroadcast() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasScreenPlayback() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasStreamingAudio() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasStreamingVideo() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasTLS() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get hasVideoEncoder() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get isDebugger() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get isEmbeddedInAcrobat() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get language() : String
		{
			throw new NotImplementedError();
		}

		public static function get localFileReadDisable() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function get manufacturer() : String
		{
			throw new NotImplementedError();
		}

		[Version("10")]
		public static function get maxLevelIDC() : String
		{
			throw new NotImplementedError();
		}

		public static function get os() : String
		{
			throw new NotImplementedError();
		}

		public static function get pixelAspectRatio() : Number
		{
			throw new NotImplementedError();
		}

		public static function get playerType() : String
		{
			throw new NotImplementedError();
		}

		public static function get screenColor() : String
		{
			throw new NotImplementedError();
		}

		public static function get screenDPI() : Number
		{
			throw new NotImplementedError();
		}

		public static function get screenResolutionX() : Number
		{
			throw new NotImplementedError();
		}

		public static function get screenResolutionY() : Number
		{
			throw new NotImplementedError();
		}

		public static function get serverString() : String
		{
			throw new NotImplementedError();
		}

		[Version("10.0.32")]
		public static function get supports32BitProcesses() : Boolean
		{
			throw new NotImplementedError();
		}

		[Version("10.0.32")]
		public static function get supports64BitProcesses() : Boolean
		{
			throw new NotImplementedError();
		}

		[Version("10.1")]
		public static function get touchscreenType() : String
		{
			throw new NotImplementedError();
		}

		public static function get version() : String
		{
			throw new NotImplementedError();
		}
	}
}
