package bc.lang;

import java.util.List;

public class BcDeclaration extends BcNode
{
	protected List<String> modifiers;
	
	protected boolean constant;
	
	public void setModifiers(List<String> modifiers)
	{
		this.modifiers = modifiers;
	}
	
	public boolean isStatic()
	{
		return modifiers.contains("static");
	}
	
	public String getVisiblity()
	{
		if (modifiers.contains("private"))
			return "private";
		if (modifiers.contains("protected"))
			return "protected";
		
		return "public";
	}
	
	public boolean isConst()
	{
		return constant;
	}
	
	public void setConst(boolean constant)
	{
		this.constant = constant;
	}
}
