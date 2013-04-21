package bc.flash
{
    import bc.flash.errors.NotImplementedError;

    /**
     * @author weee
     */
    [ConvertOnce]
    public final class Uint extends Object
    {
        public static const MAX_VALUE:uint = 4294967295;
        public static const MIN_VALUE:uint = 0;
        
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
        
        public function valueOf():uint
        {
            throw new NotImplementedError();
        }
    }
}