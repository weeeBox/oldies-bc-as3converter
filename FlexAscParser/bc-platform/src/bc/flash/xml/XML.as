package bc.flash.xml
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.error.AbstractClassError;

	[NoConversion]
	public class XML extends Object
	{
		private var mParent : XML;
		private var mName : String;
		
		/* public static const length : uint = 1; */
		
		public function XML(name : String = null)
		{
			mName = name;
		}
		
		/* public function addNamespace(ns : *) : XML; */
		public function appendChild(child : XML) : XML
		{		
			return child;	
		}
		
		public function attributeValue(name : String) : String
		{
			return null;
		}

		public function attribute(name : String) : String
		{
			return attributeValue(name);
		}

		public function attributes() : XMLList
		{
			return null;
		}

		public function child(propertyName : String) : XMLList
		{
			return null;
		}

		public function childIndex() : int
		{
			throw new NotImplementedError();
		}

		public function children() : XMLList
		{
			return null;			 
		}

		public function comments() : XMLList
		{
			throw new NotImplementedError();
		}

		public function contains(value : String) : Boolean
		{
			return false;
		}

		public function copy() : XML
		{
			throw new NotImplementedError();
		}

		/* static public function defaultSettings() : Object; */
		/* public function descendants(name : * = "*") : XMLList; */
		
		public function elements(name : String = "*") : XMLList
		{
			return null;
		}

		/* public function hasComplexContent() : Boolean; */
		/* public function hasOwnProperty(name : String) : Boolean { throw new AbstractClassError(); } */
		/* public function hasSimpleContent() : Boolean; */
		/* public static function get ignoreComments() : Boolean; */
		/* public static function set ignoreComments(newIgnore : Boolean) : *; */
		/* public static function get ignoreProcessingInstructions() : Boolean; */
		/* public static function set ignoreProcessingInstructions(newIgnore : Boolean) : *; */
		/* public static function get ignoreWhitespace() : Boolean; */
		/* public static function set ignoreWhitespace(newIgnore : Boolean) : *; */
		/* public function inScopeNamespaces() : Array; */
		/* public function insertChildAfter(child1 : *, child2 : *) : *; */
		/* public function insertChildBefore(child1 : *, child2 : *) : *; */
		public function length() : int
		{
			return 1;
		}

		/* public function localName() : *; */
		public function name() : String
		{
			return mName;
		}

		/* public function namespace(prefix : * = null) : *; */
		/* public function namespaceDeclarations() : Array; */
		public function nodeKind() : String
		{
			throw new AbstractClassError();
		}

		/* public function normalize() : XML; */
		/* public function notification() : Function; */
		public function parent() : XML
		{
			return mParent;
		}

		/* public function prependChild(value : *) : XML; */
		/* public static function get prettyIndent() : int; */
		/* public static function set prettyIndent(newIndent : int) : *; */
		/* public static function get prettyPrinting() : Boolean; */
		/* public static function set prettyPrinting(newPretty : Boolean) : *; */
		/* public function processingInstructions(name : * = "*") : XMLList; */
		/* public function propertyIsEnumerable(P : * = undefined) : Boolean; */
		/* public function removeNamespace(ns : *) : XML; */
		/* public function replace(propertyName : *, value : *) : XML; */
		/* public function setChildren(value : *) : XML; */
		/* public function setLocalName(name : *) : void; */
		/* public function setName(name : *) : void; */
		/* public function setNamespace(ns : *) : void; */
		/* public function setNotification(f : Function) : *; */
		/* static public function setSettings(o : Object = null) : void; */
		/* static public function settings() : Object; */
		
		public function text() : String
		{
			return null;
		}
		
		/* public function toString() : String; */

		/* public function toXMLString() : String; */

		/* public function valueOf() : XML; */
	}
}
