using System;
 
using bc.flash;
 
namespace bc.test.functions.anonymous
{
	public class AsFunctionsAnonymousTest : AsObject
	{
		public virtual void testAnonymous1()
		{
			AsFunction func = null;
			func = __function(delegate()
			{
				doStuff();
			});
			func.invoke();
		}
		public virtual void testAnonymous2()
		{
			AsFunction func = null;
			func = __function(delegate(String arg1)
			{
				doStuff();
			});
			func.invoke();
		}
		public virtual void testAnonymous3()
		{
			AsFunction func = null;
			func = __function(delegate(String arg1, String arg2)
			{
				doStuff();
			});
			func.invoke();
		}
		public virtual void testAnonymous4()
		{
			AsFunction func = null;
			func = __function(delegate(String arg1, String arg2)
			{
				return arg1 + " " + arg2;
			});
			String result = (String)(func.invoke());
		}
		public virtual void testAnonymous5()
		{
			AsFunction func = null;
			func = __function(delegate(int arg1, int arg2)
			{
				return arg1 + arg2;
			});
			int result = (int)(func.invoke());
		}
		private void doStuff()
		{
		}
	}
}
