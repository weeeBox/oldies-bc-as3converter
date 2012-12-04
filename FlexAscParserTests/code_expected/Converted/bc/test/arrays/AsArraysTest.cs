using System;
 
using bc.flash;
 
namespace bc.test.arrays
{
	public class AsArraysTest : AsObject
	{
		public virtual void testArrayCreation()
		{
			AsArray oneArray = new AsArray();
			AsArray twoArray = new AsArray("a", "b", "c");
			AsArray threeArray = new AsArray("a", "b", "c");
			AsArray fourArray = (new AsArray("a", "b", "c"));
		}
		public virtual void testArrayPush()
		{
			AsArray oneArray = new AsArray();
			oneArray.push();
			oneArray.push("a");
			oneArray.push("a", "b", "c");
			oneArray.push(new AsArray("a", "b", "c"));
		}
		public virtual void testArrayIndexer()
		{
			AsArray oneArray = new AsArray();
			oneArray[0] = "a";
			String val = (String)(oneArray[0]);
		}
		public virtual void testArrayPop()
		{
			AsArray oneArray = new AsArray("a");
			String element = (String)(oneArray.pop());
		}
	}
}
