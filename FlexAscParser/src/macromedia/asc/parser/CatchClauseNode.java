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

import macromedia.asc.util.*;
import macromedia.asc.semantics.*;

/**
 * Node
 *
 * @author Jeff Dyer
 */
public class CatchClauseNode extends Node
{
	public Node parameter;
	public StatementListNode statements;
	public ReferenceValue typeref;
	public boolean finallyInserted;
    public ObjectValue default_namespace;
    public ObjectValue activation;
    
	public CatchClauseNode(Node parameter, StatementListNode statements)
	{
		this.parameter = parameter;
		this.statements = statements;
		this.typeref = null;
		this.finallyInserted = false;
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

	public int countVars()
	{
		// Add 1 for the catch variable
		return 1 + (statements != null ? statements.countVars() : 0);
	}
	
	public String toString()
	{
		return "CatchClause";
	}
}
