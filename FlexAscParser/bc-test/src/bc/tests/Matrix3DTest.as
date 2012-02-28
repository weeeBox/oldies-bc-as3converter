package bc.tests
{
	import asunit.framework.TestCase;

	import bc.flash.geom.Matrix3D;
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
			bMatrix.appendTranslation(100, 200, 300);
			fMatrix.appendTranslation(100, 200, 300);
			
			assertEquals(matrisesEquals(), true);
		}
		
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
