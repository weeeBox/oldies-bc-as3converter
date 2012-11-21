package bc.test.objects
{
    public class ObjectTypeTest
    {
        public function ObjectTypeTest()
        {
            var obj : Object = null;
            
            obj = {a:"This is string", b:10};
            var a : String = obj.a;
            var b : int = obj.b;
            
            a = obj["a"];
            b = obj["b"];
            
            obj = "This is string";
            
            var foo : FooObjectClass = new FooObjectClass();
            foo["a"] = a;
            foo["b"] = b;
            
            a = foo["a"];
            b = foo["b"];
        }
    }
}