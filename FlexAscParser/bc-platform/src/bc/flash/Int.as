package bc.flash
{
    import bc.flash.errors.NotImplementedError;

    /**
     * @author weee
     */
    [ConvertOnce]
    public final class Int extends Object
    {
        public static const MAX_VALUE:int = 2147483647;
        public static const MIN_VALUE:int = -2147483648;

        public function toExponential(fractionDigits:Number):String 
        { 
            throw new NotImplementedError(); 
        }
        
        public function toExponential(fractionDigits:Number):String
        {
            throw new NotImplementedError();
        }
        
        public function toFixed(fractionDigits:Number):String
        {
            throw new NotImplementedError();
        }
        
        public function toPrecision(precision:Number):String
        {
            throw new NotImplementedError();
        }
        
        public function toString(radix:Number):String
        {
            throw new NotImplementedError();
        }
        
        public function valueOf():int
        {
            throw new NotImplementedError();
        }
    }
}