public dynamic final class Vector extends Object
{
	public var fixed : Boolean;
	public var length : uint;

	// public function Vector(length : uint = 0, fixed : Boolean = false);

	public function concat(arg : Object) : Vector;

	// public function every(callback : Function, thisObject : Object = null) : Boolean;

	// public function filter(callback : Function, thisObject : Object = null) : Vector;

	// public function forEach(callback : Function, thisObject : Object = null);

	public function indexOf(searchElement : Object, fromIndex : int = 0) : int;

	public function join(sep : String = ",") : String;

	public function lastIndexOf(searchElement : Object, fromIndex : int = 0x7fffffff) : int;

	// public function map(callback : Function, thisObject : Object = null) : Vector;

	public function pop() : Object;

	public function push(arg : Object) : uint;

	public function reverse() : Vector;

	public function shift() : Object;

	// public function slice(startIndex : int = 0, endIndex : int = 16777215) : Vector;

	// public function some(callback : Function, thisObject : Object = null) : Boolean;

	public function sort(compareFunction : Object) : Vector;

	public function splice(startIndex : int, deleteCount : uint) : Vector;

	public function toLocaleString() : String;

	public function toString() : String;

	// public function unshift(...args : *) : uint;
}