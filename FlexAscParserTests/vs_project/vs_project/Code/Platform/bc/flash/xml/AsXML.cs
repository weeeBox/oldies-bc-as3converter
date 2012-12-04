using System;

using bc.flash;
using bc.flash.error;
using bc.flash.xml;

namespace bc.flash.xml
{
    public class AsXML : AsObject
    {
        private AsXML mParent;
        private String mName;
        public AsXML(String name)
        {
            mName = name;
        }
        public AsXML()
            : this(null)
        {
        }
        public virtual AsXML appendChild(AsXML child)
        {
            return child;
        }
        public virtual String attributeValue(String name)
        {
            return null;
        }
        public virtual String attribute(String name)
        {
            return attributeValue(name);
        }
        public virtual AsXMLList attributes()
        {
            return null;
        }
        public virtual AsXMLList child(String propertyName)
        {
            return null;
        }
        public virtual int childIndex()
        {
            throw new AsNotImplementedError();
        }
        public virtual AsXMLList children()
        {
            return null;
        }
        public virtual AsXMLList comments()
        {
            throw new AsNotImplementedError();
        }
        public virtual bool contains(String _value)
        {
            return false;
        }
        public virtual AsXML copy()
        {
            throw new AsNotImplementedError();
        }
        public virtual AsXMLList elements(String name)
        {
            return null;
        }
        public virtual AsXMLList elements()
        {
            return elements("*");
        }
        public virtual int length()
        {
            return 1;
        }
        public virtual String name()
        {
            return mName;
        }
        public virtual String nodeKind()
        {
            throw new AsAbstractClassError();
        }
        public virtual AsXML parent()
        {
            return mParent;
        }
        public virtual String text()
        {
            return null;
        }
        public override bool hasOwnProperty(String name)
        {
            return false;
        }
    }
}
