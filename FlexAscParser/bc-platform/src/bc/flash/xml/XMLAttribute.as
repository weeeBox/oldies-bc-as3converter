package bc.flash.xml
{
	/**
	 * @author weee
	 */
	public class XMLAttribute extends bc.flash.xml.XML
	{
		private var mValue : String;
		
		public function XMLAttribute(name : String, value : String)
		{
			super(name);
			mValue = value;
		}
		
		public function value() : String
		{
			return mValue;
		}
		
		override public function nodeKind() : String
		{
			return "attribute";			
		}
	}
}
