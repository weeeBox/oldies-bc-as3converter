using System;
 
using bc.flash;
using bc.flash.xml;
 
namespace bc.flash.xml
{
	public class AsXMLAttribute : AsXML
	{
		private String mValue;
		public AsXMLAttribute(String name, String _value)
		 : base(name)
		{
			mValue = _value;
		}
		public virtual String _value()
		{
			return mValue;
		}
		public override String nodeKind()
		{
			return "attribute";
		}
	}
}
