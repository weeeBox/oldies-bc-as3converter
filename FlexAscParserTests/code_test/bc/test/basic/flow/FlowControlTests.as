package bc.test.basic.flow
{
    public class FlowControlTests
    {
        public function testIfBoolean() : void
        {
            var value : Boolean = false;
            if (value)
            {
                
            }
        }
        
        public function testIfNotBoolean() : void
        {
            var value : Boolean = false;
            if (!value)
            {
                
            }
        }
        
        public function testIfNotNull() : void
        {
            var obj : Object = null;
            if (obj != null)
            {
                
            }
        }
        
        public function testIfNull() : void
        {
            var obj : Object = null;
            if (obj == null)
            {
                
            }
        }
        
        public function testIfReference() : void
        {
            var obj : Object = null;
            if (obj)
            {
                
            }
        }
        
        public function testIfNotReference() : void
        {
            var obj : Object = null;
            if (!obj)
            {
                
            }
        }
        
        public function testIfBitwiseAnd() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (value & mask)
            {
                
            }
        }
        
        public function testIfNotBitwiseAnd() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (!(value & mask))
            {
                
            }
        }
        
        public function testIfBitwiseOr() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (value | mask)
            {
                
            }
        }
        
        public function testIfNotBitwiseOr() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (!(value | mask))
            {
                
            }
        }
        
        public function testIfBitwiseXor() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (value ^ mask)
            {
                
            }
        }
        
        public function testIfNotBitwiseXor() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (!(value ^ mask))
            {
                
            }
        }
        
        public function testIfAddition() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (value + mask)
            {
                
            }
        }
        
        public function testIfNotAddition() : void
        {
            var value : int = 10;
            var mask : int = 1;
            if (!(value + mask))
            {
                
            }
        }
        
        public function testIfInteger() : void
        {
            var value : int = 10;
            if (value)
            {
                
            }
        }
        
        public function testIfNotInteger() : void
        {
            var value : int = 10;
            if (!value)
            {
                
            }
        }

        public function testIfBooleanFunction() : void
        {
            if (returnsBoolean())
            {
                
            }
        }
        
        public function testIfNotBooleanFunction() : void
        {
            if (!returnsBoolean())
            {
                
            }
        }
        
        public function testIfIntegerFunction() : void
        {
            if (returnsInteger())
            {
                
            }
        }
        
        public function testIfNotIntegerFunction() : void
        {
            if (!returnsInteger())
            {
                
            }
        }
        
        public function testIfObjectFunction() : void
        {
            if (returnsObject())
            {
                
            }
        }
        
        public function testIfNotObject() : void
        {
            if (!returnsObject())
            {
                
            }
        }
        
        public function testIsOperator() : void
        {
            var obj : Object = "This is string";
            var flag : Boolean = obj is String;
        }
        
        public function testAsOperator() : void
        {
            var obj : Object = "This is string";
            var str : String = obj as String;
        }
        
        public function testIsNumberOperator() : void
        {
            var obj : Object = 3.14;
            var flag : Boolean = obj is Number;
        }
        
        public function testAsNumberOperator() : void
        {
            var obj : Object = 3.14;
            var str : Number = obj as Number;
        }
        
        public function testIsBooleanOperator() : void
        {
            var obj : Object = true;
            var flag : Boolean = obj is Boolean;
        }
        
        public function testAsBooleanOperator() : void
        {
            var obj : Object = true;
            var str : Boolean = obj as Boolean;
        }
        
        public function testIsIntOperator() : void
        {
            var obj : Object = 10;
            var flag : Boolean = obj is int;
        }
        
        public function testAsIntOperator() : void
        {
            var obj : Object = 10;
            var str : int = obj as int;
        }
        
        public function testIsUintOperator() : void
        {
            var obj : Object = 10;
            var flag : Boolean = obj is uint;
        }
        
        public function testAsUintOperator() : void
        {
            var obj : Object = 10;
            var str : uint = obj as uint;
        }
        
        public function returnsInteger() : int
        {
            return 0;
        }
        
        public function returnsBoolean() : Boolean
        {
            return false;
        }
        
        public function returnsObject() : Object
        {
            return null;
        }
    }
}