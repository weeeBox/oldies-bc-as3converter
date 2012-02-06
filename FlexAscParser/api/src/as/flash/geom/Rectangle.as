package flash.geom 
{
	import flash.geom.Point;
	
	public class Rectangle extends Object 
	{
		public var x : Number;
		public var y : Number;
		public var width : Number;
		public var height : Number;

		function Rectangle(x : Number = 0, y : Number = 0, width : Number = 0, height : Number = 0) : void { }

		public function get bottom() : Number { return 0; }

		public function set bottom(value : Number) : void { }

		public function get bottomRight() : Point { return null; }

		public function set bottomRight(value : Point) : void { }

		public function clone() : Rectangle { return null; }

		public function contains(x : Number, y : Number) : Boolean { return false; }

		public function containsPoint(point : Point) : Boolean { return false; }

		public function containsRect(rect : Rectangle) : Boolean { return false; }

		public function equals(toCompare : Rectangle) : Boolean { return false; }

		public function inflate(dx : Number, dy : Number) : void { }

		public function inflatePoint(point : Point) : void { }

		public function intersection(toIntersect : Rectangle) : Rectangle { return null; }

		public function intersects(toIntersect : Rectangle) : Boolean { return false; }

		public function isEmpty() : Boolean { return false; }

		public function get left() : Number { return 0; }

		public function set left(value : Number) : void { }

		public function offset(dx : Number, dy : Number) : void { }

		public function offsetPoint(point : Point) : void { }

		public function get right() : Number { return 0; }

		public function set right(value : Number) : void { }

		public function setEmpty() : void { }

		public function get size() : Point { return null; }

		public function set size(value : Point) : void { }

		public function get top() : Number { return 0; }

		public function set top(value : Number) : void { }

		public function get topLeft() : Point { return null; }

		public function set topLeft(value : Point) : void { }

		public function union(toUnion : Rectangle) : Rectangle { return null; }
	}
}
