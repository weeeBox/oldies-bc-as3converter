package bc.flash 
{
	import bc.flash.error.NotImplementedError;
	
	[NoConversion]
	public final class String extends Object 
	{
		/* public static const length : int = 1; */

		public function String(value : String = "") : void { throw new NotImplementedError(); }

		public function charAt(i : uint) : String { throw new NotImplementedError(); }

		/* public function charCodeAt(i : uint) : Number; */

		/* public function concat(...args : *) : String; */

		/* static public function fromCharCode(...args : *) : String; */

		public function indexOf(s : String, i : uint = 0) : int { throw new NotImplementedError(); }

		public function lastIndexOf(s : String, i : uint = 2147483647) : int { throw new NotImplementedError(); }

		public function get length() : int { throw new NotImplementedError(); }

		/* public function localeCompare(other : * = undefined) : int; */

		/* public function match(p : * = undefined) : Array; */

		public function replace(p : String, repl : String) : String { throw new NotImplementedError(); }

		/* public function search(p : * = undefined) : int; */

		public function slice(start : uint, end : uint = 2147483647) : String { throw new NotImplementedError(); }

		public function split(delim : String) : Array { throw new NotImplementedError(); }

		public function substr(start : uint, len : uint = 2147483647) : String { throw new NotImplementedError(); }

		public function substring(start : uint, end : uint = 2147483647) : String { throw new NotImplementedError(); }

		public function toLocaleLowerCase() : String { throw new NotImplementedError(); }

		public function toLocaleUpperCase() : String { throw new NotImplementedError(); }

		public function toLowerCase() : String { throw new NotImplementedError(); }

		public function toString() : String { throw new NotImplementedError(); }

		public function toUpperCase() : String { throw new NotImplementedError(); }

		public function valueOf() : String { throw new NotImplementedError(); }
	}
}
