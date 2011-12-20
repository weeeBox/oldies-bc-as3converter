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

package macromedia.asc.parser;

import macromedia.asc.semantics.Value;
import macromedia.asc.util.Context;

public class UseNumericNode extends UsePragmaNode {
	
	public int numeric_mode;
	
	public UseNumericNode(Node idNode, Node argument, int n_mode)
	{
		super(idNode, argument);
		numeric_mode = n_mode;
	}

	public Value evaluate(Context cx, Evaluator evaluator)
	{
		if (evaluator.checkFeature(cx, this))
		{
			return evaluator.evaluate(cx, this);
		}
		else
		{
			return null;
		}
	}

	// keep the names below in sync with the numeric usage constants (NU_MUMBLE) is Context.java
	private static String numeric_mode_names[] = {"Number", "decimal", "double", "int", "uint"};
	public String toString()
	{
		return "UsePrecision " + numeric_mode_names[numeric_mode];
	}
}
