package flash.net
{
	import flash.events;
	import flash.utils;
	
	public class FileReference extends EventDispatcher implements IEventDispatcher
	{
		public function load() : void { throw new NotImplementedError(); }
		public function download(arg1:URLRequest, arg2:String=null) : void { throw new NotImplementedError(); }
		public function cancel() : void { throw new NotImplementedError(); }
		public function browse(arg1:Array=null) : Boolean { throw new NotImplementedError(); }
		public function upload(arg1:URLRequest, arg2:String=null, arg3:Boolean=false) : void { throw new NotImplementedError(); }
		public function save(arg1:*, arg2:String=null) : void { throw new NotImplementedError(); }
		public function uploadUnencoded(arg1:URLRequest) : void { throw new NotImplementedError(); }
		public function get creationDate() : Date { throw new NotImplementedError(); }
		public function get creator() : String { throw new NotImplementedError(); }
		public function get modificationDate() : Date { throw new NotImplementedError(); }
		public function get extension() : String { throw new NotImplementedError(); }
		public function get size() : Number { throw new NotImplementedError(); }
		public function get type() : String { throw new NotImplementedError(); }
		public function get name() : String { throw new NotImplementedError(); }
		public function get data() : ByteArray { throw new NotImplementedError(); }
	}
}