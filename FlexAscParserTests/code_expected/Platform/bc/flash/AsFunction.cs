using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace bc.flash
{
    public class AsFunction
    {
        private const BindingFlags flags = BindingFlags.Public | BindingFlags.NonPublic |
                                           BindingFlags.Static | BindingFlags.Instance;

        private static Object[] EMPTY_PARAMS = new Object[0];
        private static Type[] EMPTY_TYPES = new Type[0];

        private Object target;
        private MethodInfo methodInfo;

        public AsFunction(Object target, String name)
            : this(target, name, EMPTY_TYPES)
        {
        }

        public AsFunction(Object target, String name, params Type[] types)
        {
            if (target == null)
            {
                throw new ArgumentException("Target is null");
            }

            methodInfo = target.GetType().GetMethod(name, flags, null, types, null);
            if (methodInfo == null)
            {
                throw new ArgumentException("Can't function: " + name);
            }

            this.target = target;
        }

        public Object apply(Object target, AsArray args)
        {
            throw new NotImplementedException();
        }

        public Object apply(Object target, params Object[] args)
        {
            throw new NotImplementedException();
        }

        public Object invoke()
        {
            return invoke(EMPTY_PARAMS);
        }

        public Object invoke(Object param)
        {
            return invoke(new Object[] { param });
        }

        public Object invoke(params Object[] parameters)
        {
            return parameters == null ? invoke(new Object[] { null }) : 
                   methodInfo.Invoke(target, parameters);
        }
    }
}
