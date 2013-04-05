using System;

using bc.flash;
using bc.test.functions;

namespace bc.test.functions
{
    public class AsFunctionsTypeTest : AsObject
    {
        private AsFunction mFunc;
        private AsFunctionsTypeTest mObj;
        public virtual void testFunctionAssignment()
        {
            mFunc = __function("functionDefaultCallback");
            mObj.mFunc = __function("functionDefaultCallback");
            mFunc = mObj.__function("functionDefaultCallback");
            mObj.mFunc = mObj.__function("functionDefaultCallback");
        }
        public virtual void testFunctionUsage()
        {
            mFunc.invoke("This is string", "This is another string");
            mObj.mFunc.invoke("This is string", "This is another string");
        }
        public virtual void testFunctionArgument()
        {
            accept(__function("functionDefaultCallback"));
            mObj.accept(__function("functionDefaultCallback"));
        }
        public virtual void testFunctionApply()
        {
            AsArray args = new AsArray("This is string", "This is another string");
            __function("functionDefaultCallback").apply(null, args);
            mObj.__function("functionDefaultCallback").apply(null, args);
        }
        public virtual void argumentsFunction(String a, String b)
        {
            mFunc.apply(null, a, b);
        }
        public virtual void argumentsCastFunction(Object a, String b)
        {
            mFunc.apply(null, a, b);
        }
        private void accept(AsFunction func)
        {
            func.invoke("This is string", "This is another string");
        }
        private void functionDefaultCallback(String a, String b)
        {
        }
    }
}
