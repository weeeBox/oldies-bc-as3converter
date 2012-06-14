package bc.flash.display3D {
	import bc.flash.error.NotImplementedError;
	import bc.flash.utils.ByteArray;
	/**
	 * @author weee
	 */
	public final class IndexBuffer3D 
	{
		public function dispose() : void { throw new NotImplementedError(); }

		public function uploadFromByteArray(data : ByteArray, byteArrayOffset : int, startOffset : int, count : int) : void { throw new NotImplementedError(); }

		public function uploadFromVector(data : Vector.<uint>, startOffset : int, count : int) : void { throw new NotImplementedError(); }
	}
}
