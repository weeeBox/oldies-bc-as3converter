package bc.flash
{
	[ConvertOnce]
	public class Object
	{
		private var mProperties : Dictionary;
		
		/* public static var prototype : Object; */
		/* public var constructor : Object; */

		/* public static const length : int = 1; */
		
		/* protected static function _dontEnumPrototype(proto : Object) : void; */
		
		/* protected static function _setPropertyIsEnumerable(o : *, V : String, enumerable : Boolean) : void; */
		
		public function hasOwnProperty(name : String) : Boolean
		{
			if (mProperties != null)
			{
				return mProperties[name] != null;
			}
			return false;
		}

        public function getOwnProperty(name : String) : Object
        {
            if (hasOwnProperty(name))
			{
				return mProperties[name];
			}
			return null;
        }

        public function setOwnProperty(name : String, value : Object) : void
        {
            if (mProperties == null)
			{
				mProperties = new Dictionary();
			}
			mProperties[name] = value;
        }

        public function deleteOwnProperty(name : String) : void
        {
            if (mProperties != null)
			{
				delete mProperties[name];
			}
        }

		public function __function(name:String) : Function
		{
			return null;
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
