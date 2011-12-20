public class Stage extends DisplayObjectContainer
{
	public function addChild(child : DisplayObject) : DisplayObject;

	public function addChildAt(child : DisplayObject, index : int) : DisplayObject;

	public function addEventListener(type : String, listener : Object) : void;
	
	public function addEventListener(type : String, listener : Object, useCapture : Boolean) : void;
	
	public function addEventListener(type : String, listener : Object, useCapture : Boolean, priority : int) : void;
	
	public function addEventListener(type : String, listener : Object, useCapture : Boolean, priority : int, useWeakReference : Boolean) : void;

	public function get align() : String;

	public function set align(value : String) : void;

	public function set alpha(value : Number) : void;

	public function set blendMode(value : String) : void;

	public function set cacheAsBitmap(value : Boolean) : void;

	public function get colorCorrection() : String;

	public function set colorCorrection(value : String) : void;

	public function get colorCorrectionSupport() : String;

	public function get displayState() : String;

	public function set displayState(value : String) : void;

	public function set filters(value : Array) : void;

	public function get focus() : DisplayObject;

	public function set focus(newFocus : DisplayObject) : void;

	public function set focusRect(value : Object) : void;

	public function get frameRate() : Number;

	public function set frameRate(value : Number) : void;

	public function get fullScreenHeight() : uint;

	public function get fullScreenSourceRect() : Rectangle;

	public function set fullScreenSourceRect(value : Rectangle) : void;

	public function get fullScreenWidth() : uint;

	public function hasEventListener(type : String) : Boolean;

	public function get height() : Number;

	public function set height(value : Number) : void;

	public function invalidate() : void;

	public function isFocusInaccessible() : Boolean;

	public function set mask(value : DisplayObject) : void;

	public function get mouseChildren() : Boolean;

	public function set mouseChildren(value : Boolean) : void;

	public function set mouseEnabled(value : Boolean) : void;

	public function set name(value : String) : void;

	public function get numChildren() : int;

	public function set opaqueBackground(value : Object) : void;

	public function get quality() : String;

	public function set quality(value : String) : void;

	public function removeChildAt(index : int) : DisplayObject;

	public function set rotation(value : Number) : void;

	public function set rotationX(value : Number) : void;

	public function set rotationY(value : Number) : void;

	public function set rotationZ(value : Number) : void;

	public function set scale9Grid(value : Rectangle) : void;

	public function get scaleMode() : String;

	public function set scaleMode(value : String) : void;

	public function set scaleX(value : Number) : void;

	public function set scaleY(value : Number) : void;

	public function set scaleZ(value : Number) : void;

	public function set scrollRect(value : Rectangle) : void;

	public function setChildIndex(child : DisplayObject, index : int) : void;

	public function get showDefaultContextMenu() : Boolean;

	public function set showDefaultContextMenu(value : Boolean) : void;

	public function get stageFocusRect() : Boolean;

	public function set stageFocusRect(on : Boolean) : void;

	public function get stageHeight() : int;

	public function set stageHeight(value : int) : void;

	public function get stageWidth() : int;

	public function set stageWidth(value : int) : void;

	public function swapChildrenAt(index1 : int, index2 : int) : void;

	public function get tabChildren() : Boolean;

	public function set tabChildren(value : Boolean) : void;

	public function set tabEnabled(value : Boolean) : void;

	public function set tabIndex(value : int) : void;

	public function set transform(value : Transform) : void;

	public function set visible(value : Boolean) : void;

	public function get width() : Number;

	public function set width(value : Number) : void;

	public function willTrigger(type : String) : Boolean;

	public function set x(value : Number) : void;

	public function set y(value : Number) : void;

	public function set z(value : Number) : void;
}