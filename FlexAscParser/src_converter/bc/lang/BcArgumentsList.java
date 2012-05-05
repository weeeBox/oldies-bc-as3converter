package bc.lang;

import java.util.ArrayList;

import bc.help.BcStringUtils;

@SuppressWarnings("serial")
public class BcArgumentsList extends ArrayList<Object>
{
	public BcArgumentsList()
	{
		super(0);
	}
	
	public BcArgumentsList(int size)
	{
		super(size);
	}

	@Override
	public String toString()
	{
		return BcStringUtils.commaSeparated(this);
	}
}
