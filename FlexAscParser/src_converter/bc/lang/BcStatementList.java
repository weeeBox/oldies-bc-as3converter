package bc.lang;

import java.util.ArrayList;
import java.util.List;

public class BcStatementList extends BcNode
{
	private List<BcStatement> statements;
	
	public BcStatementList()
	{
		statements = new ArrayList<BcStatement>();
	}
	
	public void add(BcStatement statement)
	{
		statements.add(statement);
	}
}
