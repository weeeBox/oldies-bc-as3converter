package as.flash.display 
{
	public class InteractiveObject extends DisplayObject 
	{
		/* [Inspectable(environment="none")] */
		/* public function get accessibilityImplementation() : AccessibilityImplementation; */

		/* public function set accessibilityImplementation(value : AccessibilityImplementation) : void; */

		/* public function get contextMenu() : ContextMenu; */

		/* public function set contextMenu(cm : ContextMenu) : void; */

		public function get doubleClickEnabled() : Boolean { return false; }

		public function set doubleClickEnabled(enabled : Boolean) : void { }

		public function get focusRect() : Object { return null; }

		public function set focusRect(focusRect : Boolean) : void { }

		public function get mouseEnabled() : Boolean { return false; }

		public function set mouseEnabled(enabled : Boolean) : void { }

		/* public function get tabEnabled() : Boolean; */

		/* public function set tabEnabled(enabled : Boolean) : void; */

		/* public function get tabIndex() : int; */

		/* public function set tabIndex(index : int) : void; */
	}
}
