package _as_.flash.geom 
{
	public class Point extends Object 
	{
		public var x : Number;
		public var y : Number;

		function Point(x : Number = 0, y : Number = 0) : void { }

		public function add(v : Point) : Point { return null; }

		public function clone() : Point { return null; }

		public static function distance(pt1 : Point, pt2 : Point) : Number { return 0; }

		public function equals(toCompare : Point) : Boolean { return false; }

		public static function interpolate(pt1 : Point, pt2 : Point, f : Number) : Point { return null; }

		public function get length() : Number { return 0; }

		public function normalize(thickness : Number) : void { }

		public function offset(dx : Number, dy : Number) : void { }

		public static function polar(len : Number, angle : Number) : Point { return null; }

		public function subtract(v : Point) : Point { return null; }
	}
}
