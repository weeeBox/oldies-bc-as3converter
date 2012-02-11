package bc.flash.geom
{
	/**
	 * @author weee
	 */
	public class Matrix3D extends Object
	{
		function Matrix3D(v : Vector.<Number> = null) : void
		{
			
		}

		public function append(lhs : Matrix3D) : void
		{
			
		}

		public function appendRotation(degrees : Number, axis : Vector3D, pivotPoint : Vector3D = null) : void
		{
			
		}

		public function appendScale(xScale : Number, yScale : Number, zScale : Number) : void
		{
			
		}

		public function appendTranslation(x : Number, y : Number, z : Number) : void
		{
			
		}

		public function clone() : Matrix3D
		{
			
		}

		/* [API("674")] */
		/* public function copyColumnFrom(column : uint, vector3D : Vector3D) : void; */

		/* [API("674")] */
		/* public function copyColumnTo(column : uint, vector3D : Vector3D) : void; */

		/* [API("674")] */
		/* public function copyFrom(sourceMatrix3D : Matrix3D) : void; */

		[API("674")]
		public function copyRawDataFrom(vector : Vector.<Number>, index : uint = 0, transpose : Boolean = false) : void;

		[API("674")]
		public function copyRawDataTo(vector : Vector.<Number>, index : uint = 0, transpose : Boolean = false) : void;

		[API("674")]
		public function copyRowFrom(row : uint, vector3D : Vector3D) : void;

		[API("674")]
		public function copyRowTo(row : uint, vector3D : Vector3D) : void;

		public function copyToMatrix3D(dest : Matrix3D) : void;

		public function decompose(orientationStyle : String = "eulerAngles") : Vector.<Vector3D>;

		public function deltaTransformVector(v : Vector3D) : Vector3D;

		public function get determinant() : Number;

		public function identity() : void;

		public static function interpolate(thisMat : Matrix3D, toMat : Matrix3D, percent : Number) : Matrix3D;

		public function interpolateTo(toMat : Matrix3D, percent : Number) : void
		{
			
		}

		public function invert() : Boolean
		{
			
		}

		public function pointAt(pos : Vector3D, at : Vector3D = null, up : Vector3D = null) : void
		{
			
		}

		public function get position() : Vector3D
		{
			
		}

		public function set position(pos : Vector3D) : void
		{
			
		}

		public function prepend(rhs : Matrix3D) : void
		{
			
		}

		public function prependRotation(degrees : Number, axis : Vector3D, pivotPoint : Vector3D = null) : void
		{
			
		}

		public function prependScale(xScale : Number, yScale : Number, zScale : Number) : void
		{
			
		}

		public function prependTranslation(x : Number, y : Number, z : Number) : void
		{
			
		}

		public function get rawData() : Vector.<Number>
		{
			
		}

		public function set rawData(v : Vector.<Number>) : void
		{
			
		}

		public function recompose(components : Vector.<Vector3D>, orientationStyle : String = "eulerAngles") : Boolean
		{
			
		}

		public function transformVector(v : Vector3D) : Vector3D
		{
			
		}

		public function transformVectors(vin : Vector.<Number>, vout : Vector.<Number>) : void
		{
			
		}

		public function transpose() : void
		{
			
		}
	}
}
