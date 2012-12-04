using System;
 
using bc.flash;
 
namespace bc.test.basic.types
{
	public class AsTypesTests : AsObject
	{
		public AsTypesTests()
		{
		}
		public virtual void testIntegrals()
		{
			int integer = 0;
			uint uinteger = 0;
			float number = 0;
			bool boolean = false;
		}
		public virtual void testAssignments()
		{
			int integer = -10;
			uint uinteger = (uint)(10);
			float number = 3.14f;
			bool boolean = true;
			integer = (int)(uinteger);
			uinteger = (uint)(integer);
			number = integer;
			number = uinteger;
			integer = (int)(number);
			uinteger = (uint)(number);
			boolean = false;
		}
	}
}
