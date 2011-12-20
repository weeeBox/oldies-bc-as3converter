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

package macromedia.asc.util;

import macromedia.asc.semantics.*;

import java.util.TreeMap;
import java.util.Comparator;

/**
 * @author Jeff Dyer
 */
public final class Qualifiers extends TreeMap<ObjectValue, Integer>
{
	private static Comparator c = new ObjectValue.ObjectValueCompare();

	public Qualifiers()
	{            
		super(c);
	}

}
