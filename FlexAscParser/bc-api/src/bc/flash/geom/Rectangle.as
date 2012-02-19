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

		public function get bottom() : Number { throw new NotImplementedError(); }

		public function set bottom(value : Number) : void { throw new NotImplementedError(); }

		public function get bottomRight() : Point { throw new NotImplementedError(); }

		public function set bottomRight(value : Point) : void { throw new NotImplementedError(); }

		public function clone() : Rectangle { throw new NotImplementedError(); }

		public function contains(x : Number, y : Number) : Boolean { throw new NotImplementedError(); }

		public function containsPoint(point : Point) : Boolean { throw new NotImplementedError(); }

		public function containsRect(rect : Rectangle) : Boolean { throw new NotImplementedError(); }

		public function equals(toCompare : Rectangle) : Boolean { throw new NotImplementedError(); }

		public function inflate(dx : Number, dy : Number) : void { throw new NotImplementedError(); }

		public function inflatePoint(point : Point) : void { throw new NotImplementedError(); }

		public function intersection(toIntersect : Rectangle) : Rectangle { throw new NotImplementedError(); }

		public function intersects(toIntersect : Rectangle) : Boolean { throw new NotImplementedError(); }

		public function isEmpty() : Boolean { throw new NotImplementedError(); }

		public function get left() : Number { throw new NotImplementedError(); }

		public function set left(value : Number) : void { throw new NotImplementedError(); }

		public function offset(dx : Number, dy : Number) : void { throw new NotImplementedError(); }

		public function offsetPoint(point : Point) : void { throw new NotImplementedError(); }

		public function get right() : Number { throw new NotImplementedError(); }

		public function set right(value : Number) : void { throw new NotImplementedError(); }

		public function setEmpty() : void { throw new NotImplementedError(); }

		public function get size() : Point { throw new NotImplementedError(); }

		public function set size(value : Point) : void { throw new NotImplementedError(); }

		public function get top() : Number { throw new NotImplementedError(); }

		public function set top(value : Number) : void { throw new NotImplementedError(); }

		public function get topLeft() : Point { throw new NotImplementedError(); }

		public function set topLeft(value : Point) : void { throw new NotImplementedError(); }

		public function union(toUnion : Rectangle) : Rectangle { throw new NotImplementedError(); }
	}
}
