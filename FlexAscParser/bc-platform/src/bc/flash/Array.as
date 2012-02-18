package bc.flash 
{	
	import bc.flash.error.NotImplementedError;
	
	[NoConversion]
	public dynamic class Array extends Object 
	{		
		public static const CASEINSENSITIVE : uint = 1;
		public static const DESCENDING : uint = 2;
		public static const UNIQUESORT : uint = 4;
		public static const RETURNINDEXEDARRAY : uint = 8;
		public static const NUMERIC : uint = 16;
		/* public static const length : int = 1; */

		/* public function concat(...args : *) : Array; */

		/* public function every(callback : Function, thisObject : * = null) : Boolean { throw new NotImplementedError(); } */

		/* public function filter(callback : Function, thisObject : * = null) : Array { throw new NotImplementedError(); } */

		/* public function forEach(callback : Function, thisObject : * = null) : void { throw new NotImplementedError(); } */

		public function indexOf(searchElement : Object, fromIndex : uint = 0) : int { throw new NotImplementedError(); }

		/* public function join(sep : * = undefined) : String; */

		public function lastIndexOf(searchElement : Object, fromIndex : uint = 2147483647) : int { throw new NotImplementedError(); }

		public function get length() : uint { throw new NotImplementedError(); }

		public function set length(newLength : uint) : void { throw new NotImplementedError(); }

		/* public function map(callback : Function, thisObject : * = null) : Array; */

		public function pop() : Object { throw new NotImplementedError(); }

		public function push(arg : Object) : uint { throw new NotImplementedError(); }

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
