package bc.lang;

public class BcLiteralNumber extends BcNode
{
	private String value;

	public BcLiteralNumber(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
