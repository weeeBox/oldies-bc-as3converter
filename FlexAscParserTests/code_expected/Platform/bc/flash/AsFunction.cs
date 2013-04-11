using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace bc.flash
{
    public abstract class AsFunction : AsObject
    {
        public virtual Object apply(Object target, AsArray args)
        {
            throw new NotImplementedException();
        }

        public virtual Object apply(Object target, params Object[] args)
        {
            throw new NotImplementedException();
        }

        public virtual Object invoke()
        {
            throw new FunctionInvokationException("Can't invoke function with no parameters");
        }

        public virtual Object invoke(Object param)
        {
            throw new FunctionInvokationException("Can't invoke function with 1 parameter");
        }

        public virtual Object invoke(params Object[] parameters)
        {
            throw new FunctionInvokationException("Can't invoke function with " + parameters.Length + " parameter(s)");
        }
    }

    public class FunctionRef : AsFunction
    {
        private const BindingFlags flags = BindingFlags.Public | BindingFlags.NonPublic |
                                           BindingFlags.Static | BindingFlags.Instance;

        private static Object[] EMPTY_PARAMS = new Object[0];
        private static Object[] SINGLE_PARAM = new Object[1];

        private Object target;
        private MethodInfo methodInfo;

        public FunctionRef(Object target, String name)
        {   
            if (target == null)
            {
                throw new ArgumentException("Target is null");
            }

            MethodInfo[] methods = target.GetType().GetMethods(flags);
            foreach (MethodInfo method in methods)
            {
                if (method.Name == name)
                {
                    methodInfo = method;
                    break;
                }
            }

            if (methodInfo == null)
            {
                throw new ArgumentException("Can't resolve function: " + name);
            }

            this.target = target;
        }

        public override object invoke()
        {
            return invoke(EMPTY_PARAMS);
        }

        public override Object invoke(Object param)
        {
            return invoke(singleParam(param));
        }

        public override Object invoke(params Object[] parameters)
        {
            return methodInfo.Invoke(target, parameters);
        }

        private Object[] singleParam(Object param)
        {
            SINGLE_PARAM[0] = param;
            return SINGLE_PARAM;
        }
    }

    public class FunctionInvokationException : Exception
    {
        public FunctionInvokationException(String message = "") 
            : base(message)
        {
        }
    }
}
