package bc.flash.core.natives
{
	import bc.flash.Vector;
	import bc.flash.error.IllegalOperationError;
	
	public class BcNativeApplication
	{
		private var mTickListeners : Vector.<BcINativeTickListener>;
		private var mKeyListeners : Vector.<BcINativeKeyListener>;
		private var mTouchListeners : Vector.<BcINativeTouchListener>;
		
		private var mWidth : int;
		private var mHeight : int;
		
		public function BcNativeApplication(width : int, height : int)
		{
			mWidth = width;
			mHeight = height;
			
			mTickListeners = new Vector.<BcINativeTickListener>();			
			mKeyListeners = new Vector.<BcINativeKeyListener>();
			mTouchListeners = new Vector.<BcINativeTouchListener>();
		}
		
		public function addTickListener(listener : BcINativeTickListener) : void
		{
			if (mTickListeners.indexOf(listener) != -1)
			{
				throw new IllegalOperationError("Tried to add tick listener twice");
			}
			mTickListeners.push(listener);
		}
		
		public function removeTickListener(listener : BcINativeTickListener) : void
		{
			var index : int = mTickListeners.indexOf(listener);
			if (index != -1)
			{
				mTickListeners.splice(index, 1);
			}
		}
		
		public function addKeyListener(listener : BcINativeKeyListener) : void
		{			
			if (mKeyListeners.indexOf(listener) != -1)
			{
				throw new IllegalOperationError("Tried to add key listener twice");
			}
			mKeyListeners.push(listener);
		}
		
		public function removeKeyListener(listener : BcINativeKeyListener) : void
		{
			var index : int = mKeyListeners.indexOf(listener);
			if (index != -1)
			{
				mKeyListeners.splice(index, 1);
			}
		}
		
		public function addTouchListener(listener : BcINativeTouchListener) : void
		{
			if (mTouchListeners.indexOf(listener) != -1)
			{
				throw new IllegalOperationError("Tried to add touch listener twice");
			}
			mTouchListeners.push(listener);
		}
		
		public function removeTouchListener(listener : BcINativeTouchListener) : void
		{
			var index : int = mTouchListeners.indexOf(listener);
			if (index != -1)
			{
				mTouchListeners.splice(index, 1);
			}
		}
		
		public function tick(dt : Number) : void
		{
			for each (var listener : BcINativeTickListener in mTickListeners) 
			{
				listener.tick(dt);				
			}
		}
		
		public function keyPressed(code : int) : void
		{
			for each (var listener : BcINativeKeyListener in mKeyListeners) 
			{
				listener.keyPressed(code);				
			}
		}
		
		public function keyReleased(code : int) : void
		{	
			for each (var listener : BcINativeKeyListener in mKeyListeners) 
			{
				listener.keyReleased(code);				
			}		
		}
		
		public function touchDown(x : Number, y : Number, touchId : int) : void
		{	
			for each (var listener : BcINativeTouchListener in mTouchListeners) 
			{
				listener.touchDown(x, y, touchId);				
			}		
		}
		
		public function touchMove(x : Number, y : Number, touchId : int) : void
		{
			for each (var listener : BcINativeTouchListener in mTouchListeners) 
			{
				listener.touchMove(x, y, touchId);				
			}	
		}
		
		public function touchUp(x : Number, y : Number, touchId : int) : void
		{
			for each (var listener : BcINativeTouchListener in mTouchListeners) 
			{
				listener.touchUp(x, y, touchId);				
			}	
		}
	}
}
