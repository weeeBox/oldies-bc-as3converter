package bc.tests
{
	import flash.geom.Rectangle;
	import bc.flash.utils.MathHelper;
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
			
			trace(flashBitmap.transform.matrix);
			trace(bcBitmap.transform.matrix);
			assertEquals(matrisesEquals(bcBitmap.transform.matrix, flashBitmap.transform.matrix), true);
		}
		
		public function testUntrasformedBounds() : void
		{		
			var flashBitmap : flash.display.Bitmap = new flash.display.Bitmap(bitmapData);
			var bcBitmap : bc.flash.display.Bitmap = new bc.flash.display.Bitmap(bitmapData);
			
			var flashBounds : flash.geom.Rectangle = flashBitmap.getBounds(flashBitmap);
			var bcBounds : bc.flash.geom.Rectangle = bcBitmap.getBounds(bcBitmap);
			
			assertEquals(rectanglesEquals(flashBounds, bcBounds), true);
		}
		
		private function rectanglesEquals(fRect : flash.geom.Rectangle, bRect : bc.flash.geom.Rectangle) : Boolean
		{
			return equalsEpsilon(fRect.x, bRect.x) &&
				equalsEpsilon(fRect.y, bRect.y) &&
				equalsEpsilon(fRect.width, bRect.width) &&
				equalsEpsilon(fRect.height, bRect.height);
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
