using System;
 
using bc.flash;
using bc.test.vectors;
 
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
		public virtual void testVectorCast()
		{
			Object obj = new AsVector<String>();
			AsVector<String> vector = (AsVector<String>)(obj);
		}
		public virtual void testVectorQualifiedCast()
		{
			Object obj = new AsVector<bc.test.vectors.AsVectorsTest>();
			AsVector<bc.test.vectors.AsVectorsTest> vector = (AsVector<bc.test.vectors.AsVectorsTest>)(obj);
		}
		public virtual void testVectorQaulifiedTernaryCast()
		{
			Object obj = new AsVector<bc.test.vectors.AsVectorsTest>();
			AsVector<bc.test.vectors.AsVectorsTest> vector = obj != null ? (AsVector<bc.test.vectors.AsVectorsTest>)obj : null;
			vector = obj == null ? null : (AsVector<bc.test.vectors.AsVectorsTest>)obj;
		}
	}
}
