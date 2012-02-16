package bc.lang;

public class BcMetadata 
{
	private BcMetadataCompositeNode rootNode;

	public BcMetadata() 
	{
		rootNode = new BcMetadataCompositeNode(null);
	}
	
	public void add(BcMetadataNode node) 
	{
		rootNode.addChild(node);
	}
	
	public BcMetadataCompositeNode getRootNode() 
	{
		return rootNode;
	}

	public boolean contains(String name) 
	{
		return rootNode.containNode(name);
	}
}
