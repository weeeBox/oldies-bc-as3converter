package bc.flash 
{
	[NoConversion]
	public class Vector extends Object 
	{
		/* public var fixed : Boolean; */
		/* public var length : uint; */

		public function Vector(length : uint = 0, fixed : Boolean = false) {}

		public function concat(obj : Object = null) : Vector { throw new NotImplementedError(); }

		/* public function every(callback : Function, thisObject : Object = null) : Boolean { return false; } */

		/* public function filter(callback : Function, thisObject : Object = null) : Vector { throw new NotImplementedError(); } */

		/* public function forEach(callback : Function, thisObject : Object = null) {} */
		
		public function get length() : uint { return 0; }
		
		public function set length(newLenght : uint) : void { }

		public function indexOf(searchElement : Object, fromIndex : int = 0) : int { return -1; }

		public function join(sep : String = ",") : String { throw new NotImplementedError(); }

		public function lastIndexOf(searchElement : Object, fromIndex : int = 0x7fffffff) : int { return -1; }

		/* public function map(callback : Function, thisObject : Object = null) : Vector { throw new NotImplementedError(); } */

		public function pop() : Object {return null; }

		public function push(arg : Object) : uint { return -1; }

		public function reverse() : Vector { throw new NotImplementedError(); }

		/* public function shift() : *; */

		public function slice(startIndex : int = 0, endIndex : int = 16777215) : Vector { throw new NotImplementedError(); }

		/* public function some(callback : Function, thisObject : Object = null) : Boolean; */

		public function sort(compareFunction : Function) : Vector { throw new NotImplementedError(); }

		public function splice(startIndex : int, deleteCount : uint, item : Object = null) : Vector { throw new NotImplementedError(); }

		/* public function toLocaleString() : String; */

		public function toString() : String { throw new NotImplementedError(); }

		public function unshift(arg : Object) : uint { return 0; }
	}
}
