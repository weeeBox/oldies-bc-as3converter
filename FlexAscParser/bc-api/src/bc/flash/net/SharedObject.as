package bc.flash.net
{
    import bc.flash.errors.NotImplementedError;
    import bc.flash.events.EventDispatcher;
    
    public class SharedObject extends EventDispatcher
    {
         public function clear():void
        {
            throw new NotImplementedError();
        }
        
         public function close():void
        {
            throw new NotImplementedError();
        }
        
         public function connect(myConnection:NetConnection, params:String = null):void
        {
            throw new NotImplementedError();
        }
        
         public function flush(minDiskSpace:int = 0):String
        {
            throw new NotImplementedError();
        }
        
         public static function getLocal(name:String, localPath:String = null, secure:Boolean = false):SharedObject
        {
            throw new NotImplementedError();
        }
        
         public static function getRemote(name:String, remotePath:String = null, persistence:Object = false, secure:Boolean = false):SharedObject
        {
            throw new NotImplementedError();
        }
        
         public function send(... arguments):void
        {
            throw new NotImplementedError();
        }
        
         public function setDirty(propertyName:String):void
        {
            throw new NotImplementedError();
        }
        
         public function setProperty(propertyName:String, value:Object = null):void
        {
            throw new NotImplementedError();
        }
        
        public function get client():Object { throw new NotImplementedError(); }
        public function set client(value:Object):void { throw new NotImplementedError(); }
        public function get data():Object { throw new NotImplementedError(); }
        public function set data(value:Object):void { throw new NotImplementedError(); }
        public function get defaultObjectEncoding():uint { throw new NotImplementedError(); }
        public function set defaultObjectEncoding(value:uint):void { throw new NotImplementedError(); }
        public function get fps():Number { throw new NotImplementedError(); }
        public function set fps(value:Number):void { throw new NotImplementedError(); }
        public function get objectEncoding():uint { throw new NotImplementedError(); }
        public function set objectEncoding(value:uint):void { throw new NotImplementedError(); }
        public function get preventBackup():Boolean { throw new NotImplementedError(); }
        public function set preventBackup(value:Boolean):void { throw new NotImplementedError(); }
        public function get size():uint { throw new NotImplementedError(); }
        public function set size(value:uint):void { throw new NotImplementedError(); }
    }
}
