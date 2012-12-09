using System;
 
using bc.flash;
using bc.flash.error;
 
namespace bc.flash.error
{
	public class AsRangeError : AsError
	{
		public AsRangeError(String message)
		 : base(message)
		{
		}
		public AsRangeError()
		 : this("")
		{
		}
	}
}
