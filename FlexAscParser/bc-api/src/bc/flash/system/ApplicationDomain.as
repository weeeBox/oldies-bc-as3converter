package flash.system
{
	import flash.utils;
	
	public class ApplicationDomain extends Object
	{
		public function ApplicationDomain(arg1:ApplicationDomain=null) { throw new NotImplementedError(); }
		public function getQualifiedDefinitionNames() : Vector.<String> { throw new NotImplementedError(); }
		public function getDefinition(arg1:String) : Object { throw new NotImplementedError(); }
		public function hasDefinition(arg1:String) : Boolean { throw new NotImplementedError(); }
		public function get parentDomain() : ApplicationDomain { throw new NotImplementedError(); }
		public function get domainMemory() : ByteArray { throw new NotImplementedError(); }
		public function set domainMemory(value:ByteArray) : void { throw new NotImplementedError(); }
	}
}