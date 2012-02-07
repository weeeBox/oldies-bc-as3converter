package _as_.flash.events
{
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
			Debug.implementMe("dispatchEvent");
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
