package bc.flash.display
{
	import bc.flash.errors.NotImplementedError;
	import bc.flash.net.URLRequest;
	import flash.errors.IllegalOperationError;
	import flash.system.LoaderContext;
	import flash.utils.ByteArray;

	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class Loader extends DisplayObjectContainer
	{
		private var mContent : DisplayObject;
		private var mContentLoaderInfo : LoaderInfo;

		public function Loader()
		{
			mContentLoaderInfo = new LoaderInfo();
		}

		public function close() : void
		{
		}

		public function get content() : DisplayObject
		{
			return mContent;
		}

		public function get contentLoaderInfo() : LoaderInfo
		{
			return mContentLoaderInfo;
		}

		public function load(request : URLRequest, context : LoaderContext = null) : void
		{
			throw new NotImplementedError();
		}

		public function loadBytes(bytes : ByteArray, context : LoaderContext = null) : void
		{
			throw new NotImplementedError();
		}

		/* public function get uncaughtErrorEvents() : UncaughtErrorEvents; */
		
		public function unload() : void
		{
			throw new NotImplementedError();
		}

		/* public function unloadAndStop(gc : Boolean = true) : void { throw new NotImplementedError(); } */
		override public function addChild(child : DisplayObject) : void
		{
			throw new IllegalOperationError();
		}

		override public function addChildAt(child : DisplayObject, index : int) : void
		{
			throw new IllegalOperationError();
		}

		override public function removeChild(child : DisplayObject, dispose : Boolean = false) : void
		{
			throw new IllegalOperationError();
		}

		override public function removeChildAt(index : int, dispose : Boolean = false) : void
		{
			throw new IllegalOperationError();
		}

		override public function setChildIndex(child : DisplayObject, index : int) : void
		{
			throw new IllegalOperationError();
		}
	}
}
