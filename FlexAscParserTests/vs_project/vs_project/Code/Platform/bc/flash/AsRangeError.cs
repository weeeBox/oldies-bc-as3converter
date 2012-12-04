using System;
 
using bc.flash;
 
namespace bc.flash
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
