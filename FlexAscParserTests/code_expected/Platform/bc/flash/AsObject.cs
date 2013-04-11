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

        public AsFunction __function(String name)
        {
            return new FunctionRef(this, name);
        }

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
