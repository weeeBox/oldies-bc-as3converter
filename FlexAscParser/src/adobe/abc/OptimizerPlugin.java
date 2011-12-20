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

public interface OptimizerPlugin
{
	public void initializePlugin(adobe.abc.GlobalOptimizer caller, java.util.Vector<String> options);
	public void runPlugin(String abc_filename, adobe.abc.CallGraph call_graph);
	
}
