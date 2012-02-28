package bc.flash.display
{
	import flash.utils.ByteArray;
	import bc.flash.error.NotImplementedError;
	import bc.flash.events.EventDispatcher;

	/**
	 * @author weee
	 */
	public class LoaderInfo extends EventDispatcher
	{
		public function get actionScriptVersion() : uint
		{
			return 3;
		}

		/* public function get applicationDomain() : ApplicationDomain; */
		public function get bytes() : ByteArray
		{
			throw new NotImplementedError();
		}

		public function get bytesLoaded() : uint
		{
			throw new NotImplementedError();
		}

		public function get bytesTotal() : uint
		{
			throw new NotImplementedError();
		}

		public function get childAllowsParent() : Boolean
		{
			throw new NotImplementedError();
		}

		public function get content() : DisplayObject
		{
			throw new NotImplementedError();
		}

		public function get contentType() : String
		{
			throw new NotImplementedError();
		}

		public function get frameRate() : Number
		{
			throw new NotImplementedError();
		}

		/* public static function getLoaderInfoByDefinition(object : Object) : LoaderInfo; */
		public function get height() : int
		{
			throw new NotImplementedError();
		}

		public function get isURLInaccessible() : Boolean
		{
			throw new NotImplementedError();
		}

		public function get loader() : Loader
		{
			throw new NotImplementedError();
		}

		public function get loaderURL() : String
		{
			throw new NotImplementedError();
		}

		public function get parameters() : Object
		{
			throw new NotImplementedError();
		}

		public function get parentAllowsChild() : Boolean
		{
			throw new NotImplementedError();
		}

		public function get sameDomain() : Boolean
		{
			throw new NotImplementedError();
		}

		public function get sharedEvents() : EventDispatcher
		{
			throw new NotImplementedError();
		}

		public function get swfVersion() : uint
		{
			throw new NotImplementedError();
		}

		/* public function get uncaughtErrorEvents() : UncaughtErrorEvents; */
		public function get url() : String
		{
			throw new NotImplementedError();
		}

		public function get width() : int
		{
			throw new NotImplementedError();
		}
	}
}
