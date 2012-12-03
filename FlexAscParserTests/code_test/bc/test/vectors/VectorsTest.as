package bc.test.vectors
{
    public class VectorsTest
    {
        public function VectorsTest()
        {
            var v : Vector.<String> = new Vector.<String>();
            v = new<String>[];
            v = new<String>["a", "b", "c"];
            v = Vector.<String>(["a", "b", "c"]);
            
            var value : String = v[0];
            v[0] = value;
        }
    }
}