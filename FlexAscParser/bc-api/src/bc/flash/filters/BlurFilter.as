package bc.flash.filters
{
    import bc.flash.errors.NotImplementedError;
    import bc.flash.filters.BitmapFilter;
    
    public final  class BlurFilter extends BitmapFilter
    {
        public function BlurFilter(blurX:Number = 4.0, blurY:Number = 4.0, quality:int = 1)
        {
            throw new NotImplementedError();
        }
        
        override public function clone():BitmapFilter
        {
            throw new NotImplementedError();
        }
        
        public function get blurX():Number { throw new NotImplementedError(); }
        public function set blurX(value:Number):void { throw new NotImplementedError(); }
        public function get blurY():Number { throw new NotImplementedError(); }
        public function set blurY(value:Number):void { throw new NotImplementedError(); }
        public function get quality():int { throw new NotImplementedError(); }
        public function set quality(value:int):void { throw new NotImplementedError(); }
    }
}
