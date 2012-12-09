using System;
 
using bc.flash;
 
namespace bc.flash.errors
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
