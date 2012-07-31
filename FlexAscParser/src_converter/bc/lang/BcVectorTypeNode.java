package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcVectorTypeNode extends BcTypeNode
{
	private BcTypeNode generic;
	
	protected BcVectorTypeNode(BcTypeName name, BcTypeNode generic)
	{
		super(name);
		this.generic = generic;
	}
	
	public void setGeneric(BcTypeNode generic) 
	{
		this.generic = generic;
	}
	
	public BcTypeNode getGeneric()
	{
		return generic;
	}
	
	@Override
	public String getNameEx()
	{
		return getName() + "<" + generic.getName() + ">";
	}

	@Override
	public String toString()
	{
		return super.toString() + "<" + getGeneric() + ">";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((generic == null) ? 0 : generic.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BcVectorTypeNode other = (BcVectorTypeNode) obj;
		if (generic == null)
		{
			if (other.generic != null)
				return false;
		}
		else if (!generic.equals(other.generic))
			return false;
		return true;
	}
}
