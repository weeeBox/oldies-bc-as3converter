package bc.flash.display3D.textures 
{
	import bc.flash.display.BitmapData;
	import bc.flash.utils.ByteArray;
	
	/**
	 * @author weee
	 */
	public final class CubeTexture extends TextureBase 
	{
		public function uploadCompressedTextureFromByteArray(data : ByteArray, byteArrayOffset : uint, async : Boolean = false) : void;

		public function uploadFromBitmapData(source : BitmapData, side : uint, miplevel : uint = 0) : void;

		public function uploadFromByteArray(data : ByteArray, byteArrayOffset : uint, side : uint, miplevel : uint = 0) : void;
	}
}
