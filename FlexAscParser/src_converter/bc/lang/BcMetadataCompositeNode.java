package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcMetadataCompositeNode extends BcMetadataNode 
{
	private List<BcMetadataNode> childs;
	
	public BcMetadataCompositeNode(String name) 
	{
		super(name);
		childs = new ArrayList<BcMetadataNode>();
	}

	public void addChild(BcMetadataNode node)
	{
		childs.add(node);
	}
	
	public List<BcMetadataNode> children(String name)
	{
		List<BcMetadataNode> childList = new ArrayList<BcMetadataNode>();
		
		for (BcMetadataNode node : childs) 
		{
			if (node.getName().equals(name))
			{
				childList.add(node);
			}
		}
		
		return childList;
	}
	
	public List<BcMetadataNode> children() 
	{
		return childs;
	}

	public boolean containNode(String name) 
	{
		for (BcMetadataNode node : childs) 
		{
			if (node.getName().equals(name))
			{
				return true;
			}
		}
		return false;
	}
}
