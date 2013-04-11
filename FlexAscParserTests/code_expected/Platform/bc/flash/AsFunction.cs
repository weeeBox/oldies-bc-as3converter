using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace bc.flash
{
    public abstract class AsFunction : AsObject
    {
        public abstract Object apply(Object target, AsArray args);
        public abstract Object apply(Object target, params Object[] args);

        public abstract Object invoke();
        public abstract Object invoke(Object param);
        public abstract Object invoke(params Object[] parameters);
    }

    public class FunctionRef : AsFunction
    {
        private const BindingFlags flags = BindingFlags.Public | BindingFlags.NonPublic |
                                           BindingFlags.Static | BindingFlags.Instance;

        private static Object[] EMPTY_PARAMS = new Object[0];

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

        public override Object apply(Object target, AsArray args)
        {
            throw new NotImplementedException();
        }

        public override Object apply(Object target, params Object[] args)
        {
            throw new NotImplementedException();
        }

        public override Object invoke()
        {
            return invoke(EMPTY_PARAMS);
        }

        public override Object invoke(Object param)
        {
            return invoke(new Object[] { param });
        }

        public override Object invoke(params Object[] parameters)
        {
            return parameters == null ? invoke(new Object[] { null }) : 
                   methodInfo.Invoke(target, parameters);
        }
    }
}
