using System;
 
using bc.flash;
 
namespace bc.test.arrays
{
	public class AsArraysTest : AsObject
	{
		public AsArraysTest()
		{
			AsArray oneArray = new AsArray();
			AsArray twoArray = new AsArray("a", "b", "c");
			AsArray threeArray = new AsArray("a", "b", "c");
			AsArray fourArray = (new AsArray("a", "b", "c"));
		}
	}
}
