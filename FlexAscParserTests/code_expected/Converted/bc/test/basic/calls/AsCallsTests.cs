using System;

using bc.flash;
using bc.test.basic.calls;

namespace bc.test.basic.calls
{
    public class AsCallsTests : AsObject
    {
        private AsCallsTests instance;
        public AsCallsTests()
        {
        }
        private void testMemberCall()
        {
            instance.func();
        }
        private void testMemberCallWithArg()
        {
            instance.funcWithArg("arg");
        }
        public virtual void testMemberCallWithArgs()
        {
            instance.funcWithArgs("arg1", "arg2");
        }
        public virtual void testMemberCallWithRestArgs()
        {
            instance.funcWithRestArgs();
        }
        public virtual void testMemberCallWithRestArgs2()
        {
            instance.funcWithRestArgs("arg");
        }
        public virtual void testMemberCallWithRestArgs3()
        {
            instance.funcWithRestArgs("arg1", "arg2", "arg3");
        }
        public virtual void testMemberCallWithUntypedArg()
        {
            instance.funcWithUntypedArg(10);
        }
        public virtual void testMemberCallWithUntypedArg2()
        {
            instance.funcWithUntypedArg("arg");
        }
        public virtual void testMemberCallWithUntypedArg3()
        {
            Object arg = new Object();
            instance.funcWithUntypedArg(arg);
        }
        public virtual void testMemberCallWithArgsAndRestArgs()
        {
            instance.funcWithArgsAndRestArgs("arg1", "arg2");
        }
        public static void testMemberCallStaticFunc()
        {
            AsCallsTests.staticFunc();
        }
        public virtual void func()
        {
        }
        public virtual void funcWithArg(String arg)
        {
        }
        public virtual void funcWithArgs(String arg1, String arg2)
        {
        }
        public virtual void funcWithRestArgs(params Object[] args)
        {
        }
        public virtual void funcWithUntypedArg(Object arg)
        {
        }
        public virtual void funcWithArgsAndRestArgs(String arg1, String arg2, params Object[] args)
        {
        }
        public static void staticFunc()
        {
        }
        public virtual String funcWithReturnType()
        {
            return null;
        }
        public virtual String funcWithArgAndReturnType(String arg)
        {
            return null;
        }
        public virtual String funcWithArgsAndReturnType(String arg1, String arg2)
        {
            return null;
        }
        public virtual String funcWithRestArgsAndReturnType(params Object[] args)
        {
            return null;
        }
        public virtual String funcWithUntypedArgAndReturnType(Object arg)
        {
            return null;
        }
        public virtual Object funcWithUntypedArgAndUndefinedReturnType(Object arg)
        {
            return null;
        }
        public virtual String funcWithArgsAndRestArgsAndReturnType(String arg1, String arg2, params Object[] args)
        {
            return null;
        }
        public static String staticFuncWithReturnType()
        {
            return null;
        }
    }
}
