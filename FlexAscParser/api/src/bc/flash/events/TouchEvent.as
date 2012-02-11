package bc.flash.events
{
	import bc.flash.display.DisplayObject;
	import bc.flash.display.DisplayObjectContainer;
	/**
	 * @author weee
	 */
	public class TouchEvent extends Event
    {
        /** Event type for touch or mouse input. */
        public static const TOUCH:String = "touch";
        
        private var mTouches:Vector.<Touch>;
        private var mShiftKey:Boolean;
        private var mCtrlKey:Boolean;
        private var mTimestamp:Number;
        
        /** Creates a new TouchEvent instance. */
        public function TouchEvent(type:String, touches:Vector.<Touch>, shiftKey:Boolean=false, 
                                   ctrlKey:Boolean=false, bubbles:Boolean=true)
        {
            super(type, bubbles);
            
            mTouches = touches ? touches : new <Touch>[];
            mShiftKey = shiftKey;
            mCtrlKey = ctrlKey;
            mTimestamp = -1.0;
            
            var numTouches:int=touches.length;
            for (var i:int=0; i<numTouches; ++i)
                if (touches[i].timestamp > mTimestamp)
                    mTimestamp = touches[i].timestamp;
        }
        
        /** Returns a list of touches that originated over a certain target. */
        public function getTouches(target:DisplayObject, phase:String=null):Vector.<Touch>
        {
            var touchesFound:Vector.<Touch> = new <Touch>[];
            var numTouches:int = mTouches.length;
            
            for (var i:int=0; i<numTouches; ++i)
            {
                var touch:Touch = mTouches[i];
                var correctTarget:Boolean = (touch.target == target) ||
                    ((target is DisplayObjectContainer) && 
                     (target as DisplayObjectContainer).contains(touch.target));
                var correctPhase:Boolean = (phase == null || phase == touch.phase);
                    
                if (correctTarget && correctPhase)
                    touchesFound.push(touch);
            }
            return touchesFound;
        }
        
        /** Returns a touch that originated over a certain target. */
        public function getTouch(target:DisplayObject, phase:String=null):Touch
        {
            var touchesFound:Vector.<Touch> = getTouches(target, phase);
            if (touchesFound.length > 0) return touchesFound[0];
            else return null;
        }
        
        /** Indicates if a target is currently being touched or hovered over. */
        public function interactsWith(target:DisplayObject):Boolean
        {
            if (getTouch(target) == null)
                return false;
            else
            {
                var touches:Vector.<Touch> = getTouches(target);
                
                for (var i:int=touches.length-1; i>=0; --i)
                    if (touches[i].phase != TouchPhase.ENDED)
                        return true;
                
                return false;
            }
        }

        /** The time the event occurred (in seconds since application launch). */
        public function get timestamp():Number { return mTimestamp; }
        
        /** All touches that are currently available. */
        public function get touches():Vector.<Touch> { return mTouches.concat(); }
        
        /** Indicates if the shift key was pressed when the event occurred. */
        public function get shiftKey():Boolean { return mShiftKey; }
        
        /** Indicates if the ctrl key was pressed when the event occurred. (Mac OS: Cmd or Ctrl) */
        public function get ctrlKey():Boolean { return mCtrlKey; }
    }
}
