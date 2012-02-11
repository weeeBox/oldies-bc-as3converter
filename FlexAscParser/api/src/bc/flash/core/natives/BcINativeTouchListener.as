package bc.flash.core.natives
{
	/**
	 * @author weee
	 */
	public interface BcINativeTouchListener
	{
		function touchDown(x : Number, y : Number, touchId : int) : void;
		function touchMove(x : Number, y : Number, touchId : int) : void;
		function touchUp(x : Number, y : Number, touchId : int) : void;
	}
}
