package flash 
{
	public final class String extends Object 
	{
		public static const length : int = 1;

		function String(value : Object) : void {}

		public function charAt(i : uint) : String { return null; }

		/* public function charCodeAt(i : uint) : Number; */

		/* public function concat(...args : *) : String; */

		/* static public function fromCharCode(...args : *) : String; */

		public function indexOf(s : String, i : uint = 0) : int { return 0; }

		public function lastIndexOf(s : String, i : uint = 2147483647) : int { return 0; }

		public function get length() : int { return 0; }

		/* public function localeCompare(other : * = undefined) : int; */

		/* public function match(p : * = undefined) : Array; */

		public function replace(p : String, repl : String) : String {}

		/* public function search(p : * = undefined) : int; */

		/* public function slice(start : uint, end : Number = 2147483647) : String; */

		/* public function split(delim : * = undefined, limit : * = 4.294967295E9) : Array; */

		public function substr(start : uint, len : uint = 2147483647) : String { return null; }

		public function substring(start : uint, end : uint = 2147483647) : String { return null; }

		public function toLocaleLowerCase() : String { return null; }

		public function toLocaleUpperCase() : String { return null; }

		public function toLowerCase() : String { return null; }

		public function toString() : String { return null; }

		public function toUpperCase() : String;

		public function valueOf() : String;
	}
}
