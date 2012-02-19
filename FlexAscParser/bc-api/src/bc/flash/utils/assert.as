package bc.flash.utils
{
	import bc.flash.error.Error;
	/**
	 * @author weee
	 */
	public function assert(condition : Boolean, message : String = "") : void
	{
		if (!condition)
		{
			throw new Error("Assert: " + message);
		}
	}
}
