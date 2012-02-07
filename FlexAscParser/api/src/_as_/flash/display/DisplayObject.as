package _as_.flash.display
{
	import _as_.flash.events.EventDispatcher;
	import _as_.flash.geom.Rectangle;
	import _as_.flash.geom.Transform;

	public class DisplayObject extends EventDispatcher implements IBitmapDrawable
	{
		private var m_name : String;
		private var m_parent : DisplayObjectContainer;
		
		private var m_x : Number;
		private var m_y : Number;
		private var m_z : Number;
		
		private var m_visible : Boolean;
		
		private var m_width : Number;
		private var m_height : Number;
		
		private var m_pivotX : Number;
		private var m_pivotY : Number;
		
		private var m_scaleX : Number = 1;
		private var m_scaleY : Number = 1;
		private var m_scaleZ : Number = 1;
		
		private var m_rotationX : Number;
		private var m_rotationY : Number;
		private var m_rotationZ : Number;
		
		private var m_alpha : Number;		
		
		private var m_blendMode : String = BlendMode.NORMAL;
				
		private var m_cacheAsBitmap : Boolean;
		
		/*
		float mX;
	    float mY;
	    float mPivotX;
	    float mPivotY;
	    float mScaleX;
	    float mScaleY;
	    float mRotationZ;
	    float mAlpha;
	    BOOL mVisible;
	    BOOL mTouchable;
	    
	    SPDisplayObjectContainer *mParent;    
	    double mLastTouchTimestamp;
	    NSString *mName;
	    */
		
		/* public function get accessibilityProperties() : AccessibilityProperties; */
		/* public function set accessibilityProperties(value : AccessibilityProperties) : void; */
		
		public function get alpha() : Number
		{
			return m_alpha;
		}

		public function set alpha(value : Number) : void
		{
			m_alpha = value;
		}

		public function get blendMode() : String
		{
			return m_blendMode;
		}

		public function set blendMode(value : String) : void
		{
			m_blendMode = value;
		}

		/* [Version("10")] */
		/* public function set blendShader(value : Shader) : void; */
		
		public function get cacheAsBitmap() : Boolean
		{
			return m_cacheAsBitmap;
		}

		public function set cacheAsBitmap(value : Boolean) : void
		{
			m_cacheAsBitmap = value;
		}

		/* public function get filters() : Array; */
		/* public function set filters(value : Array) : void; */
		/* public function getBounds(targetCoordinateSpace : DisplayObject) : Rectangle; */
		/* public function getRect(targetCoordinateSpace : DisplayObject) : Rectangle; */
		/* public function globalToLocal(point : Point) : Point; */
		
		/* [Version("10")] */
		/* public function globalToLocal3D(point : Point) : Vector3D; */
		
		public function get width() : Number
		{
			return m_width;
		}

		public function set width(value : Number) : void
		{
			m_width = value;
		}
		
		
		public function get height() : Number
		{
			return m_height;
		}

		public function set height(value : Number) : void
		{
			m_height = value;
		}

		/* public function hitTestObject(obj : DisplayObject) : Boolean; */
		/* public function hitTestPoint(x : Number, y : Number, shapeFlag : Boolean = false) : Boolean; */
		/* public function get loaderInfo() : LoaderInfo; */
		
		/* [Version("10")] */
		/* public function local3DToGlobal(point3d : Vector3D) : Point; */
		/* public function localToGlobal(point : Point) : Point; */
		/* public function get mask() : DisplayObject; */
		/* public function set mask(value : DisplayObject) : void; */
		/* public function get mouseX() : Number; */
		/* public function get mouseY() : Number; */
		
		public function get name() : String
		{
			return m_name;
		}

		public function set name(value : String) : void
		{
			m_name = value;
		}

		public function get opaqueBackground() : uint
		{
			return 0;
		}

		public function set opaqueBackground(value : uint) : void
		{
		}

		public function get parent() : DisplayObjectContainer
		{
			return m_parent;
		}

		public function get root() : DisplayObject
		{
			return m_parent == null ? this : m_parent.root;
		}

		public function get rotation() : Number
		{
			return m_rotationZ;
		}

		public function set rotation(value : Number) : void
		{
			m_rotationZ = value;
		}

		/* [Version("10")]*/
		public function get rotationX() : Number
		{
			return m_rotationX;
		}

		[Version("10")]
		public function set rotationX(value : Number) : void
		{
			m_rotationX = value;
		}

		[Version("10")]
		public function get rotationY() : Number
		{
			return m_rotationY;
		}

		[Version("10")]
		public function set rotationY(value : Number) : void
		{
			m_rotationY = value;
		}

		[Version("10")]
		public function get rotationZ() : Number
		{
			return rotation;
		}

		[Version("10")]
		public function set rotationZ(value : Number) : void
		{
			rotation = value;
		}

		/* public function get scale9Grid() : Rectangle; */
		/* public function set scale9Grid(innerRectangle : Rectangle) : void; */
		
		public function get scaleX() : Number
		{
			return m_scaleX;
		}

		public function set scaleX(value : Number) : void
		{
			m_scaleX = value;
		}

		public function get scaleY() : Number
		{
			return m_scaleY;
		}

		public function set scaleY(value : Number) : void
		{
			m_scaleY = value;
		}

		/* [Version("10")] */
		public function get scaleZ() : Number
		{
			return m_scaleZ;
		}

		/* [Version("10")] */
		public function set scaleZ(value : Number) : void
		{
			m_scaleZ = value;
		}

		public function get scrollRect() : Rectangle
		{
			return null;
		}

		public function set scrollRect(value : Rectangle) : void
		{
		}

		public function get stage() : Stage
		{
			return null;
		}

		public function get transform() : Transform
		{
			return null;
		}

		public function set transform(value : Transform) : void
		{
		}

		public function get visible() : Boolean
		{
			return m_visible;
		}

		public function set visible(value : Boolean) : void
		{
			m_visible = value;
		}

		public function get x() : Number
		{
			return m_x;
		}

		public function set x(value : Number) : void
		{
			m_x = value;
		}

		public function get y() : Number
		{
			return m_y;
		}

		public function set y(value : Number) : void
		{
			m_y = value;
		}

		/* [Version("10")] */
		public function get z() : Number
		{
			return m_z;
		}

		/* [Version("10")] */
		public function set z(value : Number) : void
		{
			m_z = value;
		}
	}
}
