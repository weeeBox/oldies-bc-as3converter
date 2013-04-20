package bc.flash.events
{
    import bc.flash.errors.NotImplementedError;

    public class FullScreenEvent extends Event
    {
        public function FullScreenEvent(arg1:String, arg2:Boolean=false, arg3:Boolean=false, arg4:Boolean=false, arg5:Boolean=false) { throw new NotImplementedError(); }
        public function clone() : Event { throw new NotImplementedError(); }
        public function get fullScreen() : Boolean { throw new NotImplementedError(); }
    }
}