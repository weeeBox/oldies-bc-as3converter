using System;

using bc.flash;
using bc.flash.errors;

namespace bc.flash
{
    public sealed class AsNumber : AsObject
    {
        public static float NaN = (0.0f / 0.0f);
        public static float NEGATIVE_INFINITY = (-1.0f / 0.0f);
        public static float POSITIVE_INFINITY = (1.0f / 0.0f);
        public static float MIN_VALUE = 1.401298464324817E-45f;
        public static float MAX_VALUE = 3.4028234663852886E38f;

        private AsNumber()
        {
        }

        public static bool isUint(object obj)
        {
            throw new NotImplementedException();
        }

        public static bool isInt(object obj)
        {
            throw new NotImplementedException();
        }

        public static bool isBool(object obj)
        {
            throw new NotImplementedException();
        }

        public static bool isFloat(object obj)
        {
            throw new NotImplementedException();
        }

        public static uint asUint(object obj)
        {
            throw new NotImplementedException();
        }

        public static int asInt(object obj)
        {
            throw new NotImplementedException();
        }

        public static bool asBool(object obj)
        {
            throw new NotImplementedException();
        }

        public static float asFloat(object obj)
        {
            throw new NotImplementedException();
        }

        public static string toExponential(float num)
        {
            throw new NotImplementedException();
        }

        public static string toExponential(float num, int p)
        {
            throw new NotImplementedException();
        }

        public static string toFixed(float num)
        {
            throw new NotImplementedException();
        }

        public static string toFixed(float num, int p)
        {
            throw new NotImplementedException();
        }

        public static string toPrecision(float num)
        {
            throw new NotImplementedException();
        }

        public static string toPrecision(float num, int p)
        {
            throw new NotImplementedException();
        }

        public static string toString(float num)
        {
            throw new NotImplementedException();
        }

        public static string toString(float num, int p)
        {
            throw new NotImplementedException();
        }

        public static float valueOf(float num)
        {
            throw new NotImplementedException();
        }
    }
}