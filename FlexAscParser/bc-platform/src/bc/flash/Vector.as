package bc.flash 
{
	import bc.flash.errors.NotImplementedError;
	
	[ConvertOnce]
	public class Vector extends Object 
	{
		/* public var fixed : Boolean; */
		/* public var length : uint; */

		public function Vector(length : uint = 0, fixed : Boolean = false) { throw new NotImplementedError(); }

		public function concat(obj : Object = null) : Vector { throw new NotImplementedError(); }

		/* public function every(callback : Function, thisObject : Object = null) : Boolean { throw new NotImplementedError(); } */

		/* public function filter(callback : Function, thisObject : Object = null) : Vector { throw new NotImplementedError(); } */

		/* public function forEach(callback : Function, thisObject : Object = null) {} */
		
		public function get length() : uint { throw new NotImplementedError(); }
		
		public function set length(newLenght : uint) : void { throw new NotImplementedError(); }

		public function indexOf(searchElement : Object, fromIndex : int = 0) : int { throw new NotImplementedError(); }

		public function join(sep : String = ",") : String { throw new NotImplementedError(); }

		public function lastIndexOf(searchElement : Object, fromIndex : int = 0x7fffffff) : int { throw new NotImplementedError(); }

		/* public function map(callback : Function, thisObject : Object = null) : Vector { throw new NotImplementedError(); } */

		public function pop() : _$_generic_$_ { throw new NotImplementedError(); }

		public function push(... arg) : uint { throw new NotImplementedError(); }

		public function reverse() : Vector { throw new NotImplementedError(); }

		/* public function shift() : *; */

		public function slice(startIndex : int = 0, endIndex : int = 16777215) : Vector { throw new NotImplementedError(); }

		/* public function some(callback : Function, thisObject : Object = null) : Boolean; */

		[FunctionType(callback="ComparatorFunction", params="o1:Object,o2:Object")]
		public function sort(compareFunction : Function) : Vector { throw new NotImplementedError(); }

		public function splice(startIndex : int, deleteCount : uint, item : Object = null) : Vector { throw new NotImplementedError(); }

		/* public function toLocaleString() : String; */

		public function toString() : String { throw new NotImplementedError(); }

		public function unshift(arg : Object) : uint { throw new NotImplementedError(); }
	}
}
