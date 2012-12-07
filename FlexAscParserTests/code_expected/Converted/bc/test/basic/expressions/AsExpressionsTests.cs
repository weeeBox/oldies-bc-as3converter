using System;
 
using bc.flash;
 
namespace bc.test.basic.expressions
{
	public class AsExpressionsTests : AsObject
	{
		public virtual void testAssignment()
		{
			int a = 10;
			int b = 20;
			Object c = a + b;
		}
		public virtual void testAssignment2()
		{
			int a = 10;
			int b = 20;
			int c = 30;
			Object d = a + b + c;
		}
		public virtual void testAssignment3()
		{
			int a = 10;
			int b = 20;
			int c = 30;
			Object d = (a + b) + c;
		}
		public virtual void testAssignment4()
		{
			int a = 10;
			int b = 20;
			int c = 30;
			Object d = a + (b + c);
		}
		public virtual void testPriority()
		{
			int a = 10;
			int b = 20;
			int c = 30;
			Object d = (a + b) * c;
		}
		public virtual void testPriority2()
		{
			int a = 10;
			int b = 20;
			int c = 30;
			Object d = a * (b + c);
		}
	}
}
