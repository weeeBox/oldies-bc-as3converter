package bc.flash.input
{
	import bc.flash.Vector;
	/**
	 * @author weee
	 */
	public class GamePad
	{
		private var mPlayerIndex:uint;
		private var mRightStick:ThumbStick;
		private var mLeftStick:ThumbStick;		
		private var mLeftTrigger:Number;
		private var mRightTrigger:Number;
		
		private static var mGamePads:Vector.<GamePad> = Vector.<GamePad>([new GamePad(0), new GamePad(1), new GamePad(2), new GamePad(3)]);
		
		public static function player(index:uint):GamePad
		{
			return mGamePads[index];		
		}
		
		public function GamePad(playerIndex:uint)
		{
			mPlayerIndex = playerIndex; 
			mRightStick = new ThumbStick();
			mLeftStick = new ThumbStick();
		}
		
		public function update(leftTrigger:Number, rightTrigger:Number):void
		{
			mLeftTrigger = leftTrigger;
			mRightTrigger = rightTrigger;
		}
		
		public function get playerIndex():uint
		{
			return mPlayerIndex;
		}
		
		public function get leftStick():ThumbStick
		{
			return mLeftStick;			 		
		}
		
		public function get rightStick():ThumbStick
		{
			return mRightStick;
		}
		
		public function get leftTrigger():Number
		{
			return mLeftTrigger;
		}
		
		public function get rightTrigger():Number
		{
			return mRightTrigger;
		}
	}
}
