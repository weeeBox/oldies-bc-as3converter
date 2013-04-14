using System;

using bc.flash;
using bc.test.functions.types;

namespace bc.test.functions.types
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
        public virtual void testApplyFunction(AsArray args)
        {
            mFunc.apply(null, args);
        }
        public virtual void testApplyFunction(params Object[] args)
        {
            mFunc.apply(null, args);
        }
        public virtual void testCallFunction(Object a, String b)
        {
            mFunc.call(null, a, b);
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
