using System;
 
using bc.flash;
 
namespace bc.flash
{
	public class AsError : Exception
	{
		public String message;
		public String name;
		public AsError(String message)
		{
			this.message = message;
		}
		public AsError()
		 : this("")
		{
		}
	}
}
