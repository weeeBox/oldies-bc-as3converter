package bc.test.functions
{
    [FunctionType(callback="FunctionDefaultCallback", params="a:String,b:String", useByDefault="true")]
    public class FunctionsTypeTest
    {
        private var mFunc:Function;
        
        public function testFunctionAssignment() : void
        {
            mFunc = functionDefaultCallback;
        }
        
        public function testFunctionUsage() : void
        {
            mFunc("This is string", "This is another string");
        }
        
        public function testFunctionArgument() : void
        {
            accept(functionDefaultCallback);
        }
        
        public function testFunctionApply() : void
        {
            var args : Array = ["This is string", "This is another string"];
            functionDefaultCallback.apply(null, args);
        }
        
        public function argumentsFunction(a:String, b:String) : void
        {
            mFunc.apply(null, arguments);
        }
        
        public function argumentsCastFunction(a:Object, b:String) : void
        {
            mFunc.apply(null, arguments);
        }

        private function accept(func:Function) : void 
        {
            func("This is string", "This is another string");
        }
        
        private function functionDefaultCallback(a:String, b:String) : void {}
    }
}