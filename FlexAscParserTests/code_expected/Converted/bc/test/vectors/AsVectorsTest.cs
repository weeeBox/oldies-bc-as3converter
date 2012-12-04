using System;
 
using bc.flash;
 
namespace bc.test.vectors
{
	public class AsVectorsTest : AsObject
	{
		public virtual void testVectorCreation()
		{
			AsVector<String> v = new AsVector<String>();
			v = new AsVector<String>();
			v = new AsVector<String>();
			v = AsVector<String>.create("a", "b", "c");
		}
		public virtual void testVectorPush()
		{
			AsVector<String> v = new AsVector<String>();
			v.push();
			v.push("a");
			v.push("a", "b", "c");
			v.push(new AsArray("a", "b", "c"));
		}
		public virtual void testVectorIndexer()
		{
			AsVector<String> v = new AsVector<String>();
			v[0] = "a";
			String val = v[0];
		}
		public virtual void testVectorPop()
		{
			AsVector<String> v = new AsVector<String>();
			String element = v.pop();
		}
	}
}
