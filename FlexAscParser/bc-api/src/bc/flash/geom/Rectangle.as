package bc.flash.geom 
{
	import bc.flash.error.NotImplementedError;
	
	public class Rectangle extends Object 
	{
		public var x : Number;
		public var y : Number;
		public var width : Number;
		public var height : Number;

		public function Rectangle(x : Number = 0, y : Number = 0, width : Number = 0, height : Number = 0) : void 
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public function get bottom() : Number { implementMe(); }

		public function set bottom(value : Number) : void { implementMe(); }

		public function get bottomRight() : Point { implementMe(); }

		public function set bottomRight(value : Point) : void { implementMe(); }

		public function clone() : Rectangle { implementMe(); }

		public function contains(x : Number, y : Number) : Boolean { implementMe(); }

		public function containsPoint(point : Point) : Boolean { implementMe(); }

		public function containsRect(rect : Rectangle) : Boolean { implementMe(); }

		public function equals(toCompare : Rectangle) : Boolean { implementMe(); }

		public function inflate(dx : Number, dy : Number) : void { implementMe(); }

		public function inflatePoint(point : Point) : void { implementMe(); }

		public function intersection(toIntersect : Rectangle) : Rectangle { implementMe(); }

		public function intersects(toIntersect : Rectangle) : Boolean { implementMe(); }

		public function isEmpty() : Boolean { implementMe(); }

		public function get left() : Number { implementMe(); }

		public function set left(value : Number) : void { implementMe(); }

		public function offset(dx : Number, dy : Number) : void { implementMe(); }

		public function offsetPoint(point : Point) : void { implementMe(); }

		public function get right() : Number { implementMe(); }

		public function set right(value : Number) : void { implementMe(); }

		public function setEmpty() : void { implementMe(); }

		public function get size() : Point { implementMe(); }

		public function set size(value : Point) : void { implementMe(); }

		public function get top() : Number { implementMe(); }

		public function set top(value : Number) : void { implementMe(); }

		public function get topLeft() : Point { implementMe(); }

		public function set topLeft(value : Point) : void { implementMe(); }

		public function union(toUnion : Rectangle) : Rectangle { implementMe(); }
	}
}
