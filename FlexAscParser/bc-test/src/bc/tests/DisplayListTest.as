package bc.tests
{
	import flash.geom.Matrix;
	import bc.flash.geom.Matrix;
	import flash.display.Bitmap;
	
	import flash.display.BitmapData;
	import bc.flash.display.Bitmap;
		
	import asunit.framework.TestCase;
	/**
	 * @author weee
	 */
	public class DisplayListTest extends TestCase
	{
		[Embed(source="sprite.png")]
        private var rSpriteImg:Class; 
		
		private var bitmapData : BitmapData = (new rSpriteImg()).bitmapData;
		
		public function testTransformIdentity() : void
		{		
			var flashBitmap : flash.display.Bitmap = new flash.display.Bitmap(bitmapData);
			var bcBitmap : bc.flash.display.Bitmap = new bc.flash.display.Bitmap(bitmapData);
			
			assertEquals(matrisesEquals(bcBitmap.transform.matrix, flashBitmap.transform.matrix));
		}
		
		public function testTransformRotation() : void
		{		
			var flashBitmap : flash.display.Bitmap = new flash.display.Bitmap(bitmapData);
			var bcBitmap : bc.flash.display.Bitmap = new bc.flash.display.Bitmap(bitmapData);
			
			flashBitmap.rotation = 30;
			bcBitmap.rotation = 30;
			
			assertEquals(matrisesEquals(bcBitmap.transform.matrix, flashBitmap.transform.matrix));
		}
		
		private function matrisesEquals(bcMatrix : bc.flash.geom.Matrix, flashMatrix : flash.geom.Matrix) : Boolean
		{
			return equalsEpsilon(bcMatrix.a, flashMatrix.a) && 
				equalsEpsilon(bcMatrix.b, flashMatrix.b) && 
				equalsEpsilon(bcMatrix.c, flashMatrix.c) && 
				equalsEpsilon(bcMatrix.d, flashMatrix.d) && 
				equalsEpsilon(bcMatrix.tx, flashMatrix.tx) && 
				equalsEpsilon(bcMatrix.ty, flashMatrix.ty);
		}
		
		private function equalsEpsilon(a : Number, b : Number) : Boolean
		{
			return MathHelper.epsilonEquals(a, b);
		}
	}	
}
