package flash.display 
{
	import flash.geom.Transform;
	import flash.events.EventDispatcher;
	import flash.display.DisplayObjectContainer;
	
	public class DisplayObject extends EventDispatcher /* implements IBitmapDrawable, IBitmapDrawable */ 
	{
		/* public function get accessibilityProperties() : AccessibilityProperties; */

		/* public function set accessibilityProperties(value : AccessibilityProperties) : void; */

		public function get alpha() : Number { return 1.0; }

		public function set alpha(value : Number) : void { }

		public function get blendMode() : String { return null; }

		public function set blendMode(value : String) : void {}
		
		/* [Version("10")] */
		/* public function set blendShader(value : Shader) : void; */

		public function get cacheAsBitmap() : Boolean { return false; }

		public function set cacheAsBitmap(value : Boolean) : void { }

		/* public function get filters() : Array; */

		/* public function set filters(value : Array) : void; */

		/* public function getBounds(targetCoordinateSpace : DisplayObject) : Rectangle; */

		/* public function getRect(targetCoordinateSpace : DisplayObject) : Rectangle; */

		/* public function globalToLocal(point : Point) : Point; */

		/* [Version("10")] */
		/* public function globalToLocal3D(point : Point) : Vector3D; */

		public function get height() : Number { return 0; }

		public function set height(value : Number) : void {}

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

		/* public function get name() : String; */

		/* public function set name(value : String) : void; */

		public function get opaqueBackground() : Object { return null; }

		public function set opaqueBackground(value : Object) : void { }

		public function get parent() : DisplayObjectContainer { return null; }

		public function get root() : DisplayObject { return null; }

		public function get rotation() : Number { return 0; }

		public function set rotation(value : Number) : void { }

		[Version("10")]
		public function get rotationX() : Number { return 0; }

		[Version("10")]
		public function set rotationX(value : Number) : void { }

		[Version("10")]
		public function get rotationY() : Number { return 0; }

		[Version("10")]
		public function set rotationY(value : Number) : void { }

		[Version("10")]
		public function get rotationZ() : Number { return 0; }

		[Version("10")]
		public function set rotationZ(value : Number) : void { }

		/* public function get scale9Grid() : Rectangle; */

		/* public function set scale9Grid(innerRectangle : Rectangle) : void; */

		public function get scaleX() : Number { return 0; }

		public function set scaleX(value : Number) : void { }

		public function get scaleY() : Number { return 0; }

		public function set scaleY(value : Number) : void { }

		[Version("10")]
		public function get scaleZ() : Number { return 0; }

		[Version("10")]
		public function set scaleZ(value : Number) : void { }

		public function get scrollRect() : Rectangle { return null; }

		public function set scrollRect(value : Rectangle) : void { }

		public function get stage() : Stage { return null; }

		public function get transform() : Transform { return null; }

		public function set transform(value : Transform) : void { }

		public function get visible() : Boolean { return false; }

		public function set visible(value : Boolean) : void { }

		public function get width() : Number { return 0; }

		public function set width(value : Number) : void { }

		public function get x() : Number { return 0; }

		public function set x(value : Number) : void { }

		public function get y() : Number { return 0; }

		public function set y(value : Number) : void { }

		[Version("10")]
		public function get z() : Number { return 0; }

		[Version("10")]
		public function set z(value : Number) : void { }
	}
}
