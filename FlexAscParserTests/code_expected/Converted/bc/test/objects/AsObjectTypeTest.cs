using System;
 
using bc.flash;
 
namespace bc.test.objects
{
	public class AsObjectTypeTest : AsObject
	{
		public AsObjectTypeTest()
		{
			Object obj = null;
			obj = AsObject.createLiteralObject("a", "This is string", "b", 10);
			String a = (String)(AsObject.getOwnProperty(obj, "a"));
			int b = (int)(AsObject.getOwnProperty(obj, "b"));
            a = (String)(AsObject.getOwnProperty(obj, "a"));
            b = (int)(AsObject.getOwnProperty(obj, "b"));
			obj = "This is string";
		}
	}
}
