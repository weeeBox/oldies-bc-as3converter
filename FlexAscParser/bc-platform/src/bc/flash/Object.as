package bc.flash
{
	[NoConversion]
	public class Object
	{
		private var properties : Dictionary;
		
		/* public static var prototype : Object; */
		/* public var constructor : Object; */

		/* public static const length : int = 1; */
		
		/* protected static function _dontEnumPrototype(proto : Object) : void; */
		
		/* protected static function _setPropertyIsEnumerable(o : *, V : String, enumerable : Boolean) : void; */
		
		public function hasOwnProperty(name : String) : Boolean
		{
			if (properties != null)
			{
				return properties[name] != null;
			}
			return false;
		}

        public function getOwnProperty(name : String) : Object
        {
            if (hasOwnProperty(name))
			{
				return properties[name];
			}
			return null;
        }

        public function setOwnProperty(name : String, value : Object) : void
        {
            if (properties == null)
			{
				properties = new Dictionary();
			}
			properties[name] = value;
        }

        public function deleteOwnProperty(name : String) : void
        {
            if (properties != null)
			{
				delete properties[name];
			}
        }

		public virtual function toString() : String
		{
			return "Object";
		}
		/* internal static function init() : *; */

		/* AS3 function isPrototypeOf(V : * = undefined) : Boolean; */

		/* AS3 function propertyIsEnumerable(V : * = undefined) : Boolean; */
	}
}
