public class XML extends Object
{
	attribute(arg : String) : XMLList;
	attributes() : XMLList;
	child(propertyName : String) : XMLList;
	childIndex() : int;
	children() : XMLList;
	comments() : XMLList;
	contains(value : String) : Boolean;
	copy() : XML;
	descendants() : XMLList;
	descendants(name : String) : XMLList;
	elements() : XMLList;
	elements(name : String) : XMLList;
	hasComplexContent() : Boolean;
	hasOwnProperty(p : String) : Boolean;
	hasSimpleContent() : Boolean;	
	length() : int;	
	nodeKind() : String;
	parent() : Object;
	text() : XMLList;
	toString() : String;
	toXMLString() : String;
	valueOf() : XML;
}