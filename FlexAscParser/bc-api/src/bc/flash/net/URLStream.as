package flash.net
{
	import flash.events;
	import flash.utils;
	
	public class URLStream extends EventDispatcher implements IEventDispatcher/*, IDataInput */
	{
		public function load(arg1:URLRequest) : void { throw new NotImplementedError(); }
		public function readDouble() : Number { throw new NotImplementedError(); }
		public function readUnsignedInt() : uint { throw new NotImplementedError(); }
		public function readInt() : int { throw new NotImplementedError(); }
		public function stop() : void { throw new NotImplementedError(); }
		public function readBytes(arg1:ByteArray, arg2:uint=null, arg3:uint=null) : void { throw new NotImplementedError(); }
		public function readByte() : int { throw new NotImplementedError(); }
		public function readUnsignedByte() : uint { throw new NotImplementedError(); }
		public function readBoolean() : Boolean { throw new NotImplementedError(); }
		public function readShort() : int { throw new NotImplementedError(); }
		public function readFloat() : Number { throw new NotImplementedError(); }
		public function close() : void { throw new NotImplementedError(); }
		public function readUTF() : String { throw new NotImplementedError(); }
		public function readMultiByte(arg1:uint, arg2:String) : String { throw new NotImplementedError(); }
		public function readObject() : * { throw new NotImplementedError(); }
		public function readUTFBytes(arg1:uint) : String { throw new NotImplementedError(); }
		public function readUnsignedShort() : uint { throw new NotImplementedError(); }
		public function get position() : Number { throw new NotImplementedError(); }
		public function set position(value:Number) : void { throw new NotImplementedError(); }
		public function get endian() : String { throw new NotImplementedError(); }
		public function set endian(value:String) : void { throw new NotImplementedError(); }
		public function get connected() : Boolean { throw new NotImplementedError(); }
		public function get length() : Number { throw new NotImplementedError(); }
		public function get diskCacheEnabled() : Boolean { throw new NotImplementedError(); }
		public function get objectEncoding() : uint { throw new NotImplementedError(); }
		public function set objectEncoding(value:uint) : void { throw new NotImplementedError(); }
		public function get bytesAvailable() : uint { throw new NotImplementedError(); }
	}
}