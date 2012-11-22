using System;
 
using bc.flash;
 
namespace bc.flash
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
