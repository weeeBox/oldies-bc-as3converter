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
			String a = (String)(((AsObject)obj).getOwnProperty("a"));
			int b = (int)(((AsObject)obj).getOwnProperty("b"));
			a = (String)(((AsObject)obj).getOwnProperty("a"));
			b = (int)(((AsObject)obj).getOwnProperty("b"));
			obj = "This is string";
		}
	}
}
