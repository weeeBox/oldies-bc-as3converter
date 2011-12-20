////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2004-2007 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

/*
 * Written by Jeff Dyer
 * Copyright (c) 1998-2003 Mountain View Compiler Company
 * All rights reserved.
 */

package macromedia.asc.parser;

import macromedia.asc.semantics.*;
import macromedia.asc.util.*;

/**
 * Node
 *
 * @author Jeff Dyer
 */
public class CoerceNode extends Node
{
	public Node expr;
	public TypeInfo actual;
	public TypeInfo expected;
	public boolean void_result;
	public boolean is_explicit;

	public CoerceNode(Node expr, TypeInfo actual, TypeInfo expected, boolean is_explicit)
	{
		this.expr = expr;
		this.actual = actual;
		this.expected = expected;
		void_result = false;
		this.is_explicit = is_explicit;
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

	public void voidResult()
	{
		void_result = true;
		expr.voidResult();
	}
}
