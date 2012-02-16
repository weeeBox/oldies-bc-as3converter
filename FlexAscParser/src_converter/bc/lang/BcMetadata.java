package bc.lang;

import java.util.List;

public class BcMetadata 
{
	private BcMetadataNode rootNode;

	public BcMetadata() 
	{
		rootNode = new BcMetadataNode(null);
	}
	
	public BcMetadataNode getRootNode() 
	{
		return rootNode;
	}
	
	public List<BcMetadataNode> children(String name)
	{
		return rootNode.childs(name);
	}
	
	public List<BcMetadataNode> children() 
	{
		return rootNode.childs();
	}

	public boolean contains(String name) 
	{
		return rootNode.contains(name);
	}

	public void add(BcMetadataNode node) 
	{
		rootNode.add(node);
	}
}
