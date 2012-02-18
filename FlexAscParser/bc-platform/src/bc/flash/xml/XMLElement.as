package bc.flash.xml
{
	/**
	 * @author weee
	 */
	public class XMLElement extends bc.flash.xml.XML
	{
		private var mAttributes : bc.flash.xml.XMLList;
		private var mChildren : bc.flash.xml.XMLList;
		
		public function XMLElement(name : String)
		{
			super(name);
			mAttributes = new bc.flash.xml.XMLList();
			mChildren = new bc.flash.xml.XMLList();			 
		}
		
		override public function appendChild(child : XML) : XML
		{
			return mChildren.appendChild(child);
		}
		
		override public function attributeValue(arg : String) : String
		{	
			for each (var attr : XML in mAttributes.list())
			{
				if (attr.name() == arg)
				{
					return XMLAttribute(attr).value();			
				}
			}
			return null;
		}		

		override public function attributes() : bc.flash.xml.XMLList
		{
			return mAttributes;
		}

		override public function child(name : String) : bc.flash.xml.XMLList
		{
			var result : bc.flash.xml.XMLList = new bc.flash.xml.XMLList();
			
			for each (var child : XML in mChildren.list())
			{
				if (child.name() == name)
				{
					result.appendChild(child);
				}
			}
			
			return result;
		}

		override public function children() : bc.flash.xml.XMLList
		{
			return mChildren;			 
		}

		override public function contains(name : String) : Boolean
		{
			for each (var child : XML in mChildren.list())
			{
				if (child.name() == name)
				{
					return true;
				}
			}
			return false;
		}

		override public function elements(name : String = "*") : bc.flash.xml.XMLList
		{
			if (name == "*")
			{
				return mChildren;
			}
			
			var result : bc.flash.xml.XMLList = new bc.flash.xml.XMLList();
			
			for each (var child : XML in mChildren.list())
			{
				if (child.name() == name && child.nodeKind() == nodeKind())
				{
					result.appendChild(child);
				}
			}
			
			return result;
		}

		override public function nodeKind() : String
		{
			return "element";
		}
	}
}
