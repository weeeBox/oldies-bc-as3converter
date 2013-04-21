package bc.flash
{
    import bc.flash.errors.NotImplementedError;

    /**
     * @author weee
     */
    [ConvertOnce]
    public final class Number extends Object
    {
        public static const NaN : Number = 0.0/0.0; 
        public static const NEGATIVE_INFINITY : Number = -1.0/0.0;
        public static const POSITIVE_INFINITY : Number = 1.0/0.0;
        public static const MIN_VALUE : Number = 1.401298464324817E-45;
        public static const MAX_VALUE : Number = 3.4028234663852886E38;

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
        
        public function toString(radix:Number = 10):String 
        { 
            throw new NotImplementedError(); 
        }
        
        public function valueOf():Number 
        { 
            throw new NotImplementedError(); 
        }
    }
}
