package bc.test.functions
{
    [FunctionType(callback="FunctionDefaultCallback", params="string:String", useByDefault="true")]
    public class FunctionsTypeTest
    {
        private var mFunc:Function;
        
        public function FunctionsTypeTest()
        {
            accept(functionDefaultCallback);
            var args : Array = ["This is string"];
            functionDefaultCallback.apply(null, args);
        }

        private function accept(func:Function) : void 
        {
            func("This is string");
        }
        
        private function functionDefaultCallback(string:String) : void {}
    }
}