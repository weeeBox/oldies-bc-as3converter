package bc.lang;

public class BcGetExpression extends BcSelector
{
	private String identifier;
	
	public BcGetExpression(String name)
	{
		identifier = name;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
}
