package bc.flash.xml
{
	import bc.flash.errors.AbstractClassError;
	import bc.flash.errors.NotImplementedError;

	[ConvertOnce]
	public class XML extends Object
	{
		private var mParent : bc.flash.xml.XML;
		private var mName : String;
		
		/* public static const length : uint = 1; */
		
		public function XML(name : String = null)
		{
			mName = name;
		}
		
		/* public function addNamespace(ns : *) : XML; */
		public function appendChild(child : bc.flash.xml.XML) : bc.flash.xml.XML
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

		public function attributes() : bc.flash.xml.XMLList
		{
			return null;
		}

		public function child(propertyName : String) : bc.flash.xml.XMLList
		{
			return null;
		}

		public function childIndex() : int
		{
			throw new NotImplementedError();
		}

		public function children() : bc.flash.xml.XMLList
		{
			return null;			 
		}

		public function comments() : bc.flash.xml.XMLList
		{
			throw new NotImplementedError();
		}

		public function contains(value : String) : Boolean
		{
			return false;
		}

		public function copy() : bc.flash.xml.XML
		{
			throw new NotImplementedError();
		}

		/* static public function defaultSettings() : Object; */
		/* public function descendants(name : * = "*") : XMLList; */
		
		public function elements(name : String = "*") : bc.flash.xml.XMLList
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
		public function parent() : bc.flash.xml.XML
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
		
		public function hasOwnProperty(name : String) : Boolean
		{
			return false;
		}
		
		/* public function toString() : String; */

		/* public function toXMLString() : String; */

		/* public function valueOf() : XML; */
	}
}
