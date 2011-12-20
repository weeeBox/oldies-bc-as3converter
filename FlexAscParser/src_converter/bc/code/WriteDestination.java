package bc.code;

public abstract class WriteDestination
{
	private StringBuilder tabs;
	private String tab;
	private boolean needTab;
	
	private StringBuilder lineBuffer;

	public WriteDestination()
	{
		needTab = true;
		tabs = new StringBuilder();
		lineBuffer = new StringBuilder();
		setTab("\t");
	}
	
	public void setTab(String tab)
	{
		this.tab = tab;
	}

	public void write(Object o)
	{
		if (needTab)
		{
			needTab = false;
			writeBuffer(tabs);
		}
		writeBuffer(o);
	}

	protected void writeBuffer(Object o)
	{
		String value = o.toString();
		for (int i = 0; i < value.length(); i++)
		{
			char chr = value.charAt(i);
			if (chr == '\n')
			{
				if (lineBuffer.length() == 0)
				{
					lineBuffer.append(" ");
				}
				
				flushBuffer();
				if (i < value.length() - 1)
				{
					lineBuffer.append(tabs);
				}
			}
			else
			{
				lineBuffer.append(chr);
			}
		}
	}
	
	protected void flushBuffer()
	{
		if (lineBuffer.length() > 0)
		{
			internalWrite(lineBuffer.toString());
			lineBuffer.setLength(0);
		}
	}
	
	protected abstract void internalWrite(String value);

	public void writef(String str, Object...args)
	{
		write(String.format(str, args));
	}

	public void writelnf(String str, Object...args)
	{
		writeln(String.format(str, args));
	}

	public void writeln(Object o)
	{
		write(o + "\n");
		needTab = true;
	}

	public void writeln()
	{
		writeln("");
	}

	public void incTab()
	{
		tabs.append(tab);
	}

	public void decTab()
	{
		if (tabs.length() == 0)
			throw new RuntimeException("Unable to dec tabs");
		
		tabs.setLength(tabs.length() - 1);
	}
}