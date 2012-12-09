using System;
 
using bc.flash;
using bc.flash.xml;
 
namespace bc.flash.xml
{
	public class AsXMLElement : AsXML
	{
		private bc.flash.xml.AsXMLList mAttributes;
		private bc.flash.xml.AsXMLList mChildren;
		public AsXMLElement(String name)
		 : base(name)
		{
			mAttributes = new bc.flash.xml.AsXMLList();
			mChildren = new bc.flash.xml.AsXMLList();
		}
		public override bc.flash.xml.AsXML appendChild(bc.flash.xml.AsXML child)
		{
			return mChildren.appendChild(child);
		}
		public virtual bc.flash.xml.AsXML appendAttribute(String name, String _value)
		{
			return mAttributes.appendChild(new AsXMLAttribute(name, _value));
		}
		public override bool hasOwnProperty(String name)
		{
			return mAttributes.containsChild(name);
		}
		public override String attributeValue(String arg)
		{
			AsVector<AsXML> __attrs_ = mAttributes.list();
			if (__attrs_ != null)
			{
				foreach (AsXML attr in __attrs_)
				{
					if((attr.name() == arg))
					{
						return ((AsXMLAttribute)(attr))._value();
					}
				}
			}
			return null;
		}
		public override bc.flash.xml.AsXMLList attributes()
		{
			return mAttributes;
		}
		public override bc.flash.xml.AsXMLList child(String name)
		{
			bc.flash.xml.AsXMLList result = new bc.flash.xml.AsXMLList();
			AsVector<AsXML> __childs_ = mChildren.list();
			if (__childs_ != null)
			{
				foreach (bc.flash.xml.AsXML child in __childs_)
				{
					if((child.name() == name))
					{
						result.appendChild(child);
					}
				}
			}
			return result;
		}
		public override bc.flash.xml.AsXMLList children()
		{
			return mChildren;
		}
		public override bool contains(String name)
		{
			AsVector<AsXML> __childs_ = mChildren.list();
			if (__childs_ != null)
			{
				foreach (AsXML child in __childs_)
				{
					if((child.name() == name))
					{
						return true;
					}
				}
			}
			return false;
		}
		public override bc.flash.xml.AsXMLList elements(String name)
		{
			if((name == "*"))
			{
				return mChildren;
			}
			bc.flash.xml.AsXMLList result = new bc.flash.xml.AsXMLList();
			AsVector<AsXML> __childs_ = mChildren.list();
			if (__childs_ != null)
			{
				foreach (bc.flash.xml.AsXML child in __childs_)
				{
					if(((child.name() == name) && (child.nodeKind() == nodeKind())))
					{
						result.appendChild(child);
					}
				}
			}
			return result;
		}
		public virtual bc.flash.xml.AsXMLList elements()
		{
			return elements("*");
		}
		public override String nodeKind()
		{
			return "element";
		}
	}
}
