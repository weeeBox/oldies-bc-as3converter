package bc.lang;

import java.io.File;

import macromedia.asc.parser.Node;
import macromedia.asc.util.ObjectList;

public class BcModuleEntry
{
	private File file;
	private ObjectList<Node> items;

	public BcModuleEntry(File file, ObjectList<Node> items)
	{
		this.file = file;
		this.items = items;
	}
	
	public File getFile()
	{
		return file;
	}
	
	public ObjectList<Node> getItems()
	{
		return items;
	}
}
