using System;
 
using bc.flash;
using bc.flash.error;
 
namespace bc.flash.error
{
	public class AsAbstractMethodError : AsError
	{
		public AsAbstractMethodError(String message)
		 : base(message)
		{
		}
		public AsAbstractMethodError()
		 : this("")
		{
		}
	}
}
