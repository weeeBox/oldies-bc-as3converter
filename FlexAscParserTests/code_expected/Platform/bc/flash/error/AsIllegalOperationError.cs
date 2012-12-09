using System;
 
using bc.flash;
using bc.flash.error;
 
namespace bc.flash.error
{
	public class AsIllegalOperationError : AsError
	{
		public AsIllegalOperationError(String message)
		 : base(message)
		{
		}
		public AsIllegalOperationError()
		 : this(null)
		{
		}
	}
}
