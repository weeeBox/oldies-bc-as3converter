using System;
 
using bc.flash;
 
namespace bc.flash
{
	public class AsArray : AsVector<Object>
	{
		public const int CASEINSENSITIVE = 1;
		public const int DESCENDING = 2;
		public const int UNIQUESORT = 4;
		public const int RETURNINDEXEDARRAY = 8;
		public const int NUMERIC = 16;		

        public AsArray(params Object[] elements) : base(elements)
        {
        }
    }
}
