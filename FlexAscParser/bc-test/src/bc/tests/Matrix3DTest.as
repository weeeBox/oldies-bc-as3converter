package bc.tests
{
	import asunit.framework.TestCase;

	import bc.flash.geom.Vector3D;
	import bc.flash.geom.Matrix3D;
	import flash.geom.Vector3D;
	import flash.geom.Matrix3D;
	
	/**
	 * @author weee
	 */
	public class Matrix3DTest extends TestCase
	{
		private var bMatrix : bc.flash.geom.Matrix3D = new bc.flash.geom.Matrix3D();
		private var fMatrix : flash.geom.Matrix3D = new flash.geom.Matrix3D();
		
		public function testTranslate() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.appendTranslation(100, 200, 300);
			fMatrix.appendTranslation(100, 200, 300);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testScale() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.appendScale(1.0, 2.0, 3.0);
			fMatrix.appendScale(1.0, 2.0, 3.0);			
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testRotateX() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.appendRotation(30, bc.flash.geom.Vector3D.X_AXIS);
			fMatrix.appendRotation(30, flash.geom.Vector3D.X_AXIS);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testRotateY() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.appendRotation(30, bc.flash.geom.Vector3D.Y_AXIS);
			fMatrix.appendRotation(30, flash.geom.Vector3D.Y_AXIS);

			assertEquals(matrisesEquals(), true);
		}
		
		public function testRotateZ() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.appendRotation(30, bc.flash.geom.Vector3D.Z_AXIS);
			fMatrix.appendRotation(30, flash.geom.Vector3D.Z_AXIS);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function prependTranslate() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.prependTranslation(100, 200, 300);
			fMatrix.prependTranslation(100, 200, 300);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function prependScale() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.prependScale(1.0, 2.0, 3.0);
			fMatrix.prependScale(1.0, 2.0, 3.0);			
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testPrependRotateX() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.prependRotation(30, bc.flash.geom.Vector3D.X_AXIS);
			fMatrix.prependRotation(30, flash.geom.Vector3D.X_AXIS);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testPrependRotateY() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.prependRotation(30, bc.flash.geom.Vector3D.Y_AXIS);
			fMatrix.prependRotation(30, flash.geom.Vector3D.Y_AXIS);

			assertEquals(matrisesEquals(), true);
		}
		
		public function testPrependRotateZ() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			bMatrix.prependRotation(30, bc.flash.geom.Vector3D.Z_AXIS);
			fMatrix.prependRotation(30, flash.geom.Vector3D.Z_AXIS);
			
			assertEquals(matrisesEquals(), true);
		}
		
//		public function testRotateArbitary() : void
//		{
//			bMatrix.identity();
//			fMatrix.identity();
//			bMatrix.appendRotation(20, new bc.flash.geom.Vector3D(10, 7, -1.5));
//			fMatrix.appendRotation(20, new flash.geom.Vector3D(10, 7, -1.5));
//			
//			trace(fMatrix.rawData);
//			trace(bMatrix.rawData);
//			
//			assertEquals(matrisesEquals(), true);
//		}
		
		private function matrisesEquals() : Boolean
		{
			var bRaw : Vector.<Number> = bMatrix.rawData;
			var fRaw : Vector.<Number> = fMatrix.rawData;
			
			for (var i : int = 0; i < 12; ++i)
			{
				if (!equalsEpsilon(bRaw[i], fRaw[i]))
				{
					return false;
				}
			}
			
			return true;
		}
		
		private function equalsEpsilon(a : Number, b : Number) : Boolean
		{
			return Math.abs(a - b) < 0.0000001;
		}
	}
}
