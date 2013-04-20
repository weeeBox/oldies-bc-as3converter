package bc.flash.filters
{
    import bc.flash.errors.NotImplementedError;

    public class ColorMatrixFilter extends BitmapFilter
    {
        public function ColorMatrixFilter(arg1:Array=null) { throw new NotImplementedError(); }
        public function clone() : BitmapFilter { throw new NotImplementedError(); }
        public function get matrix() : Array { throw new NotImplementedError(); }
        public function set matrix(value:Array) : void { throw new NotImplementedError(); }
    }
}