package bc.flash.events
{
	import bc.flash.display.Stage;
	import bc.flash.geom.Point;
	/** @private
     *  The TouchProcessor is used internally to convert mouse and touch events of the conventional
     *  Flash stage to Starling's TouchEvents. */
    public class TouchProcessor
    {
        private static const MULTITAP_TIME:Number = 0.3;
        private static const MULTITAP_DISTANCE:Number = 25;
        
        private var mStage:Stage;
        private var mElapsedTime:Number;
        private var mOffsetTime:Number;
        // private var mTouchMarker:TouchMarker; FIXME
        
        private var mCurrentTouches:Vector.<Touch>;
        private var mQueue:Vector.<Array>;
        private var mLastTaps:Vector.<Touch>;
        
        private var mShiftDown:Boolean = false;
        private var mCtrlDown:Boolean = false;
        
        /** Helper objects. */
        private static var sProcessedTouchIDs:Vector.<int> = new <int>[];
        private static var sHoveringTouchData:Vector.<TouchData> = new <TouchData>[];
        
        public function TouchProcessor(stage:Stage)
        {
            mStage = stage;
            mElapsedTime = mOffsetTime = 0.0;
            mCurrentTouches = new <Touch>[];
            mQueue = new <Array>[];
            mLastTaps = new <Touch>[];
            
            mStage.addEventListener(KeyboardEvent.KEY_DOWN, onKey);
            mStage.addEventListener(KeyboardEvent.KEY_UP,   onKey);
        }

        public function dispose():void
        {
            mStage.removeEventListener(KeyboardEvent.KEY_DOWN, onKey);
            mStage.removeEventListener(KeyboardEvent.KEY_UP,   onKey);
            // if (mTouchMarker) mTouchMarker.dispose(); FIXME
        }
        
        public function advanceTime(passedTime:Number):void
        {
            var i:int;            
            var touch:Touch;
            
            mElapsedTime += passedTime;
            mOffsetTime = 0.0;
            
            // remove old taps
            if (mLastTaps.length > 0)
            {
                for (i=mLastTaps.length-1; i>=0; --i)
                    if (mElapsedTime - mLastTaps[i].timestamp > MULTITAP_TIME)
                        mLastTaps.splice(i, 1);
            }
            
            while (mQueue.length > 0)
            {
                sProcessedTouchIDs.length = 0; 
				sHoveringTouchData.length = 0;
                
                // update existing touches
                for each (var currentTouch:Touch in mCurrentTouches)
                {
                    // set touches that were new or moving to phase 'stationary'
                    if (currentTouch.phase == TouchPhase.BEGAN || currentTouch.phase == TouchPhase.MOVED)
                        currentTouch.setPhase(TouchPhase.STATIONARY);
                }
                
//                // process new touches, but each ID only once
//                while (mQueue.length > 0 && sProcessedTouchIDs.indexOf(mQueue[mQueue.length-1][0]) == -1)
//                {
//                    var touchArgs:Array = mQueue.pop();
//                    touchID = touchArgs[0] as int;
//                    touch = getCurrentTouch(touchID);
//                    
//                    // hovering touches need special handling (see below)
//                    if (touch && touch.phase == TouchPhase.HOVER && touch.target)
//                        sHoveringTouchData.push(new TouchData(touch, touch.target));
//                    
//                    // processTouch.apply(this, touchArgs); FIXME!!!
//                    sProcessedTouchIDs.push(touchID);
//                }
                
                // if the target of a hovering touch changed, we dispatch an event to the previous
                // target to notify it that it's no longer being hovered over.
                for each (var touchData:TouchData in sHoveringTouchData)
                    if (touchData.touch.target != touchData.target)
                        touchData.target.dispatchEvent(new TouchEvent(
                            TouchEvent.TOUCH, mCurrentTouches, mShiftDown, mCtrlDown));
                
                // dispatch events
                for each (var touchID:int in sProcessedTouchIDs)
                {
                    touch = getCurrentTouch(touchID);
                    
                    if (touch.target)
                        touch.target.dispatchEvent(new TouchEvent(TouchEvent.TOUCH, mCurrentTouches,
                                                                  mShiftDown, mCtrlDown));
                }
                
                // remove ended touches
                for (i=mCurrentTouches.length-1; i>=0; --i)
                    if (mCurrentTouches[i].phase == TouchPhase.ENDED)
                        mCurrentTouches.splice(i, 1);
                
                // timestamps must differ for remaining touches
                mOffsetTime += 0.00001;
            }
        }
        
        public function enqueue(touchID:int, phase:String, globalX:Number, globalY:Number):void
        {
            /* FIXME
            mQueue.unshift(arguments);
            
            // multitouch simulation (only with mouse)
            if (mCtrlDown && simulateMultitouch && touchID == 0) 
            {
                mTouchMarker.moveMarker(globalX, globalY, mShiftDown);
                mQueue.unshift([1, phase, mTouchMarker.mockX, mTouchMarker.mockY]);
            }
            */
        }
        
        private function processTouch(touchID:int, phase:String, globalX:Number, globalY:Number):void
        {
            var position:Point = new Point(globalX, globalY);
            var touch:Touch = getCurrentTouch(touchID);
            
            if (touch == null)
            {
                touch = new Touch(touchID, globalX, globalY, phase, null);
                addCurrentTouch(touch);
            }
            
            touch.setPosition(globalX, globalY);
            touch.setPhase(phase);
            touch.setTimestamp(mElapsedTime + mOffsetTime);
            
            if (phase == TouchPhase.HOVER || phase == TouchPhase.BEGAN)
                touch.setTarget(mStage.hitTest(position, true));
            
            if (phase == TouchPhase.BEGAN)
                processTap(touch);
        }
        
        private function onKey(evt:Event):void
	{
            var event : KeyboardEvent = KeyboardEvent(evt);
			
            if (event.keyCode == 17 || event.keyCode == 15) // ctrl or cmd key
            {
                var wasCtrlDown:Boolean = mCtrlDown;
                mCtrlDown = event.type == KeyboardEvent.KEY_DOWN;
                
				/* FIXME
                if (simulateMultitouch && wasCtrlDown != mCtrlDown)
                {
                    mTouchMarker.visible = mCtrlDown;
                    mTouchMarker.moveCenter(mStage.stageWidth/2, mStage.stageHeight/2);
                    
                    var mouseTouch:Touch = getCurrentTouch(0);
                    var mockedTouch:Touch = getCurrentTouch(1);
                    
                    if (mouseTouch)
                        mTouchMarker.moveMarker(mouseTouch.globalX, mouseTouch.globalY);
                    
                    // end active touch ...
                    if (wasCtrlDown && mockedTouch && mockedTouch.phase != TouchPhase.ENDED)
                        mQueue.unshift([1, TouchPhase.ENDED, mockedTouch.globalX, mockedTouch.globalY]);
                    // ... or start new one
                    else if (mCtrlDown && mouseTouch)
                    {
                        if (mouseTouch.phase == TouchPhase.BEGAN || mouseTouch.phase == TouchPhase.MOVED)
                            mQueue.unshift([1, TouchPhase.BEGAN, mTouchMarker.mockX, mTouchMarker.mockY]);
                        else
                            mQueue.unshift([1, TouchPhase.HOVER, mTouchMarker.mockX, mTouchMarker.mockY]);
                    }
                }
                */
            }
            else if (event.keyCode == 16) // shift key 
            {
                mShiftDown = event.type == KeyboardEvent.KEY_DOWN;
            }
        }
        
        private function processTap(touch:Touch):void
        {
            var nearbyTap:Touch = null;
            var minSqDist:Number = MULTITAP_DISTANCE * MULTITAP_DISTANCE;
            
            for each (var tap:Touch in mLastTaps)
            {
                var sqDist:Number = Math.pow(tap.globalX - touch.globalX, 2) +
                                    Math.pow(tap.globalY - touch.globalY, 2);
                if (sqDist <= minSqDist)
                {
                    nearbyTap = tap;
                    break;
                }
            }
            
            if (nearbyTap)
            {
                touch.setTapCount(nearbyTap.tapCount + 1);
                mLastTaps.splice(mLastTaps.indexOf(nearbyTap), 1);
            }
            else
            {
                touch.setTapCount(1);
            }
            
            mLastTaps.push(touch.clone());
        }
        
        private function addCurrentTouch(touch:Touch):void
        {
            for (var i:int=mCurrentTouches.length-1; i>=0; --i)
                if (mCurrentTouches[i].id == touch.id)
                    mCurrentTouches.splice(i, 1);
            
            mCurrentTouches.push(touch);
        }
        
        private function getCurrentTouch(touchID:int):Touch
        {
            for each (var touch:Touch in mCurrentTouches)
                if (touch.id == touchID) return touch;
            return null;
        }
        
		/* FIXME
        public function get simulateMultitouch():Boolean { return mTouchMarker != null; }
        public function set simulateMultitouch(value:Boolean):void
        { 
            if (simulateMultitouch == value) return; // no change
            if (value)
            {
                mTouchMarker = new TouchMarker();
                mTouchMarker.visible = false;
                mStage.addChild(mTouchMarker);
            }
            else
            {                
                mTouchMarker.removeFromParent(true);
                mTouchMarker = null;
            }
        }
        */
    }
}
