package bc.flash.net
{
    import bc.flash.errors.NotImplementedError;
    import bc.flash.events.EventDispatcher;
    
    public class LocalConnection extends EventDispatcher
    {
        public function LocalConnection()
        {
            throw new NotImplementedError();
        }
        
         public function allowDomain(... domains):void
        {
            throw new NotImplementedError();
        }
        
         public function allowInsecureDomain(... domains):void
        {
            throw new NotImplementedError();
        }
        
         public function close():void
        {
            throw new NotImplementedError();
        }
        
         public function connect(connectionName:String):void
        {
            throw new NotImplementedError();
        }
        
         public function send(connectionName:String, methodName:String, ... arguments):void
        {
            throw new NotImplementedError();
        }
        
        public function get client():Object { throw new NotImplementedError(); }
        public function set client(value:Object):void { throw new NotImplementedError(); }
        public function get domain():String { throw new NotImplementedError(); }
        public function set domain(value:String):void { throw new NotImplementedError(); }
        public function get isPerUser():Boolean { throw new NotImplementedError(); }
        public function set isPerUser(value:Boolean):void { throw new NotImplementedError(); }
        public function get isSupported():Boolean { throw new NotImplementedError(); }
        public function set isSupported(value:Boolean):void { throw new NotImplementedError(); }
    }
}
