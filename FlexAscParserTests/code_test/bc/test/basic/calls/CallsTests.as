package bc.test.basic.calls
{
    public class CallsTests
    {
        private var instance : CallsTests;
        
        public function CallsTests()
        {
        }
        
        // test calls
        
        private function testMemberCall() : void
        {
            instance.func();    
        }
        
        private function testMemberCallWithArg() : void
        {
            instance.funcWithArg("arg");
        }
        
        public function testMemberCallWithArgs() : void
        {
            instance.funcWithArgs("arg1", "arg2");
        }
        
        public function testMemberCallWithRestArgs() : void
        {
            instance.funcWithRestArgs();
        }
        
        public function testMemberCallWithRestArgs2() : void
        {
            instance.funcWithRestArgs("arg");
        }
        
        public function testMemberCallWithRestArgs3() : void
        {
            instance.funcWithRestArgs("arg1", "arg2", "arg3");
        }
        
        public function testMemberCallWithUntypedArg() : void
        {
            instance.funcWithUntypedArg(10);
        }
        
        public function testMemberCallWithUntypedArg2() : void
        {
            instance.funcWithUntypedArg("arg");
        }
        
        public function testMemberCallWithUntypedArg3() : void
        {
            var arg : Object = new Object();
            instance.funcWithUntypedArg(arg);
        }
        
        public function testMemberCallWithArgsAndRestArgs() : void
        {
            instance.funcWithArgsAndRestArgs("arg1", "arg2");
        }
        
        public static function testMemberCallStaticFunc() : void
        {
            CallsTests.staticFunc();
        }
        
        /*
        public static function testArgumentsLength(arg1:String, arg2:String) : void
        {
            var length:int = arguments.length;
        }

        public static function testArgumentsCall(arg1:String, arg2:String) : void
        {
            funcWithArgsAndRestArgs("arg1", "arg2", arguments);
        }
        */        
        // void functions
        
        public function func() : void
        {
            
        }
        
        public function funcWithArg(arg : String) : void
        {
            
        }
        
        public function funcWithArgs(arg1 : String, arg2 : String) : void
        {
            
        }
        
        public function funcWithRestArgs(... args) : void
        {
            
        }
        
        public function funcWithUntypedArg(arg : *) : void
        {
            
        }
        
        public function funcWithArgsAndRestArgs(arg1 : String, arg2 : String, ... args) : void
        {
            
        }
        
        public static function staticFunc() : void
        {
            
        }
        
        // functions with return type
        
        public function funcWithReturnType() : String
        {
            return null;
        }
        
        public function funcWithArgAndReturnType(arg : String) : String 
        {
            return null;
        }
        
        public function funcWithArgsAndReturnType(arg1 : String, arg2 : String) : String
        {
            return null;
        }
        
        public function funcWithRestArgsAndReturnType(... args) : String
        {
            return null;
        }
        
        public function funcWithUntypedArgAndReturnType(arg : *) : String
        {
            return null;
        }
        
        /*
        // FIXME: add support for untyped return
        public function funcWithUntypedArgAndUndefinedReturnType(arg : *) : *
        {
            return null;
        }
        */
        
        public function funcWithArgsAndRestArgsAndReturnType(arg1 : String, arg2 : String, ... args) : String
        {
            return null;
        }
        
        public static function staticFuncWithReturnType() : String
        {
            return null;
        }
    }
}