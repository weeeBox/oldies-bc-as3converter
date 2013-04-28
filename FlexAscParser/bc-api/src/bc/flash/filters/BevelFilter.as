package bc.flash.filters
{
    import bc.flash.errors.NotImplementedError;
    import bc.flash.filters.BitmapFilter;
    
    public final  class BevelFilter extends BitmapFilter
    {
        public function BevelFilter(distance:Number = 4.0, angle:Number = 45, highlightColor:uint = 0xFFFFFF, highlightAlpha:Number = 1.0, shadowColor:uint = 0x000000, shadowAlpha:Number = 1.0, blurX:Number = 4.0, blurY:Number = 4.0, strength:Number = 1, quality:int = 1, type:String = "inner", knockout:Boolean = false)
        {
            throw new NotImplementedError();
        }
        
        override public function clone():BitmapFilter
        {
            throw new NotImplementedError();
        }
        
        public function get angle():Number { throw new NotImplementedError(); }
        public function set angle(value:Number):void { throw new NotImplementedError(); }
        public function get blurX():Number { throw new NotImplementedError(); }
        public function set blurX(value:Number):void { throw new NotImplementedError(); }
        public function get blurY():Number { throw new NotImplementedError(); }
        public function set blurY(value:Number):void { throw new NotImplementedError(); }
        public function get distance():Number { throw new NotImplementedError(); }
        public function set distance(value:Number):void { throw new NotImplementedError(); }
        public function get highlightAlpha():Number { throw new NotImplementedError(); }
        public function set highlightAlpha(value:Number):void { throw new NotImplementedError(); }
        public function get highlightColor():uint { throw new NotImplementedError(); }
        public function set highlightColor(value:uint):void { throw new NotImplementedError(); }
        public function get knockout():Boolean { throw new NotImplementedError(); }
        public function set knockout(value:Boolean):void { throw new NotImplementedError(); }
        public function get quality():int { throw new NotImplementedError(); }
        public function set quality(value:int):void { throw new NotImplementedError(); }
        public function get shadowAlpha():Number { throw new NotImplementedError(); }
        public function set shadowAlpha(value:Number):void { throw new NotImplementedError(); }
        public function get shadowColor():uint { throw new NotImplementedError(); }
        public function set shadowColor(value:uint):void { throw new NotImplementedError(); }
        public function get strength():Number { throw new NotImplementedError(); }
        public function set strength(value:Number):void { throw new NotImplementedError(); }
        public function get type():String { throw new NotImplementedError(); }
        public function set type(value:String):void { throw new NotImplementedError(); }
    }
}
