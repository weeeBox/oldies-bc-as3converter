public class Matrix extends Object
{
	public var a : Number;
	public var c : Number;
	public var ty : Number;
	public var b : Number;
	public var d : Number;
	public var tx : Number;

	public function clone() : Matrix;

	public function concat(m : Matrix) : void;

	public function createBox(scaleX : Number, scaleY : Number) : void;

	public function createBox(scaleX : Number, scaleY : Number, rotation : Number) : void;

	public function createBox(scaleX : Number, scaleY : Number, rotation : Number, tx : Number, ty : Number) : void;

	public function createGradientBox(width : Number, height : Number) : void;

	public function createGradientBox(width : Number, height : Number, rotation : Number) : void;

	public function createGradientBox(width : Number, height : Number, rotation : Number, tx : Number, ty : Number) : void;

	public function deltaTransformPoint(point : Point) : Point;

	public function identity() : void;

	public function invert() : void;

	public function rotate(angle : Number) : void;

	public function scale(sx : Number, sy : Number) : void;

	public function toString() : String;

	public function transformPoint(point : Point) : Point;

	public function translate(dx : Number, dy : Number) : void;
}