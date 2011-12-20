package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcPackageDefinitionNode
{
	private String name;
	
	private List<BcClassDefinitionNode> classes;

	public BcPackageDefinitionNode(String name)
	{
		this.name = name;
		classes = new ArrayList<BcClassDefinitionNode>();
	}
	
	public void add(BcClassDefinitionNode node)
	{
		classes.add(node);
	}
	
	public String getName()
	{
		return name;
	}
}
