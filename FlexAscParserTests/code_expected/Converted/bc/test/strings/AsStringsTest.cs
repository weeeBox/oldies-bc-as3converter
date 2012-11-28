using System;
 
using bc.flash;
 
namespace bc.test.strings
{
	public class AsStringsTest : AsObject
	{
		public AsStringsTest()
		{
			String str = "This is a string";
			str = "This is a string";
			str = "This is a string with \"escapes\"";
			str = "This is a string with \"escapes\"";
			int length = str.Length;
			String chrString = AsString.charAt(str, 0);
			float chrCode = AsString.charCodeAt(str, 0);
			String fromCharCode = AsString.fromCharCode(0);
			int indexOf = AsString.indexOf(str, "string");
			indexOf = AsString.indexOf(str, "string", 0);
			String replace = AsString.replace(str, "string", "foo");
			String slice = AsString.slice(str, 0);
			slice = AsString.slice(str, 0, 10);
			AsArray split = AsString.split(str, " ");
			String substr = AsString.substr(str, 0);
			substr = AsString.substring(str, 0, 10);
			String substring = AsString.substring(str, 0);
			substring = AsString.substring(str, 0, 10);
			String toLocalLowerCase = AsString.toLocaleLowerCase(str);
			String toLocalUpperCase = AsString.toLocaleUpperCase(str);
			String toLowerCase = AsString.toLowerCase(str);
			String toUpperCase = AsString.toUpperCase(str);
			String toString = str.ToString();
			String valueOf = AsString.valueOf(str);
		}
	}
}
