package bc.test.strings
{
    public class StringsTest
    {
        public function testLiterals() : void
        {
            // literals
            var str : String = "This is a string";
            str = 'This is a string';
            str = "This is a string with \"escapes\"";
            str = 'This is a string with "escapes"';
        }
        
        public function testLength() : void
        {
            var str : String = "This is a string";
            var length : int = str.length;
        }
        
        public function testMethods() : void
        {
            var str : String = "This is a string";
            var chrString : String = str.charAt(0);
            var chrCode : Number = str.charCodeAt(0);
            
            var fromCharCode : String = String.fromCharCode(0);
            var indexOf : int = str.indexOf("string");
            indexOf = str.indexOf("string", 0);

            var replace : String = str.replace("string", "foo");
            var slice : String = str.slice(0);
            slice = str.slice(0, 10);
            
            var split : Array = str.split(" ");
            
            var substr : String = str.substr(0);
            substr = str.substring(0, 10);

            var substring : String = str.substring(0);
            substring = str.substring(0, 10);

            var toLocalLowerCase : String = str.toLocaleLowerCase();
            var toLocalUpperCase : String = str.toLocaleUpperCase();
            
            var toLowerCase : String = str.toLowerCase();
            var toUpperCase : String = str.toUpperCase();
            
            var toString : String = str.toString();
            var valueOf : String = str.valueOf();
        }
        
        public function testTypesParsing() : void
        {
            var num : Number = Number("10");
            var i : int = int("10");
            var ui : uint = uint("10");
            var b : Boolean = Boolean("true");
        }
        
        public function testLiteralsCalls() : void
        {
            var length : int = "This is a string".length;
            var replace : String = "This is a string".replace("string", "foo");
        }
    }
}