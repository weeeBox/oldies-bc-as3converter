using System;

using bc.flash;

namespace bc.flash
{
    public class AsString : AsObject
    {
        public static String fromCharCode(params Object[] charCodes) 
        { 
            throw new NotImplementedException(); 
        }

        public static int parseInt(String str)
        {
            return AsGlobal.parseInt(str);
        }

        public static uint parseUint(String str)
        {
            return AsGlobal.parseUInt(str);
        }

        public static long parseLong(String str)
        {
            return AsGlobal.parseLong(str);
        }

        public static float parseFloat(String str)
        {
            return AsGlobal.parseFloat(str);
        }

        public static bool parseBool(String str)
        {
            return AsGlobal.parseBool(str);
        }
    }

    public static class StringExtensions
    {
        public static int length(this String str)
        {
            return str.Length;
        }

        public static String charAt(this String str, float index = 0) 
        { 
            throw new NotImplementedException(); 
        }

        public static float charCodeAt(this String str, float index = 0) 
        { 
            throw new NotImplementedException(); 
        }

        public static String concat(this String str, params Object[] args) 
        { 
            throw new NotImplementedException(); 
        }

        public static int indexOf(this String str, String val, float startIndex = 0) 
        { 
            throw new NotImplementedException(); 
        }

        public static int lastIndexOf(this String str, String val, float startIndex = 0x7FFFFFFF) 
        { 
            throw new NotImplementedException(); 
        }

        public static int localeCompare(this String str, String other, params Object[] values) 
        { 
            throw new NotImplementedException(); 
        }

        public static AsArray match(this String str, Object pattern) 
        { 
            throw new NotImplementedException(); 
        }

        public static String replace(this String str, Object pattern, Object repl) 
        { 
            throw new NotImplementedException(); 
        }

        public static int search(this String str, Object pattern) 
        { 
            throw new NotImplementedException(); 
        }

        public static String slice(this String str, float startIndex = 0, float endIndex = 0x7fffffff) 
        { 
            throw new NotImplementedException(); 
        }

        public static AsArray split(this String str, Object delimiter, float limit = 0x7fffffff) 
        { 
            throw new NotImplementedException(); 
        }

        public static String substr(this String str, float startIndex = 0, float len = 0x7fffffff) 
        { 
            throw new NotImplementedException(); 
        }

        public static String substring(this String str, float startIndex = 0, float endIndex = 0x7fffffff) 
        { 
            throw new NotImplementedException(); 
        }

        public static String toLocaleLowerCase(this String str) 
        { 
            throw new NotImplementedException(); 
        }

        public static String toLocaleUpperCase(this String str) 
        { 
            throw new NotImplementedException(); 
        }

        public static String toLowerCase(this String str) 
        { 
            throw new NotImplementedException(); 
        }

        public static String toUpperCase(this String str) 
        { 
            throw new NotImplementedException(); 
        }

        public static String valueOf(this String str) 
        { 
            throw new NotImplementedException(); 
        }

        public static String toString(this String str)
        {
            return str;
        }
    }
}
