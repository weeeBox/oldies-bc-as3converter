package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcImportList
{
	private List<BcImportEntry> entriesList;
	
	public BcImportList()
	{
		entriesList = new ArrayList<BcImportList.BcImportEntry>();
	}
	
	public void add(String typeName, String packageName)
	{
		BcImportEntry entry = new BcImportEntry(typeName, packageName);		
		if (!entriesList.contains(entry))
		{
			entriesList.add(entry);		
		}
	}
	
	public String findPackage(String typeName)
	{
		for (BcImportEntry entry : entriesList)
		{
			if (entry.getType().equals(typeName))
			{
				return entry.getQualifier();
			}
		}
		
		return null;
	}
	
	public boolean hasWildcardMaskPackage(String packageName)
	{
		for (BcImportEntry entry : entriesList)
		{
			if (entry.isWildcardMask() && packageName.equals(entry.getQualifier()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean containsType(BcTypeNode type)
	{
		for (BcImportEntry entry : entriesList)
		{
			if (entry.getType().equals(type.getName()) && entry.getQualifier().equals(type.getQualifier()))
			{
				return true;
			}
		}
		return false;
	}
	
	private static class BcImportEntry
	{
		private String type;
		private String qualifier;

		public BcImportEntry(String type, String qualifier)
		{
			this.type = type;
			this.qualifier = qualifier;
		}
		
		public String getType()
		{
			return type;
		}
		
		public String getQualifier()
		{
			return qualifier;
		}
		
		public boolean isWildcardMask()
		{
			return type.length() == 0;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			BcImportEntry other = (BcImportEntry) obj;
			if (qualifier == null)
			{
				if (other.qualifier != null)
					return false;
			}
			else if (!qualifier.equals(other.qualifier))
				return false;
			if (type == null)
			{
				if (other.type != null)
					return false;
			}
			else if (!type.equals(other.type))
				return false;
			return true;
		}
	}
}
