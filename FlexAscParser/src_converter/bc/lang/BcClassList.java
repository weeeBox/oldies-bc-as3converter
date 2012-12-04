package bc.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import macromedia.asc.parser.DefinitionNode;
import macromedia.asc.parser.Node;

public class BcClassList implements Iterable<BcClassDefinitionNode> 
{
	private List<BcClassDefinitionNode> classes;
	private List<BcFunctionDeclaration> globalFunctions;
	private Map<DefinitionNode, BcMetadata> metadataMap;
	
	private boolean processed;
	
	public BcClassList()
	{
		classes = new ArrayList<BcClassDefinitionNode>();
		globalFunctions = new ArrayList<BcFunctionDeclaration>();
		metadataMap = new HashMap<DefinitionNode, BcMetadata>();
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

	public void addGlobalFunction(BcFunctionDeclaration bcFunc) 
	{
		// TODO: check if function is added twice
		globalFunctions.add(bcFunc);
	}
	
	public List<BcFunctionDeclaration> getGlobalFunctions() 
	{
		return globalFunctions;
	}

	public void addMetadata(DefinitionNode def, BcMetadata metadata) 
	{
		metadataMap.put(def, metadata);
	}

	public BcMetadata findMetadata(Node def) 
	{
		return metadataMap.get(def);
	}
}
