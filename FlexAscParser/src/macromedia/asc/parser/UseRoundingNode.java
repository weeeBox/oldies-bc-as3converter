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


public class UseRoundingNode extends UsePragmaNode {

	public int mode;

	public UseRoundingNode(Node id, Node argument)
	{
		super(id, argument);
		this.mode = NumberUsage.round_HALF_EVEN; // until proven otherwise
		if (argument instanceof IdentifierNode) {
			String arg = ((IdentifierNode)argument).toIdentifierString();
			if (arg.equals("HALF_EVEN")) {
				mode = NumberUsage.round_HALF_EVEN;
			}
			else if (arg.equals("DOWN")) {
				mode = NumberUsage.round_DOWN;
			}
			else if (arg.equals("FLOOR")) {
				mode = NumberUsage.round_FLOOR;
			}
			else if (arg.equals("UP")) {
				mode = NumberUsage.round_UP;
			}
			else if (arg.equals("CEILING")) {
				mode = NumberUsage.round_CEILING;
			}
			else if (arg.equals("HALF_UP")) {
				mode = NumberUsage.round_HALF_UP;
			}
			else if (arg.equals("HALF_DOWN")) {
				mode = NumberUsage.round_HALF_DOWN;
			}
			// should report error if something else
		}
		// should report error if not identifier
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
		return "UseRounding " + NumberUsage.roundingModeName[mode];
	}
}
