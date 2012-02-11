package _as_.flash.events
{
	import _as_.flash.display.DisplayObject;	
	import _as_.flash.Vector;

	import flash.utils.Dictionary;

	public class EventDispatcher extends Object
	{
		private var mEventListeners : Dictionary;

		/** Creates an EventDispatcher. */
		public function EventDispatcher()
		{
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
					var remainListeners : Vector.<EventListener> = new Vector.<EventListener>();
					for each (var eventListener : EventListener in listeners)
					{
						if (eventListener.listener != listener)
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
			if (listeners == null && !event.bubbles) return;
			// no need to do anything

			// if the event already has a current target, it was re-dispatched by user -> we change
			// the target to 'this' for now, but undo that later on (instead of creating a clone)

			var previousTarget : EventDispatcher = event.target;
			if (previousTarget == null || event.currentTarget != null) event.setTarget(this);

			var stopImmediatePropagation : Boolean = false;
			var numListeners : int = listeners == null ? 0 : listeners.length;

			if (numListeners != 0)
			{
				event.setCurrentTarget(this);

				// we can enumerate directly over the vector, since "add"- and "removeEventListener"
				// won't change it, but instead always create a new vector.

				for (var i : int = 0; i < numListeners; ++i)
				{
					listeners[i](event);

					if (event.stopsImmediatePropagation)
					{
						stopImmediatePropagation = true;
						break;
					}
				}
			}

			if (!stopImmediatePropagation && event.bubbles && !event.stopsPropagation && this is DisplayObject)
			{
				var targetDisplayObject : DisplayObject = this as DisplayObject;
				if (targetDisplayObject.parent != null)
				{
					event.setCurrentTarget(null);
					// to find out later if the event was redispatched
					targetDisplayObject.parent.dispatchEvent(event);
				}
			}

			if (previousTarget)
				event.setTarget(previousTarget);
		}

		/** Returns if there are listeners registered for a certain event type. */
		public function hasEventListener(type : String) : Boolean
		{
			return mEventListeners != null && type in mEventListeners;
		}
	}
}
