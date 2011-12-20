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

import macromedia.asc.util.*;
import macromedia.asc.semantics.*;

/**
 * Node
 *
 * @author Dick Sweet
 */

public class UsePrecisionNode extends UsePragmaNode {

	public int precision;

	public UsePrecisionNode(Node idNode, Node precisionNode)
	{
		super(idNode, precisionNode);
		precision = 34; // until proven otherwise
		if (precisionNode instanceof LiteralNumberNode) {
			this.precision = ((LiteralNumberNode)precisionNode).intValue();
		}
		// else ?
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

	public String toString()
	{
		return "UsePrecision " + precision;
	}
}
