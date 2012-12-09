using System;
 
using bc.flash;
using bc.flash.errors;
using bc.flash.xml;
 
namespace bc.flash.system
{
	public sealed class AsSystem : AsObject
	{
		public static void disposeXML(AsXML node)
		{
			throw new AsNotImplementedError();
		}
		public static void exit(uint code)
		{
			throw new AsNotImplementedError();
		}
		public static float getFreeMemory()
		{
			throw new AsNotImplementedError();
		}
		public static void gc()
		{
			throw new AsNotImplementedError();
		}
		public static AsObject getIme()
		{
			throw new AsNotImplementedError();
		}
		public static void pause()
		{
			throw new AsNotImplementedError();
		}
		public static void pauseForGCIfCollectionImminent(float imminence)
		{
			throw new AsNotImplementedError();
		}
		public static void pauseForGCIfCollectionImminent()
		{
			pauseForGCIfCollectionImminent(0.75f);
		}
		public static float getPrivateMemory()
		{
			throw new AsNotImplementedError();
		}
		public static float getProcessCPUUsage()
		{
			throw new AsNotImplementedError();
		}
		public static void resume()
		{
			throw new AsNotImplementedError();
		}
		public static void setClipboard(String _string)
		{
			throw new AsNotImplementedError();
		}
		public static uint getTotalMemory()
		{
			throw new AsNotImplementedError();
		}
		public static float getTotalMemoryNumber()
		{
			throw new AsNotImplementedError();
		}
		public static bool getUseCodePage()
		{
			throw new AsNotImplementedError();
		}
		public static void setUseCodePage(bool _value)
		{
			throw new AsNotImplementedError();
		}
		public static String getVmVersion()
		{
			throw new AsNotImplementedError();
		}
	}
}
