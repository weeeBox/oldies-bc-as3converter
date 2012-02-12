package bc.flash.core
{
	import bc.flash.ui.MultitouchInputMode;
	import bc.flash.ui.Multitouch;
	import bc.flash.core.natives.BcNativeStage;
	import bc.flash.display.DisplayObject;
	import bc.flash.display.Stage;
	import bc.flash.error.IllegalOperationError;
	import bc.flash.events.Event;
	import bc.flash.events.EventDispatcher;
	import bc.flash.events.KeyboardEvent;
	import bc.flash.events.MouseEvent;
	import bc.flash.events.TouchEvent;
	import bc.flash.events.TouchPhase;
	import bc.flash.events.TouchProcessor;
	import bc.flash.geom.Rectangle;
	import bc.flash.util.getTimer;

    public class Starling extends EventDispatcher
    {
        /** The version of the Starling framework. */
        public static const VERSION:String = "0.9.1";
        
        // members
        
        // private var mStage3D:Stage3D; FIXME
        private var mStage:Stage; // starling.display.stage!
        private var mRootObject:DisplayObject;
        // private var mJuggler:Juggler; FIXME
        private var mStarted:Boolean;        
        private var mSupport:RenderSupport;
        private var mTouchProcessor:TouchProcessor;
        private var mAntiAliasing:int;
        private var mSimulateMultitouch:Boolean;
        private var mEnableErrorChecking:Boolean;
        private var mLastFrameTimestamp:Number;
        private var mViewPort:Rectangle;
        private var mLeftMouseDown:Boolean;
        
        // private var mNativeStage:flash.display.Stage; FIXME
        // private var mNativeOverlay:flash.display.Sprite; FIXME
        
		private var mNativeStage : BcNativeStage;
		
        // private var mContext:Context3D; FIXME
        // private var mPrograms:Dictionary; FIXME
        
        private static var sCurrent:Starling;
        private static var sHandleLostContext:Boolean;
        
        // construction
        
        /** Creates a new Starling instance. 
         *  @param rootClass  A subclass of a Starling display object. It will be created as soon as
         *                    initialization is finished and will become the first child of the
         *                    Starling stage.
         *  @param stage      The Flash (2D) stage.
         *  @param viewPort   A rectangle describing the area into which the content will be 
         *                    rendered. @default stage size
         *  @param stage3D    The Stage3D object into which the content will be rendered.
         *                    @default the first available Stage3D.
         *  @param renderMode Use this parameter to force "software" rendering. 
         */
        public function Starling(stage : BcNativeStage, rootObject : DisplayObject) 
        {
            if (stage == null) throw new ArgumentError("Native stage must not be null");
            
            makeCurrent();
            
            // mRootClass = rootClass;
            // mViewPort = viewPort;
            // mStage3D = stage3D; FIXME
            mRootObject = rootObject;
            mStage = new Stage(stage.width, stage.height);
            // mNativeStage = stage; FIXME
            mTouchProcessor = new TouchProcessor(mStage);
            // mJuggler = new Juggler(); FIXME
            mAntiAliasing = 0;
            mSimulateMultitouch = false;
            mEnableErrorChecking = false;
            mLastFrameTimestamp = getTimer() / 1000.0;
            // mPrograms = new Dictionary(); FIXME
            mSupport  = new RenderSupport();
            
            // register touch/mouse event handlers
            for each (var touchEventType:String in touchEventTypes)
                stage.addEventListener(touchEventType, onTouch);
            
            // register other event handlers
            stage.addEventListener(Event.ENTER_FRAME, onEnterFrame);
            stage.addEventListener(KeyboardEvent.KEY_DOWN, onKey);
            stage.addEventListener(KeyboardEvent.KEY_UP, onKey);
			
			/* FIXME
            stage.addEventListener(Event.RESIZE, onResize);
            mStage3D.addEventListener(Event.CONTEXT3D_CREATE, onContextCreated, false, 1, true);
            mStage3D.addEventListener(ErrorEvent.ERROR, onStage3DError, false, 1, true);
            
            try { mStage3D.requestContext3D(renderMode); } 
            catch (e:Error) { showFatalError("Context3D error: " + e.message); }
            */
        }
        
        /** Disposes Shader programs and render context. */
        public function dispose():void
        {
            mNativeStage.removeEventListener(Event.ENTER_FRAME, onEnterFrame);
            mNativeStage.removeEventListener(KeyboardEvent.KEY_DOWN, onKey);
            mNativeStage.removeEventListener(KeyboardEvent.KEY_UP, onKey);
			
			/* FIXME
            mNativeStage.removeEventListener(Event.RESIZE, onResize, false);
			
            mStage3D.removeEventListener(Event.CONTEXT3D_CREATE, onContextCreated, false);
            mStage3D.removeEventListener(ErrorEvent.ERROR, onStage3DError, false);
            */
            
            for each (var touchEventType:String in touchEventTypes)
                mNativeStage.removeEventListener(touchEventType, onTouch);

			/* FIXME            
            for each (var program:Program3D in mPrograms)
                program.dispose();
            
            if (mContext) mContext.dispose();
            if (mSupport) mSupport.dispose();
            */
			
            if (mTouchProcessor) mTouchProcessor.dispose();
            if (sCurrent == this) sCurrent = null;
        }
        
        // functions
        
		/*
        private function initializeGraphicsAPI():void
        {
            mContext = mStage3D.context3D;
            mContext.enableErrorChecking = mEnableErrorChecking;
            mPrograms = new Dictionary();
			
            updateViewPort();
            
            trace("[Starling] Initialization complete.");
            trace("[Starling] Display Driver:" + mContext.driverInfo);
        }
        */
        
		/*
        private function initializeRoot():void
        {
            if (mStage.numChildren > 0) return;
            mStage.addChild(mRootObject);
        }
        */
        
		/*
        private function updateViewPort():void
        {
            if (mContext)
                mContext.configureBackBuffer(mViewPort.width, mViewPort.height, mAntiAliasing, false);
            
            mStage3D.x = mViewPort.x;
            mStage3D.y = mViewPort.y;
        }
        */
        
        private function render():void
        {
			/* FIXME
            if (mContext == null || mContext.driverInfo == "Disposed")
                return;            
            */
            
            var now:Number = getTimer() / 1000.0;
            var passedTime:Number = now - mLastFrameTimestamp;
            mLastFrameTimestamp = now;
            
            mStage.advanceTime(passedTime);
            // mJuggler.advanceTime(passedTime); FIXME
            mTouchProcessor.advanceTime(passedTime);
            
             /*
            RenderSupport.clear(mStage.color, 1.0);
            mSupport.setOrthographicProjection(mStage.stageWidth, mStage.stageHeight);
             */
            
            mStage.render(mSupport, 1.0);

             /*
            mSupport.finishQuadBatch();
            mSupport.nextFrame();             
            */
            
             /* FIXME
            mContext.present();
            */
        }
        
		/*
        private function updateNativeOverlay():void
        {
            mNativeOverlay.x = mViewPort.x;
            mNativeOverlay.y = mViewPort.y;
            mNativeOverlay.scaleX = mViewPort.width / mStage.stageWidth;
            mNativeOverlay.scaleY = mViewPort.height / mStage.stageHeight;
            
            // Having a native overlay on top of Stage3D content can cause a performance hit on
            // some environments. For that reason, we add it only to the stage while it's not empty.
            
            var numChildren:int = mNativeOverlay.numChildren;
            var parent:flash.display.DisplayObject = mNativeOverlay.parent;
            
            if (numChildren != 0 && parent == null) 
                mNativeStage.addChild(mNativeOverlay);
            else if (numChildren == 0 && parent)
                mNativeStage.removeChild(mNativeOverlay);
        }
        */
        
		/*
        private function showFatalError(message:String):void
        {
            var textField:TextField = new TextField();
            var textFormat:TextFormat = new TextFormat("Verdana", 12, 0xFFFFFF);
            textFormat.align = TextFormatAlign.CENTER;
            textField.defaultTextFormat = textFormat;
            textField.wordWrap = true;
            textField.width = mStage.stageWidth * 0.75;
            textField.autoSize = TextFieldAutoSize.CENTER;
            textField.text = message;
            textField.x = (mStage.stageWidth - textField.width) / 2;
            textField.y = (mStage.stageHeight - textField.height) / 2;
            textField.background = true;
            textField.backgroundColor = 0x440000;
            nativeOverlay.addChild(textField);
        }
        */
        
        /** Make this Starling instance the <code>current</code> one. */
        public function makeCurrent():void
        {
            sCurrent = this;
        }
        
        /** Starts rendering and dispatching of <code>ENTER_FRAME</code> events. */
        public function start():void { mStarted = true; }
        
        /** Stops rendering. */
        public function stop():void { mStarted = false; }
        
        // event handlers

		/*
        private function onStage3DError(event:ErrorEvent):void
        {
            showFatalError("This application is not correctly embedded (wrong wmode value)");
        }
        
        private function onContextCreated(event:Event):void
        {
            if (!Starling.handleLostContext && mContext)
            {
                showFatalError("Fatal error: The application lost the device context!");
                stop();
                return;
            }
            
            makeCurrent();
            
            initializeGraphicsAPI();
            initializeRoot();
            
            mTouchProcessor.simulateMultitouch = mSimulateMultitouch;
            dispatchEvent(new starling.events.Event(starling.events.Event.CONTEXT3D_CREATE));
        }
        */
        
        private function onEnterFrame(event:Event):void
        {
            makeCurrent();
            
            // if (mNativeOverlay) updateNativeOverlay(); FIXME
            if (mStarted) render();           
        }
        
        private function onKey(event:KeyboardEvent):void
        {
            makeCurrent();
            
            mStage.dispatchEvent(event);
        }
        
        /*
		private function onResize(event:flash.events.Event):void
        {
            var stage:flash.display.Stage = event.target as flash.display.Stage; 
            mStage.dispatchEvent(new ResizeEvent(Event.RESIZE, stage.stageWidth, stage.stageHeight));
        }
		*/
		
        private function onTouch(event:Event):void
        {
            var globalX:Number;
            var globalY:Number;
            var touchID:int;
            var phase:String;
            
            // figure out general touch properties
            if (event is MouseEvent)
            {
                var mouseEvent:MouseEvent = event as MouseEvent;
                globalX = mouseEvent.stageX;
                globalY = mouseEvent.stageY;
                touchID = 0;
                
                // MouseEvent.buttonDown returns true for both left and right button (AIR supports
                // the right mouse button). We only want to react on the left button for now,
                // so we have to save the state for the left button manually.
                if (event.type == MouseEvent.MOUSE_DOWN)    mLeftMouseDown = true;
                else if (event.type == MouseEvent.MOUSE_UP) mLeftMouseDown = false;
            }
            else
            {
//                var touchEvent:TouchEvent = event as TouchEvent;
//                globalX = touchEvent.stageX;
//                globalY = touchEvent.stageY;
//                touchID = touchEvent.touchPointID;
            }
            
            // figure out touch phase
            switch (event.type)
            {
                case TouchEvent.TOUCH_BEGIN: phase = TouchPhase.BEGAN; break;
                case TouchEvent.TOUCH_MOVE:  phase = TouchPhase.MOVED; break;
                case TouchEvent.TOUCH_END:   phase = TouchPhase.ENDED; break;
                case MouseEvent.MOUSE_DOWN:  phase = TouchPhase.BEGAN; break;
                case MouseEvent.MOUSE_UP:    phase = TouchPhase.ENDED; break;
                case MouseEvent.MOUSE_MOVE: 
                    phase = (mLeftMouseDown ? TouchPhase.MOVED : TouchPhase.HOVER); break;
            }
            
            // move position into viewport bounds
            globalX = mStage.stageWidth  * (globalX - mViewPort.x) / mViewPort.width;
            globalY = mStage.stageHeight * (globalY - mViewPort.y) / mViewPort.height;
            
            // enqueue touch in touch processor
            mTouchProcessor.enqueue(touchID, phase, globalX, globalY);
        }
        
        private function get touchEventTypes():Array
        {
			/* FIXME
            return Mouse.supportsCursor || !multitouchEnabled ?
                [ MouseEvent.MOUSE_DOWN,  MouseEvent.MOUSE_MOVE, MouseEvent.MOUSE_UP ] :
                [ TouchEvent.TOUCH_BEGIN, TouchEvent.TOUCH_MOVE, TouchEvent.TOUCH_END ];  
        	*/
			return [TouchEvent.TOUCH_BEGIN, TouchEvent.TOUCH_MOVE, TouchEvent.TOUCH_END];
        }
        
        // program management
        
        /** Registers a vertex- and fragment-program under a certain name. */
		/* FIXME
        public function registerProgram(name:String, vertexProgram:ByteArray, fragmentProgram:ByteArray):void
        {
            if (name in mPrograms)
                throw new Error("Another program with this name is already registered");
            
            var program:Program3D = mContext.createProgram();
            program.upload(vertexProgram, fragmentProgram);            
            mPrograms[name] = program;
        }
        */
		
        /** Deletes the vertex- and fragment-programs of a certain name. */
		/* FIXME
        public function deleteProgram(name:String):void
        {
            var program:Program3D = getProgram(name);            
            if (program)
            {                
                program.dispose();
                delete mPrograms[name];
            }
        }
        */
		
        /** Returns the vertex- and fragment-programs registered under a certain name. */
		/* FIXME
        public function getProgram(name:String):Program3D
        {
            return mPrograms[name] as Program3D;
        }
        */
		
        /** Indicates if a set of vertex- and fragment-programs is registered under a certain name. */
		/* FIXME
        public function hasProgram(name:String):Boolean
        {
            return name in mPrograms;
        }
        */
		
        // properties
        
        /** Indicates if this Starling instance is started. */
        public function get isStarted():Boolean { return mStarted; }
        
        /** The default juggler of this instance. Will be advanced once per frame. */
		/* FIXME
        public function get juggler():Juggler { return mJuggler; }
        */
		
        /** The render context of this instance. */
		/* 
        public function get context():Context3D { return mContext; }
        */
		
        /** Indicates if multitouch simulation with "Shift" and "Ctrl"/"Cmd"-keys is enabled. 
         *  @default false */
		/* 
        public function get simulateMultitouch():Boolean { return mSimulateMultitouch; }
        public function set simulateMultitouch(value:Boolean):void
        {
            mSimulateMultitouch = value;
            if (mContext) mTouchProcessor.simulateMultitouch = value;
        }
        */
        
        /** Indicates if Stage3D render methods will report errors. Activate only when needed,
         *  as this has a negative impact on performance. @default false */
		/*
        public function get enableErrorChecking():Boolean { return mEnableErrorChecking; }
        public function set enableErrorChecking(value:Boolean):void 
        { 
            mEnableErrorChecking = value;
            if (mContext) mContext.enableErrorChecking = value; 
        }
        */
        
        /** The antialiasing level. 0 - no antialasing, 16 - maximum antialiasing. @default 0 */
		/*
        public function get antiAliasing():int { return mAntiAliasing; }
        public function set antiAliasing(value:int):void
        {
            mAntiAliasing = value;
            updateViewPort();
        }
        */
		
        /** The viewport into which Starling contents will be rendered. */
		/*
        public function get viewPort():Rectangle { return mViewPort.clone(); }
        public function set viewPort(value:Rectangle):void
        {
            mViewPort = value.clone();
            updateViewPort();
        }
        */
		
        /** A Flash Sprite placed directly on top of the Starling content. Use it to display native
         *  Flash components. */
		/* 
        public function get nativeOverlay():Sprite
        {
            if (mNativeOverlay == null)
            {
                mNativeOverlay = new Sprite();
                mNativeStage.addChild(mNativeOverlay);
                updateNativeOverlay();
            }
            
            return mNativeOverlay;
        }
        */
		
        /** The Starling stage object, which is the root of the display tree that is rendered. */
        public function get stage():Stage
        {
            return mStage;
        }

        /** The Flash Stage3D object Starling renders into. */
		/* FIXME
        public function get stage3D():Stage3D
        {
            return mStage3D;
        }
        */
        
        /** The Flash (2D) stage object Starling renders beneath. */
        public function get nativeStage():BcNativeStage
        {
            return mNativeStage;
        }
        
        // static properties
        
        /** The currently active Starling instance. */
        public static function get current():Starling { return sCurrent; }
        
        /** The render context of the currently active Starling instance. */
        /* public static function get context():Context3D { return sCurrent ? sCurrent.context : null; } */
        
        /** The default juggler of the currently active Starling instance. */
        /* public static function get juggler():Juggler { return sCurrent ? sCurrent.juggler : null; } */
        
        /** Indicates if multitouch input should be supported. */
        public static function get multitouchEnabled():Boolean 
        { 
            return Multitouch.inputMode == MultitouchInputMode.TOUCH_POINT;
        }
        
        public static function set multitouchEnabled(value:Boolean):void
        {            
            Multitouch.inputMode = value ? MultitouchInputMode.TOUCH_POINT :
                                           MultitouchInputMode.NONE;
        }
        
        /** Indicates if Starling should automatically recover from a lost device context.
         *  On some systems, an upcoming screensaver or entering sleep mode may invalidate the
         *  render context. This setting indicates if Starling should recover from such incidents.
         *  Beware that this has a huge impact on memory consumption! @default false */
        public static function get handleLostContext():Boolean { return sHandleLostContext; }
        public static function set handleLostContext(value:Boolean):void 
        {
            if (sCurrent != null) throw new IllegalOperationError(
                "Setting must be changed before Starling instance is created");
            else
                sHandleLostContext = value;
        }
    }
}
