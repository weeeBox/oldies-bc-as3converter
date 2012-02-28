package bc.tests
{
	import bc.flash.geom.Matrix;
	import flash.geom.Matrix;
	
	import asunit.framework.TestCase;
	
	public class MatrixTest extends TestCase
	{
		private var bMatrix : bc.flash.geom.Matrix = new bc.flash.geom.Matrix();
		private var fMatrix : flash.geom.Matrix = new flash.geom.Matrix();
			
		public function test1() : void
		{
			assertEquals(matrisesEquals(), true);
		}
			
		public function test2() : void
		{
			bMatrix.rotate(73);
			fMatrix.rotate(73);
			assertEquals(matrisesEquals(), true);
		}
		
		public function test3() : void
		{
			bMatrix.scale(1.5, 0.7);
			fMatrix.scale(1.5, 0.7);
			assertEquals(matrisesEquals(), true);
		}
		
		public function test4() : void
		{
			bMatrix.translate(20, -10);
			fMatrix.translate(20, -10);
			assertEquals(matrisesEquals(), true);
		}
		
		public function test5() : void
		{
			bMatrix.createBox(1.5, 0.7, 30, 20, -30);
			fMatrix.createBox(1.5, 0.7, 30, 20, -30);
			assertEquals(matrisesEquals(), true);
		}
		
		public function test6() : void
		{
			bMatrix.createBox(1.5, 0.7, 30, 20, -30);
			fMatrix.createBox(1.5, 0.7, 30, 20, -30);
			
			bMatrix.concat(bMatrix);
			fMatrix.concat(fMatrix);
			assertEquals(matrisesEquals(), true);
		}
		
		private function matrisesEquals() : Boolean
		{
			return equalsEpsilon(bMatrix.a, fMatrix.a) && 
				equalsEpsilon(bMatrix.b, fMatrix.b) && 
				equalsEpsilon(bMatrix.c, fMatrix.c) && 
				equalsEpsilon(bMatrix.d, fMatrix.d) && 
				equalsEpsilon(bMatrix.tx, fMatrix.tx) && 
				equalsEpsilon(bMatrix.ty, fMatrix.ty);
		}
		
		private function equalsEpsilon(a : Number, b : Number) : Boolean
		{
			return Math.abs(a - b) < 0.0000001;
		}
	}
}
