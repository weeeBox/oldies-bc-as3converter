package bc.flash.geom 
{
	import bc.flash.error.NotImplementedError;
	import bc.flash.display.DisplayObject;
	
	public class Transform extends Object 
	{
		function Transform(displayObject : DisplayObject) : void;

		public function get colorTransform() : ColorTransform { implementMe(); }

		public function set colorTransform(value : ColorTransform) : void { implementMe(); }

		public function get concatenatedColorTransform() : ColorTransform { implementMe(); }

		public function get concatenatedMatrix() : Matrix { implementMe(); }

		/* [Version("10")] */
		/* public function getRelativeMatrix3D(relativeTo : DisplayObject) : Matrix3D; */

		public function get matrix() : Matrix { implementMe(); }

		public function set matrix(value : Matrix) : void { implementMe(); }

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
