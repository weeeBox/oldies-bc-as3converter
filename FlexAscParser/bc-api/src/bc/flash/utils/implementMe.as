package bc.flash.utils
{
	import bc.flash.error.NotImplementedError;
	/**
	 * @author weee
	 */
	[ConvertOnce]
	public function implementMe(message : String) : void
	{
		throw new NotImplementedError(message);
	}
}
