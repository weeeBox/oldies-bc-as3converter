package bc.code;

import java.util.ArrayList;
import java.util.List;

public class ListWriteDestination extends WriteDestination
{
	private List<String> lines;
	
	public ListWriteDestination()
	{
		this(new ArrayList<String>());
	}
	
	public ListWriteDestination(List<String> lines)
	{
		this.lines = lines;
	}
	
	@Override
	protected void internalWrite(String line)
	{
		addLine(line);
	}

	public void addLine(String line) 
	{
		lines.add(line);
	}
	
	public String peekLine()
	{
		return lines.get(lines.size() - 1);
	}
	
	public String popLine()
	{
		String line = peekLine();
		lines.remove(lines.size() - 1);
		return line;
	}
	
	public List<String> getLines()
	{
		flushBuffer();
		return lines;
	}
	
	public int linesCount()
	{
		return getLines().size();
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
