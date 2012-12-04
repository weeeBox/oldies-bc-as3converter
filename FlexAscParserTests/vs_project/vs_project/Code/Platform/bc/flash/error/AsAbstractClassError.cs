using System;
 
using bc.flash;
using bc.flash.error;
 
namespace bc.flash.error
{
	public class AsAbstractClassError : AsError
	{
		public AsAbstractClassError(String message)
		 : base(message)
		{
		}
		public AsAbstractClassError()
		 : this("")
		{
		}
	}
}
