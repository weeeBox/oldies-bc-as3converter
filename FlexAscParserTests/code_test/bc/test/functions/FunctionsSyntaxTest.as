package bc.test.functions
{	
	public class FunctionsSyntaxTest
	{
		/*
		protected var fnCall		:Object;
		protected var uParams		:Array;

		public function Callback(fnCall:Function, uParams:Array = null)
		{
			this.fnCall = fnCall;
			this.uParams = uParams;
			
			if (uParams == null) this.uParams = [];
		}
		*/
		
		public function call(oCaller:Object = null, ...uArgs):void
		{
			/*
			var uParams:Array = uArgs.length? uArgs : this.uParams.concat();

			uParams.unshift(oCaller);
			this.fnCall.apply(null,uParams);
			*/
		}
		
		public function setParams(oCaller:Object = null):void
		{
			/*
			this.uParams.push(oCaller);
			this.fnCall.apply(null,this.uParams);
			*/
		}
	}
}