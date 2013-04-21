using System;

using bc.flash;

namespace bc.test.objects.dynamics
{
    public class AsDynamicMethodCallTest : AsObject
    {
        private Object mObject;
        public virtual void testDynamicMethod()
        {
            String methodName = "someMethod";
            Object obj = new AsObject();

            obj.__function(methodName).invoke();
            mObject.__function(methodName).invoke();
            this.mObject.__function(methodName).invoke();
            getObject().__function(methodName).invoke();
            this.getObject().__function(methodName).invoke();

            obj.__function(methodName).invoke("arg");
            mObject.__function(methodName).invoke("arg");
            this.mObject.__function(methodName).invoke("arg");
            getObject().__function(methodName).invoke("arg");
            this.getObject().__function(methodName).invoke("arg");
        }
        public virtual Object getObject()
        {
            return mObject;
        }
    }
}
