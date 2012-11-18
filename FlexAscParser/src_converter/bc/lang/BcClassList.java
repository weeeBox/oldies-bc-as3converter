package bc.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BcClassList implements Iterable<BcClassDefinitionNode> 
{
	private List<BcClassDefinitionNode> classes;
	private boolean processed;
	
	public BcClassList()
	{
		classes = new ArrayList<BcClassDefinitionNode>();
	}
	
	public void setProcessed() 
	{
		processed = true;
	}
	
	public boolean isProcessed() 
	{
		return processed;
	}

	public void add(BcClassDefinitionNode bcClass) 
	{
		classes.add(bcClass);
	}

	@Override
	public Iterator<BcClassDefinitionNode> iterator() 
	{
		return classes.iterator();
	}

	public boolean contains(BcClassDefinitionNode bcClass) 
	{
		return classes.contains(bcClass);
	}
}
