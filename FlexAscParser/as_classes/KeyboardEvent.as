public class KeyboardEvent extends Event
{
	public static const KEY_DOWN : String = "keyDown";
	public static const KEY_UP : String = "keyUp";

	public function get altKey() : Boolean;

	public function set altKey(value : Boolean) : void;

	public function get charCode() : uint;

	public function set charCode(value : uint) : void;

	public function clone() : Event;

	public function get ctrlKey() : Boolean;

	public function set ctrlKey(value : Boolean) : void;

	public function get keyCode() : uint;

	public function set keyCode(value : uint) : void;

	public function get keyLocation() : uint;

	public function set keyLocation(value : uint) : void;

	public function get shiftKey() : Boolean;

	public function set shiftKey(value : Boolean) : void;

	public function toString() : String;

	public function updateAfterEvent() : void;
}