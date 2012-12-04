package bc.lang;

import java.util.List;

public class BcDeclaration extends BcNode
{
	protected List<String> modifiers;
	
	protected boolean constant;
	
	protected BcMetadata metadata;
	protected BcImportList importList;
	
	public void setMetadata(BcMetadata metadata) 
	{
		this.metadata = metadata;
	}
	
	public BcMetadata getMetadata() 
	{
		return metadata;
	}
	
	public boolean hasMetadata()
	{
		return metadata != null;
	}
	
	public void setModifiers(List<String> modifiers)
	{
		this.modifiers = modifiers;
	}
	
	public void setImportList(BcImportList importList)
	{
		this.importList = importList;
	}
	
	public BcImportList getImportList()
	{
		return importList;
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
	
	public boolean isPublic()
	{
		return modifiers.contains("public");
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
