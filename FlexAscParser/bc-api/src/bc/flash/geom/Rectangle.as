package bc.flash.geom
{
	import flash.geom.Rectangle;
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

		public function get bottom() : Number
		{
			return y + height;
		}

		public function set bottom(value : Number) : void
		{
			throw new NotImplementedError();
		}

		public function get bottomRight() : Point
		{
			return new Point(x + width, y + height);
		}

		public function set bottomRight(value : Point) : void
		{
			throw new NotImplementedError();
		}

		public function clone() : Rectangle
		{
			return new Rectangle(x, y, width, height);
		}

		public function contains(x : Number, y : Number) : Boolean
		{
			throw new NotImplementedError();
		}

		public function containsPoint(point : Point) : Boolean
		{
			throw new NotImplementedError();
		}

		public function containsRect(rect : Rectangle) : Boolean
		{
			throw new NotImplementedError();
		}

		public function equals(toCompare : Rectangle) : Boolean
		{
			return x == toCompare.x && y == toCompare.y && width == toCompare.width && height == toCompare.height;
		}

		public function inflate(dx : Number, dy : Number) : void
		{
			throw new NotImplementedError();
		}

		public function inflatePoint(point : Point) : void
		{
			throw new NotImplementedError();
		}

		public function intersection(toIntersect : Rectangle) : Rectangle
		{
			throw new NotImplementedError();
		}

		public function intersects(toIntersect : Rectangle) : Boolean
		{
			throw new NotImplementedError();
		}

		public function isEmpty() : Boolean
		{
			throw new NotImplementedError();
		}

		public function get left() : Number
		{
			return x;
		}

		public function set left(value : Number) : void
		{
			x = value;
		}

		public function offset(dx : Number, dy : Number) : void
		{
			throw new NotImplementedError();
		}

		public function offsetPoint(point : Point) : void
		{
			throw new NotImplementedError();
		}

		public function get right() : Number
		{
			return x + width;
		}

		public function set right(value : Number) : void
		{
			throw new NotImplementedError();
		}

		public function setEmpty() : void
		{
			throw new NotImplementedError();
		}

		public function get size() : Point
		{
			return new Point(width, height);
		}

		public function set size(value : Point) : void
		{
			width = value.x;
			height = value.y;
		}

		public function get top() : Number
		{
			return y;
		}

		public function set top(value : Number) : void
		{
			y = value;
		}

		public function get topLeft() : Point
		{
			return new Point(x, y);
		}

		public function set topLeft(value : Point) : void
		{
			x = value.x;
			y = value.y;
		}

		public function union(toUnion : Rectangle) : Rectangle
		{
			throw new NotImplementedError();
		}
		
		public function toString() : String 
		{
			return "(x=" + x + ", y=" + y + ", w=" + width + ", h=" + height + ")";
		}
	}
}
