using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace bc.flash
{
    public class AsGlobal
    {
        private static int totalTime;

        public static void setTimer(int time)
        {
            totalTime = time;
        }

        public static int getTimer() 
        {
            return totalTime;
        }

        public static int parseInt(String s)
        {
            int val = 0;
            int.TryParse(s, out val);
            return val;
        }

        public static uint parseUInt(String s)
        {
            uint val = 0;
            uint.TryParse(s, out val);
            return val;
        }

        public static int parseInt(String s, int radix)
        {
            throw new NotImplementedException();
        }

        public static float parseFloat(String s)
        {
            float val = 0;
            float.TryParse(s, out val);
            return val;
        }

        public static long parseLong(String s)
        {
            long val = 0;
            long.TryParse(s, out val);
            return val;
        }

        public static bool parseBool(string s)
        {
            bool val = false;
            bool.TryParse(s, out val);
            return val;
        }

        public static void trace(uint val)
        {
            Debug.WriteLine(val);
        }

        public static void trace(int val)
        {
            Debug.WriteLine(val);
        }

        public static void trace(bool val)
        {
            Debug.WriteLine(val);
        }

        public static void trace(float val)
        {
            Debug.WriteLine(val);
        }

        public static void trace(double val)
        {
            Debug.WriteLine(val);
        }

        public static void trace(String val)
        {
            Debug.WriteLine(val);
        }

        public static void trace(Object val)
        {
            Debug.WriteLine(val);
        }

        public static String getQualifiedClassName(AsObject obj)
        {
            return obj.GetType().Name;
        }

        public static void assert(bool condition)
        {
            Debug.Assert(condition);
        }

        public static void assert(bool condition, String message)
        {
            Debug.Assert(condition, message);
        }
    }
}
