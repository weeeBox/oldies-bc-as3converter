package bc.flash.display
{
	import bc.flash.system.LoaderContext;
	import bc.flash.utils.ByteArray;
	import bc.flash.net.URLRequest;
	import bc.flash.error.NotImplementedError;
	/**
	 * @author weee
	 */
	public class Loader extends DisplayObjectContainer
	{
		public function close() : void { throw new NotImplementedError(); }

		public function get content() : DisplayObject { throw new NotImplementedError(); }

		public function get contentLoaderInfo() : LoaderInfo { throw new NotImplementedError(); }

		public function load(request : URLRequest, context : LoaderContext = null) : void { throw new NotImplementedError(); }

		public function loadBytes(bytes : ByteArray, context : LoaderContext = null) : void { throw new NotImplementedError(); }

		/* public function get uncaughtErrorEvents() : UncaughtErrorEvents; */

		public function unload() : void { throw new NotImplementedError(); }

		/* public function unloadAndStop(gc : Boolean = true) : void { throw new NotImplementedError(); } */
	}
}
