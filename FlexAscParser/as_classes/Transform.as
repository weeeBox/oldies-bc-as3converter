public class Transform extends Object
{
	public function get colorTransform() : ColorTransform;

	public function set colorTransform(value : ColorTransform) : void;

	public function get concatenatedColorTransform() : ColorTransform;

	public function get concatenatedMatrix() : Matrix;

	public function get matrix() : Matrix;

	public function set matrix(value : Matrix) : void;

	public function get pixelBounds() : Rectangle;
}