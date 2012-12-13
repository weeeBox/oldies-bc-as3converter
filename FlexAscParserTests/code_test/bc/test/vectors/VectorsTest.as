package bc.test.vectors
{
    public class VectorsTest
    {
        public function testVectorCreation() : void
        {
            var v : Vector.<String> = new Vector.<String>();
            v = new<String>[];
            v = new<String>["a", "b", "c"];
            v = Vector.<String>(["a", "b", "c"]);
        }
        
        public function testVectorPush() : void
        {
            var v : Vector.<String> = new<String>[];
            v.push();
            v.push("a");
            v.push("a", "b", "c");
            v.push(["a", "b", "c"]);
        }
        
        public function testVectorIndexer() : void
        {
            var v : Vector.<String> = new<String>[];
            v[0] = "a";
            var val : String = v[0];
        }
        
        public function testVectorPop() : void
        {
            var v:Vector.<String> = new<String>["a"];
            var element:String = v.pop();
        }
        
        public function testVectorCast() : void
        {
            var obj : Object = new Vector.<String>();
            var vector : Vector.<String> = Vector.<String>(obj);
        }
        
        public function testVectorQualifiedCast() : void
        {
            var obj : Object = new Vector.<bc.test.vectors.VectorsTest>();
            var vector : Vector.<bc.test.vectors.VectorsTest> = Vector.<bc.test.vectors.VectorsTest>(obj);
        }
        
        public function testVectorQaulifiedTernaryCast() : void
        {
            var obj : Object = new Vector.<bc.test.vectors.VectorsTest>();
            var vector : Vector.<bc.test.vectors.VectorsTest> = obj != null ? Vector.<bc.test.vectors.VectorsTest>(obj) : null;
            vector = obj == null ? null : Vector.<bc.test.vectors.VectorsTest>(obj);
        }
        
        public function testVectorQualifiedAsOperator() : void
        {
            var obj : Object = new Vector.<bc.test.vectors.VectorsTest>();
            var vector : Vector.<bc.test.vectors.VectorsTest> = obj as Vector.<bc.test.vectors.VectorsTest>;
        }
    }
}