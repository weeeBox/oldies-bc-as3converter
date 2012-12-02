package bc.test.basic.inheritance
{
    public class ChildClass extends BaseClass
    {
        public function ChildClass(arg1:String, arg2:String)
        {
            super(arg1, arg2);
        }
        
        override public function func1() : void
        {
            super.func1();
        }
        
        public function func3() : void
        {
            
        }
        
        private function func2() : void
        {
            
        }
    }
}