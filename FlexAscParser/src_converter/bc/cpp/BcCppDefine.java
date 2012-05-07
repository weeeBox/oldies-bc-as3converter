package bc.cpp;

import java.util.ArrayList;
import java.util.List;

import bc.code.WriteDestination;

public class BcCppDefine
{
	private String name;
	private String[] params;
	private List<String> body;

	public BcCppDefine(String name, String... params)
	{
		this.name = name;
		this.params = params;
		body = new ArrayList<String>();
	}

	public void addLine(String line)
	{
		body.add(line);
	}
	
	public void write(WriteDestination dest, String... args)
	{
		if (params.length != args.length)
			throw new IllegalArgumentException("Params are different then args");
		
		for (String line : body)
		{
			String inlined = inlineArgs(line, args);
			dest.writeln(inlined);
		}
	}
	
	private String inlineArgs(String line, String[] args)
	{
		for (int i = 0; i < args.length; ++i)
		{
			String arg = args[i];
			String param = params[i];
			
			line = inlineArg(line, param, arg);
		}
		return line;
	}

	private String inlineArg(String line, String param, String arg)
	{
		line = line.replaceAll("##" + param, arg);
		line = line.replaceAll(param + "##", arg);
		line = line.replaceAll("#" + param, '"' + arg + '"');
		line = line.replaceAll("\\b" + param + "\\b", arg);
		
		return line;
	}

	public String getName()
	{
		return name;
	}

	public String[] getParams()
	{
		return params;
	}
}
