using System;
 
using bc.flash;
using bc.flash.error;
 
namespace bc.flash.error
{
	public class AsArgumentError : AsError
	{
		public AsArgumentError(String message)
		 : base(message)
		{
		}
		public AsArgumentError()
		 : this("")
		{
		}
	}
}
