package bc.flash.display
{
	import bc.flash.error.IllegalOperationError;
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
		
		override public function addChild(child:DisplayObject):void
        {
			throw new IllegalOperationError();
        }
        
        override public function addChildAt(child:DisplayObject, index:int):void
		{
			throw new IllegalOperationError();			
		}
		
		override public function removeChild(child:DisplayObject, dispose:Boolean=false):void
        {
			throw new IllegalOperationError();
        }
        
        override public function removeChildAt(index:int, dispose:Boolean=false):void
		{
			throw new IllegalOperationError();			
		}
		
		override public function setChildIndex(child:DisplayObject, index:int):void
		{
			throw new IllegalOperationError();			
		}
	}
}
