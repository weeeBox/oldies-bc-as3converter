using System;
 
using bc.flash;
 
namespace bc.test.functions.syntax
{
	public class AsFunctionsSyntaxTest : AsObject
	{
		public virtual void func1(Object arg = null, params Object[] rest)
		{
		}
		public virtual void func2(Object arg = null)
		{
		}
		public virtual void func3(Object arg)
		{
			return null;
		}
	}
}
