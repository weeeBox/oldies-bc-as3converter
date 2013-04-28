package bc.flash.filters
{
    import bc.flash.errors.NotImplementedError;
    import bc.flash.filters.BitmapFilter;
    
    public final  class GlowFilter extends BitmapFilter
    {
        public function GlowFilter(color:uint = 0xFF0000, alpha:Number = 1.0, blurX:Number = 6.0, blurY:Number = 6.0, strength:Number = 2, quality:int = 1, inner:Boolean = false, knockout:Boolean = false)
        {
            throw new NotImplementedError();
        }
        
        override public function clone():BitmapFilter
        {
            throw new NotImplementedError();
        }
        
        public function get alpha():Number { throw new NotImplementedError(); }
        public function set alpha(value:Number):void { throw new NotImplementedError(); }
        public function get blurX():Number { throw new NotImplementedError(); }
        public function set blurX(value:Number):void { throw new NotImplementedError(); }
        public function get blurY():Number { throw new NotImplementedError(); }
        public function set blurY(value:Number):void { throw new NotImplementedError(); }
        public function get color():uint { throw new NotImplementedError(); }
        public function set color(value:uint):void { throw new NotImplementedError(); }
        public function get inner():Boolean { throw new NotImplementedError(); }
        public function set inner(value:Boolean):void { throw new NotImplementedError(); }
        public function get knockout():Boolean { throw new NotImplementedError(); }
        public function set knockout(value:Boolean):void { throw new NotImplementedError(); }
        public function get quality():int { throw new NotImplementedError(); }
        public function set quality(value:int):void { throw new NotImplementedError(); }
        public function get strength():Number { throw new NotImplementedError(); }
        public function set strength(value:Number):void { throw new NotImplementedError(); }
    }
}
