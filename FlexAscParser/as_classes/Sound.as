public class Sound extends Object
{
	public function get length() : Number;	

	public function play(startTime : Number) : SoundChannel;

	public function play(startTime : Number, loops : int) : SoundChannel;

	public function play(startTime : Number, loops : int, sndTransform : SoundTransform) : SoundChannel;
}