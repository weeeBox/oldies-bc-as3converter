package bc.test.objects
{
    import bc.flash.display.MovieClip;

    public class ObjectTypeTest
    {
        public function testNullAssignemnt() : void
        {
            var obj : Object = null;
        }
        
        public function testLiteralObject() : void
        {
            var obj : Object = {a:"This is string", b:10};
            var a : String = obj.a;
            var b : int = obj.b;
            
            obj.a = a;
            obj.b = b;
        }
        
        public function testLiteralObject2() : void
        {
            var obj : Object = {a:"This is string", b:10};
            var a : String = obj.a;
            var b : int = obj.b;
            
            obj["a"] = a;
            obj["b"] = b;
        }
        
        public function testLiteralObject3() : void
        {
            var obj : Object = null;
            
            obj = {a:"This is string", b:10};
            var a : String = obj["a"];
            var b : int = obj["b"];
            
            obj["a"] = a;
            obj["b"] = b;
        }
        
        public function testLiteralObject4() : void
        {
            var obj : Object = null;
            
            obj = {a:"This is string", b:10};
            var a : String = obj.a;
            var b : int = obj.b;
            
            obj["a"] = a;
            obj["b"] = b;
        }
        
        public function testObjectAsDictionary1() : void
        {
            var a : String = "This is string";
            var b : int = 10;
            
            var foo : Object = new Object();
            foo["a"] = a;
            foo["b"] = b;
            
            a = foo["a"];
            b = foo["b"];
        }
        
        public function testObjectAsDictionary2() : void
        {
            var a : String = "This is string";
            var b : int = 10;
            
            var foo : FooObjectClass = new FooObjectClass();
            foo["a"] = a;
            foo["b"] = b;
            
            a = foo["a"];
            b = foo["b"];
        }
        
        public function testBasicAssignment() : void
        {
            var obj : Object = null;
            
            var uinteger : uint = 10;
            var integer : int = -10;
            var number : Number = 3.14;
            var boolean : Boolean = true;
            
            obj = uinteger;
            obj = integer;
            obj = number;
            obj = boolean;
        }
        
        public function testMovieClipCall() : void
        {
            var mc : MovieClip = null;
            var mc1 : MovieClip = mc.childClip;
            var mc2 : MovieClip = mc.childClip.anotherClip;
            
            mc.childClip.anotherClip.play();
        }
        
        public function testDynamics() : void
        {
            var arr:Array = null;
            
            arr[0].someMethod();
            
            var val:Object
            val = arr[0].someField;
            val = arr[0].someMethod();
            val = arr[0].someMethod("arg1", "arg2", 0);
            val = arr[0].someField.someMethod();
            val = arr[0].someField.someMethod("arg1", "arg2", 0);
            val = arr[0].someField.anotherField.someMethod();
            val = arr[0].someField.anotherField.someMethod("arg1", "arg2", 0); 
        }
    }
}