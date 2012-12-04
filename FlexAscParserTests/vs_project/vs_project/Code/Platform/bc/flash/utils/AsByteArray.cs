using System;
 
using bc.flash;
using bc.flash.error;
using bc.flash.utils;
 
namespace bc.flash.utils
{
	public class AsByteArray : AsObject
	{
		public virtual uint getBytesAvailable()
		{
			throw new AsNotImplementedError();
		}
		public virtual void clear()
		{
			throw new AsNotImplementedError();
		}
		public static uint getDefaultObjectEncoding()
		{
			throw new AsNotImplementedError();
		}
		public static void setDefaultObjectEncoding(uint version)
		{
			throw new AsNotImplementedError();
		}
		public virtual String getEndian()
		{
			throw new AsNotImplementedError();
		}
		public virtual void setEndian(String type)
		{
			throw new AsNotImplementedError();
		}
		public virtual uint getLength()
		{
			throw new AsNotImplementedError();
		}
		public virtual void setLength(uint _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual uint getObjectEncoding()
		{
			throw new AsNotImplementedError();
		}
		public virtual void setObjectEncoding(uint version)
		{
			throw new AsNotImplementedError();
		}
		public virtual uint getPosition()
		{
			throw new AsNotImplementedError();
		}
		public virtual void setPosition(uint offset)
		{
			throw new AsNotImplementedError();
		}
		public virtual bool readBoolean()
		{
			throw new AsNotImplementedError();
		}
		public virtual int readByte()
		{
			throw new AsNotImplementedError();
		}
		public virtual void readBytes(AsByteArray bytes, uint offset, uint length)
		{
			throw new AsNotImplementedError();
		}
		public virtual void readBytes(AsByteArray bytes, uint offset)
		{
			readBytes(bytes, offset, (uint)(0));
		}
		public virtual void readBytes(AsByteArray bytes)
		{
			readBytes(bytes, (uint)(0), (uint)(0));
		}
		public virtual float readDouble()
		{
			throw new AsNotImplementedError();
		}
		public virtual float readFloat()
		{
			throw new AsNotImplementedError();
		}
		public virtual int readInt()
		{
			throw new AsNotImplementedError();
		}
		public virtual String readMultiByte(uint length, String charSet)
		{
			throw new AsNotImplementedError();
		}
		public virtual AsObject readObject()
		{
			throw new AsNotImplementedError();
		}
		public virtual int readShort()
		{
			throw new AsNotImplementedError();
		}
		public virtual String readUTF()
		{
			throw new AsNotImplementedError();
		}
		public virtual String readUTFBytes(uint length)
		{
			throw new AsNotImplementedError();
		}
		public virtual uint readUnsignedByte()
		{
			throw new AsNotImplementedError();
		}
		public virtual uint readUnsignedInt()
		{
			throw new AsNotImplementedError();
		}
		public virtual uint readUnsignedShort()
		{
			throw new AsNotImplementedError();
		}
		public virtual String toString()
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeBoolean(bool _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeByte(int _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeBytes(AsByteArray bytes, uint offset, uint length)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeBytes(AsByteArray bytes, uint offset)
		{
			writeBytes(bytes, offset, (uint)(0));
		}
		public virtual void writeBytes(AsByteArray bytes)
		{
			writeBytes(bytes, (uint)(0), (uint)(0));
		}
		public virtual void writeDouble(float _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeFloat(float _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeInt(int _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeMultiByte(String _value, String charSet)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeObject(AsObject _object)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeShort(int _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeUTF(String _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeUTFBytes(String _value)
		{
			throw new AsNotImplementedError();
		}
		public virtual void writeUnsignedInt(uint _value)
		{
			throw new AsNotImplementedError();
		}
	}
}
