using System;

using bc.flash;

namespace bc.test.basic.types
{
    public class AsTypesTests : AsObject
    {
        public AsTypesTests()
        {
        }
        public virtual void testIntegrals()
        {
            int integer = 0;
            uint uinteger = 0;
            float number = 0;
            bool boolean = false;
        }
        public virtual void testAssignments()
        {
            int integer = -10;
            uint uinteger = (uint)(10);
            float number = 3.14f;
            bool boolean = true;
            integer = (int)(uinteger);
            uinteger = (uint)(integer);
            number = integer;
            number = uinteger;
            integer = (int)(number);
            uinteger = (uint)(number);
            boolean = false;
        }
        public virtual void testExpressions()
        {
            int integer = -10;
            uint uinteger = (uint)(10);
            float number = 3.14f;
            float value1 = uinteger + number;
            int value2 = (int)(uinteger + number);
            uint value3 = (uint)(uinteger + number);
            value1 = integer + uinteger;
            value2 = (int)(integer + uinteger);
            value3 = (uint)(integer + uinteger);
            value1 = integer + uinteger + number;
            value2 = (int)(integer + uinteger + number);
            value3 = (uint)(integer + uinteger + number);
        }
        public virtual void testNumberCalls()
        {
            float num = 10;
            String str = null;
            str = num.toExponential(10);
            str = num.toFixed(10);
            str = num.toPrecision(10);
            str = num.toString();
            str = num.toString(10);
            num = num.valueOf();
            num = AsNumber.MAX_VALUE;
            num = AsNumber.MIN_VALUE;
            num = AsNumber.NaN;
            num = AsNumber.NEGATIVE_INFINITY;
            num = AsNumber.POSITIVE_INFINITY;
            int i = 0;
            i = AsInt.MAX_VALUE;
            i = AsInt.MIN_VALUE;
            uint ui = 0;
            ui = AsUint.MAX_VALUE;
            ui = AsUint.MIN_VALUE;
        }
    }
}
