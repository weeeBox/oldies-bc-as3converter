package bc.flash.geom
{
	import bc.flash.Vector;
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
				m12 = v[1];								
				m13 = v[2];
				t.x = v[3];
										
				m21 = v[4];								
				m22 = v[5];								
				m23 = v[6];
				t.y = v[7];
												
				m31 = v[8];								
				m32 = v[9];								
				m33 = v[10];
				t.z = v[11];								
			}
			else
			{
				m11 = m22 = m33 = 1.0;
			}
		}
		
		private function appendValues(m11 : Number, m12 : Number, m13 : Number, tx : Number, m21 : Number, m22 : Number, m23 : Number, ty : Number, m31 : Number, m32 : Number, m33 : Number, tz : Number) : void
		{
			this.m11 = m11 * this.m11 + m12 * this.m21 + m13 * this.m31;						
			this.m12 = m11 * this.m12 + m12 * this.m22 + m13 * this.m32;						
			this.m13 = m11 * this.m13 + m12 * this.m23 + m13 * this.m33;
			this.t.x = m11 * this.t.x + m12 * this.t.y + m13 * this.t.z + tx;
			
			this.m21 = m21 * this.m11 + m22 * this.m21 + m23 * this.m31;						
			this.m22 = m21 * this.m12 + m22 * this.m22 + m23 * this.m32;						
			this.m23 = m21 * this.m13 + m22 * this.m23 + m23 * this.m33;						
			this.t.y = m21 * this.t.x + m22 * this.t.y + m23 * this.t.z + ty;
									
			this.m31 = m31 * this.m11 + m32 * this.m21 + m33 * this.m31;						
			this.m32 = m31 * this.m12 + m32 * this.m22 + m33 * this.m32;						
			this.m33 = m31 * this.m13 + m32 * this.m23 + m33 * this.m33;						
			this.t.z = m31 * this.t.x + m32 * this.t.y + m33 * this.t.z + tz;						
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
			
			var ax : Number = axis.x;
			var ay : Number = axis.y;
			var az : Number = axis.z;
			if (ax == 0.0 && ay == 0.0 && az == 1.0)
			{
				
			}
			else if (ax == 0.0 && ay == 0.1 && az == 0.0)
			{
				
			}
			else if (ax == 0.1 && ay == 0.0 && az == 0.0)
			{
				
			}
			else
			{
				var cosA : Number = Math.cos(degrees);
				var sinA : Number = Math.sin(degrees);
				var oneMinusCosA : Number = 1 - cosA;
				var axay : Number = ax * ay;
				var ayaz : Number = ay * az;
				var axaz : Number = ax * az;
				var ax2 : Number = ax * ax;
				var ay2 : Number = ay * ay;
				var az2 : Number = az * az;
				var axSinA : Number = ax * sinA;
				var aySinA : Number = ay * sinA;
				var azSinA : Number = az * sinA;
				var axayOneMinusCosA : Number = axay * oneMinusCosA;
				var ayazOneMinusCosA : Number = ayaz * oneMinusCosA;
				var axazOneMinusCosA : Number = axaz * oneMinusCosA;
				
				var m11 : Number = ax2 * oneMinusCosA + cosA;
				var m12 : Number = axayOneMinusCosA - azSinA;
				var m13 : Number = axazOneMinusCosA + aySinA;
				
				var m21 : Number = axayOneMinusCosA + azSinA;
				var m22 : Number = ay2 * oneMinusCosA + cosA;
				var m23 : Number = ayazOneMinusCosA - axSinA;
				
				var m31 : Number = axazOneMinusCosA - aySinA;
				var m32 : Number = ayazOneMinusCosA + axSinA;
				var m33 : Number = az2 * oneMinusCosA + cosA;
				
				appendValues(m11, m12, m13, 0, m21, m22, m23, 0, m31, m32, m33, 0); 
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
			throw new NotImplementedError();
		}
	}
}
