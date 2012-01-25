package flash.display 
{
	import flash.display.DisplayObject;
	import flash.display.InteractiveObject;	
	
	public class DisplayObjectContainer extends InteractiveObject 
	{
		public function addChild(child : DisplayObject) : DisplayObject { return null; }

		public function addChildAt(child : DisplayObject, index : int) : DisplayObject { return null; }

		/* public function areInaccessibleObjectsUnderPoint(point : Point) : Boolean;*/

		public function contains(child : DisplayObject) : Boolean { return false; }

		public function getChildAt(index : int) : DisplayObject { return null; }

		public function getChildByName(name : String) : DisplayObject { return null; }

		public function getChildIndex(child : DisplayObject) : int { return -1; }

		/* public function getObjectsUnderPoint(point : Point) : Array; */

		public function get mouseChildren() : Boolean { return false; }

		public function set mouseChildren(enable : Boolean) : void { }

		public function get numChildren() : int { return -1; }

		public function removeChild(child : DisplayObject) : DisplayObject { return null; }

		public function removeChildAt(index : int) : DisplayObject { return null; }

		public function setChildIndex(child : DisplayObject, index : int) : void { }

		public function swapChildren(child1 : DisplayObject, child2 : DisplayObject) : void { }

		public function swapChildrenAt(index1 : int, index2 : int) : void { }

		/* public function get tabChildren() : Boolean; */

		/* public function set tabChildren(enable : Boolean) : void; */

		/* public function get textSnapshot() : TextSnapshot; */
	}
}
