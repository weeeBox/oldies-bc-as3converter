package bc.flash.core.natives
{
	import bc.flash.events.KeyboardEvent;
	import bc.flash.ui.Keyboard;
	import bc.flash.events.EnterFrameEvent;
	import bc.flash.events.Event;

	import flash.utils.Dictionary;
	
	public class BcNativeStage
	{
		private var mEventListeners : Dictionary;

		private var mWidth : int;
		private var mHeight : int;

		public function BcNativeStage(width : int, height : int)
		{
			mWidth = width;
			mHeight = height;
		}
		
		public function get width() : int
		{
			return mWidth;
		}
		
		public function get height() : int
		{
			return mHeight;
		}

		/** Registers an event listener at a certain object. */
		public function addEventListener(type : String, listener : Function) : void
		{
			if (mEventListeners == null)
				mEventListeners = new Dictionary();

			var listeners : Vector.<Function> = mEventListeners[type];
			if (listeners == null)
			{
				listeners = new Vector.<Function>();
				mEventListeners[type] = listeners;
			}

			listeners.push(listener);
		}

		/** Removes an event listener from the object. */
		public function removeEventListener(type : String, listener : Function) : void
		{
			if (mEventListeners)
			{
				var listeners : Vector.<Function> = mEventListeners[type];
				if (listeners)
				{
					var remainListeners : Vector.<Function> = new Vector.<Function>();
					for each (var eventListener : Function in listeners)
					{
						if (eventListener != listener)
						{
							remainListeners.push(eventListener);
						}
					}

					if (remainListeners.length > 0)
					{
						mEventListeners[type] = remainListeners;
					}
					else
					{
						delete mEventListeners[type];
					}
				}
			}
		}

		/** Removes all event listeners with a certain type, or all of them if type is null. 
		 *  Be careful when removing all event listeners: you never know who else was listening. */
		public function removeEventListeners(type : String = null) : void
		{
			if (type && mEventListeners)
				delete mEventListeners[type];
			else
				mEventListeners = null;
		}

		/** Dispatches an event to all objects that have registered for events of the same type. */
		public function dispatchEvent(event : Event) : void
		{
			var listeners : Vector.<Function> = mEventListeners ? mEventListeners[event.type] : null;
			if (listeners == null) return;
			// no need to do anything

			var numListeners : int = listeners.length;
			if (numListeners != 0)
			{
				// we can enumerate directly over the vector, since "add"- and "removeEventListener"
				// won't change it, but instead always create a new vector.

				for (var i : int = 0; i < numListeners; ++i)
				{
					listeners[i](event);
				}
			}
		}

		/** Returns if there are listeners registered for a certain event type. */
		public function hasEventListener(type : String) : Boolean
		{
			return mEventListeners != null && type in mEventListeners;
		}
		
		public function tick(dt : Number) : void
		{
			dispatchEvent(new EnterFrameEvent(Event.ENTER_FRAME, dt));
		}
		
		public function keyPressed(code : int) : void
		{
			dispatchEvent(new KeyboardEvent(KeyboardEvent.KEY_DOWN, code));
		}
		
		public function keyReleased(code : int) : void
		{
			dispatchEvent(new KeyboardEvent(KeyboardEvent.KEY_UP, code));
		}
		
		public function touchDown(x : Number, y : Number, touchId : int) : void
		{	
		}
		
		public function touchMove(x : Number, y : Number, touchId : int) : void
		{
		}
		
		public function touchUp(x : Number, y : Number, touchId : int) : void
		{
		}
	}
}
