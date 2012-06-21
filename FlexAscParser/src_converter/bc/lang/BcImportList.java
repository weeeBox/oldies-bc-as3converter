package bc.lang;

import java.util.HashMap;
import java.util.Map;

public class BcImportList
{
	private static final String SYSTEM_PACKAGE_PREFIX = "__AS3__.";
	private static final String BC_SYSTEM_PACKAGE = "bc.flash";
	
	private Map<String, String> data;
	
	public BcImportList()
	{
		data = new HashMap<String, String>();
	}
	
	public boolean add(String typeName, String packageName)
	{
		if (packageName.startsWith(SYSTEM_PACKAGE_PREFIX))
		{
			packageName = BC_SYSTEM_PACKAGE;
		}
		
		if (data.containsKey(typeName))
		{
			return data.get(typeName).equals(packageName);
		}
		
		data.put(typeName, packageName);		
		return true;
	}
	
	public String findPackage(String typeName)
	{
		return data.get(typeName);
	}
}
