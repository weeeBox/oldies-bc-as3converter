package bc.flash.geom
{
	import bc.flash.Math;
	public class Matrix extends Object
	{
		private static const U : int = 0;
		private static const V : int = 0;
		private static const W : int = 1;
		public var a : Number;
		public var b : Number;
		public var c : Number;
		public var d : Number;
		public var tx : Number;
		public var ty : Number;

		public function Matrix(a : Number = 1, b : Number = 0, c : Number = 0, d : Number = 1, tx : Number = 0, ty : Number = 0) : void
		{
			setValues(a, b, c, d, tx, ty);
		}

		private function setValues(a : Number, b : Number, c : Number, d : Number, tx : Number, ty : Number) : void
		{
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.tx = tx;
			this.ty = ty;
		}

		public function clone() : Matrix
		{
			return new Matrix(a, b, c, d, tx, ty);
		}

		public function concat(m : Matrix) : void
		{
			concatValues(m.a, m.b, m.c, m.d, m.tx, m.ty);
		}
		
		public function concatValues(a : Number, b : Number, c : Number, d : Number, tx : Number, ty : Number) : void
		{
			setValues(a * this.a + c * this.b, 
					  b * this.a + d * this.b, 
					  a * this.c + c * this.d, 
					  b * this.c + d * this.d,
					  a * this.tx + c * this.ty + tx * W, 
					  b * this.tx + d * this.ty + ty * W);
		}

		public function createBox(scaleX : Number, scaleY : Number, rotation : Number = 0, tx : Number = 0, ty : Number = 0) : void
		{
		}

		public function createGradientBox(width : Number, height : Number, rotation : Number = 0, tx : Number = 0, ty : Number = 0) : void
		{
		}

		public function deltaTransformPoint(point : Point) : Point
		{
			return null;
		}

		public function identity() : void
		{
			setValues(1, 0, 0, 1, 0, 0);
		}

		public function invert() : void
		{
			var det : Number = determinant();
			setValues(d / det, -b / det, -c / det, a / det, (c * ty - d * tx) / det, (b * tx - a * ty) / det);
		}

		public function rotate(angle : Number) : void
		{
			var cosA : Number = Math.cos(angle);
			var sinA : Number = Math.sin(angle);
			concatValues(cosA, sinA, -sinA, cosA, 0, 0);			
		}

		public function scale(sx : Number, sy : Number) : void
		{
			a *= sx;
			b *= sy;
			c *= sx;
			d *= sy;
			tx *= sx;
			ty *= sy;
		}

		public function translate(dx : Number, dy : Number) : void
		{
			tx += dx;
			ty += dy;
		}

		/* public function toString() : String { throw new NotImplementedError(); } */
		public function transformPoint(point : Point) : Point
		{
			return new Point(a * point.x + c * point.y + tx, b * point.x + d * point.y + ty);
		}

		private function determinant() : Number
		{
			return a * d - c * b;
		}
	}
}
