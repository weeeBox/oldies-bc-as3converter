package bc.flash 
{
	import bc.flash.errors.NotImplementedError;
	
	[NoConversion]
	public final class String extends Object 
	{	
		public function String(value : String = "") : void { throw new NotImplementedError(); }
		public function get length():int { throw new NotImplementedError(); }
		public function charAt(index:Number = 0):String { throw new NotImplementedError(); }
		public function charCodeAt(index:Number = 0):Number { throw new NotImplementedError(); }
		public function concat(... args):String { throw new NotImplementedError(); }
		public static function fromCharCode(... charCodes):String { throw new NotImplementedError(); }
		public function indexOf(val:String, startIndex:Number = 0):int { throw new NotImplementedError(); }
		public function lastIndexOf(val:String, startIndex:Number = 0x7FFFFFFF):int { throw new NotImplementedError(); }
		public function localeCompare(other:String, ... values):int { throw new NotImplementedError(); }
		public function match(pattern:*):Array { throw new NotImplementedError(); }
		public function replace(pattern:*, repl:Object):String { throw new NotImplementedError(); }
		public function search(pattern:*):int { throw new NotImplementedError(); }
		public function slice(startIndex:Number = 0, endIndex:Number = 0x7fffffff):String { throw new NotImplementedError(); }
		public function split(delimiter:*, limit:Number = 0x7fffffff):Array { throw new NotImplementedError(); }
		public function substr(startIndex:Number = 0, len:Number = 0x7fffffff):String { throw new NotImplementedError(); }
		public function substring(startIndex:Number = 0, endIndex:Number = 0x7fffffff):String { throw new NotImplementedError(); }
		public function toLocaleLowerCase():String { throw new NotImplementedError(); }
		public function toLocaleUpperCase():String { throw new NotImplementedError(); }
		public function toLowerCase():String { throw new NotImplementedError(); }
		public function toUpperCase():String { throw new NotImplementedError(); }
		public function valueOf():String { throw new NotImplementedError(); }
	}
}
