package bc.test.functions
{	
	public class FunctionsReflectionTest
	{
		public var func:Function;
        
        public function testAssignment() : void
        {
            func = someFunction;
        }
        
        public function someFunction() : void
        {
            
        }
        
        public function someFunctionWithArg(arg:int) : void
        {
            
        }
	}
}