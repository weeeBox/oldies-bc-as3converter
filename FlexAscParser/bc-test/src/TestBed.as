package
{
	import flash.geom.Point;
	import flash.geom.Matrix;
	import flash.display.MovieClip;
	 
	public class TestBed extends MovieClip
	{
		public function TestBed()
		{
			trace("Working");

			
			var m : Matrix = new Matrix();
			m.translate(24, -50);
			m.scale(1.5, 0.8);
			m.rotate(25);
			
			var p : Point = new Point(10, -5);
			var p2 : Point = m.transformPoint(p);
			
			trace(p2.x + " " + p2.y);			
		}
	}
}
