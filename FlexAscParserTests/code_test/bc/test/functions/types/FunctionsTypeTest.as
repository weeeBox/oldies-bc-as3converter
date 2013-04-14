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
        
        public function testFunctionApply1() : void
        {
            var args : Array = ["This is string", "This is another string"];
            functionDefaultCallback.apply(null, args);
            mObj.functionDefaultCallback.apply(null, args);
        }
        
        public function testFunctionApply2(...args) : void
        {
            mFunc.apply(null, args);
        }
        
        public function testFunctionApply3(...args) : void
        {
            functionDefaultCallback.apply(null, args);
        }
        
        public function testCallFunction1(a:Object, b:String) : void
        {
            mFunc.call(null, a, b);
        }
        
        public function testCallFunction2(a:Object, b:String) : void
        {
            functionDefaultCallback.call(null, a, b);
        }

        private function accept(func:Function) : void 
        {
            func("This is string", "This is another string");
        }
        
        private function functionDefaultCallback(a:String, b:String) : void {}
    }
}