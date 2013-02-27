using System;
 
using bc.flash;
 
namespace bc.flash
{
	public sealed class AsInt : AsObject
	{
		public const int MAX_VALUE = 2147483647;
		public const int MIN_VALUE = -2147483648;
		private AsInt()
		{
		}
	}
}
