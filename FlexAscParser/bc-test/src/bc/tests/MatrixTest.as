package bc.tests
{
	import asunit.framework.TestCase;
	import bc.flash.geom.Matrix;
	import bc.flash.utils.MathHelper;
	import flash.geom.Matrix;
	
	
	public class MatrixTest extends TestCase
	{
		private var bMatrix : bc.flash.geom.Matrix = new bc.flash.geom.Matrix();
		private var fMatrix : flash.geom.Matrix = new flash.geom.Matrix();
			
		public function testEquals() : void
		{
			assertEquals(matrisesEquals(), true);
		}
			
		public function testRotate() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			
			bMatrix.rotate(30);
			fMatrix.rotate(30);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testScale() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			
			bMatrix.scale(1.5, 0.7);
			fMatrix.scale(1.5, 0.7);
			assertEquals(matrisesEquals(), true);
		}
		
		public function testTranslate() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			
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
		
		public function testConcat() : void
		{
			var bBox : bc.flash.geom.Matrix = new bc.flash.geom.Matrix();
			var fBox : flash.geom.Matrix = new flash.geom.Matrix();
			
			bBox.createBox(1.5, 0.7, 30, 20, -30);
			fBox.createBox(1.5, 0.7, 30, 20, -30);
			
			bMatrix.identity();
			fMatrix.identity();
			
			bMatrix.translate(-10, -20);
			fMatrix.translate(-10, -20);
			
			bMatrix.concat(bBox);
			fMatrix.concat(fBox);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testConcat2() : void
		{
			bMatrix.identity();
			fMatrix.identity();
			
			bMatrix.createBox(1.5, 0.7, 30, 20, -30);
			fMatrix.createBox(1.5, 0.7, 30, 20, -30);
			
			bMatrix.concat(bMatrix);
			fMatrix.concat(fMatrix);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testInvert() : void
		{
			fMatrix.identity();
			bMatrix.identity();
			
			fMatrix.translate(-12, -45);
			fMatrix.scale(1.1, -0.5);
			fMatrix.rotate(30);
			fMatrix.translate(50, -14);
			
			bMatrix.translate(-12, -45);
			bMatrix.scale(1.1, -0.5);
			bMatrix.rotate(30);
			bMatrix.translate(50, -14);
			
			var bOrig : bc.flash.geom.Matrix = bMatrix.clone();
			var fOrig : flash.geom.Matrix = fMatrix.clone();
			
			bOrig.invert();
			fOrig.invert();
			
			bMatrix.concat(bOrig);
			fMatrix.concat(fOrig);
			
			assertEquals(matrisesEquals(), true);
		}
		
		public function testInvert2() : void
		{
			fMatrix.identity();
			bMatrix.identity();
			
			fMatrix.scale(1.1, 1.1);			
			bMatrix.scale(1.1, 1.1);			
			fMatrix.translate(-12, -45);			
			bMatrix.translate(-12, -45);

			fMatrix.rotate(30);
			bMatrix.rotate(30);
			
			var bOrig : bc.flash.geom.Matrix = bMatrix.clone();
			var fOrig : flash.geom.Matrix = fMatrix.clone();
			
			bOrig.invert();
			fOrig.invert();
			
			bMatrix.concat(bOrig);
			fMatrix.concat(fOrig);
			
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
			return MathHelper.epsilonEquals(a, b);
		}
	}
}
