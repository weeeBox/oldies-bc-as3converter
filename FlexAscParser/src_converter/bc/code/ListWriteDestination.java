package bc.code;

import java.util.ArrayList;
import java.util.List;

public class ListWriteDestination extends WriteDestination
{
	private List<String> lines;
	
	public ListWriteDestination()
	{
		lines = new ArrayList<String>();
	}
	
	@Override
	protected void internalWrite(String line)
	{
		lines.add(line);
	}
	
	public List<String> getLines()
	{
		flushBuffer();
		return lines;
	}
	
	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		int lineIndex = 0;
		List<String> lines = getLines();
		for (String line : lines)
		{
			buf.append(line);
			if (++lineIndex < lines.size())
			{
				buf.append("\n");
			}
		}
		
		return buf.toString();
	}
}
