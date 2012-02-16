package bc.lang;

public class BcMetadataAttributeNode extends BcMetadataNode 
{
	private String value;

	public BcMetadataAttributeNode(String name, String value) 
	{
		super(name);
		this.value = value;
	}

	public String getValue() 
	{
		return value;
	}
}
