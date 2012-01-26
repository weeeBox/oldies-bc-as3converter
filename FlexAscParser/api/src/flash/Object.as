package flash 
{
	public class Object 
	{
		public static var prototype : Object;
		public var constructor : Object;
		public static const length : int = 1;

		/* protected static function _dontEnumPrototype(proto : Object) : void; */

		/* protected static function _setPropertyIsEnumerable(o : *, V : String, enumerable : Boolean) : void; */

		public function hasOwnProperty(name : String) : Boolean { return false; }
		
		public virtual function toString() : String { return null; }

		/* internal static function init() : *; */

		/* AS3 function isPrototypeOf(V : * = undefined) : Boolean; */

		/* AS3 function propertyIsEnumerable(V : * = undefined) : Boolean; */
	}
}
