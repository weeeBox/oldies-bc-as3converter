using System;
 
using bc.flash;
 
namespace bc.test.basic.calls
{
	public class AsPropertiesTests : AsObject
	{
		private String mField;
		public virtual void testProperties()
		{
			String _value = "Some string";
			setField(_value);
			_value = getField();
		}
		public virtual void testThisProperties()
		{
			String _value = "Some string";
			this.setField(_value);
			_value = this.getField();
		}
		public virtual void testVisiblity()
		{
			String field = "Some string";
			this.setField(field);
			field = this.getField();
		}
		public virtual void testRigthFunctionCall()
		{
			setField((getField() + " more string"));
			Object _value = (getField() + " more string");
		}
		public virtual String getField()
		{
			return mField;
		}
		public virtual void setField(String _value)
		{
			mField = _value;
		}
	}
}
