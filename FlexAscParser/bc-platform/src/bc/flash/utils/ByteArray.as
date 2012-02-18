package bc.flash.utils
{
	import bc.flash.error.NotImplementedError;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class ByteArray
	{
		public function get bytesAvailable() : uint { throw new NotImplementedError(); }

		public function clear() : void { throw new NotImplementedError(); }

		/* public function compress(algorithm : String = "zlib") : void; */

		public static function get defaultObjectEncoding() : uint { throw new NotImplementedError(); }

		public static function set defaultObjectEncoding(version : uint) : void { throw new NotImplementedError(); }

		/* public function deflate() : void; */

		public function get endian() : String { throw new NotImplementedError(); }

		public function set endian(type : String) : void { throw new NotImplementedError(); }

		/* public function inflate() : void; */

		public function get length() : uint { throw new NotImplementedError(); }

		public function set length(value : uint) : void { throw new NotImplementedError(); }

		public function get objectEncoding() : uint { throw new NotImplementedError(); }

		public function set objectEncoding(version : uint) : void { throw new NotImplementedError(); }

		public function get position() : uint { throw new NotImplementedError(); }

		public function set position(offset : uint) : void { throw new NotImplementedError(); }

		public function readBoolean() : Boolean { throw new NotImplementedError(); }

		public function readByte() : int { throw new NotImplementedError(); }

		public function readBytes(bytes : ByteArray, offset : uint = 0, length : uint = 0) : void { throw new NotImplementedError(); }

		public function readDouble() : Number { throw new NotImplementedError(); }

		public function readFloat() : Number { throw new NotImplementedError(); }

		public function readInt() : int { throw new NotImplementedError(); }

		public function readMultiByte(length : uint, charSet : String) : String { throw new NotImplementedError(); }

		public function readObject() : Object { throw new NotImplementedError(); }

		public function readShort() : int { throw new NotImplementedError(); }

		public function readUTF() : String { throw new NotImplementedError(); }

		public function readUTFBytes(length : uint) : String { throw new NotImplementedError(); }

		public function readUnsignedByte() : uint { throw new NotImplementedError(); }

		public function readUnsignedInt() : uint { throw new NotImplementedError(); }

		public function readUnsignedShort() : uint { throw new NotImplementedError(); }

		public function toString() : String { throw new NotImplementedError(); }

		/* public function uncompress(algorithm : String = "zlib") : void; */

		public function writeBoolean(value : Boolean) : void { throw new NotImplementedError(); }

		public function writeByte(value : int) : void { throw new NotImplementedError(); }

		public function writeBytes(bytes : ByteArray, offset : uint = 0, length : uint = 0) : void { throw new NotImplementedError(); }

		public function writeDouble(value : Number) : void { throw new NotImplementedError(); }

		public function writeFloat(value : Number) : void { throw new NotImplementedError(); }

		public function writeInt(value : int) : void { throw new NotImplementedError(); }

		public function writeMultiByte(value : String, charSet : String) : void { throw new NotImplementedError(); }

		public function writeObject(object : Object) : void { throw new NotImplementedError(); }

		public function writeShort(value : int) : void { throw new NotImplementedError(); }

		public function writeUTF(value : String) : void { throw new NotImplementedError(); }

		public function writeUTFBytes(value : String) : void { throw new NotImplementedError(); }

		public function writeUnsignedInt(value : uint) : void { throw new NotImplementedError(); }
	}
}
