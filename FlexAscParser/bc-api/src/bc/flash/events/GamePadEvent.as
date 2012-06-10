package bc.flash.events
{
	/**
	 * @author weee
	 */
	public class GamePadEvent extends Event
	{
		public static const BUTTON_UP : String = "buttonUp";
		public static const BUTTON_DOWN : String = "buttonDown";
		public static const CONNECTED : String = "gamepadConnected";
		public static const DISCONNECTED : String = "gamepadDisconnected";
		public static const Undefined : uint = 0;
		public static const DPadUp : uint = 1;
		public static const DPadDown : uint = 2;
		public static const DPadLeft : uint = 4;
		public static const DPadRight : uint = 8;
		public static const Start : uint = 16;
		public static const Back : uint = 32;
		public static const LeftStick : uint = 64;
		public static const RightStick : uint = 128;
		public static const LeftShoulder : uint = 256;
		public static const RightShoulder : uint = 512;
		public static const BigButton : uint = 2048;
		public static const A : uint = 4096;
		public static const B : uint = 8192;
		public static const X : uint = 16384;
		public static const Y : uint = 32768;
		public static const LeftThumbstickLeft : uint = 2097152;
		public static const RightTrigger : uint = 4194304;
		public static const LeftTrigger : uint = 8388608;
		public static const RightThumbstickUp : uint = 16777216;
		public static const RightThumbstickDown : uint = 33554432;
		public static const RightThumbstickRight : uint = 67108864;
		public static const RightThumbstickLeft : uint = 134217728;
		public static const LeftThumbstickUp : uint = 268435456;
		public static const LeftThumbstickDown : uint = 536870912;
		public static const LeftThumbstickRight : uint = 1073741824;
		private var mCode : uint;
		private var mPlayerIndex : uint;

		public function GamePadEvent(type : String, playerIndex : uint, code : uint = Undefined)
		{
			super(type, false);
			mPlayerIndex = playerIndex;
			mCode = code;
		}
		
		public function update(playerIndex : uint, code : uint = Undefined) : void
		{
			mPlayerIndex = playerIndex;
			mCode = code;
		}

		public function get playerIndex() : uint
		{
			return mPlayerIndex;
		}

		public function get code() : uint
		{
			return mCode;
		}
	}
}
