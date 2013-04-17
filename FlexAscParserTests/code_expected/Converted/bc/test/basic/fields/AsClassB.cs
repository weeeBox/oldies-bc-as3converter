using System;
 
using bc.flash;
using bc.test.basic.fields;
 
namespace bc.test.basic.fields
{
	public class AsClassB : AsClassA
	{
		public Object memberA1;
		public Object memberA2 = null;
		public Object memberA3 = new AsObject();
		public Object memberA4;
		public String memberB1;
		public String memberB2 = null;
		public String memberB3 = "This is a string";
		public String memberB4;
		public int memberC1;
		public int memberC2 = 10;
		public int memberC3;
		public int a1;
		public int b1;
		public int c1;
		public int d1 = 0;
		private void __internalInitializeFields()
		{
			memberA4 = memberA3;
			memberB4 = memberB3;
			memberC3 = memberB1.Length;
		}
		public AsClassB()
		 : base("Some arg")
		{
			__internalInitializeFields();
			String otherVariable = "Some string";
		}
	}
}
