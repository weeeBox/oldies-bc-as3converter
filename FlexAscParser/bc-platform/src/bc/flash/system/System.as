package bc.flash.system
{
	import bc.flash.errors.NotImplementedError;

	/**
	 * @author weee
	 */
	public final class System extends Object
	{
		public static function disposeXML(node : XML) : void
		{
			throw new NotImplementedError();
		}

		public static function exit(code : uint) : void
		{
			throw new NotImplementedError();
		}

		public static function get freeMemory() : Number
		{
			throw new NotImplementedError();
		}

		public static function gc() : void
		{
			throw new NotImplementedError();
		}

		public static function get ime() : Object // IME
		{
			throw new NotImplementedError();
		}

		public static function pause() : void
		{
			throw new NotImplementedError();
		}

		public static function pauseForGCIfCollectionImminent(imminence : Number = 0.75) : void
		{
			throw new NotImplementedError();
		}

		public static function get privateMemory() : Number
		{
			throw new NotImplementedError();
		}

		public static function get processCPUUsage() : Number
		{
			throw new NotImplementedError();
		}

		public static function resume() : void
		{
			throw new NotImplementedError();
		}

		public static function setClipboard(string : String) : void
		{
			throw new NotImplementedError();
		}

		public static function get totalMemory() : uint
		{
			throw new NotImplementedError();
		}

		public static function get totalMemoryNumber() : Number
		{
			throw new NotImplementedError();
		}

		public static function get useCodePage() : Boolean
		{
			throw new NotImplementedError();
		}

		public static function set useCodePage(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public static function get vmVersion() : String
		{
			throw new NotImplementedError();
		}
	}
}
