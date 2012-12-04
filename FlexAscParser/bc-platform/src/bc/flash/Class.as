package bc.flash 
{
	import bc.flash.errors.NotImplementedError;
	
	[NoConversion]
	public class Class extends Object 
	{
		/* public static const length : int = 1; */
		public function get prototype() : Object { throw new NotImplementedError(); }
	}
}
