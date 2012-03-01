package bc.flash.display
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.core.RenderSupport;
	import bc.flash.geom.Transform;
	import bc.flash.error.AbstractClassError;
	import bc.flash.error.AbstractMethodError;
	import bc.flash.events.Event;
	import bc.flash.events.EventDispatcher;
	import bc.flash.events.TouchEvent;
	import bc.flash.geom.Point;
	import bc.flash.geom.Rectangle;

	import flash.utils.getQualifiedClassName;

	public class DisplayObject extends EventDispatcher implements IBitmapDrawable
	{
		// members
		private var mX : Number;
		private var mY : Number;
		private var mScaleX : Number;
		private var mScaleY : Number;
		private var mRotation : Number;
		private var mAlpha : Number;
		private var mVisible : Boolean;
		private var mTouchable : Boolean;
		private var mName : String;
		private var mLastTouchTimestamp : Number;
		private var mParent : DisplayObjectContainer;
		
		/** Helper objects. */
		private var mScrollRect : Rectangle;
		private var mTransform : Transform;

		/** @private */
		public function DisplayObject()
		{
			if (getQualifiedClassName(this) == "starling.display::DisplayObject")
				throw new AbstractClassError();

			mX = mY = mRotation = 0.0;
			mScaleX = mScaleY = mAlpha = 1.0;
			mVisible = mTouchable = true;
			mLastTouchTimestamp = -1;

			mTransform = new Transform(this);
		}

		/** Disposes all resources of the display object. 
		 * GPU buffers are released, event listeners are removed. */
		public function dispose() : void
		{
			removeEventListeners();
		}

		/** Removes the object from its parent, if it has one. */
		public function removeFromParent(dispose : Boolean = false) : void
		{
			if (mParent) mParent.removeChild(this);
			if (dispose) this.dispose();
		}

		/** Returns a rectangle that completely encloses the object as it appears in another 
		 *  coordinate system. If you pass a 'resultRectangle', the result will be stored in this 
		 *  rectangle instead of creating a new object. */
		public function getBounds(targetSpace : DisplayObject, resultRect : Rectangle = null) : Rectangle
		{
			throw new AbstractMethodError("Method needs to be implemented in subclass");
		}

		/** Returns the object that is found topmost beneath a point in local coordinates, or nil if 
		 *  the test fails. If "forTouch" is true, untouchable and invisible objects will cause
		 *  the test to fail. */
		public function hitTest(localPoint : Point, forTouch : Boolean = false) : DisplayObject
		{
			throw new NotImplementedError();
		}

		/** Transforms a point from the local coordinate system to global (stage) coordinates. */
		public function localToGlobal(localPoint : Point) : Point
		{
			throw new NotImplementedError();
		}

		/** Transforms a point from global (stage) coordinates to the local coordinate system. */
		public function globalToLocal(globalPoint : Point) : Point
		{
			throw new NotImplementedError();
		}

		/** Renders the display object with the help of a support object. Never call this method
		 *  directly, except from within another render method.
		 *  @param support Provides utility functions for rendering.
		 *  @param alpha The accumulated alpha value from the object's parent up to the stage. */
		public function render(support : RenderSupport, alpha : Number) : void
		{
			throw new AbstractMethodError("Method needs to be implemented in subclass");
		}

		/** @inheritDoc */
		public override function dispatchEvent(event : Event) : void
		{
			// on one given moment, there is only one set of touches -- thus,
			// we process only one touch event with a certain timestamp per frame
			if (event is TouchEvent)
			{
				var touchEvent : TouchEvent = event as TouchEvent;
				if (touchEvent.timestamp == mLastTouchTimestamp) return;
				else mLastTouchTimestamp = touchEvent.timestamp;
			}

			super.dispatchEvent(event);
		}

		// internal methods
		/** @private */
		internal function setParent(value : DisplayObjectContainer) : void
		{
			mParent = value;
		}

		/** @private */
		internal function dispatchEventOnChildren(event : Event) : void
		{
			dispatchEvent(event);
		}

		/** The width of the object in pixels. */
		public function get width() : Number
		{
			throw new NotImplementedError();
		}

		public function set width(value : Number) : void
		{
			// this method calls 'this.scaleX' instead of changing mScaleX directly.
			// that way, subclasses reacting on size changes need to override only the scaleX method.

			mScaleX = 1.0;
			var actualWidth : Number = width;
			if (actualWidth != 0.0) scaleX = value / actualWidth;
			else scaleX = 1.0;
		}

		/** The height of the object in pixels. */
		public function get height() : Number
		{
			throw new NotImplementedError();
		}

		public function set height(value : Number) : void
		{
			mScaleY = 1.0;
			var actualHeight : Number = height;
			if (actualHeight != 0.0) scaleY = value / actualHeight;
			else scaleY = 1.0;
		}

		/** The topmost object in the display tree the object is part of. */
		public function get root() : DisplayObject
		{
			var currentObject : DisplayObject = this;
			while (currentObject.parent) currentObject = currentObject.parent;
			return currentObject;
		}

		/** The x coordinate of the object relative to the local coordinates of the parent. */
		public function get x() : Number
		{
			return mX;
		}

		public function set x(value : Number) : void
		{
			mX = value;
		}

		/** The y coordinate of the object relative to the local coordinates of the parent. */
		public function get y() : Number
		{
			return mY;
		}

		public function set y(value : Number) : void
		{
			mY = value;
		}

		/** The horizontal scale factor. '1' means no scale, negative values flip the object. */
		public function get scaleX() : Number
		{
			return mScaleX;
		}

		public function set scaleX(value : Number) : void
		{
			mScaleX = value;
		}

		/** The vertical scale factor. '1' means no scale, negative values flip the object. */
		public function get scaleY() : Number
		{
			return mScaleY;
		}

		public function set scaleY(value : Number) : void
		{
			mScaleY = value;
		}

		/** The rotation of the object in radians. (In Starling, all angles are measured 
		 *  in radians.) */
		public function get rotation() : Number
		{
			return mRotation;
		}

		public function set rotation(value : Number) : void
		{
			// move into range [-180 deg, +180 deg]
			while (value < -Math.PI) value += Math.PI * 2.0;
			while (value > Math.PI) value -= Math.PI * 2.0;
			mRotation = value;
		}

		/** The opacity of the object. 0 = transparent, 1 = opaque. */
		public function get alpha() : Number
		{
			return mAlpha;
		}

		public function set alpha(value : Number) : void
		{
			mAlpha = value < 0.0 ? 0.0 : (value > 1.0 ? 1.0 : value);
		}

		/** The visibility of the object. An invisible object will be untouchable. */
		public function get visible() : Boolean
		{
			return mVisible;
		}

		public function set visible(value : Boolean) : void
		{
			mVisible = value;
		}

		/** Indicates if this object (and its children) will receive touch events. */
		public function get touchable() : Boolean
		{
			return mTouchable;
		}

		public function set touchable(value : Boolean) : void
		{
			mTouchable = value;
		}

		/** The name of the display object (default: null). Used by 'getChildByName()' of 
		 *  display object containers. */
		public function get name() : String
		{
			return mName;
		}

		public function set name(value : String) : void
		{
			mName = value;
		}

		/** The display object container that contains this display object. */
		public function get parent() : DisplayObjectContainer
		{
			return mParent;
		}

		/** The stage the display object is connected to, or null if it is not connected 
		 *  to a stage. */
		public function get stage() : Stage
		{
			return Stage.getInstance();
		}

		/** An object with properties pertaining to a display object's matrix, color transform, and pixel bounds. */
		public function get transform() : Transform
		{
			return mTransform;
		}

		/** An object with properties pertaining to a display object's matrix, color transform, and pixel bounds. */
		public function set transform(value : Transform) : void
		{
			mTransform = value;
		}

		public function get scrollRect() : Rectangle
		{
			return mScrollRect;
		}

		public function set scrollRect(value : Rectangle) : void
		{
			mScrollRect = value;
		}

		public function get opaqueBackground() : uint
		{
			return 0xffffffff;
		}

		public function set opaqueBackground(value : uint) : void
		{
		}
	}
}
