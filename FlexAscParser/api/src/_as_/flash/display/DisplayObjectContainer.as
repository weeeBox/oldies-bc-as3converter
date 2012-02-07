package _as_.flash.display
{	
	public class DisplayObjectContainer extends InteractiveObject
	{
		private static var EMPTY_VECTOR : Vector.<DisplayObject> = new Vector.<DisplayObject>();		
		private var m_childs : Vector.<DisplayObject> = EMPTY_VECTOR;
		
		public function addChild(child : DisplayObject) : DisplayObject
		{
			return addChildAt(child, 0);
		}

		public function addChildAt(child : DisplayObject, index : int) : DisplayObject
		{
			if (m_childs == EMPTY_VECTOR)
			{
				m_childs = new Vector.<DisplayObject>();
			}
			
			if (child.parent != null)
			{
				child.parent.removeChild(child);
			}
			
			m_childs.splice(index, 0, child);
			child.parent = this;
			
			return child;
		}

		/* public function areInaccessibleObjectsUnderPoint(point : Point) : Boolean;*/
		
		public function contains(child : DisplayObject) : Boolean
		{
			return m_childs.indexOf(child) != -1;
		}

		public function getChildAt(index : int) : DisplayObject
		{			
			if (index < 0 || index >= m_childs.length)
			{
				throw new RangeError();
			}
			return m_childs[index];
		}

		public function getChildByName(name : String) : DisplayObject
		{
			if (name != null)
			{
				for each (var child : DisplayObject in m_childs) 
				{
					if (name == child.name)
					{
						return child;										
					}
				}
			}
			return null;
		}

		public function getChildIndex(child : DisplayObject) : int
		{
			var index : int = m_childs.indexOf(child);
			if (index == -1)
			{
				throw new ArgumentError();
			}
			
			return index;
		}

		/* public function getObjectsUnderPoint(point : Point) : Array; */
		
		public function get mouseChildren() : Boolean
		{
			return false;
		}

		public function set mouseChildren(enable : Boolean) : void
		{
		}

		public function get numChildren() : int
		{
			return -1;
		}

		public function removeChild(child : DisplayObject) : DisplayObject
		{
			var index : int = getChildIndex(child);
			return removeChildAt(index);
		}

		public function removeChildAt(index : int) : DisplayObject
		{
			var child : DisplayObject = getChildAt(index);			
			child.parent = null;
			m_childs.splice(index, 1);
			
			return child;
		}

		public function setChildIndex(child : DisplayObject, index : int) : void
		{
			var oldIndex : int = getChildIndex(child);
			if (index != oldIndex)
			{
				if (index < oldIndex)
				{
					for (var i1 : int = oldIndex-1; i1 >= index; i1--) 
					{
						m_childs[i1+1] = m_childs[i1]; 						
					}	
				}
				else
				{
					for (var i2 : int = oldIndex+1; i2 <= index; i2++) 
					{
						m_childs[i2-1] = m_childs[i2]; 						
					}					
				}
				m_childs[index] = child;
			}			
		}

		public function swapChildren(child1 : DisplayObject, child2 : DisplayObject) : void
		{
			var index1 : int = getChildIndex(child1);
			var index2 : int = getChildIndex(child2);
			
			m_childs[index1] = child2;
			m_childs[index2] = child1;
		}

		public function swapChildrenAt(index1 : int, index2 : int) : void
		{
			var child1 : DisplayObject = getChildAt(index1);
			var child2 : DisplayObject = getChildAt(index2);
			
			m_childs[index1] = child2;
			m_childs[index2] = child1;
		}

		public function get tabChildren() : Boolean
		{
			return false;
		}

		public function set tabChildren(enable : Boolean) : void
		{
		}
		/* public function get textSnapshot() : TextSnapshot; */
	}
}
