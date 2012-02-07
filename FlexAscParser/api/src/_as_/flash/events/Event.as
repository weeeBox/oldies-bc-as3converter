package _as_.flash.events
{
	import _as_.flash.Debug;

	public class Event extends Object
	{
		public static const ACTIVATE : String = "activate";
		public static const ADDED : String = "added";
		public static const ADDED_TO_STAGE : String = "addedToStage";
		public static const CANCEL : String = "cancel";
		public static const CHANGE : String = "change";
		public static const CLEAR : String = "clear";
		public static const CLOSE : String = "close";
		public static const COMPLETE : String = "complete";
		public static const CONNECT : String = "connect";
		public static const COPY : String = "copy";
		public static const CUT : String = "cut";
		public static const DEACTIVATE : String = "deactivate";
		public static const ENTER_FRAME : String = "enterFrame";
		public static const FRAME_CONSTRUCTED : String = "frameConstructed";
		public static const EXIT_FRAME : String = "exitFrame";
		public static const ID3 : String = "id3";
		public static const INIT : String = "init";
		public static const MOUSE_LEAVE : String = "mouseLeave";
		public static const OPEN : String = "open";
		public static const PASTE : String = "paste";
		public static const REMOVED : String = "removed";
		public static const REMOVED_FROM_STAGE : String = "removedFromStage";
		public static const RENDER : String = "render";
		public static const RESIZE : String = "resize";
		public static const SCROLL : String = "scroll";
		public static const SELECT : String = "select";
		public static const SELECT_ALL : String = "selectAll";
		public static const SOUND_COMPLETE : String = "soundComplete";
		public static const TAB_CHILDREN_CHANGE : String = "tabChildrenChange";
		public static const TAB_ENABLED_CHANGE : String = "tabEnabledChange";
		public static const TAB_INDEX_CHANGE : String = "tabIndexChange";
		public static const UNLOAD : String = "unload";
		public static const FULLSCREEN : String = "fullScreen";
		
		private var m_type : String;
		private var m_bubbles : Boolean;
		private var m_cancelable : Boolean;
		private var m_currentTarget : Object;
		private var m_eventPhase : uint;
		private var m_defaultPrevented : Boolean;
		private var m_target : Object;

		function Event(type : String, bubbles : Boolean = false, cancelable : Boolean = false) : void
		{
			m_type = type;
			m_bubbles = bubbles;
			m_cancelable = cancelable;
		}

		public function get bubbles() : Boolean
		{
			return m_bubbles;
		}

		public function get cancelable() : Boolean
		{
			return m_cancelable;
		}

		public function clone() : Event
		{
			return new Event(type, bubbles, cancelable);
		}

		public function get currentTarget() : Object
		{
			return m_currentTarget;
		}

		public function get eventPhase() : uint
		{
			return m_eventPhase;
		}

		/* public function formatToString(className : String, ...args : *) : String; */

		public function isDefaultPrevented() : Boolean
		{
			return m_defaultPrevented;
		}

		public function preventDefault() : void
		{
			m_defaultPrevented = true;
		}

		public function stopImmediatePropagation() : void
		{
			Debug.implementMe("stopImmediatePropagation");
		}

		public function stopPropagation() : void
		{
			Debug.implementMe("stopPropagation");
		}

		public function get target() : Object
		{
			return m_target;
		}

		public function get type() : String
		{
			return m_type;
		}
	}
}
