package bc.flash 
{
	import bc.flash.errors.NotImplementedError;
	
	[NoConversion]
	public final class String extends Object 
	{
		public static const Length : int = 1;

		public function String(value : String = "") : void { throw new NotImplementedError(); }
        
		public static function charAt(str : String, i : Number) : String { throw new NotImplementedError(); }

		public static function charCodeAt(str : String, i : Number) : Number { throw new NotImplementedError(); }

		/* public function concat(...args : *) : String; */

		public static function fromCharCode(str : String, ...args : Number) : String
		{
			throw new NotImplementedError();
		}

		public static function indexOf(str : String, s : String, i : Number = 0) : int { throw new NotImplementedError(); }

		public static function lastIndexOf(str : String, s : String, i : Number = 2147483647) : int { throw new NotImplementedError(); }

		/* public function localeCompare(other : * = undefined) : int; */

		/* public function match(p : * = undefined) : Array; */

		public static function replace(str : String, p : String, repl : String) : String { throw new NotImplementedError(); }

		/* public function search(p : * = undefined) : int; */

		public static function slice(str : String, start : Number, end : Number = 2147483647) : String { throw new NotImplementedError(); }

		public static function split(str : String, delim : String) : Array { throw new NotImplementedError(); }

		public static function substr(str : String, start : Number, len : Number = 2147483647) : String { throw new NotImplementedError(); }

		public static function substring(str : String, start : Number, end : Number = 2147483647) : String { throw new NotImplementedError(); }

		public static function toLocaleLowerCase(str : String, ) : String { throw new NotImplementedError(); }

		public static function toLocaleUpperCase(str : String, ) : String { throw new NotImplementedError(); }

		public function toLower() : String { throw new NotImplementedError(); }

		public function ToString() : String { throw new NotImplementedError(); }

		public function toUpper() : String { throw new NotImplementedError(); }

		public static function valueOf(str : String) : String { throw new NotImplementedError(); }
	}
}
