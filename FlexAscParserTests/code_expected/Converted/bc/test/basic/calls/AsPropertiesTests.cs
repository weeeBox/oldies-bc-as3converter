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
			field(_value);
			_value = field();
		}
		public virtual void testThisProperties()
		{
			String _value = "Some string";
			this.field(_value);
			_value = this.field();
		}
		public virtual void testVisiblity()
		{
			String field = "Some string";
			this.field(field);
			field = this.field();
		}
		public virtual void testRigthFunctionCall()
		{
			field(field() + " more string");
			Object _value = field() + " more string";
		}
		public virtual String field()
		{
			return mField;
		}
		public virtual void field(String _value)
		{
			mField = _value;
		}
	}
}
