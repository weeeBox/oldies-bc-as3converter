package bc.flash.geom
{
	import bc.flash.error.NotImplementedError;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class Matrix3D extends Object
	{
		private var m11 : Number;
		private var m12 : Number;
		private var m13 : Number;
		private var m21 : Number;
		private var m22 : Number;
		private var m23 : Number;
		private var m31 : Number;
		private var m32 : Number;
		private var m33 : Number;
		
		private var t : Vector3D;
		
		private var mDeterminant : Number;
		
		public function Matrix3D(v:Vector.<Number> = null)
		{
			t = new Vector3D();
			
			if (v != null)
			{
				m11 = v[0];								
				m21 = v[1];								
				m31 = v[2];				
										
				m12 = v[4];								
				m22 = v[5];								
				m32 = v[6];				
												
				m13 = v[8];								
				m23 = v[9];								
				m33 = v[10];
				
				t.x = v[12];								
				t.y = v[13];								
				t.z = v[14];								
			}
			else
			{
				m12 = m13 = m21 = m23 = m31 = m32 = 0.0;
				m11 = m22 = m33 = 1.0;
			}
		}
		
		private function appendValues(m11 : Number, m12 : Number, m13 : Number, tx : Number, m21 : Number, m22 : Number, m23 : Number, ty : Number, m31 : Number, m32 : Number, m33 : Number, tz : Number) : void
		{
			var o11 : Number = this.m11;
			var o12 : Number = this.m12;
			var o13 : Number = this.m13;
			var x : Number = this.t.x;

			var o21 : Number = this.m21;
			var o22 : Number = this.m22;
			var o23 : Number = this.m23;
			var y : Number = this.t.y;

			var o31 : Number = this.m31;
			var o32 : Number = this.m32;
			var o33 : Number = this.m33;
			var z : Number = this.t.z;

			this.m11 = m11 * o11 + m12 * o21 + m13 * o31;
			this.m12 = m11 * o12 + m12 * o22 + m13 * o32;
			this.m13 = m11 * o13 + m12 * o23 + m13 * o33;
			this.t.x = m11 * x + m12 * y + m13 * z + tx;

			this.m21 = m21 * o11 + m22 * o21 + m23 * o31;
			this.m22 = m21 * o12 + m22 * o22 + m23 * o32;
			this.m23 = m21 * o13 + m22 * o23 + m23 * o33;
			this.t.y = m21 * x + m22 * y + m23 * z + ty;

			this.m31 = m31 * o11 + m32 * o21 + m33 * o31;
			this.m32 = m31 * o12 + m32 * o22 + m33 * o32;
			this.m33 = m31 * o13 + m32 * o23 + m33 * o33;
			this.t.z = m31 * x + m32 * y + m33 * z + tz;						
		}
		
		private function prependValues(m11 : Number, m12 : Number, m13 : Number, tx : Number, m21 : Number, m22 : Number, m23 : Number, ty : Number, m31 : Number, m32 : Number, m33 : Number, tz : Number) : void
		{
			var o11 : Number = this.m11;
			var o12 : Number = this.m12;
			var o13 : Number = this.m13;
			var x : Number = this.t.x;

			var o21 : Number = this.m21;
			var o22 : Number = this.m22;
			var o23 : Number = this.m23;
			var y : Number = this.t.y;

			var o31 : Number = this.m31;
			var o32 : Number = this.m32;
			var o33 : Number = this.m33;
			var z : Number = this.t.z;

			this.m11 = o11 * m11 + o12 * m21 + o13 * m31;
			this.m12 = o11 * m12 + o12 * m22 + o13 * m32;
			this.m13 = o11 * m13 + o12 * m23 + o13 * m33;
			this.t.x = o11 * x + o12 * y + o13 * z + tx;

			this.m21 = o21 * m11 + o22 * m21 + o23 * m31;
			this.m22 = o21 * m12 + o22 * m22 + o23 * m32;
			this.m23 = o21 * m13 + o22 * m23 + o23 * m33;
			this.t.y = o21 * x + o22 * y + o23 * z + ty;

			this.m31 = o31 * m11 + o32 * m21 + o33 * m31;
			this.m32 = o31 * m12 + o32 * m22 + o33 * m32;
			this.m33 = o31 * m13 + o32 * m23 + o33 * m33;
			this.t.z = o31 * x + o32 * y + o33 * z + tz;					
		}
		
		public function append(lhs:Matrix3D) : void
		{
			appendValues(lhs.m11, lhs.m12, lhs.m13, lhs.t.x, lhs.m21, lhs.m22, lhs.m23, lhs.t.y, lhs.m31, lhs.m32, lhs.m33, lhs.t.z);
		}
		
		public function appendRotation(degrees:Number, axis:Vector3D, pivotPoint:Vector3D = null) : void
		{
			var hasPivot : Boolean = pivotPoint != null && (pivotPoint.x != 0.0 || pivotPoint.y != 0.0 || pivotPoint.z != 0.0);
			if (hasPivot)
			{
				appendTranslation(-pivotPoint.x, -pivotPoint.y, -pivotPoint.z);
			}
			
			var radians : Number = 0.0055555555555556 * degrees * Math.PI;
			var cosA : Number = Math.cos(radians);
			var sinA : Number = Math.sin(radians);
			
			var ax : Number = axis.x;
			var ay : Number = axis.y;
			var az : Number = axis.z;
			
			if (ax == 0.0 && ay == 0.0 && az == 1.0)
			{
				appendValues(cosA, -sinA, 0, 0, sinA, cosA, 0, 0, 0, 0, 1, 0);				
			}
			else if (ax == 0.0 && ay == 1.0 && az == 0.0)
			{
				appendValues(cosA, 0, sinA, 0, 0, 1, 0, 0, -sinA, 0, cosA, 0);
			}
			else if (ax == 1.0 && ay == 0.0 && az == 0.0)
			{
				appendValues(1, 0, 0, 0, 0, cosA, -sinA, 0, 0, sinA, cosA, 0);
			}
			else
			{
//				var oneMinusCosA : Number = 1 - cosA;
//				var axay : Number = ax * ay;
//				var ayaz : Number = ay * az;
//				var axaz : Number = ax * az;
//				var ax2 : Number = ax * ax;
//				var ay2 : Number = ay * ay;
//				var az2 : Number = az * az;
//				var axSinA : Number = ax * sinA;
//				var aySinA : Number = ay * sinA;
//				var azSinA : Number = az * sinA;
//				var axayOneMinusCosA : Number = axay * oneMinusCosA;
//				var ayazOneMinusCosA : Number = ayaz * oneMinusCosA;
//				var axazOneMinusCosA : Number = axaz * oneMinusCosA;
//				
//				var m11 : Number = ax2 * oneMinusCosA + cosA;
//				var m12 : Number = axayOneMinusCosA - azSinA;
//				var m13 : Number = axazOneMinusCosA + aySinA;
//				
//				var m21 : Number = axayOneMinusCosA + azSinA;
//				var m22 : Number = ay2 * oneMinusCosA + cosA;
//				var m23 : Number = ayazOneMinusCosA - axSinA;
//				
//				var m31 : Number = axazOneMinusCosA - aySinA;
//				var m32 : Number = ayazOneMinusCosA + axSinA;
//				var m33 : Number = az2 * oneMinusCosA + cosA;
//
//				appendValues(m11, m12, m13, 0, m21, m22, m23, 0, m31, m32, m33, 0);
				throw new NotImplementedError(); 
			}
			
			if (hasPivot)
			{
				appendTranslation(pivotPoint.x, pivotPoint.y, pivotPoint.z);
			}
		}
		
		public function appendScale(xScale:Number, yScale:Number, zScale:Number) : void
		{
			appendValues(xScale, 0, 0, 0, 0, yScale, 0, 0, 0, 0, zScale, 0);
		}
		
		public function appendTranslation(x:Number, y:Number, z:Number) : void
		{
			appendValues(1, 0, 0, x, 0, 1, 0, y, 0, 0, 1, z);
		}
		
		public function clone():Matrix3D
		{
			var m : Matrix3D = new Matrix3D();
			m.m11 = m11;
			m.m12 = m12;
			m.m13 = m13;
			m.t.x = t.x;
			
			m.m21 = m21;
			m.m22 = m22;
			m.m23 = m23;
			m.t.y = t.y;
			
			m.m31 = m31;
			m.m32 = m32;
			m.m33 = m33;
			m.t.z = t.z;
			
			m.mDeterminant = mDeterminant;
			
			return m;
		}
		
		/** Copies a Vector3D object into specific column of the calling Matrix3D object. */
		public function copyColumnFrom(column:uint, vector3D : Vector3D) : void
		{
			if (column == 1)
			{
				  m11 = vector3D.x;
				  m21 = vector3D.y;
				  m31 = vector3D.z;
			}
			else if (column == 2)
			{
				  m12 = vector3D.x;
				  m22 = vector3D.y;
				  m32 = vector3D.z;
			}
			else if (column == 3)
			{
				  m13 = vector3D.x;
				  m23 = vector3D.y;
				  m33 = vector3D.z;
			}
			else if (column == 4)
			{
				  t.x = vector3D.x;
				  t.y = vector3D.y;
				  t.z = vector3D.z;
			}
			else
			{
				throw new ArgumentError();
			}
		}
		
		/** Copies specific column of the calling Matrix3D object into the Vector3D object. */
		public function copyColumnTo(column:uint, vector3D:Vector3D) : void
		{
			if (column == 1)
			{
				  vector3D.x = m11;
				  vector3D.y = m21;
				  vector3D.z = m31;
			}
			else if (column == 2)
			{
				  vector3D.x = m12;
				  vector3D.y = m22;
				  vector3D.z = m32;
			}
			else if (column == 3)
			{
				  vector3D.x = m13;
				  vector3D.y = m23;
				  vector3D.z = m33;
			}
			else if (column == 4)
			{
				  vector3D.x = t.x;
				  vector3D.y = t.y;
				  vector3D.z = t.z;
			}
			else
			{
				throw new ArgumentError();
			}
		}
		
		/** Copies all of the matrix data from the source Matrix3D object into the calling Matrix3D object. */
		public function copyFrom(sourceMatrix3D:Matrix3D) : void
		{
			m11 = sourceMatrix3D.m11;
			m12 = sourceMatrix3D.m12;
			m13 = sourceMatrix3D.m13;
			t.x = sourceMatrix3D.t.x;

			m21 = sourceMatrix3D.m21;
			m22 = sourceMatrix3D.m22;
			m23 = sourceMatrix3D.m23;
			t.y = sourceMatrix3D.t.y;

			m31 = sourceMatrix3D.m31;
			m32 = sourceMatrix3D.m32;
			m33 = sourceMatrix3D.m33;
			t.z = sourceMatrix3D.t.z;						
		}
		
		/** Copies all of the vector data from the source vector object into the calling Matrix3D object. The optional index parameter allows you to select any starting slot in the vector. */
		public function copyRawDataFrom(vector:Vector.<Number>, index:uint = 0, transpose:Boolean = false) : void
		{
			if (transpose)
			{
				throw new NotImplementedError();
			}
			
			if (index == 0)
			{
				m11 = vector[0];
				m21 = vector[1];
				m31 = vector[2];

				m12 = vector[4];
				m22 = vector[5];
				m32 = vector[6];

				m13 = vector[8];
				m23 = vector[9];
				m33 = vector[10];

				t.x = vector[12];
				t.y = vector[13];
				t.z = vector[14];
			}
			else
			{
				if (index + 14 >= vector.length)
				{
					throw new ArgumentError();
				}
				
				m11 = vector[index];
				m21 = vector[index + 1];
				m31 = vector[index + 2];

				m12 = vector[index + 4];
				m22 = vector[index + 5];
				m32 = vector[index + 6];

				m13 = vector[index + 8];
				m23 = vector[index + 9];
				m33 = vector[index + 10];

				t.x = vector[index + 12];
				t.y = vector[index + 13];
				t.z = vector[index + 14];
			}
		}
		
		/** Copies all of the matrix data from the calling Matrix3D object into the provided vector. The optional index parameter allows you to select any target starting slot in the vector. */
		public function copyRawDataTo(vector:Vector.<Number>, index:uint = 0, transpose:Boolean = false) : void
		{
			if (transpose)
			{
				throw new NotImplementedError();
			}
			
			if (index == 0)
			{
				vector[0] = m11;
				vector[1] = m21;
				vector[2] = m31;

				vector[4] = m12;
				vector[5] = m22;
				vector[6] = m32;

				vector[8] = m13;
				vector[9] = m23;
				vector[10] = m33;

				vector[12] = t.x;
				vector[13] = t.y;
				vector[14] = t.z;
			}
			else
			{
				vector[index] = m11;
				vector[index + 1] = m21;
				vector[index + 2] = m31;

				vector[index + 4] = m12;
				vector[index + 5] = m22;
				vector[index + 6] = m32;

				vector[index + 8] = m13;
				vector[index + 9] = m23;
				vector[index + 10] = m33;

				vector[index + 12] = t.x;
				vector[index + 13] = t.y;
				vector[index + 14] = t.z;
			}
		}
		
		/** Copies a Vector3D object into specific row of the calling Matrix3D object. */
		public function copyRowFrom(row:uint, vector3D:Vector3D):void
		{
			if (row == 1)
			{
				m11 = vector3D.x;
				m12 = vector3D.y;
				m13 = vector3D.z;
				t.x = vector3D.w;
			}
			else if (row == 2)
			{
				m21 = vector3D.x;
				m22 = vector3D.y;
				m23 = vector3D.z;
				t.y = vector3D.w;
			}
			else if (row == 3)
			{
				m31 = vector3D.x;
				m32 = vector3D.y;
				m33 = vector3D.z;
				t.z = vector3D.w;
			}
			else
			{
				throw new RangeError();
			}
		}
		
		/** Copies specific row of the calling Matrix3D object into the Vector3D object. */
		public function copyRowTo(row:uint, vector3D:Vector3D) : void
		{
			if (row == 1)
			{
				vector3D.x = m11;
				vector3D.y = m12;
				vector3D.z = m13;
				vector3D.w = t.x;
			}
			else if (row == 2)
			{
				vector3D.x = m21;
				vector3D.y = m22;
				vector3D.z = m23;
				vector3D.w = t.y;
			}
			else if (row == 3)
			{
				vector3D.x = m31;
				vector3D.y = m32;
				vector3D.z = m33;
				vector3D.w = t.z;
			}
			else
			{
				throw new RangeError();
			}
		}
		
		public function copyToMatrix3D(dest:Matrix3D) : void
		{
			dest.m11 = m11;
			dest.m12 = m12;
			dest.m13 = m13;

			dest.m21 = m21;
			dest.m22 = m22;
			dest.m23 = m23;

			dest.m31 = m31;
			dest.m32 = m32;
			dest.m33 = m33;
			
			dest.t.x = t.x;
			dest.t.y = t.y;
			dest.t.z = t.z;
		}
		
		/** Returns the transformation matrix's translation, rotation, and scale settings as a Vector of three Vector3D objects. 
		 * The first Vector3D object holds the translation elements. 
		 * The second Vector3D object holds the rotation elements. 
		 * The third Vector3D object holds the scale elements. */
		public function decompose(orientationStyle:String = "eulerAngles") : Vector.<Vector3D>
		{
			throw new NotImplementedError();			
		}
		
		/** Uses the transformation matrix without its translation elements to transform a Vector3D object from one space coordinate to another. */
		public function deltaTransformVector(v:Vector3D) : Vector3D
		{
			var x : Number = v.x;
			var y : Number = v.y;
			var z : Number = v.z;
			return new Vector3D(m11*x + m12*y + m13*z, m21*x + m22*y + m23*z, m31*x + m32*y + m33*z);
		}
		
		public function identity():void
		{
			m12 = m13 = m21 = m23 = m31 = m32 = 0.0;
			m11 = m22 = m33 = 1.0;
			t.x = t.y = t.z = 0.0;
		}
		
		/** Interpolates the translation, rotation, and scale transformation of one matrix toward those of the target matrix. */
		public static function interpolate(thisMat:Matrix3D, toMat:Matrix3D, percent:Number):Matrix3D
		{
			throw new NotImplementedError();			
		}
		
		/** nterpolates this matrix towards the translation, rotation, and scale transformations of the target matrix. */
		public function interpolateTo(toMat:Matrix3D, percent:Number) : void
		{
			throw new NotImplementedError();
		}
		
		/** Inverts the current matrix. */
		public function invert() : Boolean
		{
			throw new NotImplementedError();	
		}
		
		/** Rotates the display object so that it faces a specified position. */
		public function pointAt(pos:Vector3D, at:Vector3D = null, up:Vector3D = null) : void
		{
			throw new NotImplementedError();			
		}
		
		/** Prepends a matrix by multiplying the current Matrix3D object by another Matrix3D object. */
		public function prepend(rhs:Matrix3D) : void
		{
			prependValues(rhs.m11, rhs.m12, rhs.m13, rhs.t.x, rhs.m21, rhs.m22, rhs.m23, rhs.t.y, rhs.m31, rhs.m32, rhs.m33, rhs.t.z);
		}
		
		public function prependRotation(degrees:Number, axis:Vector3D, pivotPoint:Vector3D = null) : void
		{
			var hasPivot : Boolean = pivotPoint != null && (pivotPoint.x != 0.0 || pivotPoint.y != 0.0 || pivotPoint.z != 0.0);
			if (hasPivot)
			{
				prependTranslation(-pivotPoint.x, -pivotPoint.y, -pivotPoint.z);
			}
			
			var radians : Number = 0.0055555555555556 * degrees * Math.PI;
			var cosA : Number = Math.cos(radians);
			var sinA : Number = Math.sin(radians);
			
			var ax : Number = axis.x;
			var ay : Number = axis.y;
			var az : Number = axis.z;
			
			if (ax == 0.0 && ay == 0.0 && az == 1.0)
			{
				prependValues(cosA, -sinA, 0, 0, sinA, cosA, 0, 0, 0, 0, 1, 0);				
			}
			else if (ax == 0.0 && ay == 1.0 && az == 0.0)
			{
				prependValues(cosA, 0, sinA, 0, 0, 1, 0, 0, -sinA, 0, cosA, 0);
			}
			else if (ax == 1.0 && ay == 0.0 && az == 0.0)
			{
				prependValues(1, 0, 0, 0, 0, cosA, -sinA, 0, 0, sinA, cosA, 0);
			}
			else
			{
//				var oneMinusCosA : Number = 1 - cosA;
//				var axay : Number = ax * ay;
//				var ayaz : Number = ay * az;
//				var axaz : Number = ax * az;
//				var ax2 : Number = ax * ax;
//				var ay2 : Number = ay * ay;
//				var az2 : Number = az * az;
//				var axSinA : Number = ax * sinA;
//				var aySinA : Number = ay * sinA;
//				var azSinA : Number = az * sinA;
//				var axayOneMinusCosA : Number = axay * oneMinusCosA;
//				var ayazOneMinusCosA : Number = ayaz * oneMinusCosA;
//				var axazOneMinusCosA : Number = axaz * oneMinusCosA;
//				
//				var m11 : Number = ax2 * oneMinusCosA + cosA;
//				var m12 : Number = axayOneMinusCosA - azSinA;
//				var m13 : Number = axazOneMinusCosA + aySinA;
//				
//				var m21 : Number = axayOneMinusCosA + azSinA;
//				var m22 : Number = ay2 * oneMinusCosA + cosA;
//				var m23 : Number = ayazOneMinusCosA - axSinA;
//				
//				var m31 : Number = axazOneMinusCosA - aySinA;
//				var m32 : Number = ayazOneMinusCosA + axSinA;
//				var m33 : Number = az2 * oneMinusCosA + cosA;
//
//				prependValues(m11, m12, m13, 0, m21, m22, m23, 0, m31, m32, m33, 0);
				throw new NotImplementedError(); 
			}
			
			if (hasPivot)
			{
				prependTranslation(pivotPoint.x, pivotPoint.y, pivotPoint.z);
			}
		}
		
		public function prependScale(xScale:Number, yScale:Number, zScale:Number) : void
		{
			prependValues(xScale, 0, 0, 0, 0, yScale, 0, 0, 0, 0, zScale, 0);
		}
		
		public function prependTranslation(x:Number, y:Number, z:Number) : void
		{
			prependValues(1, 0, 0, x, 0, 1, 0, y, 0, 0, 1, z);
		}
		
		/** Sets the transformation matrix's translation, rotation, and scale settings. */
		public function recompose(components:Vector.<Vector3D>, orientationStyle:String = "eulerAngles"):Boolean
		{
			throw new NotImplementedError();
		}
		
		/** Uses the transformation matrix to transform a Vector3D object from one space coordinate to another. */ 
		public function transformVector(v:Vector3D) : Vector3D
		{
			var x : Number = v.x;
			var y : Number = v.y;
			var z : Number = v.z;
			return new Vector3D(m11 * x + m12 * y + m13 * z + t.x, m21 * x + m22 * y + m23 * z + t.y, m31 * x + m32 * y + m33 * z + t.z);
		}
		
		/** Uses the transformation matrix to transform a Vector of Numbers from one coordinate space to another. */
		public function transformVectors(vin:Vector.<Number>, vout:Vector.<Number>) : void
		{
			var len : int = vin.length;
			if ((len % 3) != 0)
			{
				throw new ArgumentError();
			}
			
			if (len > vout.length)
			{
				throw new ArgumentError();
			}
			
			for (var i : int = 0; i < len; i += 3)
			{			
				var x : Number = vin[i];
				var y : Number = vin[i + 1];
				var z : Number = vin[i + 2];
				vout[i] = m11 * x + m12 * y + m13 * z + t.x;
				vout[i + 1] = m21 * x + m22 * y + m23 * z + t.y;
				vout[i + 2] = m31 * x + m32 * y + m33 * z + t.z;
			}			
		}
		
		/** Converts the current Matrix3D object to a matrix where the rows and columns are swapped. */
		public function transpose() : void
		{
			throw new NotImplementedError();			
		}		
		
		public function get determinant() : Number
		{
			throw new NotImplementedError();
		}
		
		public function get position() : Vector3D
		{
			return t;			
		}
		
		public function get rawData() : Vector.<Number>
		{
			return Vector.<Number>([m11, m21, m31, 0, m12, m22, m32, 0, m13, m23, m33, 0, t.x, t.y, t.z, 1]);
		}
	}
}
