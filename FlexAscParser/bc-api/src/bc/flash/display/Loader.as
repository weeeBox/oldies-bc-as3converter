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
		public function close() : void { implementMe(); }

		public function get content() : DisplayObject { implementMe(); }

		public function get contentLoaderInfo() : LoaderInfo { implementMe(); }

		public function load(request : URLRequest, context : LoaderContext = null) : void { implementMe(); }

		public function loadBytes(bytes : ByteArray, context : LoaderContext = null) : void { implementMe(); }

		/* public function get uncaughtErrorEvents() : UncaughtErrorEvents; */

		public function unload() : void { implementMe(); }

		/* public function unloadAndStop(gc : Boolean = true) : void { implementMe(); } */
		
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
