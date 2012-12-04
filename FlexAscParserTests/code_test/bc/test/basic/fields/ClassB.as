package bc.test.basic.fields
{
    public class ClassB extends ClassA
    {
        public var memberA1 : Object;
        public var memberA2 : Object = null;
        public var memberA3 : Object = new Object();
        public var memberA4 : Object = memberA3;
        
        public var memberB1 : String;
        public var memberB2 : String = null;
        public var memberB3 : String = "This is a string";
        public var memberB4 : String = memberB3;
        
        public var memberC1 : int;
        public var memberC2 : int = 10;
        public var memberC3 : int = memberB1.length;
        
        public function ClassB()
        {
            super("Some arg");
            var otherVariable : String = "Some string";
        }
    }
}