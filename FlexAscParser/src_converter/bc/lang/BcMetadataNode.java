package bc.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BcMetadataNode 
{
	private String name;
	
	private Map<String, String> attributes;
	private List<BcMetadataNode> childs;

	public BcMetadataNode(String name) 
	{
		this.name = name;
		attributes = new HashMap<String, String>();
		childs = new ArrayList<BcMetadataNode>();
	}
	
	public void setAttribute(String name, String value)
	{
		attributes.put(name, value);
	}
	
	public String attribute(String name)
	{
		return attributes.get(name);
	}
	
	public void add(BcMetadataNode node) 
	{
		childs.add(node);
	}
	
	public BcMetadataNode child(String name)
	{
		for (BcMetadataNode child : childs) 
		{
			if (child.getName().equals(name))
			{
				return child;
			}
		}
		return null;
	}
	
	public List<BcMetadataNode> childs(String name)
	{
		List<BcMetadataNode> result = new ArrayList<BcMetadataNode>();
		for (BcMetadataNode child : childs) 
		{
			if (child.getName().equals(name))
			{
				result.add(child);
			}
		}
		return result;
	}
	
	public List<BcMetadataNode> childs() 
	{
		return childs;
	}
	
	public boolean contains(String name) 
	{
		return child(name) != null;
	}
	
	public String getName() 
	{
		return name;
	}

}
