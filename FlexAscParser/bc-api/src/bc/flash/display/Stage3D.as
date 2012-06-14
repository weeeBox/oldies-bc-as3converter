package bc.flash.display {
	import bc.flash.error.NotImplementedError;
	import bc.flash.display3D.Context3D;
	/**
	 * @author weee
	 */
	public class Stage3D 
	{	
		public function get context3D() : Context3D { throw new NotImplementedError(); }

		public function requestContext3D(context3DRenderMode : String = "auto") : void { throw new NotImplementedError(); }

		public function get visible() : Boolean { throw new NotImplementedError(); }

		public function set visible(value : Boolean) : void { throw new NotImplementedError(); }

		public function get x() : Number { throw new NotImplementedError(); }

		public function set x(value : Number) : void { throw new NotImplementedError(); }

		public function get y() : Number { throw new NotImplementedError(); }

		public function set y(value : Number) : void { throw new NotImplementedError(); }					
	}
}
