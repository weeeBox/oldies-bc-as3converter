package bc.flash.display
{
	/**
	 * @author weee
	 */
	public final class Graphics extends Object
	{
		/* public function beginBitmapFill(bitmap : BitmapData, matrix : Matrix = null, repeat : Boolean = true, smooth : Boolean = false) : void { throw new AbstractClassError(); } */
		public function beginFill(color : uint, alpha : Number = 1.0) : void
		{
		}

		/* public function beginGradientFill(type : String, colors : Array, alphas : Array, ratios : Array, matrix : Matrix = null, spreadMethod : String = "pad", interpolationMethod : String = "rgb", focalPointRatio : Number = 0) : void { throw new AbstractClassError(); } */
		/* [Version("10")] */
		/* public function beginShaderFill(shader : Shader, matrix : Matrix = null) : void { throw new AbstractClassError(); } */
		public function clear() : void
		{
		}

		/* [Version("10")] */
		/* public function copyFrom(sourceGraphics : Graphics) : void { throw new AbstractClassError(); } */
		public function curveTo(controlX : Number, controlY : Number, anchorX : Number, anchorY : Number) : void
		{
		}

		public function drawCircle(x : Number, y : Number, radius : Number) : void
		{
		}

		public function drawEllipse(x : Number, y : Number, width : Number, height : Number) : void
		{
		}

		/* [Version("10")] */
		/* public function drawGraphicsData(graphicsData : Vector.<IGraphicsData>) : void { throw new AbstractClassError(); } */
		
		/* [Version("10")] */
		/* public function drawPath(commands : Vector.<int>, data : Vector.<Number>, winding : String = "evenOdd") : void { throw new AbstractClassError(); } */
		
		public function drawRect(x : Number, y : Number, width : Number, height : Number) : void
		{
		}

		public function drawRoundRect(x : Number, y : Number, width : Number, height : Number, ellipseWidth : Number, ellipseHeight : Number) : void
		{
		}
		
		public function drawBitmap(bitmap : BitmapData, x : Number, y : Number) : void
		{
		}

		/* [Inspectable(environment="none")] */
		/* public function drawRoundRectComplex(x : Number, y : Number, width : Number, height : Number, topLeftRadius : Number, topRightRadius : Number, bottomLeftRadius : Number, bottomRightRadius : Number) : void { throw new AbstractClassError(); } */
		
		/* [Version("10")] */
		/* public function drawTriangles(vertices : Vector.<Number>, indices : Vector.<int> = null, uvtData : Vector.<Number> = null, culling : String = "none") : void { throw new AbstractClassError(); } */
		
		public function endFill() : void
		{
		}

		/* [Version("10")] */
		/* public function lineBitmapStyle(bitmap : BitmapData, matrix : Matrix = null, repeat : Boolean = true, smooth : Boolean = false) : void { throw new AbstractClassError(); } */
		
		/* public function lineGradientStyle(type : String, colors : Array, alphas : Array, ratios : Array, matrix : Matrix = null, spreadMethod : String = "pad", interpolationMethod : String = "rgb", focalPointRatio : Number = 0) : void { throw new AbstractClassError(); } */
		
		/* [Version("10")] */
		/* public function lineShaderStyle(shader : Shader, matrix : Matrix = null) : void { throw new AbstractClassError(); } */
		
		public function lineStyle(thickness : Number = 0, color : uint = 0, alpha : Number = 1.0, pixelHinting : Boolean = false, scaleMode : String = "normal", caps : String = null, joints : String = null, miterLimit : Number = 3) : void
		{
		}

		public function lineTo(x : Number, y : Number) : void
		{
		}

		public function moveTo(x : Number, y : Number) : void
		{
		}
	}
}
	