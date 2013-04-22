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
            var c = (a + b);
        }
        
        public function testAssignment3() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = a + b + c;
        }
        
        public function testAssignment4() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = (a + b) + c;
        }
        
        public function testAssignment5() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : int = 30;
            var d = a + (b + c);
        }
		
        public function testAssignment6() : void
        {
            var a : Number = 10;
            var b : Number = 20;
            var c : int = (int)(a + b);
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
        
        public function testBooleans() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : Boolean = false;
            var d = c && (a + b);
        }
        
        public function testBooleans2() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : Boolean = false;
            var d = (a + b) && c;
        }
        
        public function testBooleans3() : void
        {
            var a : int = 10;
            var b : int = 20;
            var d = !(a + b) && (a + b);
        }
        
        public function testBooleans4() : void
        {
            var a : int = 10;
            var b : int = 20;
            var c : Boolean = false;
            var d = (a + b) && !(a + b);
        }
    }
}