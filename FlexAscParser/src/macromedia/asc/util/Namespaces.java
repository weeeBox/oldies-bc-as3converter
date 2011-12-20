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

/**
 * @author Jeff Dyer
 */
public final class Namespaces extends ObjectList<ObjectValue>
{
	public Namespaces()
	{
		super(1);
	}

	public Namespaces(ObjectValue qualifier)
	{
		super(1);
		add(qualifier);
	}
	
	public Namespaces(int size)
	{
		super(size);
	}

	public Namespaces(Namespaces namespaces)
	{
		super(namespaces);
	}

	public final boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}

		if (!(o instanceof Namespaces))
		{
			return false;
		}

		Namespaces nss = (Namespaces) o;
		if (size() != nss.size())
		{
			return false;
		}

		for (int i = 0, s = size(); i < s; ++i)
		{
			if (!get(i).equals(nss.get(i)))
			{
				return false;
			}
		}

		return true;
	}
}
