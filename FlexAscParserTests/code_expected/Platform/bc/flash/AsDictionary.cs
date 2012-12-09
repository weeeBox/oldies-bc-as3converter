using System;
 
using bc.flash;
using System.Collections.Generic;
 
namespace bc.flash
{
	public class AsDictionary : AsObject
	{
        private Dictionary<String, Object> dictionary;

        public AsDictionary()
        {
            dictionary = new Dictionary<String, Object>();
        }

        public Object this[String key]
        {
            get
            {
                Object val = null;
                dictionary.TryGetValue(key, out val);
                return val;
            }
            set
            {
                remove(key);
                dictionary.Add(key, value);
            }
        }

        public void remove(String key)
        {
            if (dictionary.ContainsKey(key))
            {
                dictionary.Remove(key);
            }
        }
	}
}
