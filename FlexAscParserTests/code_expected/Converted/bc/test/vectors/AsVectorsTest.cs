using System;
 
using bc.flash;
 
namespace bc.test.vectors
{
	public class AsVectorsTest : AsObject
	{
		public AsVectorsTest()
		{
			AsVector<String> v = new AsVector<String>();
			v = new AsVector<String>();
			v = new AsVector<String>();
			v = new AsVector<String>("a", "b", "c");
			String _value = v[0];
			v[0] = _value;
		}
	}
}
