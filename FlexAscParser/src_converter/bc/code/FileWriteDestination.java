package bc.code;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class FileWriteDestination extends WriteDestination 
{
	PrintStream out;
	public FileWriteDestination(File file) throws IOException
	{
		this(new PrintStream(file));
	}
	
	public FileWriteDestination(PrintStream out) 
	{
		this.out = out;		
	}
	
	public void close() throws IOException
	{
		out.close();
	}

	@Override
	protected void internalWrite(String line)
	{
		out.println(line);
	}
}
