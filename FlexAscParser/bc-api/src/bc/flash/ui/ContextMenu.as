package bc.flash.ui
{
    import bc.flash.display.NativeMenu;
    import bc.flash.errors.NotImplementedError;
    
    public final  class ContextMenu extends NativeMenu
    {
        public function ContextMenu()
        {
            throw new NotImplementedError();
        }
        
        override public function addItemAt(item:NativeMenuItem, index:int):NativeMenuItem
        {
            throw new NotImplementedError();
        }
        
        override public function clone():NativeMenu
        {
            throw new NotImplementedError();
        }
        
        override public function containsItem(item:NativeMenuItem):Boolean
        {
            throw new NotImplementedError();
        }
        
        override public function display(stage:Stage, stageX:Number, stageY:Number):void
        {
            throw new NotImplementedError();
        }
        
        override public function getItemAt(index:int):NativeMenuItem
        {
            throw new NotImplementedError();
        }
        
        override public function getItemIndex(item:NativeMenuItem):int
        {
            throw new NotImplementedError();
        }
        
         public function hideBuiltInItems():void
        {
            throw new NotImplementedError();
        }
        
        override public function removeAllItems():void
        {
            throw new NotImplementedError();
        }
        
        override public function removeItemAt(index:int):NativeMenuItem
        {
            throw new NotImplementedError();
        }
        
        public function get builtInItems():ContextMenuBuiltInItems { throw new NotImplementedError(); }
        public function set builtInItems(value:ContextMenuBuiltInItems):void { throw new NotImplementedError(); }
        public function get clipboardItems():ContextMenuClipboardItems { throw new NotImplementedError(); }
        public function set clipboardItems(value:ContextMenuClipboardItems):void { throw new NotImplementedError(); }
        public function get clipboardMenu():Boolean { throw new NotImplementedError(); }
        public function set clipboardMenu(value:Boolean):void { throw new NotImplementedError(); }
        public function get customItems():Array { throw new NotImplementedError(); }
        public function set customItems(value:Array):void { throw new NotImplementedError(); }
        public function get isSupported():Boolean { throw new NotImplementedError(); }
        public function set isSupported(value:Boolean):void { throw new NotImplementedError(); }
        public function get items():Array { throw new NotImplementedError(); }
        public function set items(value:Array):void { throw new NotImplementedError(); }
        public function get link():URLRequest { throw new NotImplementedError(); }
        public function set link(value:URLRequest):void { throw new NotImplementedError(); }
        public function get numItems():int { throw new NotImplementedError(); }
        public function set numItems(value:int):void { throw new NotImplementedError(); }
    }
}
