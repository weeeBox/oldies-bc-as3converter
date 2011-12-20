////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2009 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package macromedia.asc.parser;

import macromedia.asc.semantics.Value;
import macromedia.asc.util.Context;

/**
 * LiteralVectorNode represents a vector literal, e.g., Vector.<int>.[1,2,3]
 * @author tharwood
 */
public class LiteralVectorNode extends Node 
{
	public ArgumentListNode elementlist;
	public Node type;

	public LiteralVectorNode(ArgumentListNode elementlist, Node type)
	{
		void_result = false;
		this.elementlist = elementlist;
		this.type = type;
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

    public boolean isLiteral()
    {
        return true;
    }

	public boolean void_result;
	public Value value;

	public void voidResult()
	{
		void_result = true;
	}

	public String toString()
	{
		return "LiteralVector";
	}
}
