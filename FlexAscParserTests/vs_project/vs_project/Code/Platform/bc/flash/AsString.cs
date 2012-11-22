using System;

using bc.flash;

namespace bc.flash
{
    public class AsString : AsObject
    {
        public static String charAt(String str, int index)
        {
            return str.Substring(index, 1);
        }

        public static int indexOf(String str, String s, int index)
        {
            return str.IndexOf(s, index);
        }

        public static int indexOf(String str, String s)
        {
            return str.IndexOf(s);
        }

        public static int lastIndexOf(String str, String s, int index)
        {
            return str.LastIndexOf(s, index);
        }

        public static int lastIndexOf(String str, String s)
        {
            return str.LastIndexOf(s);
        }

        public static String replace(String str, String token, String replacement)
        {
            int startIndex = str.IndexOf(token);
            if (startIndex == -1)
            {
                return str;
            }

            return str.Substring(0, startIndex) + replacement + str.Substring(startIndex + 1);
        }

        public static String slice(String str, int start, int end)
        {
            return str.Substring(start, end - start);
        }

        public static String slice(String str, int start)
        {
            return str.Substring(start);
        }

        public static AsArray split(String str, String delim)
        {
            String[] tokens = str.Split(new String[] { delim }, StringSplitOptions.None);
            return new AsArray(tokens);
        }

        public static String substr(String str, int start, int len)
        {
            return str.Substring(start, len);
        }

        public static String substr(String str, int start)
        {
            return str.Substring(start);
        }

        public static String substring(String str, int start, int end)
        {
            return str.Substring(start, end - start);
        }

        public static String substring(String str, int start)
        {
            return str.Substring(start);
        }

        public static String toLocaleLowerCase(String str)
        {
            return str.ToLower();
        }

        public static String toLocaleUpperCase(String str)
        {
            return str.ToUpper();
        }

        public static String toLowerCase(String str)
        {
            return str.ToLower();
        }

        public static String toUpperCase(String str)
        {
            return str.ToUpper();
        }

        public static String valueOf(Object obj)
        {
            return obj.ToString();
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
}
