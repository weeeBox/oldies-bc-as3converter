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
public class ListErrorNode extends ListNode // ErrorNode
{
	public String value;

	public ListErrorNode(String str)
	{
		super(null, null, 0);
		value = str;

		// C: should make IdentifierNode and ErrorNode interface
		//    no multiple inheritance in Java
		// ErrorNode(str)
	}

	public String toString()
	{
		return "ListError";
	}
}
