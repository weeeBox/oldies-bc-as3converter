using System;

using bc.flash;
using bc.flash.error;
using bc.flash.xml;
using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;

namespace bc.flash.xml
{
    public class AsXMLList : AsObject
    {
        private AsVector<AsXML> mElements;
        public AsXMLList()
        {
            mElements = new AsVector<AsXML>(0);
        }
        public virtual AsXML appendChild(AsXML child)
        {
            mElements.push(child);
            return child;
        }
        public AsXML this[int i]
        {
            get
            {
                if (i < mElements.getLength())
                {
                    return mElements[i];
                }
                return null;
            }
        }
        public virtual bool containsChild(String name)
        {
            foreach (AsXML element in mElements)
            {
                if ((name == element.name()))
                {
                    return true;
                }
            }
            return false;
        }
        public virtual AsVector<AsXML> list()
        {
            return mElements;
        }
        public virtual AsXMLList attribute(String arg)
        {
            throw new AsNotImplementedError();
        }
        public virtual AsXMLList attributes()
        {
            throw new AsNotImplementedError();
        }
        public virtual AsXMLList child(String propertyName)
        {
            throw new AsNotImplementedError();
        }
        public virtual int childIndex()
        {
            throw new AsNotImplementedError();
        }
        public virtual AsXMLList children()
        {
            throw new AsNotImplementedError();
        }
        public virtual AsXMLList copy()
        {
            throw new AsNotImplementedError();
        }
        public virtual int length()
        {
            return (int)(mElements.getLength());
        }
        public virtual String name()
        {
            throw new AsNotImplementedError();
        }
        public virtual String nodeKind()
        {
            throw new AsNotImplementedError();
        }

        public IEnumerator GetEnumerator()
        {
            return mElements.GetEnumerator();
        }
    }
}
