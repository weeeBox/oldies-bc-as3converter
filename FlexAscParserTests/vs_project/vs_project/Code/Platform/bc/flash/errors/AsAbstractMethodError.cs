using System;
 
using bc.flash;
 
namespace bc.flash.errors
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
