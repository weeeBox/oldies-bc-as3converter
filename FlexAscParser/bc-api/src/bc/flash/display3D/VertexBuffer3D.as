package bc.flash.display3D {
	import bc.flash.error.NotImplementedError;
	import bc.flash.utils.ByteArray;
	/**
	 * @author weee
	 */
	public class VertexBuffer3D extends Object 
	{
		public function dispose() : void { throw new NotImplementedError(); }

		public function uploadFromByteArray(data : ByteArray, byteArrayOffset : int, startVertex : int, numVertices : int) : void { throw new NotImplementedError(); }

		public function uploadFromVector(data : Vector.<Number>, startVertex : int, numVertices : int) : void { throw new NotImplementedError(); }
	}
}
