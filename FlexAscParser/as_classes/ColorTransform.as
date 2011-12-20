public class ColorTransform extends Object
{
	public var redOffset : Number;
	public var greenMultiplier : Number;
	public var blueOffset : Number;
	public var alphaOffset : Number;
	public var redMultiplier : Number;
	public var blueMultiplier : Number;
	public var greenOffset : Number;
	public var alphaMultiplier : Number;

	public function get color() : uint;

	public function set color(newColor : uint) : void;

	public function concat(second : ColorTransform) : void;

	public function toString() : String;
}