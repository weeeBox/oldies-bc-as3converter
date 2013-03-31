using System;

using bc.flash;

namespace bc.test.functions
{
    public class AsFunctionsReflectionTest : AsObject
    {
        public AsFunction func;
        public virtual void testAssignment()
        {
            func = new AsFunction(this, "someFunction");
        }
        public virtual void someFunction()
        {
        }
        public virtual void someFunctionWithArg(int arg)
        {
        }
    }
}
