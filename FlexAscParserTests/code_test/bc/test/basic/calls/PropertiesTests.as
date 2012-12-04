package bc.test.basic.calls
{
    public class PropertiesTests
    {
        private var mField : String;
        
        public function testProperties() : void
        {
            var value : String = "Some string";
            field = value;
            value = field;
        }

        public function testThisProperties() : void
        {
            var value : String = "Some string";
            this.field = value;
            value = this.field;
        }
        
        public function testVisiblity() : void
        {
            var field : String = "Some string";
            this.field = field;
            field = this.field;
        }
        
        public function testRigthFunctionCall() : void
        {
            field = field + " more string";
            var value = field + " more string";
        }
        
        public function get field() : String
        {
            return mField;
        }
        
        public function set field(value : String) : void
        {
            mField = value;
        }
    }
}