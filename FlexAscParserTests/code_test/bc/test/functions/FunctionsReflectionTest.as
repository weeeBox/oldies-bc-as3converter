package bc.test.functions
{	
	public class FunctionsReflectionTest
	{
		public var func:Function;
        
        public var obj:FunctionsReflectionTest;
        
        public function testAssignment() : void
        {
            func = someFunction;
            func = obj.someFunction;
            func = this.someFunction;
            acceptFunction(someFunction);
        }
        
        public function acceptFunction(func:Function) : void
        {
            
        }
        
        public function someFunction() : void
        {
            
        }
        
        public function someFunctionWithArg(arg:int) : void
        {
            
        }
	}
}