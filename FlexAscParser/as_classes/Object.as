public dynamic class Object
{
	public static var prototype : Object;
	public var constructor : Object;
	public static const length : int = 1;

	protected static function _dontEnumPrototype(proto : Object) : void;

	protected static function _setPropertyIsEnumerable(o : Object, v : String, enumerable : Boolean) : void;

	AS3 function hasOwnProperty(v : String) : Boolean;

	AS3 function isPrototypeOf(v : String) : Boolean;

	AS3 function propertyIsEnumerable(v : String) : Boolean;
}