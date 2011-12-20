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
public class InterfaceDefinitionNode extends ClassDefinitionNode
{
	public InterfaceDefinitionNode(Context cx, PackageDefinitionNode pkgdef, AttributeListNode attrs, IdentifierNode name, ListNode interfaces, StatementListNode statements)
	{
		super(cx, pkgdef, attrs, name, null, interfaces, statements);
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
	
	public boolean isInterface() 
	{
		return true;
	}

	public String toString()
	{
		return "InterfaceDefinition";
	}
}
