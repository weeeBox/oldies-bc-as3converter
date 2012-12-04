using System;
 
using bc.flash;
using bc.test.basic.inheritance;
 
namespace bc.test.basic.inheritance
{
	public class AsChildClass : AsBaseClass
	{
		public AsChildClass(String arg1, String arg2)
		 : base(arg1)
		{
		}
		public override void func1()
		{
			base.func1();
		}
		private void func2()
		{
		}
	}
}
