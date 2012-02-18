package bc.flash.xml
{
	import bc.flash.error.NotImplementedError;

	[ConvertOnce]
	public class XMLList
	{
		/* public static const length : uint = 1; */
		/* function XMLList(value : * = undefined) : void; */
		/* public function addNamespace(ns : *) : XML; */
		
		public function appendChild(child : XML) : XML
		{
			throw new NotImplementedError();
		}
		
		public function list() : Object
		{
			throw new NotImplementedError();
		}

		public function attribute(arg : String) : XMLList
		{
			throw new NotImplementedError();
		}

		public function attributes() : bc.flash.xml.XMLList
		{
			throw new NotImplementedError();
		}

		public function child(propertyName : String) : XMLList
		{
			throw new NotImplementedError();
		}

		public function childIndex() : int
		{
			throw new NotImplementedError();
		}

		public function children() : XMLList
		{
			throw new NotImplementedError();
		}

		/* public function comments() : XMLList; */
		/* public function contains(value : *) : Boolean; */
		
		public function copy() : XMLList
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
			throw new NotImplementedError();
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
