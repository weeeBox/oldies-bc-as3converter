package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcMetadata 
{
	private List<String> items;

	public BcMetadata() 
	{
		items = new ArrayList<String>();
	}
	
	public void add(String name) 
	{
		items.add(name);
	}
	
	public boolean contains(String name)
	{
		return items.contains(name);
	}
}
