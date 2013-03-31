package flash.display
{
	import flash.events;
	
	public class Shape extends DisplayObject implements IEventDispatcher, IBitmapDrawable
	{
		public function get graphics() : Graphics { throw new NotImplementedError(); }
	}
}