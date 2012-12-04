package bc.test.basic.types
{
    public class TypesTests
    {
        public function TypesTests()
        {
        }
        
        public function testIntegrals() : void
        {
            var integer : int;
            var uinteger : uint;
            var number : Number;
            var boolean : Boolean;
        }
        
        public function testAssignments() : void
        {
            var integer : int = -10;
            var uinteger : uint = 10;
            var number : Number = 3.14;
            var boolean : Boolean = true;
            
            integer = uinteger;
            uinteger = integer;
            number = integer;
            number = uinteger;
            
            integer = number;
            uinteger = number;
            
            boolean = false;
        }
    }
}