package bc.flash.geom 
{
	import bc.flash.display.DisplayObject;
	
	public class Transform extends Object 
	{
		function Transform(displayObject : DisplayObject) : void;

		public function get colorTransform() : ColorTransform { return null; }

		public function set colorTransform(value : ColorTransform) : void { }

		public function get concatenatedColorTransform() : ColorTransform { return null; }

		public function get concatenatedMatrix() : Matrix { return null; }

		/* [Version("10")] */
		/* public function getRelativeMatrix3D(relativeTo : DisplayObject) : Matrix3D; */

		public function get matrix() : Matrix { return null; }

		public function set matrix(value : Matrix) : void { }

		/* [Version("10")] */
		/* public function get matrix3D() : Matrix3D; */

		/* [Version("10")] */
		/* public function set matrix3D(m : Matrix3D) : *; */

		/* [Version("10")] */
		/* public function get perspectiveProjection() : PerspectiveProjection; */

		/* [Version("10")] */
		/* public function set perspectiveProjection(pm : PerspectiveProjection) : void; */

		/* public function get pixelBounds() : Rectangle; */
	}
}
