package bc.flash.display
{
	import bc.flash.error.IllegalOperationError;
	import bc.flash.events.EnterFrameEvent;
	import bc.flash.events.Event;
	import bc.flash.events.KeyboardEvent;
	import bc.flash.geom.Point;
	import bc.flash.geom.Rectangle;

	/** A Stage represents the root of the display tree.  
	 *  Only objects that are direct or indirect children of the stage will be rendered.
	 * 
	 *  <p>This class represents the Starling version of the stage. Don't confuse it with its 
	 *  Flash equivalent: while the latter contains objects of the type 
	 *  <code>flash.display.DisplayObject</code>, the Starling stage contains only objects of the
	 *  type <code>starling.display.DisplayObject</code>. Those classes are not compatible, and 
	 *  you cannot exchange one type with the other.</p>
	 * 
	 *  <p>A stage object is created automatically by the <code>Starling</code> class. Don't
	 *  create a Stage instance manually.</p>
	 * 
	 *  <strong>Keyboard Events</strong>
	 * 
	 *  <p>In Starling, keyboard events are only dispatched at the stage. Add an event listener
	 *  directly to the stage to be notified of keyboard events.</p>
	 * 
	 *  <strong>Resize Events</strong>
	 * 
	 *  <p>When the Flash player is resized, the stage dispatches a <code>ResizeEvent</code>. The 
	 *  event contains properties containing the updated width and height of the Flash player.</p>
	 *
	 *  @see starling.events.KeyboardEvent
	 *  @see starling.events.ResizeEvent  
	 * 
	 * */
	public class Stage extends DisplayObjectContainer
	{
		private var mWidth : int;
		private var mHeight : int;
		private var mColor : uint;
		private var mStageQuality : String;
		private var mFrameRate : Number;
		private var mAlign : String;
		private var mScaleMode : String;
		private var mFullScreenSourceRect : Rectangle;
		private var mStageFocusRect : Boolean;
		private var mTabChildren : Boolean;

		/** @private */
		public function Stage(width : int, height : int, color : uint = 0)
		{
			mWidth = width;
			mHeight = height;
			mColor = color;
		}

		public function tick(dt : Number) : void
		{
			dispatchEventOnChildren(new EnterFrameEvent(Event.ENTER_FRAME, dt));
		}

		/** Returns the object that is found topmost beneath a point in stage coordinates, or  
		 *  the stage itself if nothing else is found. */
		public override function hitTest(localPoint : Point, forTouch : Boolean = false) : DisplayObject
		{
			if (forTouch && (!visible || !touchable))
				return null;

			// if nothing else is hit, the stage returns itself as target
			var target : DisplayObject = super.hitTest(localPoint, forTouch);
			if (target == null) target = this;
			return target;
		}

		/** @private */
		public override function set width(value : Number) : void
		{
			throw new IllegalOperationError("Cannot set width of stage");
		}

		/** @private */
		public override function set height(value : Number) : void
		{
			throw new IllegalOperationError("Cannot set height of stage");
		}

		/** @private */
		public override function set x(value : Number) : void
		{
			throw new IllegalOperationError("Cannot set x-coordinate of stage");
		}

		/** @private */
		public override function set y(value : Number) : void
		{
			throw new IllegalOperationError("Cannot set y-coordinate of stage");
		}

		/** @private */
		public override function set scaleX(value : Number) : void
		{
			throw new IllegalOperationError("Cannot scale stage");
		}

		/** @private */
		public override function set scaleY(value : Number) : void
		{
			throw new IllegalOperationError("Cannot scale stage");
		}

		/** @private */
		public override function set rotation(value : Number) : void
		{
			throw new IllegalOperationError("Cannot rotate stage");
		}

		/** The background color of the stage. */
		public function get color() : uint
		{
			return mColor;
		}

		public function set color(value : uint) : void
		{
			mColor = value;
		}

		/** The width of the stage coordinate system. Change it to scale its contents relative
		 *  to the <code>viewPort</code> property of the Starling object. */
		public function get stageWidth() : int
		{
			return mWidth;
		}

		public function set stageWidth(value : int) : void
		{
			mWidth = value;
		}

		/** The height of the stage coordinate system. Change it to scale its contents relative
		 *  to the <code>viewPort</code> property of the Starling object. */
		public function get stageHeight() : int
		{
			return mHeight;
		}

		public function set stageHeight(value : int) : void
		{
			mHeight = value;
		}

		public function get quality() : String
		{
			return mStageQuality;
		}

		public function set quality(value : String) : void
		{
			mStageQuality = value;
		}

		public function get frameRate() : Number
		{
			return mFrameRate;
		}

		public function set frameRate(value : Number) : void
		{
			mFrameRate = value;
		}

		public function get align() : String
		{
			return mAlign;
		}

		public function set align(value : String) : void
		{
			mAlign = value;
		}

		public function get scaleMode() : String
		{
			return mScaleMode;
		}

		public function set scaleMode(value : String) : void
		{
			mScaleMode = value;
		}

		public function get fullScreenSourceRect() : Rectangle
		{
			return mFullScreenSourceRect;
		}

		public function set fullScreenSourceRect(value : Rectangle) : void
		{
			mFullScreenSourceRect = value;
		}

		public function get stageFocusRect() : Boolean
		{
			return mStageFocusRect;
		}

		public function set stageFocusRect(on : Boolean) : void
		{
			mStageFocusRect = on;
		}

		public function get tabChildren() : Boolean
		{
			return mTabChildren;
		}

		public function set tabChildren(value : Boolean) : void
		{
			mTabChildren = value;
		}
		
		public function keyPressed(code : uint) : void
		{
			dispatchEvent(new KeyboardEvent(KeyboardEvent.KEY_DOWN, code));
		}
		
		public function keyReleased(code : uint) : void
		{
			dispatchEvent(new KeyboardEvent(KeyboardEvent.KEY_UP, code));
		}
		
		public function touchDown(x : Number, y : Number, touchId : int) : void
		{	
		}
		
		public function touchMove(x : Number, y : Number, touchId : int) : void
		{
		}
		
		public function touchDragged(x : Number, y : Number, touchId : int) : void
		{
		}
		
		public function touchUp(x : Number, y : Number, touchId : int) : void
		{
		}
	}
}
