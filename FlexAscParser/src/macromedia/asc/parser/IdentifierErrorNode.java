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

/**
 * Node
 *
 * @author Jeff Dyer
 */
public class IdentifierErrorNode extends IdentifierNode // ErrorNode
{
	public String value;

	public IdentifierErrorNode()
	{
		super("", 0);
		value = "Expecting an identifier";

		// C: should make IdentifierNode and ErrorNode interface
		//    no multiple inheritance in Java
		// ErrorNode("Expecting an identifier")
	}

	public boolean isIdentifier()
	{
		return true;
	}

	public String toString()
	{
		return "IdentifierError";
	}
}
