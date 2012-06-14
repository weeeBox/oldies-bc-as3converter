package bc.flash.display3D.textures {
	import bc.flash.display.BitmapData;
	import bc.flash.error.NotImplementedError;
	import bc.flash.utils.ByteArray;
	/**
	 * @author weee
	 */
	public final class Texture extends TextureBase 
	{
		public function uploadCompressedTextureFromByteArray(data : ByteArray, byteArrayOffset : uint, async : Boolean = false) : void { throw new NotImplementedError(); }

		public function uploadFromBitmapData(source : BitmapData, miplevel : uint = 0) : void { throw new NotImplementedError(); }

		public function uploadFromByteArray(data : ByteArray, byteArrayOffset : uint, miplevel : uint = 0) : void { throw new NotImplementedError(); }
	}
}
