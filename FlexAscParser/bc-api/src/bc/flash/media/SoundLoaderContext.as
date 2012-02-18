package bc.flash.media
{
	/**
	 * @author weee
	 */
	public class SoundLoaderContext extends Object
	{
		public var bufferTime : Number;
		public var checkPolicyFile : Boolean;

		function SoundLoaderContext(bufferTime : Number = 1000, checkPolicyFile : Boolean = false) : void
		{
			this.bufferTime = bufferTime;
			this.checkPolicyFile = checkPolicyFile;			
		}
	}
}
