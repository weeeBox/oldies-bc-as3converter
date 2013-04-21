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
            int length = str.length();
        }
        public virtual void testMethods()
        {
            String str = "This is a string";
            String chrString = str.charAt(0);
            float chrCode = str.charCodeAt(0);
            String fromCharCode = AsString.fromCharCode(0);
            int indexOf = str.indexOf("string");
            indexOf = str.indexOf("string", 0);
            String replace = str.replace("string", "foo");
            String slice = str.slice(0);
            slice = str.slice(0, 10);
            AsArray split = str.split(" ");
            String substr = str.substr(0);
            substr = str.substring(0, 10);
            String substring = str.substring(0);
            substring = str.substring(0, 10);
            String toLocalLowerCase = str.toLocaleLowerCase();
            String toLocalUpperCase = str.toLocaleUpperCase();
            String toLowerCase = str.toLowerCase();
            String toUpperCase = str.toUpperCase();
            String toString = str.toString();
            String valueOf = str.valueOf();
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
            int length = "This is a string".length();
            String replace = "This is a string".replace("string", "foo");
        }
    }
}
