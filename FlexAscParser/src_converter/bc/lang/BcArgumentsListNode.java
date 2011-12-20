package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcArgumentsListNode extends BcNode
{
	private List<BcNode> items;
	
	public BcArgumentsListNode()
	{
		items = new ArrayList<BcNode>();
	}
	
	public void add(BcNode node)
	{
		items.add(node);
	}
}
