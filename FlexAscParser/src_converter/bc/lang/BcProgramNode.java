package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcProgramNode extends BcNode
{
	private List<BcPackageDefinitionNode> packages;
	
	private String name;
	
	public BcProgramNode(String name)
	{
		this.name = name;
		packages = new ArrayList<BcPackageDefinitionNode>();
	}
	
	public String getName()
	{
		return name;
	}

	public void add(BcPackageDefinitionNode node)
	{
		packages.add(node);
	}
}
