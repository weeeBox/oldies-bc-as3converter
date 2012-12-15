using System;
 
using bc.flash;
using bc.flash.text;
using bc.test.basic.packages.bar;
using bc.test.basic.packages.foo;
// using AsPackageClass = bc.test.basic.packages.bar.AsPackageClass;
// using AsPackageClass = bc.test.basic.packages.foo.AsPackageClass;
 
namespace bc.test.basic.packages
{
	public class AsPackagesTests : AsObject
	{
		public bc.test.basic.packages.foo.AsPackageClass foo1;
		public bc.test.basic.packages.bar.AsPackageClass foo2;
		public virtual void testLocalVariables()
		{
			bc.test.basic.packages.foo.AsPackageClass foo1 = null;
			bc.test.basic.packages.bar.AsPackageClass foo2 = null;
		}
		public virtual void testFuncParams(bc.test.basic.packages.foo.AsPackageClass foo1, bc.test.basic.packages.bar.AsPackageClass foo2)
		{
			this.foo1 = foo1;
			this.foo2 = foo2;
		}
		public virtual bc.test.basic.packages.foo.AsPackageClass testReturnType()
		{
			return null;
		}
		public virtual void testAssigment()
		{
			foo1 = new bc.test.basic.packages.foo.AsPackageClass();
			foo2 = new bc.test.basic.packages.bar.AsPackageClass();
		}
		public virtual void testCast()
		{
			Object obj1 = new bc.test.basic.packages.foo.AsPackageClass();
			Object obj2 = new bc.test.basic.packages.bar.AsPackageClass();
			foo1 = ((bc.test.basic.packages.foo.AsPackageClass)(obj1));
			foo2 = ((bc.test.basic.packages.bar.AsPackageClass)(obj2));
		}
		public virtual void testGeneric()
		{
			AsVector<bc.test.basic.packages.foo.AsPackageClass> v1 = new AsVector<bc.test.basic.packages.foo.AsPackageClass>();
			AsVector<bc.test.basic.packages.bar.AsPackageClass> v2 = new AsVector<bc.test.basic.packages.bar.AsPackageClass>();
			v1.push(foo1);
			v2.push(foo2);
			foo1 = v1[0];
			foo2 = v2[0];
		}
		public virtual void testNewApi()
		{
			bc.flash.text.AsTextField textField = new bc.flash.text.AsTextField();
		}
		public virtual void testNewApiAsOperator()
		{
			Object obj = new bc.flash.text.AsTextField();
			AsTextField textField = obj as bc.flash.text.AsTextField;
		}
	}
}
