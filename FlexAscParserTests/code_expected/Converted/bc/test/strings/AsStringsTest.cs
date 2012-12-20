using System;
 
using bc.flash;
 
namespace bc.test.strings
{
	public class AsStringsTest : AsObject
	{
		public virtual void testLiterals()
		{
			String str = "This is a string";
			str = "This is a string";
			str = "This is a string with \"escapes\"";
			str = "This is a string with \"escapes\"";
		}
		public virtual void testLength()
		{
			String str = "This is a string";
			int length = str.Length;
		}
		public virtual void testMethods()
		{
			String str = "This is a string";
			String chrString = AsString.charAt(str, 0);
			float chrCode = AsString.charCodeAt(str, 0);
			String fromCharCode = AsString.fromCharCode(0);
			int indexOf = AsString.indexOf(str, "string");
			indexOf = AsString.indexOf(str, "string", 0);
			String replace = str.Replace("string", "foo");
			String slice = AsString.slice(str, 0);
			slice = AsString.slice(str, 0, 10);
			AsArray split = AsString.split(str, " ");
			String substr = AsString.substr(str, 0);
			substr = AsString.substring(str, 0, 10);
			String substring = AsString.substring(str, 0);
			substring = AsString.substring(str, 0, 10);
			String toLocalLowerCase = AsString.toLocaleLowerCase(str);
			String toLocalUpperCase = AsString.toLocaleUpperCase(str);
			String toLowerCase = str.ToLower();
			String toUpperCase = str.ToUpper();
			String toString = str.ToString();
			String valueOf = AsString.valueOf(str);
		}
		public virtual void testTypesParsing()
		{
			float num = (AsString.parseFloat("10"));
			int i = (AsString.parseInt("10"));
			uint ui = (AsString.parseUint("10"));
			bool b = (AsString.parseBool("true"));
		}
		public virtual void testLiteralsCalls()
		{
			int length = "This is a string".Length;
			String replace = "This is a string".Replace("string", "foo");
		}
	}
}
