package bc.flash.display
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.events.EventDispatcher;
	import bc.flash.utils.ByteArray;
	/**
	 * @author weee
	 */
	public class LoaderInfo extends EventDispatcher
	{
		public function get actionScriptVersion() : uint { implementMe(); }

		/* public function get applicationDomain() : ApplicationDomain; */

		public function get bytes() : ByteArray { implementMe(); }

		public function get bytesLoaded() : uint { implementMe(); }

		public function get bytesTotal() : uint { implementMe(); }

		public function get childAllowsParent() : Boolean { implementMe(); }

		public function get content() : DisplayObject { implementMe(); }

		public function get contentType() : String { implementMe(); }

		public function get frameRate() : Number { implementMe(); }

		/* public static function getLoaderInfoByDefinition(object : Object) : LoaderInfo; */

		public function get height() : int { implementMe(); }

		public function get isURLInaccessible() : Boolean { implementMe(); }

		public function get loader() : Loader { implementMe(); }

		public function get loaderURL() : String { implementMe(); }

		public function get parameters() : Object { implementMe(); }

		public function get parentAllowsChild() : Boolean { implementMe(); }

		public function get sameDomain() : Boolean { implementMe(); }

		public function get sharedEvents() : EventDispatcher { implementMe(); }

		public function get swfVersion() : uint { implementMe(); }

		/* public function get uncaughtErrorEvents() : UncaughtErrorEvents; */

		public function get url() : String { implementMe(); }

		public function get width() : int { implementMe(); }
	}
}
