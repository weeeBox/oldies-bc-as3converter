package bc.cpp;

import java.util.ArrayList;
import java.util.List;

public class BcCppDefines
{
	private List<BcCppDefine> defines;
	
	public BcCppDefines()
	{
		defines = new ArrayList<BcCppDefine>();
	}
	
	public void add(BcCppDefine define)
	{
		defines.add(define);
	}

	public List<BcCppDefine> list()
	{
		return defines;
	}
	
	public boolean contains(String name)
	{
		return find(name) != null;
	}
	
	public BcCppDefine find(String name)
	{
		for (BcCppDefine define : defines)
		{
			if (define.getName().equals(name))
				return define;
		}
		
		return null;
	}
}
