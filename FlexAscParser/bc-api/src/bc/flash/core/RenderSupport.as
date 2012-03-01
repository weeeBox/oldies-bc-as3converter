package bc.flash.core 
{
	import bc.flash.display.BitmapData;
	import bc.flash.error.NotImplementedError;
	import bc.flash.geom.Matrix;

	/** A class that contains helper methods simplifying Stage3D rendering.
	 *
	 *  A RenderSupport instance is passed to any "render" method of display objects. 
	 *  It allows manipulation of the current transformation matrix (similar to the matrix 
	 *  manipulation methods of OpenGL 1.x) and other helper methods.
	 */
	[ConvertOnce]
	public class RenderSupport 
	{
		private var mMatrixStack : Vector.<Matrix>;
		private var mMatrixStackSize : int;
		private var mCurrentMatrix : Matrix;

		/** Creates a new RenderSupport object with an empty matrix stack. */
		public function RenderSupport() 
		{
			mMatrixStack = new <Matrix>[];
			mMatrixStackSize = 0;
			
			mCurrentMatrix = new Matrix();
		}

		/** Disposes all quad batches. */
		public function dispose() : void 
		{
		}

		/** Append transform to the current matrix */
		public function transform(matrix : Matrix) : void 
		{
			mCurrentMatrix.concat(matrix);
		}

		/** Pushes the current modelview matrix to a stack from which it can be restored later. */
		public function pushMatrix() : void 
		{
			if (mMatrixStack.length < mMatrixStackSize + 1)
			{
				mMatrixStack.push(new Matrix());
			}

			mMatrixStack[mMatrixStackSize++].copyFrom(mCurrentMatrix);
		}

		/** Restores the modelview matrix that was last pushed to the stack. */
		public function popMatrix() : void 
		{
			mCurrentMatrix.copyFrom(mMatrixStack[--mMatrixStackSize]);
		}

		/** Empties the matrix stack, resets the modelview matrix to the identity matrix. */
		public function resetMatrix() : void 
		{
			mMatrixStackSize = 0;
			mCurrentMatrix.identity();
		}
		
		public function drawBitmap(bitmap : BitmapData, alpha : Number) : void
		{
			throw new NotImplementedError();
		}
	}
}
