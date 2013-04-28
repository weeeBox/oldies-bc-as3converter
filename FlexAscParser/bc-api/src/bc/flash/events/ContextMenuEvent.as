package bc.flash.events
{
    import bc.flash.errors.NotImplementedError;
    import bc.flash.events.Event;
    
    public class ContextMenuEvent extends Event
    {
        public static const MENU_ITEM_SELECT:String = "menuItemSelect";
        public static const MENU_SELECT:String = "menuSelect";
        
        public function ContextMenuEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false, mouseTarget:InteractiveObject = null, contextMenuOwner:InteractiveObject = null)
        {
            throw new NotImplementedError();
        }
        
        override public function clone():Event
        {
            throw new NotImplementedError();
        }
        
        override public function toString():String
        {
            throw new NotImplementedError();
        }
        
        public function get contextMenuOwner():InteractiveObject { throw new NotImplementedError(); }
        public function set contextMenuOwner(value:InteractiveObject):void { throw new NotImplementedError(); }
        public function get isMouseTargetInaccessible():Boolean { throw new NotImplementedError(); }
        public function set isMouseTargetInaccessible(value:Boolean):void { throw new NotImplementedError(); }
        public function get mouseTarget():InteractiveObject { throw new NotImplementedError(); }
        public function set mouseTarget(value:InteractiveObject):void { throw new NotImplementedError(); }
    }
}
