package bc.test.basic.packages
{
    import bc.flash.text.TextField;
    import bc.test.basic.packages.bar.PackageClass;
    import bc.test.basic.packages.foo.PackageClass;
    
    import flash.text.TextField;

    public class PackagesTests
    {
        public var foo1 : bc.test.basic.packages.foo.PackageClass;
        public var foo2 : bc.test.basic.packages.bar.PackageClass;
        
        public function testLocalVariables() : void
        {
            var foo1 : bc.test.basic.packages.foo.PackageClass;
            var foo2 : bc.test.basic.packages.bar.PackageClass;
        }
        
        public function testFuncParams(foo1 : bc.test.basic.packages.foo.PackageClass, foo2 : bc.test.basic.packages.bar.PackageClass) : void
        {
            this.foo1 = foo1;
            this.foo2 = foo2;
        }
        
        public function testReturnType() : bc.test.basic.packages.foo.PackageClass
        {
            return null;
        }
        
        public function testAssigment() : void
        {
            foo1 = new bc.test.basic.packages.foo.PackageClass();
            foo2 = new bc.test.basic.packages.bar.PackageClass();
        }
        
        public function testCast() : void
        {
            var obj1 : Object = new bc.test.basic.packages.foo.PackageClass();
            var obj2 : Object = new bc.test.basic.packages.bar.PackageClass();
            
            foo1 = bc.test.basic.packages.foo.PackageClass(obj1);
            foo2 = bc.test.basic.packages.bar.PackageClass(obj2);
        }
        
        public function testGeneric() : void
        {
            var v1 : Vector.<bc.test.basic.packages.foo.PackageClass> = new Vector.<bc.test.basic.packages.foo.PackageClass>();
            var v2 : Vector.<bc.test.basic.packages.bar.PackageClass> = new Vector.<bc.test.basic.packages.bar.PackageClass>();
            
            v1.push(foo1);
            v2.push(foo2);
            
            foo1 = v1[0];
            foo2 = v2[0];
        }
        
        public function testStaticCall() : void
        {
            bc.flash.text.TextField.isFontCompatible(null, null);
        }
        
        public function testApiAssignment() : void
        {
            var textField : flash.text.TextField = new flash.text.TextField();
        }
        
        public function testApiStaticCall() : void
        {
            flash.text.TextField.isFontCompatible(null, null);
        }
        
        public function testApiAsOperator() : void
        {
            var obj : Object = new flash.text.TextField();
            var textField : flash.text.TextField = obj as flash.text.TextField;
        }
        
        public function testApiIsOperator() : void
        {
            var obj : Object = new flash.text.TextField();
            var flag : Boolean = obj is flash.text.TextField;
        }
    }
}