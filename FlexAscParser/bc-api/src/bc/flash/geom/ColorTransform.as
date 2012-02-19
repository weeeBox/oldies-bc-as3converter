package bc.flash.geom 
{
	import bc.flash.error.NotImplementedError;
	public class ColorTransform extends Object 
	{
		public var redMultiplier : Number;
		public var greenMultiplier : Number;
		public var blueMultiplier : Number;
		public var alphaMultiplier : Number;
		public var redOffset : Number;
		public var greenOffset : Number;
		public var blueOffset : Number;
		public var alphaOffset : Number;

		public function ColorTransform(redMultiplier : Number = 1.0, greenMultiplier : Number = 1.0, blueMultiplier : Number = 1.0, alphaMultiplier : Number = 1.0, redOffset : Number = 0, greenOffset : Number = 0, blueOffset : Number = 0, alphaOffset : Number = 0) : void { implementMe(); }

		public function get color() : uint { implementMe(); }

		public function set color(newColor : uint) : void { implementMe(); }

		public function concat(second : ColorTransform) : void { implementMe(); }
	}
}
