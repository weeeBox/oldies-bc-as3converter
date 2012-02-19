package bc.flash.geom
{
	import bc.flash.error.IllegalOperationError;
	import bc.flash.error.NotImplementedError;

	/**
	 * @author weee
	 */
	public class Vector3D extends Object
	{
		public static const X_AXIS : Vector3D;
		public static const Y_AXIS : Vector3D;
		public static const Z_AXIS : Vector3D;
		public var x : Number;
		public var y : Number;
		public var z : Number;
		public var w : Number;

		public function Vector3D(x : Number = 0.0, y : Number = 0.0, z : Number = 0.0, w : Number = 0.0) : void
		{
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
		}

		public function add(a : Vector3D) : Vector3D
		{
			return new Vector3D(x + a.x, y + a.y, z + a.z);
		}

		public static function angleBetween(a : Vector3D, b : Vector3D) : Number
		{
			implementMe();
		}

		public function clone() : Vector3D
		{
			return new Vector3D(x, y, z, w);
		}

		/* [API("674")] */
		public function copyFrom(sourceVector3D : Vector3D) : void
		{
			x = sourceVector3D.x;
			y = sourceVector3D.y;
			z = sourceVector3D.z;
			w = sourceVector3D.w;
		}

		public function crossProduct(a : Vector3D) : Vector3D
		{
			implementMe();
		}

		public function decrementBy(a : Vector3D) : void
		{
			x -= a.x;
			y -= a.y;
			z -= a.z;
		}

		public static function distance(pt1 : Vector3D, pt2 : Vector3D) : Number
		{
			var dx : Number = pt1.x - pt2.x;
			var dy : Number = pt1.y - pt2.y;
			var dz : Number = pt1.z - pt2.z;

			return Math.sqrt(dx * dx + dy * dy + dz * dz);
		}

		public function dotProduct(a : Vector3D) : Number
		{
			return x * a.x + y * a.y + z * a.z;
		}

		public function equals(toCompare : Vector3D, allFour : Boolean = false) : Boolean
		{
			return x == toCompare.x && 
				   y == toCompare.y && 
				   z == toCompare.z && 
				   (!allFour || w == toCompare.w);
		}

		public function incrementBy(a : Vector3D) : void
		{
			x += a.x;
			y += a.y;
			z += a.z;
		}

		public function get length() : Number
		{
			return Math.sqrt(x * x + y * y + z * z);
		}

		public function get lengthSquared() : Number
		{
			return x * x + y * y + z * z;
		}

		public function nearEquals(toCompare : Vector3D, tolerance : Number, allFour : Boolean = false) : Boolean
		{
			return Math.abs(x - toCompare.x) <= tolerance && 
				   Math.abs(y - toCompare.y) <= tolerance && 
				   Math.abs(z - toCompare.z) <= tolerance && 
				   (!allFour || Math.abs(w - toCompare.w) <= tolerance);
		}

		public function negate() : void
		{
			x = -x;
			y = -y;
			z = -z;
		}

		public function normalize() : Number
		{
			var len : Number = length;
			if (len == 0)
			{
				throw new IllegalOperationError("Unable to normalize vector with zero lenght");
			}
			var lenInv : Number = 1.0 / length;
			x *= lenInv;
			y *= lenInv;
			z *= lenInv;
			
			implementMe();
		}

		public function project() : void
		{
			implementMe();
		}

		public function scaleBy(s : Number) : void
		{
			x *= s;
			y *= s;
			z *= s;
		}

		/* [API("674")] */
		public function setTo(xa : Number, ya : Number, za : Number) : void
		{
			x = xa;
			y = ya;
			z = za;
		}

		public function subtract(a : Vector3D) : Vector3D
		{
			return new Vector3D(x - a.x, y - a.y, z - a.z);
		}

		public function toString() : String
		{
			return "[" + x + ", " + y + ", " + z + "]";
		}
	}
}
