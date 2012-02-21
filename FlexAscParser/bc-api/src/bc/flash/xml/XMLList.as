package bc.flash.xml
{
	import bc.flash.Vector;
	import bc.flash.error.NotImplementedError;

	[ConvertOnce]
	public class XMLList
	{
		private var mElements : Vector.<bc.flash.xml.XML>;
		
		/* public static const length : uint = 1; */
		/* function XMLList(value : * = undefined) : void; */
		/* public function addNamespace(ns : *) : XML; */
		
		public function XMLList()
		{
			mElements = new Vector.<XML>(0);				 
		}
		
		public function appendChild(child : bc.flash.xml.XML) : bc.flash.xml.XML
		{
			mElements.push(child);
			return child;
		}
		
		public function containsChild(name : String) : Boolean
		{
			for each (var element : XML in mElements)
			{
				if (name == element.name())
				{
					return true;
				}
			}
			return false;
		}
		
		public function list() : Vector.<bc.flash.xml.XML>
		{
			return mElements;
		}

		public function attribute(arg : String) : bc.flash.xml.XMLList
		{
			throw new NotImplementedError();
		}

		public function attributes() : bc.flash.xml.XMLList
		{
			throw new NotImplementedError();
		}

		public function child(propertyName : String) : bc.flash.xml.XMLList
		{
			throw new NotImplementedError();
		}

		public function childIndex() : int
		{
			throw new NotImplementedError();
		}

		public function children() : bc.flash.xml.XMLList
		{
			throw new NotImplementedError();
		}

		/* public function comments() : XMLList; */
		/* public function contains(value : *) : Boolean; */
		
		public function copy() : bc.flash.xml.XMLList
		{
			throw new NotImplementedError();
		}

		/* public function descendants(name : * = "*") : XMLList; */
		/* public function elements(name : String = "*") : XMLList; */
		/* public function hasComplexContent() : Boolean; */
		
		public function hasOwnProperty(name : String) : Boolean
		{
			throw new NotImplementedError();
		}

		/* public function hasSimpleContent() : Boolean; */
		/* public function inScopeNamespaces() : Array; */
		/* public function insertChildAfter(child1 : *, child2 : *) : *; */
		/* public function insertChildBefore(child1 : *, child2 : *) : *; */
		
		public function length() : int
		{
			return mElements.length;
		}

		/* public function localName() : Object; */
		
		public function name() : String
		{
			throw new NotImplementedError();
		}

		/* public function namespace(prefix : * = null) : *; */
		/* public function namespaceDeclarations() : Array; */
		
		public function nodeKind() : String
		{
			throw new NotImplementedError();
		}
		
		/* public function normalize() : XMLList; */

		/* public function parent() : *; */

		/* public function prependChild(value : *) : XML; */

		/* public function processingInstructions(name : * = "*") : XMLList; */

		/* public function propertyIsEnumerable(P : * = undefined) : Boolean; */

		/* public function removeNamespace(ns : *) : XML; */

		/* public function replace(propertyName : *, value : *) : XML; */

		/* public function setChildren(value : *) : XML; */

		/* public function setLocalName(name : *) : void; */

		/* public function setName(name : *) : void; */

		/* public function setNamespace(ns : *) : void; */

		/* public function text() : XMLList; */

		/* public function toString() : String; */

		/* public function toXMLString() : String; */

		/* public function valueOf() : XMLList; */
	}
}
