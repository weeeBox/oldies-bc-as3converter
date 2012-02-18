package bc.flash.net
{
	/**
	 * @author weee
	 */
	public final class URLRequest extends Object
	{
		private var mUrl : String;
		private var mContentType : String;
		private var mData : Object;
		private var mDigest : String;
		private var mMethod : String;
		
		public function URLRequest(url : String = null) : void
		{
			mUrl = url;
		}

		public function get contentType() : String
		{
			return mContentType;
		}

		public function set contentType(value : String) : void
		{
			mContentType = value;			
		}

		public function get data() : Object
		{
			return mData;
		}

		public function set data(value : Object) : void
		{
			mData = value;
		}

		public function get digest() : String
		{
			return mDigest;			
		}

		public function set digest(value : String) : void
		{
			mDigest = value;
		}

		public function get method() : String
		{
			return mMethod;
		}

		public function set method(value : String) : void
		{
			mMethod = value;			
		}

		/* public function get requestHeaders() : Array; */

		/* public function set requestHeaders(value : Array) : void; */

		public function get url() : String
		{
			return mUrl;
		}

		public function set url(value : String) : void
		{
			mUrl = value;
		}
	}
}
