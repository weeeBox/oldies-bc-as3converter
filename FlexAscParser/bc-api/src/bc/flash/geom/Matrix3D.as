package bc.flash.geom
{
	import bc.flash.error.NotImplementedError;

	/**
	 * @author weee
	 */
	public class Matrix3D extends Object
	{
		public var n11 : Number;
		public var n12 : Number;
		public var n13 : Number;
		public var n14 : Number;
		public var n21 : Number;
		public var n22 : Number;
		public var n23 : Number;
		public var n24 : Number;
		public var n31 : Number;
		public var n32 : Number;
		public var n33 : Number;
		public var n34 : Number;
		public var n41 : Number;
		public var n42 : Number;
		public var n43 : Number;
		public var n44 : Number;

		public function Matrix3D(v : Vector.<Number> = null) : void
		{
		}

		private function setData(n11 : Number, n12 : Number, n13 : Number, n14 : Number, n21 : Number, n22 : Number, n23 : Number, n24 : Number, n31 : Number, n32 : Number, n33 : Number, n34 : Number, n41 : Number, n42 : Number, n43 : Number, n44 : Number) : void
		{
			this.n11 = n11; this.n12 = n12; this.n13 = n13; this.n14 = n14;
			this.n21 = n21; this.n22 = n22; this.n23 = n23; this.n24 = n24;
			this.n31 = n31; this.n32 = n32; this.n33 = n33; this.n34 = n34;
			this.n41 = n41;	this.n42 = n42; this.n43 = n43; this.n44 = n44; 	
		}

		public function append(m : Matrix3D) : Matrix3D
		{
			const o11 : Number = n11;
			const o12 : Number = n12;
			const o13 : Number = n13;
			const o14 : Number = n14;
			const o21 : Number = n21;
			const o22 : Number = n22;
			const o23 : Number = n23;
			const o24 : Number = n24;
			const o31 : Number = n31;
			const o32 : Number = n32;
			const o33 : Number = n33;
			const o34 : Number = n34;
			const o41 : Number = n41;
			const o42 : Number = n42;
			const o43 : Number = n43;
			const o44 : Number = n44;

			n11 = m.n11 * o11 + m.n12 * o21 + m.n13 * o31 + m.n14 * o41;
			n12 = m.n11 * o12 + m.n12 * o22 + m.n13 * o32 + m.n14 * o42;
			n13 = m.n11 * o13 + m.n12 * o23 + m.n13 * o33 + m.n14 * o43;
			n14 = m.n11 * o14 + m.n12 * o24 + m.n13 * o34 + m.n14 * o44;
			n21 = m.n21 * o11 + m.n22 * o21 + m.n23 * o31 + m.n24 * o41;
			n22 = m.n21 * o12 + m.n22 * o22 + m.n23 * o32 + m.n24 * o42;
			n23 = m.n21 * o13 + m.n22 * o23 + m.n23 * o33 + m.n24 * o43;
			n24 = m.n21 * o14 + m.n22 * o24 + m.n23 * o34 + m.n24 * o44;
			n31 = m.n31 * o11 + m.n32 * o21 + m.n33 * o31 + m.n34 * o41;
			n32 = m.n31 * o12 + m.n32 * o22 + m.n33 * o32 + m.n34 * o42;
			n33 = m.n31 * o13 + m.n32 * o23 + m.n33 * o33 + m.n34 * o43;
			n34 = m.n31 * o14 + m.n32 * o24 + m.n33 * o34 + m.n34 * o44;
			n41 = m.n41 * o11 + m.n42 * o21 + m.n43 * o31 + m.n44 * o41;
			n42 = m.n41 * o12 + m.n42 * o22 + m.n43 * o32 + m.n44 * o42;
			n43 = m.n41 * o13 + m.n42 * o23 + m.n43 * o33 + m.n44 * o43;
			n44 = m.n41 * o14 + m.n42 * o24 + m.n43 * o34 + m.n44 * o44;

			return this;
		}

		public function appendRotation(degrees : Number, axis : Vector3D, pivotPoint : Vector3D = null) : void
		{
			throw new NotImplementedError();
		}

		public function appendScale(xScale : Number, yScale : Number, zScale : Number) : void
		{
			throw new NotImplementedError();
		}

		public function appendTranslation(x : Number, y : Number, z : Number) : void
		{
			throw new NotImplementedError();
		}

		public function clone() : Matrix3D
		{
			throw new NotImplementedError();
		}

		/* [API("674")] */
		public function copyColumnFrom(column : uint, vector3D : Vector3D) : void
		{
			throw new NotImplementedError();
		}

		/* [API("674")] */
		public function copyColumnTo(column : uint, vector3D : Vector3D) : void
		{
			throw new NotImplementedError();
		}

		/* [API("674")] */
		public function copyFrom(sourceMatrix3D : Matrix3D) : void
		{
			throw new NotImplementedError();
		}

		/* [API("674")] */
		public function copyRawDataFrom(vector : Vector.<Number>, index : uint = 0, transpose : Boolean = false) : void
		{
			throw new NotImplementedError();
		}

		/* [API("674")] */
		public function copyRawDataTo(vector : Vector.<Number>, index : uint = 0, transpose : Boolean = false) : void
		{
			throw new NotImplementedError();
		}

		/* [API("674")] */
		public function copyRowFrom(row : uint, vector3D : Vector3D) : void
		{
			throw new NotImplementedError();
		}

		/* [API("674")] */
		public function copyRowTo(row : uint, vector3D : Vector3D) : void
		{
			throw new NotImplementedError();
		}

		public function copyToMatrix3D(dest : Matrix3D) : void
		{
			throw new NotImplementedError();
		}

		public function decompose(orientationStyle : String = "eulerAngles") : Vector.<Vector3D>
		{
			throw new NotImplementedError();
		}

		public function deltaTransformVector(v : Vector3D) : Vector3D
		{
			throw new NotImplementedError();
		}

		public function get determinant() : Number
		{
			throw new NotImplementedError();
		}

		public function identity() : void
		{
			throw new NotImplementedError();
		}

		public static function interpolate(thisMat : Matrix3D, toMat : Matrix3D, percent : Number) : Matrix3D
		{
			throw new NotImplementedError();
		}

		public function interpolateTo(toMat : Matrix3D, percent : Number) : void
		{
			throw new NotImplementedError();
		}

		public function invert() : Boolean
		{
			throw new NotImplementedError();
		}

		public function pointAt(pos : Vector3D, at : Vector3D = null, up : Vector3D = null) : void
		{
			throw new NotImplementedError();
		}

		public function get position() : Vector3D
		{
			throw new NotImplementedError();
		}

		public function set position(pos : Vector3D) : void
		{
			throw new NotImplementedError();
		}

		public function prepend(rhs : Matrix3D) : void
		{
			throw new NotImplementedError();
		}

		public function prependRotation(degrees : Number, axis : Vector3D, pivotPoint : Vector3D = null) : void
		{
			throw new NotImplementedError();
		}

		public function prependScale(xScale : Number, yScale : Number, zScale : Number) : void
		{
			throw new NotImplementedError();
		}

		public function prependTranslation(x : Number, y : Number, z : Number) : void
		{
			throw new NotImplementedError();
		}

		public function get rawData() : Vector.<Number>
		{
			throw new NotImplementedError();
		}

		public function set rawData(v : Vector.<Number>) : void
		{
			throw new NotImplementedError();
		}

		public function recompose(components : Vector.<Vector3D>, orientationStyle : String = "eulerAngles") : Boolean
		{
			throw new NotImplementedError();
		}

		public function transformVector(v : Vector3D) : Vector3D
		{
			throw new NotImplementedError();
		}

		public function transformVectors(vin : Vector.<Number>, vout : Vector.<Number>) : void
		{
			throw new NotImplementedError();
		}

		public function transpose() : void
		{
			throw new NotImplementedError();
		}
	}
}
