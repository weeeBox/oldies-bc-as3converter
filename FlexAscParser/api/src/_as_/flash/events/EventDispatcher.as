package _as_.flash.events
{
	import _as_.flash.display.DisplayObject;
	import _as_.flash.Debug;
	import _as_.flash.Vector;

	import flash.utils.Dictionary;

	public class EventDispatcher extends Object implements IEventDispatcher, IEventDispatcher
	{
		private static var EMPTY_EVENT_LISTENERS : Dictionary = new Dictionary();
		private var m_eventListeners : Dictionary;
		private var m_target : IEventDispatcher;

		function EventDispatcher(target : IEventDispatcher = null) : void
		{
			m_target = target;
		}

		public function addEventListener(type : String, listener : Function, useCapture : Boolean = false, priority : int = 0, useWeakReference : Boolean = false) : void
		{
			if (m_eventListeners == EMPTY_EVENT_LISTENERS)
			{
				m_eventListeners = new Dictionary();
			}

			var listeners : Vector.<EventListener> = m_eventListeners[type];
			if (listeners == null)
			{
				listeners = new Vector.<EventListener>();
				m_eventListeners[type] = listeners;
			}

			listeners.push(new EventListener(listener, useCapture));
		}

		public function removeEventListener(type : String, listener : Function, useCapture : Boolean = false) : void
		{
			var listeners : Vector.<EventListener> = m_eventListeners[type];
			if (listeners != null)
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
					m_eventListeners[type] = remainListeners;
				}
				else
				{
					delete m_eventListeners[type];
				}
			}
		}

		public function dispatchEvent(event : Event) : Boolean
		{
			var listeners : Vector.<EventListener> = m_eventListeners[event.type];

			if (!event.bubbles && !listeners) return;
			// no need to do anything.

			// if the event already has a current target, it was re-dispatched by user -> we change the
			// target to 'self' for now, but undo that later on (instead of creating a copy, which could
			// lead to the creation of a huge amount of objects).

			var previousTarget : EventDispatcher = event.target;

			if (!event.target || event.currentTarget) event.target = this;
			event.currentTarget = this;

			var stopImmediatPropagation : Boolean = true;
			if (listeners.length != 0)
			{
				// we can enumerate directly over the array, since "add"- and "removeEventListener" won't
				// change it, but instead always create a new array.
				for each (var eventListener : EventListener in listeners)
				{
					eventListener.listener(event);
					if (event.stopsImmediatePropagation)
					{
						stopImmediatPropagation = true;
						break;
					}
				}
			}

			if (!stopImmediatPropagation)
			{
				event.currentTarget = null;
				// this is how we can find out later if the event was redispatched
				if (event.bubbles && !event.stopsPropagation && this is DisplayObject)
				{
					var target : DisplayObject = DisplayObject(this);
					if (target.parent != null)
					{
						target.parent.dispatchEvent(event);
					}
				}
			}

			if (previousTarget) event.target = previousTarget;

			return false;
		}

		public function hasEventListener(type : String) : Boolean
		{
			return m_eventListeners[type] != null;
		}

		public function willTrigger(type : String) : Boolean
		{
			Debug.implementMe("willTrigger");
			return false;
		}
	}
}
