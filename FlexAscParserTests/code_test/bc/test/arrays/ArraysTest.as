package bc.test.arrays
{
    public class ArraysTest
    {
        public function testArrayCreation() : void
        {
            var oneArray:Array = new Array();
            var twoArray:Array = new Array("a", "b", "c");
            var threeArray:Array = ["a", "b", "c"];
            var fourArray:Array = Array(["a", "b", "c"]);
        }
        
        public function testArrayPush() : void
        {
            var oneArray:Array = new Array();
            oneArray.push();
            oneArray.push("a");
            oneArray.push("a", "b", "c");
            oneArray.push(["a", "b", "c"]);
        }
        
        public function testArrayIndexer() : void
        {
            var oneArray:Array = new Array();
            oneArray[0] = "a";
            var val : String = oneArray[0];
        }
        
        public function testArrayPop() : void
        {
            var oneArray:Array = ["a"];
            var element:String = oneArray.pop();
        }
    }
}