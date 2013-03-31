package flash.media
{
	import flash.events;
	
	public class Microphone extends EventDispatcher implements IEventDispatcher
	{
		public function setSilenceLevel(arg1:Number, arg2:int=0) : void { throw new NotImplementedError(); }
		public function setUseEchoSuppression(arg1:Boolean) : void { throw new NotImplementedError(); }
		public function setLoopBack(arg1:Boolean=false) : void { throw new NotImplementedError(); }
		public function get noiseSuppressionLevel() : int { throw new NotImplementedError(); }
		public function set noiseSuppressionLevel(value:int) : void { throw new NotImplementedError(); }
		public function get framesPerPacket() : int { throw new NotImplementedError(); }
		public function set framesPerPacket(value:int) : void { throw new NotImplementedError(); }
		public function get soundTransform() : SoundTransform { throw new NotImplementedError(); }
		public function set soundTransform(value:SoundTransform) : void { throw new NotImplementedError(); }
		public function get enableVAD() : Boolean { throw new NotImplementedError(); }
		public function set enableVAD(value:Boolean) : void { throw new NotImplementedError(); }
		public function get muted() : Boolean { throw new NotImplementedError(); }
		public function get index() : int { throw new NotImplementedError(); }
		public function get silenceLevel() : Number { throw new NotImplementedError(); }
		public function get silenceTimeout() : int { throw new NotImplementedError(); }
		public function get useEchoSuppression() : Boolean { throw new NotImplementedError(); }
		public function get enhancedOptions() : MicrophoneEnhancedOptions { throw new NotImplementedError(); }
		public function set enhancedOptions(value:MicrophoneEnhancedOptions) : void { throw new NotImplementedError(); }
		public function get activityLevel() : Number { throw new NotImplementedError(); }
		public function get name() : String { throw new NotImplementedError(); }
		public function get codec() : String { throw new NotImplementedError(); }
		public function set codec(value:String) : void { throw new NotImplementedError(); }
		public function get gain() : Number { throw new NotImplementedError(); }
		public function set gain(value:Number) : void { throw new NotImplementedError(); }
		public function get rate() : int { throw new NotImplementedError(); }
		public function set rate(value:int) : void { throw new NotImplementedError(); }
		public function get encodeQuality() : int { throw new NotImplementedError(); }
		public function set encodeQuality(value:int) : void { throw new NotImplementedError(); }
	}
}