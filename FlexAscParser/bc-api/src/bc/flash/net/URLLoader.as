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
			throw new NotImplementedError();
		}

		public function close() : void
		{
			throw new NotImplementedError();
		}

		public function load(request : URLRequest) : void
		{
			throw new NotImplementedError();	
		}
	}
}
