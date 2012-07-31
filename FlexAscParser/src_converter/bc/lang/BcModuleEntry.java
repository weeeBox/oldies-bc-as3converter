package bc.lang;

import java.io.File;
import java.util.List;

public class BcModuleEntry
{
	private File file;
	private List<BcModuleDeclarationEntry> entries;

	public BcModuleEntry(File file, List<BcModuleDeclarationEntry> entries)
	{
		this.file = file;
		this.entries = entries;
	}
	
	public File getFile()
	{
		return file;
	}

	public List<BcModuleDeclarationEntry> getEntries()
	{
		return entries;
	}
}
