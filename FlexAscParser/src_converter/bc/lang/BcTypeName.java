package bc.lang;

public class BcTypeName
{
	private String name;
	private String qualifier;

	public BcTypeName(String name)
	{
		this(null, name);
	}
	
	public BcTypeName(String qualifier, String name)
	{
		this.name = name;
		this.qualifier = qualifier;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getQualifier()
	{
		return qualifier;
	}
	
	public void setQualifier(String qualifier)
	{
		this.qualifier = qualifier;
	}
	
	public boolean hasQualifier()
	{
		return qualifier != null;
	}
	
	public String getQualifiedName()
	{
		if (hasQualifier())
			return getQualifier() + "." + getName();
		
		return getName();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BcTypeName other = (BcTypeName) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (qualifier == null)
		{
			if (other.qualifier != null)
				return false;
		}
		else if (!qualifier.equals(other.qualifier))
			return false;
		return true;
	}
}
