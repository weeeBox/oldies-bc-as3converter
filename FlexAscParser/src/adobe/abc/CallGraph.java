////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2008 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package adobe.abc;

import adobe.abc.GlobalOptimizer.InputAbc;


import java.util.ArrayList;
import java.util.List;

import static macromedia.asc.embedding.avmplus.ActionBlockConstants.*;

public class CallGraph
{
	InputAbc abc;
	
	CallGraph(InputAbc abc)
	{
		this.abc = abc;
	}

	public List<Method> traverseDepthFirstUnique()
	{
		List<Method> traversed_methods = new ArrayList<Method>();
		
		for (Type t: this.abc.scripts)
		{
			searchType(t, traversed_methods);
		}
		
		return traversed_methods;
	}
	
	private void searchType(Type t, List<Method> traversed_methods)
	{
		searchMethod(t.init, traversed_methods);

		for (Binding b1: t.defs.values())
			if (b1.method != null)
				searchMethod(b1.method, traversed_methods);
		
	}
		
	private void searchMethod(Method m, List<Method> traversed_methods)
	{
		if ( traversed_methods.contains(m))
		{
			return;
		}
		
		traversed_methods.add(m);
		
		for ( Block b: Algorithms.dfs(m.entry.to))
		{
			for ( Expr e: b.exprs )
			{
				switch(e.op)
				{
					case OP_newclass:
					{
						searchType(e.c, traversed_methods);
						searchType(e.c.itype, traversed_methods);
						break;
					} 
					case OP_newfunction:
					{
						Method f = e.m;
						searchMethod(f, traversed_methods);
						break;
					}
				}
			}
		}
	}

}
