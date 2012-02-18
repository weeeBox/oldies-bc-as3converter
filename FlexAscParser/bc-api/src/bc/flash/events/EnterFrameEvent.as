package bc.flash.events
{
	/** An EnterFrameEvent is triggered once per frame and is dispatched to all objects in the
     *  display tree.
     *
     *  It contains information about the time that has passed since the last frame. That way, you 
     *  can easily make animations that are independet of the frame rate, taking the passed time
     *  into account.
     */ 
    public class EnterFrameEvent extends Event
    {
        private var mPassedTime:Number;
        
        /** Creates an enter frame event with the passed time. */
        public function EnterFrameEvent(type:String, passedTime:Number, bubbles:Boolean=false)
        {
            super(type, bubbles);
            mPassedTime = passedTime;
        }
        
        /** The time that has passed since the last frame (in seconds). */
        public function get passedTime():Number { return mPassedTime; }
		
        /** The time that has passed since the last frame (in seconds). */
        public function set passedTime(value:Number) : void { mPassedTime = value; }
    }
}
