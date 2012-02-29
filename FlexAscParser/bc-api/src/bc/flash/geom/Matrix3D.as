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
		
		public function append(lhs:Matrix3D) : void
		{
			appendValues(lhs.m11, lhs.m12, lhs.m13, lhs.t.x, lhs.m21, lhs.m22, lhs.m23, lhs.t.y, lhs.m31, lhs.m32, lhs.m33, lhs.t.z);
		}
		
		public function appendRotation(degrees:Number, axis:Vector3D, pivotPoint:Vector3D = null) : void
		{
			var hasPivot : Boolean = pivotPoint != null && pivotPoint.x != 0.0 && pivotPoint.y != 0.0 && pivotPoint.z != 0.0;
			if (hasPivot)
			{
				appendTranslation(pivotPoint.x, pivotPoint.y, pivotPoint.z);
			}
			
			var cosA : Number = Math.cos(0.0055555555555556 * degrees * Math.PI);
			var sinA : Number = Math.sin(0.0055555555555556 * degrees * Math.PI);
			
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
				appendTranslation(-pivotPoint.x, -pivotPoint.y, -pivotPoint.z);
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
		
		public function identity():void
		{
			m12 = m13 = m21 = m23 = m31 = m32 = 0.0;
			m11 = m22 = m33 = 1.0;
			t.x = t.y = t.z = 0.0;
		}
		
		public function get determinant() : Number
		{
			return mDeterminant;
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
