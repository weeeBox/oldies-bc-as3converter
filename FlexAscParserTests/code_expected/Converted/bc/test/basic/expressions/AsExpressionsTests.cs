using System;

using bc.flash;

namespace bc.test.basic.expressions
{
    public class AsExpressionsTests : AsObject
    {
        public virtual void testAssignment()
        {
            int a = 10;
            int b = 20;
            Object c = a + b;
        }
        public virtual void testAssignment2()
        {
            int a = 10;
            int b = 20;
            Object c = a + b;
        }
        public virtual void testAssignment3()
        {
            int a = 10;
            int b = 20;
            int c = 30;
            Object d = a + b + c;
        }
        public virtual void testAssignment4()
        {
            int a = 10;
            int b = 20;
            int c = 30;
            Object d = (a + b) + c;
        }
        public virtual void testAssignment5()
        {
            int a = 10;
            int b = 20;
            int c = 30;
            Object d = a + (b + c);
        }
        public virtual void testAssignment6()
        {
            float a = 10;
            float b = 20;
            int c = ((int)(a + b));
        }
        public virtual void testPriority()
        {
            int a = 10;
            int b = 20;
            int c = 30;
            Object d = (a + b) * c;
        }
        public virtual void testPriority2()
        {
            int a = 10;
            int b = 20;
            int c = 30;
            Object d = a * (b + c);
        }
        public virtual void testBooleans()
        {
            int a = 10;
            int b = 20;
            bool c = false;
            Object d = c && (a + b) != 0;
        }
        public virtual void testBooleans2()
        {
            int a = 10;
            int b = 20;
            bool c = false;
            Object d = (a + b) != 0 && c;
        }
        public virtual void testBooleans3()
        {
            int a = 10;
            int b = 20;
            Object d = (a + b) == 0 && (a + b) != 0;
        }
        public virtual void testBooleans4()
        {
            int a = 10;
            int b = 20;
            bool c = false;
            Object d = (a + b) != 0 && (a + b) == 0;
        }
    }
}
