package bc.test.arrays
{
    public class ArraysTest
    {
        public function ArraysTest()
        {
            var oneArray:Array = new Array();
            var twoArray:Array = new Array("a", "b", "c");
            var threeArray:Array = ["a", "b", "c"];
            var fourArray:Array = Array(["a", "b", "c"]);
            
            oneArray.push();
            oneArray.push("a");
            oneArray.push("a", "b", "c");
            oneArray.push(["a", "b", "c"]);
            
            oneArray[0] = "a";
            var val : String = oneArray[0];
            
            var element:String = oneArray.pop();
        }
    }
}