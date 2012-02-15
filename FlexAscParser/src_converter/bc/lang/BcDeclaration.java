package bc.lang;

import java.util.List;

public class BcDeclaration extends BcNode
{
	protected List<String> modifiers;
	
	protected boolean constant;
	protected BcMetadata metadata;
	
	public void setMetadata(BcMetadata metadata) 
	{
		this.metadata = metadata;
	}
	
	public BcMetadata getMetadata() 
	{
		return metadata;
	}
	
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
		if (isPrivate())
			return "private";
		if (isProtected())
			return "protected";
		
		return "public";
	}

	private boolean isProtected() 
	{
		return modifiers.contains("protected");
	}

	public boolean isPrivate() 
	{
		return modifiers.contains("private");
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
