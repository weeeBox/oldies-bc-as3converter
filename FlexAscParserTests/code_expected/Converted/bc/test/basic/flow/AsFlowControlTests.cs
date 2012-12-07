using System;
 
using bc.flash;
 
namespace bc.test.basic.flow
{
	public class AsFlowControlTests : AsObject
	{
		public virtual void testIfBoolean()
		{
			bool _value = false;
			if(_value)
			{
			}
		}
		public virtual void testIfNotBoolean()
		{
			bool _value = false;
			if(!_value)
			{
			}
		}
		public virtual void testIfNotNull()
		{
			Object obj = null;
			if(obj != null)
			{
			}
		}
		public virtual void testIfNull()
		{
			Object obj = null;
			if(obj == null)
			{
			}
		}
		public virtual void testIfReference()
		{
			Object obj = null;
			if(obj != null)
			{
			}
		}
		public virtual void testIfNotReference()
		{
			Object obj = null;
			if(obj == null)
			{
			}
		}
		public virtual void testIfBitwiseAnd()
		{
			int _value = 10;
			int mask = 1;
			if((_value & mask) != 0)
			{
			}
		}
		public virtual void testIfNotBitwiseAnd()
		{
			int _value = 10;
			int mask = 1;
			if((_value & mask) == 0)
			{
			}
		}
		public virtual void testIfBitwiseOr()
		{
			int _value = 10;
			int mask = 1;
			if((_value | mask) != 0)
			{
			}
		}
		public virtual void testIfNotBitwiseOr()
		{
			int _value = 10;
			int mask = 1;
			if((_value | mask) == 0)
			{
			}
		}
		public virtual void testIfBitwiseXor()
		{
			int _value = 10;
			int mask = 1;
			if((_value ^ mask) != 0)
			{
			}
		}
		public virtual void testIfNotBitwiseXor()
		{
			int _value = 10;
			int mask = 1;
			if((_value ^ mask) == 0)
			{
			}
		}
		public virtual void testIfAddition()
		{
			int _value = 10;
			int mask = 1;
			if(_value + mask != 0)
			{
			}
		}
		public virtual void testIfNotAddition()
		{
			int _value = 10;
			int mask = 1;
			if(_value + mask == 0)
			{
			}
		}
		public virtual void testIfInteger()
		{
			int _value = 10;
			if(_value != 0)
			{
			}
		}
		public virtual void testIfNotInteger()
		{
			int _value = 10;
			if(_value == 0)
			{
			}
		}
		public virtual void testIfBooleanFunction()
		{
			if(returnsBoolean())
			{
			}
		}
		public virtual void testIfNotBooleanFunction()
		{
			if(!returnsBoolean())
			{
			}
		}
		public virtual void testIfIntegerFunction()
		{
			if(returnsInteger() != 0)
			{
			}
		}
		public virtual void testIfNotIntegerFunction()
		{
			if(returnsInteger() == 0)
			{
			}
		}
		public virtual void testIfObjectFunction()
		{
			if(returnsObject() != null)
			{
			}
		}
		public virtual void testIfNotObject()
		{
			if(returnsObject() == null)
			{
			}
		}
		public virtual int returnsInteger()
		{
			return 0;
		}
		public virtual bool returnsBoolean()
		{
			return false;
		}
		public virtual Object returnsObject()
		{
			return null;
		}
	}
}
