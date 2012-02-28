package bc.tests
{
	import bc.flash.geom.Matrix;
	import flash.geom.Matrix;
	
	import asunit.framework.TestCase;
	
	public class MatrixTest extends TestCase
	{
		private var bMatrix : bc.flash.geom.Matrix = new bc.flash.geom.Matrix();
		private var fMatrix : flash.geom.Matrix = new flash.geom.Matrix();
			
		public function testInit() : void
		{
			assertEquals(matrisesEquals(), true);
		}
			
		public function testRotate() : void
		{
			bMatrix.rotate(73);
			fMatrix.rotate(73);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testScale() : void
		{
			bMatrix.scale(1.5, 0.7);
			fMatrix.scale(1.5, 0.7);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testTranslate() : void
		{
			bMatrix.translate(20, -10);
			fMatrix.translate(20, -10);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testBox() : void
		{
			bMatrix.createBox(1.5, 0.7, 30, 20, -30);
			fMatrix.createBox(1.5, 0.7, 30, 20, -30);
			assertEquals(matrisesEquals(), true);
		}
		
		private function matrisesEquals() : Boolean
		{
			return bMatrix.a == fMatrix.a && bMatrix.b == fMatrix.b && bMatrix.c == fMatrix.c && bMatrix.d == fMatrix.d && bMatrix.tx == fMatrix.tx && bMatrix.ty == fMatrix.ty;
		}
	}
}
