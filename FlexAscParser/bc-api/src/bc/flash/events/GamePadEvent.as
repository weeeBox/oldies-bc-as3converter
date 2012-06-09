package bc.flash.events
{
	/**
	 * @author weee
	 */
	public class GamePadEvent extends Event
	{		
        public static const BUTTON_UP:String = "buttonUp";        
        public static const BUTTON_DOWN:String = "buttonDown";
		
		public static const CONNECTED:String = "gamepadConnected";
		public static const DISCONNECTED:String = "gamepadDisconnected";
		
		public static const Undefined:uint = 0xffffffff;
		
		public static const DPadUp:uint = 0;
        public static const DPadDown:uint = 1;
        public static const DPadLeft:uint = 2;
        public static const DPadRight:uint = 3;
        public static const Start:uint = 4;
        public static const Back:uint = 5;
        public static const LeftStick:uint = 6;
        public static const RightStick:uint = 7;
        public static const LeftShoulder:uint = 8;
        public static const RightShoulder:uint = 9;
        public static const BigButton:uint = 10;
        public static const A:uint = 11;
        public static const B:uint = 12;
        public static const X:uint = 13;
        public static const Y:uint = 14;
        public static const LeftThumbstickLeft:uint = 15;
        public static const RightTrigger:uint = 16;
        public static const LeftTrigger:uint = 17;
        public static const RightThumbstickUp:uint = 18;
        public static const RightThumbstickDown:uint = 19;
        public static const RightThumbstickRight:uint = 20;
        public static const RightThumbstickLeft:uint = 21;
        public static const LeftThumbstickUp:uint = 22;
        public static const LeftThumbstickDown:uint = 23;
        public static const LeftThumbstickRight:uint = 24;
						
		private var mCode:uint;
		private var mPlayerIndex:uint;
		
		public function GamePadEvent(type:String, playerIndex:uint, code:uint = Undefined)
		{
			super(type, false);
			mPlayerIndex = playerIndex;
			mCode = code;
		}
		
		public function get playerIndex():uint
		{
			return mPlayerIndex;
		}
		
		public function get code():uint
		{
			return mCode;
		}
	}
}
