using System;

using bc.flash;
using System.Collections.Generic;

namespace bc.flash
{
    public class AsObject
    {
        private Dictionary<String, Object> mProperties;
        
        public virtual bool hasOwnProperty(String name)
        {
            return mProperties != null && mProperties.ContainsKey(name);
        }

        public virtual Object getOwnProperty(String name)
        {
            return hasOwnProperty(name) ? mProperties[name] : null;
        }

        public virtual void setOwnProperty(String name, Object _value)
        {
            if (mProperties == null)
            {
                mProperties = new Dictionary<String, Object>();
            }
            if (mProperties.ContainsKey(name))
            {
                mProperties.Remove(name);
            }
            mProperties[name] = _value;
        }

        public virtual void deleteOwnProperty(String name)
        {
            if (hasOwnProperty(name))
            {
                mProperties.Remove(name);
            }
        }

        #region Functions

        public AsFunction __function(String name)
        {
            return new AsFunction(this, name);
        }

        public AsFunction __function(Action func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<Result>(Func<Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1>(Action<T1> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, Result>(Func<T1, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2>(Action<T1, T2> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, Result>(Func<T1, T2, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3>(Action<T1, T2, T3> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, Result>(Func<T1, T2, T3, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4>(Action<T1, T2, T3, T4> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, Result>(Func<T1, T2, T3, T4, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5>(Action<T1, T2, T3, T4, T5> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, Result>(Func<T1, T2, T3, T4, T5, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6>(Action<T1, T2, T3, T4, T5, T6> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, Result>(Func<T1, T2, T3, T4, T5, T6, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7>(Action<T1, T2, T3, T4, T5, T6, T7> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, Result>(Func<T1, T2, T3, T4, T5, T6, T7, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8>(Action<T1, T2, T3, T4, T5, T6, T7, T8> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9>(Action<T1, T2, T3, T4, T5, T6, T7, T8, T9> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(Action<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(Action<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(Action<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(Action<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(Action<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Result> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(Action<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> func) { return new AsFunction(this, func.Method); }

        public AsFunction __function<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Result>(Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Result> func) { return new AsFunction(this, func.Method); }

        public virtual Object apply(Object thisArg = null, AsArray argArray = null)
        {
            throw new NotImplementedException();
        }

        public virtual Object apply(Object thisArg = null, params Object[] args)
        {
            throw new NotImplementedException();
        }

        public virtual Object call(Object thisArg = null, params Object[] args)
        {
            throw new NotImplementedException();
        }

        #endregion

        public virtual String toString()
        {
            return "Object";
        }

        public static Object createLiteralObject(params Object[] values)
        {
            if (values.Length % 2 != 0)
            {
                throw new ArgumentException("Literal object arguments' length should be even: " + values);
            }

            AsObject obj = new AsObject();
            for (int i = 0; i < values.Length; i += 2)
            {
                String key = (String) values[i];
                Object value = values[i + 1];
                obj.setOwnProperty(key, value);
            }
            return obj;
        }
    }
}
