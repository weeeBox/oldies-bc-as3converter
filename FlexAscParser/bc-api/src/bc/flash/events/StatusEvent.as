package bc.flash.events
{
    import bc.flash.errors.NotImplementedError;
    import bc.flash.events.Event;
    
    public class StatusEvent extends Event
    {
        public static const STATUS:String = "status";
        
        public function StatusEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false, code:String = "", level:String = "")
        {
            throw new NotImplementedError();
        }
        
        override public function clone():Event
        {
            throw new NotImplementedError();
        }
        
        override public function toString():String
        {
            throw new NotImplementedError();
        }
        
        public function get code():String { throw new NotImplementedError(); }
        public function set code(value:String):void { throw new NotImplementedError(); }
        public function get level():String { throw new NotImplementedError(); }
        public function set level(value:String):void { throw new NotImplementedError(); }
    }
}
