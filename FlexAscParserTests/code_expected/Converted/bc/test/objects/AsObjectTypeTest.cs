using System;
 
using bc.flash;
using bc.test.objects;
 
namespace bc.test.objects
{
	public class AsObjectTypeTest : AsObject
	{
		public AsObjectTypeTest()
		{
			Object obj = null;
			obj = AsObject.createLiteralObject("a", "This is string", "b", 10);
			String a = (String)(((AsObject)(obj)).getOwnProperty("a"));
			int b = (int)(((AsObject)(obj)).getOwnProperty("b"));
			a = (String)(((AsObject)(obj)).getOwnProperty("a"));
			b = (int)(((AsObject)(obj)).getOwnProperty("b"));
			obj = "This is string";
			AsFooObjectClass foo = new AsFooObjectClass();
			foo.setOwnProperty("a", a);
			foo.setOwnProperty("b", b);
			a = (String)(foo.getOwnProperty("a"));
			b = (int)(foo.getOwnProperty("b"));
		}
	}
}
