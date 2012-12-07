package bc.test.basic.expressions
{
    public class ExpressionsTests
    {
        
        public function testAssignment() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c = a + b;
        }
        
        public function testAssignment2() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = a + b + c;
        }
        
        public function testAssignment3() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = (a + b) + c;
        }
        
        public function testAssignment4() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = a + (b + c);
        }
        
        public function testPriority() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = (a + b) * c;
        }
        
        public function testPriority2() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = a * (b + c);
        }
        
    }
}