using System;
 
using bc.flash;
 
namespace bc.flash
{
	public sealed class AsUint : AsObject
	{
		public const uint MAX_VALUE = 4294967295;
		public const uint MIN_VALUE = 0;
		private AsUint()
		{
		}
	}
}
