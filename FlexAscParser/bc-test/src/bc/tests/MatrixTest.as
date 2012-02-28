package bc.tests
{
	import bc.flash.geom.Matrix;
	import flash.geom.Matrix;
	
	import asunit.framework.TestCase;
	
	public class MatrixTest extends TestCase
	{
		private var bcMatrix : bc.flash.geom.Matrix = new bc.flash.geom.Matrix();
		private var flashMatrix : flash.geom.Matrix = new flash.geom.Matrix();
			
		public function testInit() : void
		{
			assertEquals(matrisesEquals(), true);
		}
			
		public function testRotate() : void
		{
			bcMatrix.rotate(73);
			flashMatrix.rotate(73);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testScale() : void
		{
			bcMatrix.scale(1.5, 0.7);
			flashMatrix.scale(1.5, 0.7);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testTranslate() : void
		{
			bcMatrix.translate(20, -10);
			flashMatrix.translate(20, -10);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testBox() : void
		{
			bcMatrix.createBox(1.5, 0.7, 30, 20, -30);
			flashMatrix.createBox(1.5, 0.7, 30, 20, -30);
			assertEquals(matrisesEquals(), true);
		}
		
		private function matrisesEquals() : Boolean
		{
			return bcMatrix.a == flashMatrix.a && bcMatrix.b == flashMatrix.b && bcMatrix.c == flashMatrix.c && bcMatrix.d == flashMatrix.d && bcMatrix.tx == flashMatrix.tx && bcMatrix.ty == flashMatrix.ty;
		}
	}
}
