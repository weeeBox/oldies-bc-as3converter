package bc.preprocessor;

import java.util.List;

public class LineIterator 
{
	private List<String> lines;
	private int pointer;

	public LineIterator(List<String> lines) 
	{
		this.lines = lines;
		pointer = -1;
	}
	
	public boolean hasNext()
	{
		return pointer + 1 < lines.size();
	}
	
	public String next()
	{
		return lines.get(++pointer);
	}
}
