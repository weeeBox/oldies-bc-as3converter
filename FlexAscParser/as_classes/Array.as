public dynamic class Array extends Object
{
	public static const CASEINSENSITIVE : uint = 1;
	public static const length : int = 1;
	public static const NUMERIC : uint = 16;
	public static const UNIQUESORT : uint = 4;
	public static const RETURNINDEXEDARRAY : uint = 8;
	public static const DESCENDING : uint = 2;

	concat(arg : Object) : Array;

	//every(callback : Function, thisObject : * = null) : Boolean;

	//filter(callback : Function, thisObject : * = null) : Array;

	//forEach(callback : Function, thisObject : * = null) : void;

	indexOf(searchElement : Object) : int;

	indexOf(searchElement : Object, fromIndex : int) : int;

	join(sep : String) : String;

	lastIndexOf(searchElement : Object) : int;

	lastIndexOf(searchElement : Object, fromIndex : int) : int;

	get length() : uint;

	set length(newLength : uint) : void;

	// map(callback : Function, thisObject : * = null) : Array;

	pop() : Object;

	push(arg : Object) : uint;

	reverse() : Array;

	// shift() : Object;

	// slice(A : * = 0, B : * = 4.294967295E9) : Array;

	// some(callback : Function, thisObject : * = null) : Boolean;

	// sort(...args : *) : *;

	// sortOn(names : *, options : * = 0, ...args : *) : *;

	// splice(...args : *) : *;

	// public function toLocaleString() : String;

	// public function toString() : String;

	// unshift(...args : *) : uint;
}