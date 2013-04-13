package bc.test.functions.anonymous
{   
    public class FunctionsAnonymousTest
    {
        public function testAnonymous1() : void
        {
        	var func:Function;
        	func = function():void
			{
				doStuff();
			};
			
			func();
        }
        
        public function testAnonymous2() : void
        {
        	var func:Function;
        	func = function(arg1:String):void
			{
				doStuff();
			};
			
			func();
        }
        
        public function testAnonymous3() : void
        {
        	var func:Function;
        	func = function(arg1:String, arg2:String):void
			{
				doStuff();
			};
			
			func();
        }
        
        public function testAnonymous4() : void
        {
            var func:Function;
            func = function(arg1:String, arg2:String):void
            {
                return arg1 + " " + arg2;
            };
            
            var result:String = func();
        }
        
        public function testAnonymous5() : void
        {
            var func:Function;
            func = function(arg1:int, arg2:int):void
            {
                return arg1 + arg2;
            };
            
            var result:int = func();
        }
        
        private function doStuff() : void
        {
        }
    }
}