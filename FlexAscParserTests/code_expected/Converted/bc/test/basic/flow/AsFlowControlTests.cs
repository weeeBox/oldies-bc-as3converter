using System;
 
using bc.flash;
 
namespace bc.test.basic.flow
{
	public class AsFlowControlTests : AsObject
	{
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
	}
}
