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
			oneArray.push();
			oneArray.push("a");
			oneArray.push("a", "b", "c");
			oneArray.push(new AsArray("a", "b", "c"));
			oneArray[0] = "a";
			String val = (String)(oneArray[0]);
			String element = (String)(oneArray.pop());
		}
	}
}
