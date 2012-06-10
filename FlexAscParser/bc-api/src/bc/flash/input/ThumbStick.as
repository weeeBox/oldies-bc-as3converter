package bc.flash.input
{
	/**
	 * @author weee
	 */
	public class ThumbStick
	{
		private var mx : Number;
		private var my : Number;
		
		public function ThumbStick()
		{
			mx = my = 0;
		}
		
		public function update(x:Number, y:Number):void
		{
			mx = x;
			my = y;
		}
		
		public function get x():Number
		{
			return mx;
		}
		
		public function get y():Number
		{
			return my;
		}
	}
}
