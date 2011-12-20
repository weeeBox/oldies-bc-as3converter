public final class String extends Object
{
	charAt(i : Number) : String;

	charCodeAt(i : Number) : Number;

	concat(arg : Object) : String;	

	indexOf(s : String) : int;

	indexOf(s : String, i : Number) : int;

	lastIndexOf(s : String) : int;

	lastIndexOf(s : String, i : Number) : int;

	get length() : int;

	replace(p : String, repl : String) : String;

	search(p : String) : int;

	slice(start : Number) : String;
	
	slice(start : Number, end : Number) : String;

	split(delim : String) : Array;

	split(delim : String, limit : int) : Array;

	substr(start : Number) : String;

	substr(start : Number, len : int) : String;
	
	substring(start : Number) : String;

	substring(start : Number, end : Number) : String;

	toLocaleLowerCase() : String;

	toLocaleUpperCase() : String;

	toLowerCase() : String;

	toString() : String;

	toUpperCase() : String;

	valueOf() : String;
}