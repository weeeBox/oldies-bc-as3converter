using System;
 
using bc.flash;
 
namespace bc.flash.errors
{
	public class AsNotImplementedError : AsError
	{
		public AsNotImplementedError(String message)
		 : base(message)
		{
		}
		public AsNotImplementedError()
		 : this("")
		{
		}
	}
}
