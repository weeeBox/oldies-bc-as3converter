using System;

using bc.flash;

namespace bc.flash.error
{
	public class AsError : Exception
    {
        public String message;
        public String name;

        public AsError(String message)
        {
            this.message = message;
        }
        public AsError()
            : this("")
        {
        }

        public override string ToString()
        {
            return GetType().Name + ": \"" + message + "\"\n" + StackTrace.ToString();
        }
    }
}
