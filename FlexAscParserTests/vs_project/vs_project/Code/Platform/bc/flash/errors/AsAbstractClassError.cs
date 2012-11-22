using System;
 
using bc.flash;
 
namespace bc.flash.errors
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
