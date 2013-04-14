package bc.test.functions.types
{
    public class FunctionsTypeTest
    {
        private var mFunc:Function;
        
        private var mObj:FunctionsTypeTest;

        public function testFunctionAssignment() : void
        {
            mFunc = functionDefaultCallback;
            mObj.mFunc = functionDefaultCallback;
            mFunc = mObj.functionDefaultCallback;
            mObj.mFunc = mObj.functionDefaultCallback;
        }
        
        public function testFunctionUsage() : void
        {
            mFunc("This is string", "This is another string");
            mObj.mFunc("This is string", "This is another string");
        }
        
        public function testFunctionArgument() : void
        {
            accept(functionDefaultCallback);
            mObj.accept(functionDefaultCallback);
        }
        
        public function testFunctionApply() : void
        {
            var args : Array = ["This is string", "This is another string"];
            functionDefaultCallback.apply(null, args);
            mObj.functionDefaultCallback.apply(null, args);
        }
        
        public function argumentsFunction(a:String, b:String) : void
        {
            mFunc.apply();
        }
        
        public function argumentsCastFunction(a:Object, b:String) : void
        {
            mFunc.apply();
        }

        private function accept(func:Function) : void 
        {
            func("This is string", "This is another string");
        }
        
        private function functionDefaultCallback(a:String, b:String) : void {}
    }
}