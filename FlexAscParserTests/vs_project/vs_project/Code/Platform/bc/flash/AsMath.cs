using System;

using bc.flash;
using bc.flash.error;

namespace bc.flash
{
    public sealed class AsMath : AsObject
    {
        public const float E = 2.718281828459045f;
        public const float LN10 = 2.302585092994046f;
        public const float LN2 = 0.6931471805599453f;
        public const float LOG10E = 0.4342944819032518f;
        public const float LOG2E = 1.4426950408889634f;
        public const float PI = 3.141592653589793f;
        public const float SQRT1_2 = 0.7071067811865476f;
        public const float SQRT2 = 1.4142135623730951f;

        private static Random mRandom = new Random();

        public static float abs(int x)
        {
            return Math.Abs(x);
        }

        public static float abs(float x)
        {
            return Math.Abs(x);
        }

        public static float acos(float x)
        {
            return (float)Math.Acos(x);
        }

        public static float asin(float x)
        {
            return (float)Math.Asin(x);
        }

        public static float atan(float x)
        {
            return (float)Math.Atan(x);
        }

        public static float atan2(float y, float x)
        {
            return (float)Math.Atan2(y, x);
        }

        public static float ceil(float x)
        {
            return (float)Math.Ceiling(x);
        }

        public static float cos(float x)
        {
            return (float)Math.Cos(x);
        }

        public static float exp(float x)
        {
            return (float)Math.Exp(x);
        }

        public static float floor(float x)
        {
            return (float)Math.Floor(x);
        }

        public static float log(float x)
        {
            return (float)Math.Log(x);
        }

        public static float max(float x, float y)
        {
            return Math.Max(x, y);
        }

        public static float min(int x, int y)
        {
            return Math.Min(x, y);
        }

        public static float max(int x, int y)
        {
            return Math.Max(x, y);
        }

        public static float min(float x, float y)
        {
            return Math.Min(x, y);
        }

        public static float max(long x, long y)
        {
            return Math.Max(x, y);
        }

        public static float min(long x, long y)
        {
            return Math.Min(x, y);
        }

        public static float pow(float x, float y)
        {
            return (float)Math.Pow(x, y);
        }

        public static float random()
        {
            return (float)mRandom.NextDouble();
        }

        public static float round(float x)
        {
            return (float)Math.Round(x);
        }

        public static float sin(float x)
        {
            return (float)Math.Sin(x);
        }

        public static float sqrt(float x)
        {
            return (float)Math.Sqrt(x);
        }

        public static float tan(float x)
        {
            return (float)Math.Tan(x);
        }
    }
}
