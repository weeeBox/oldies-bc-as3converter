package bc.flash.display3D 
{
	import bc.flash.display.BitmapData;
	import bc.flash.display3D.textures.CubeTexture;
	import bc.flash.display3D.textures.Texture;
	import bc.flash.display3D.textures.TextureBase;
	import bc.flash.errors.NotImplementedError;
	import bc.flash.geom.Matrix3D;
	import bc.flash.geom.Rectangle;
	import bc.flash.utils.ByteArray;
	/**
	 * @author weee
	 */
	public class Context3D {
		public function clear(red : Number = 0.0, green : Number = 0.0, blue : Number = 0.0, alpha : Number = 1.0, depth : Number = 1.0, stencil : uint = 0, mask : uint = 0xffffffff) : void { throw new NotImplementedError(); }

		public function configureBackBuffer(width : int, height : int, antiAlias : int, enableDepthAndStencil : Boolean = true) : void { throw new NotImplementedError(); }

		public function createCubeTexture(size : int, format : String, optimizeForRenderToTexture : Boolean) : CubeTexture { throw new NotImplementedError(); }

		public function createIndexBuffer(numIndices : int) : IndexBuffer3D { throw new NotImplementedError(); }

		public function createProgram() : Program3D { throw new NotImplementedError(); }

		public function createTexture(width : int, height : int, format : String, optimizeForRenderToTexture : Boolean) : Texture { throw new NotImplementedError(); }

		public function createVertexBuffer(numVertices : int, data32PerVertex : int) : VertexBuffer3D { throw new NotImplementedError(); }

		public function dispose() : void { throw new NotImplementedError(); }

		public function drawToBitmapData(destination : BitmapData) : void { throw new NotImplementedError(); }

		public function drawTriangles(indexBuffer : IndexBuffer3D, firstIndex : int = 0, numTriangles : int = -1) : void { throw new NotImplementedError(); }

		public function get driverInfo() : String { throw new NotImplementedError(); }

		public function get enableErrorChecking() : Boolean { throw new NotImplementedError(); }

		public function set enableErrorChecking(toggle : Boolean) : void { throw new NotImplementedError(); }

		public function present() : void { throw new NotImplementedError(); }

		public function setBlendFactors(sourceFactor : String, destinationFactor : String) : void { throw new NotImplementedError(); }

		public function setColorMask(red : Boolean, green : Boolean, blue : Boolean, alpha : Boolean) : void { throw new NotImplementedError(); }

		public function setCulling(triangleFaceToCull : String) : void { throw new NotImplementedError(); }

		public function setDepthTest(depthMask : Boolean, passCompareMode : String) : void { throw new NotImplementedError(); }

		public function setProgram(program : Program3D) : void { throw new NotImplementedError(); }

		[API("676")]
		public function setProgramConstantsFromByteArray(programType : String, firstRegister : int, numRegisters : int, data : ByteArray, byteArrayOffset : uint) : void { throw new NotImplementedError(); }

		public function setProgramConstantsFromMatrix(programType : String, firstRegister : int, matrix : Matrix3D, transposedMatrix : Boolean = false) : void { throw new NotImplementedError(); }

		public function setProgramConstantsFromVector(programType : String, firstRegister : int, data : Vector.<Number>, numRegisters : int = -1) : void { throw new NotImplementedError(); }

		public function setRenderToBackBuffer() : void { throw new NotImplementedError(); }

		public function setRenderToTexture(texture : TextureBase, enableDepthAndStencil : Boolean = false, antiAlias : int = 0, surfaceSelector : int = 0) : void { throw new NotImplementedError(); }

		public function setScissorRectangle(rectangle : Rectangle) : void { throw new NotImplementedError(); }

		public function setStencilActions(triangleFace : String = "frontAndBack", compareMode : String = "always", actionOnBothPass : String = "keep", actionOnDepthFail : String = "keep", actionOnDepthPassStencilFail : String = "keep") : void { throw new NotImplementedError(); }

		public function setStencilReferenceValue(referenceValue : uint, readMask : uint = 255, writeMask : uint = 255) : void { throw new NotImplementedError(); }

		public function setTextureAt(sampler : int, texture : TextureBase) : void { throw new NotImplementedError(); }

		public function setVertexBufferAt(index : int, buffer : VertexBuffer3D, bufferOffset : int = 0, format : String = "float4") : void { throw new NotImplementedError(); }
	}
}
