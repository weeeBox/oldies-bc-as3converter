package _as_.flash.display
{
	import _as_.flash.Debug;
	
	public class InteractiveObject extends DisplayObject
	{
		private var m_mouseEnabled : Boolean;
		private var m_doubleClickEnabled : Boolean;
		
		/* [Inspectable(environment="none")] */
		/* public function get accessibilityImplementation() : AccessibilityImplementation; */
		/* public function set accessibilityImplementation(value : AccessibilityImplementation) : void; */
		/* public function get contextMenu() : ContextMenu; */
		/* public function set contextMenu(cm : ContextMenu) : void; */
		
		public function get doubleClickEnabled() : Boolean
		{
			return m_doubleClickEnabled;
		}

		public function set doubleClickEnabled(enabled : Boolean) : void
		{
			m_doubleClickEnabled = enabled;
		}

		public function get focusRect() : Object
		{
			Debug.implementMe("get focusRect");
			return null;
		}

		public function set focusRect(focusRect : Boolean) : void
		{
			Debug.implementMe("set focusRect");
		}

		public function get mouseEnabled() : Boolean
		{
			return m_mouseEnabled;
		}

		public function set mouseEnabled(enabled : Boolean) : void
		{
			m_mouseEnabled = enabled;	
		}
		
		/* public function get tabEnabled() : Boolean; */

		/* public function set tabEnabled(enabled : Boolean) : void; */

		/* public function get tabIndex() : int; */

		/* public function set tabIndex(index : int) : void; */
	}
}
