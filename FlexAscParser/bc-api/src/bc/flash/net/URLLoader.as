package bc.flash.net
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.events.EventDispatcher;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class URLLoader extends EventDispatcher
	{
		public var data : Object;
		public var dataFormat : String = "text";
		public var bytesLoaded : uint = 0;
		public var bytesTotal : uint = 0;

		public function URLLoader(request : URLRequest = null) : void
		{
			implementMe();
		}

		public function close() : void
		{
			implementMe();
		}

		public function load(request : URLRequest) : void
		{
			implementMe();	
		}
	}
}
