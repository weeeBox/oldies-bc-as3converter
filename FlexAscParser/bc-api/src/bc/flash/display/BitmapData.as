package bc.flash.display 
{
	import bc.flash.error.AbstractClassError;
	import bc.flash.geom.ColorTransform;
	import bc.flash.geom.Matrix;
	import bc.flash.geom.Rectangle;
	
	public class BitmapData extends Object implements IBitmapDrawable
	{
		private var mWidth : int;
		private var mHeight : int;
		private var mTransparent : Boolean;
		private var mFillColor : uint;
		
		public function BitmapData(width : int, height : int, transparent : Boolean = true, fillColor : uint = 0xffffffff) : void 
		{ 
			mWidth = width;
			mHeight = height;
			mTransparent = transparent;
			mFillColor = fillColor;
		}

		public function draw(source : IBitmapDrawable, matrix : Matrix = null, colorTransform : ColorTransform = null, blendMode : String = null, clipRect : Rectangle = null, smoothing : Boolean = false) : void 
		{ 
			throw new AbstractClassError(); 
		}
		
		public function dispose() : void 
		{ 
			throw new AbstractClassError(); 
		}
		
		public function get width() : int { return mWidth; }
		
		public function get height() : int { return mHeight; }

		/* public function applyFilter(sourceBitmapData : BitmapData, sourceRect : Rectangle, destPoint : Point, filter : BitmapFilter) : void; */

		/* public function clone() : BitmapData { implementMe(); } */

		/* public function colorTransform(rect : Rectangle, colorTransform : ColorTransform) : void { implementMe(); } */

		/* public function compare(otherBitmapData : BitmapData) : Object; */

		/* public function copyChannel(sourceBitmapData : BitmapData, sourceRect : Rectangle, destPoint : Point, sourceChannel : uint, destChannel : uint) :  : void { implementMe(); }

		/* public function copyPixels(sourceBitmapData : BitmapData, sourceRect : Rectangle, destPoint : Point, alphaBitmapData : BitmapData = null, alphaPoint : Point = null, mergeAlpha : Boolean = false) : void; */

		/* public function fillRect(rect : Rectangle, color : uint) : void { implementMe(); } */

		/* public function floodFill(x : int, y : int, color : uint) : void { implementMe(); } */

		/* public function generateFilterRect(sourceRect : Rectangle, filter : BitmapFilter) : Rectangle; */

		/* public function getColorBoundsRect(mask : uint, color : uint, findColor : Boolean = true) : Rectangle; */

		/* public function getPixel(x : int, y : int) : uint; */

		/* public function getPixel32(x : int, y : int) : uint; */

		/* public function getPixels(rect : Rectangle) : ByteArray; */

		/* [Version("10")] */
		/* public function getVector(rect : Rectangle) : Vector.<uint>; */

		/* [Version("10")] */
		/* public function histogram(hRect : Rectangle = null) : Vector.<Vector.<Number>>; */

		/* public function hitTest(firstPoint : Point, firstAlphaThreshold : uint, secondObject : Object, secondBitmapDataPoint : Point = null, secondAlphaThreshold : uint = 1) : Boolean; */

		/* public function lock() : void { implementMe(); } */

		/* public function merge(sourceBitmapData : BitmapData, sourceRect : Rectangle, destPoint : Point, redMultiplier : uint, greenMultiplier : uint, blueMultiplier : uint, alphaMultiplier : uint) : void { implementMe(); } */

		/* public function noise(randomSeed : int, low : uint = 0, high : uint = 255, channelOptions : uint = 7, grayScale : Boolean = false) : void { implementMe(); } */

		/* public function paletteMap(sourceBitmapData : BitmapData, sourceRect : Rectangle, destPoint : Point, redArray : Array = null, greenArray : Array = null, blueArray : Array = null, alphaArray : Array = null) : void { implementMe(); } */

		/* public function perlinNoise(baseX : Number, baseY : Number, numOctaves : uint, randomSeed : int, stitch : Boolean, fractalNoise : Boolean, channelOptions : uint = 7, grayScale : Boolean = false, offsets : Array = null) : void { implementMe(); } */

		/* public function pixelDissolve(sourceBitmapData : BitmapData, sourceRect : Rectangle, destPoint : Point, randomSeed : int = 0, numPixels : int = 0, fillColor : uint = 0) : int; */

		/* public function get rect() : Rectangle; */

		/* public function scroll(x : int, y : int) : void { implementMe(); } */

		/* public function setPixel(x : int, y : int, color : uint) : void { implementMe(); } */

		/* public function setPixel32(x : int, y : int, color : uint) : void { implementMe(); } */

		/* public function setPixels(rect : Rectangle, inputByteArray : ByteArray) : void { implementMe(); } */

		/* [Version("10")] */
		/* public function setVector(rect : Rectangle, inputVector : Vector.<uint>) : void { implementMe(); } */

		/* public function threshold(sourceBitmapData : BitmapData, sourceRect : Rectangle, destPoint : Point, operation : String, threshold : uint, color : uint = 0, mask : uint = 4.294967295E9, copySource : Boolean = false) : uint; */

		/* public function get transparent() : Boolean; */

		/* public function unlock(changeRect : Rectangle = null) : void; */
	}
}
