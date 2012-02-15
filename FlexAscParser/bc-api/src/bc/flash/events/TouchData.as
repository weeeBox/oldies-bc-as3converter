package bc.flash.events
{
	import bc.flash.display.DisplayObject;
	/**
	 * @author weee
	 */
	public class TouchData
	{
		public var touch : Touch;
		public var target : DisplayObject;
		
		public function TouchData(touch : Touch, target : DisplayObject)
		{
			this.touch = touch;
			this.target = target;
		}
	}
}
