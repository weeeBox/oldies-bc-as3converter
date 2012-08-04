package bc.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BcFuncRegister
{
	public Map<String, FuncList> data;
	
	public BcFuncRegister()
	{
		data = new HashMap<String, FuncList>();
	}
	
	public void register(String packageName, BcFunctionTypeNode type)
	{
		FuncList list = data.get(packageName);
		if (list == null)
		{
			list = new FuncList();
			data.put(packageName, list);
		}
		
		list.add(type);
	}
	
	public boolean isRegistered(String packageName, BcFunctionTypeNode type)
	{
		FuncList list = data.get(packageName);
		if (list != null)
		{
			return list.contains(type.getName());
		}
		
		return false;
	}
	
	public static class FuncList
	{
		private List<BcFunctionTypeNode> types;
		
		public FuncList()
		{
			types = new ArrayList<BcFunctionTypeNode>();
		}
		
		public void add(BcFunctionTypeNode type)
		{
			types.add(type);
		}
		
		public boolean contains(String name)
		{
			for (BcFunctionTypeNode type : types)
			{
				if (type.getName().equals(name))
				{
					return true;
				}
			}
			
			return false;
		}
		
		public List<BcFunctionTypeNode> getTypes()
		{
			return types;
		}
	}
}
