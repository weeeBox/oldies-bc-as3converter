package bc.flash.geom
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.utils.MathHelper;
	import flash.errors.IllegalOperationError;

	/**
	 * @author weee
	 */
	public class Vector3D extends Object
	{
		public static const X_AXIS : Vector3D = new Vector3D(1, 0, 0);
		public static const Y_AXIS : Vector3D = new Vector3D(0, 1, 0);
		public static const Z_AXIS : Vector3D = new Vector3D(0, 0, 1);
		
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
			var aLen : Number = a.length;
			if (epsilonEquals(aLen, 0))
			{
				throw new ArgumentError();
			}
			
			var bLen : Number = b.length;
			if (epsilonEquals(bLen, 0))
			{
				throw new ArgumentError();
			}
			
			var dotProd : Number = a.x * b.x + a.y * b.y + a.z * b.z;
			if (epsilonEquals(dotProd, 0))
			{
				return 0.5 * Math.PI;
			}
			
			return Math.acos(dotProd / aLen / bLen); 			
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
			return new Vector3D(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x, 1);
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
			
			return length;
		}

		public function project() : void
		{
			throw new NotImplementedError();
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
		
		private static function epsilonEquals(a : Number, b : Number) : Boolean
		{
			return MathHelper.epsilonEquals(a, b);
		}
	}
}
