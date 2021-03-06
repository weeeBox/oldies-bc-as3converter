package bc.flash
{
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public class Debug
	{
		public static function assert(condition : Boolean, message : String = "") : void
		{
			if (!condition)
			{
				throw new Error("Assertion error: " + message);
			}
		}
		
		public static function implementMe(message : String = "") : void
		{
			Debug.assert(false, "Implement me: " + message);
		}
	}
}
