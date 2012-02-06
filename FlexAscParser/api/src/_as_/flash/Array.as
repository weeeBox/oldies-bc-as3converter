package _as_.flash 
{
	public dynamic class Array extends Object 
	{
		public static const CASEINSENSITIVE : uint = 1;
		public static const DESCENDING : uint = 2;
		public static const UNIQUESORT : uint = 4;
		public static const RETURNINDEXEDARRAY : uint = 8;
		public static const NUMERIC : uint = 16;
		/* public static const length : int = 1; */

		/* public function concat(...args : *) : Array; */

		/* public function every(callback : Function, thisObject : * = null) : Boolean; */

		/* public function filter(callback : Function, thisObject : * = null) : Array; */

		/* public function forEach(callback : Function, thisObject : * = null) : void; */

		public function indexOf(searchElement : Object, fromIndex : uint = 0) : int { return -1; }

		/* public function join(sep : * = undefined) : String; */

		public function lastIndexOf(searchElement : Object, fromIndex : uint = 2147483647) : int { return -1; }

		public function get length() : uint { return 0; }

		public function set length(newLength : uint) : void { }

		/* public function map(callback : Function, thisObject : * = null) : Array; */

		public function pop() : Object { return null; }

		public function push(arg : Object) : uint { return 0; }

		/* public function reverse() : Array; /*

		/* public function shift() : *; */

		/* public function slice(A : * = 0, B : * = 4.294967295E9) : Array; */

		/* public function some(callback : Function, thisObject : * = null) : Boolean; */

		/* public function sort(...args : *) : *; */

		/* public function sortOn(names : *, options : * = 0, ...args : *) : *; */

		/* public function splice(...args : *) : *; */

		/* public function toLocaleString() : String; */

		/* public function toString() : String */

		/* ublic function unshift(...args : *) : uint; */
	}
}
