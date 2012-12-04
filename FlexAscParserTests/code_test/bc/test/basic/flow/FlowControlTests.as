package bc.test.basic.flow
{
    public class FlowControlTests
    {
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
    }
}