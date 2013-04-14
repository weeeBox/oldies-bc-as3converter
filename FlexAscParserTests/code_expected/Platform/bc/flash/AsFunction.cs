﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace bc.flash
{
    public class AsFunction : AsObject
    {
        private const BindingFlags flags = BindingFlags.Public | BindingFlags.NonPublic |
                                           BindingFlags.Static | BindingFlags.Instance;

        private static Object[] EMPTY_PARAMS = new Object[0];
        private static Object[] SINGLE_PARAM = new Object[1];

        private Object target;
        private MethodInfo methodInfo;

        public AsFunction(Object target, String name)
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

        public AsFunction(Object target, MethodInfo info)
        {
            this.target = target;
            this.methodInfo = info;
        }

        public Object invoke()
        {
            return invoke(EMPTY_PARAMS);
        }

        public Object invoke(Object param)
        {
            return invoke(singleParam(param));
        }

        public Object invoke(params Object[] parameters)
        {
            return methodInfo.Invoke(target, parameters);
        }

        public Object apply(Object thisArg = null, Object argArray = null)
        {
            throw new NotImplementedException();
        }

        public Object call(Object thisArg = null, params Object[] args)
        {
            throw new NotImplementedException();
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
