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
        
        public function testExpressions() : void
        {
            var integer : int = -10;
            var uinteger : uint = 10;
            var number : Number = 3.14;
            
            var value1 : Number = uinteger + number;
            var value2 : int = uinteger + number;
            var value3 : uint = uinteger + number;
            value1 = integer + uinteger;
            value2 = integer + uinteger;
            value3 = integer + uinteger;
            value1 = integer + uinteger + number;
            value2 = integer + uinteger + number;
            value3 = integer + uinteger + number;
        }
        
        public function testNumberCalls() : void
        {
            var num : Number = 10;
            var str : String;
            str = num.toExponential();
            str = num.toExponential(10);
            str = num.toFixed();
            str = num.toFixed(10);
            str = num.toPrecision();
            str = num.toPrecision(10);
            str = num.toString();
            str = num.toString(10);
            
            num = num.valueOf();
            num = Number.MAX_VALUE;
            num = Number.MIN_VALUE;
            num = Number.NaN;
            num = Number.NEGATIVE_INFINITY;
            num = Number.POSITIVE_INFINITY;
        }
    }
}