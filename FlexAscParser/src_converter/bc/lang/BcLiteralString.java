package bc.lang;

public class BcLiteralString extends BcNode
{
	private String value;
	
	public BcLiteralString(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}
