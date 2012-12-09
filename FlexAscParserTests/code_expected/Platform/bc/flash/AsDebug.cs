using System;
 
using bc.flash;
using bc.flash.error;
 
namespace bc.flash
{
	public class AsDebug : AsObject
	{
		public static void assert(bool condition, String message)
		{
			if(!(condition))
			{
				throw new AsError(("Assertion error: " + message));
			}
		}
		public static void assert(bool condition)
		{
			assert(condition, "");
		}
		public static void implementMe(String message)
		{
			AsDebug.assert(false, ("Implement me: " + message));
		}
		public static void implementMe()
		{
			implementMe("");
		}
	}
}
