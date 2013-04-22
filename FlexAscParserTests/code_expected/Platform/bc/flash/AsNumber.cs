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

        public String toExponential(uint fractionDigits) 
        { 
            throw new NotImplementedException(); 
        }

        public String toFixed(uint fractionDigits) 
        { 
            throw new NotImplementedException(); 
        }

        public String toPrecision(uint precision) 
        { 
            throw new NotImplementedException(); 
        }

        public String toString(float radix = 10) 
        { 
            throw new NotImplementedException(); 
        }

        public float valueOf() 
        { 
            throw new NotImplementedException(); 
        }
    }

    #region Single Extension

    public static class SingleExtension
    {
        public static String toExponential(this Single val, uint fractionDigits)
        {
            throw new NotImplementedException();
        }

        public static String toFixed(this Single val, uint fractionDigits)
        {
            throw new NotImplementedException();
        }

        public static String toPrecision(this Single val, uint precision)
        {
            throw new NotImplementedException();
        }

        public static String toString(this Single val, float radix = 10)
        {
            throw new NotImplementedException();
        }

        public static float valueOf(this Single val)
        {
            throw new NotImplementedException();
        }
    }

    #endregion

    #region Int32 Extension

    public static class Int32Extension
    {
        public static String toExponential(this Int32 val, uint fractionDigits)
        {
            throw new NotImplementedException();
        }

        public static String toFixed(this Int32 val, uint fractionDigits)
        {
            throw new NotImplementedException();
        }

        public static String toPrecision(this Int32 val, uint precision)
        {
            throw new NotImplementedException();
        }

        public static String toString(this Int32 val, uint radix)
        {
            throw new NotImplementedException();
        }

        public static int valueOf(this Int32 val)
        {
            throw new NotImplementedException();
        }
    }

    #endregion

    #region UInt32 Extension

    public static class UInt32Extension
    {
        public static String toExponential(this UInt32 val, uint fractionDigits)
        {
            throw new NotImplementedException();
        }

        public static String toFixed(this UInt32 val, uint fractionDigits)
        {
            throw new NotImplementedException();
        }

        public static String toPrecision(this UInt32 val, uint precision)
        {
            throw new NotImplementedException();
        }

        public static String toString(this UInt32 val, uint radix)
        {
            throw new NotImplementedException();
        }

        public static uint valueOf(this UInt32 val)
        {
            throw new NotImplementedException();
        }
    }

    #endregion
}