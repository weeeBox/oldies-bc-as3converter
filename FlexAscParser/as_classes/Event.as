public class Event extends Object
{
	public static const CANCEL : String = "cancel";
	public static const ENTER_FRAME : String = "enterFrame";
	public static const SOUND_COMPLETE : String = "soundComplete";
	public static const UNLOAD : String = "unload";
	public static const INIT : String = "init";
	public static const RENDER : String = "render";
	public static const TAB_ENABLED_CHANGE : String = "tabEnabledChange";
	public static const ADDED_TO_STAGE : String = "addedToStage";
	public static const FRAME_CONSTRUCTED : String = "frameConstructed";
	public static const TAB_CHILDREN_CHANGE : String = "tabChildrenChange";
	public static const CUT : String = "cut";
	public static const CLEAR : String = "clear";
	public static const CHANGE : String = "change";
	public static const RESIZE : String = "resize";
	public static const COMPLETE : String = "complete";
	public static const FULLSCREEN : String = "fullScreen";
	public static const SELECT_ALL : String = "selectAll";
	public static const REMOVED : String = "removed";
	public static const CONNECT : String = "connect";
	public static const SCROLL : String = "scroll";
	public static const OPEN : String = "open";
	public static const CLOSE : String = "close";
	public static const MOUSE_LEAVE : String = "mouseLeave";
	public static const ADDED : String = "added";
	public static const REMOVED_FROM_STAGE : String = "removedFromStage";
	public static const EXIT_FRAME : String = "exitFrame";
	public static const TAB_INDEX_CHANGE : String = "tabIndexChange";
	public static const PASTE : String = "paste";
	public static const DEACTIVATE : String = "deactivate";
	public static const COPY : String = "copy";
	public static const ID3 : String = "id3";
	public static const ACTIVATE : String = "activate";
	public static const SELECT : String = "select";	

	public function get bubbles() : Boolean;

	public function get cancelable() : Boolean;

	public function clone() : Event;

	public function get currentTarget() : Object;

	public function get eventPhase() : uint;	

	public function isDefaultPrevented() : Boolean;

	public function preventDefault() : void;

	public function stopImmediatePropagation() : void;

	public function stopPropagation() : void;

	public function get target() : Object;

	public function toString() : String;

	public function get type() : String;
}