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

package adobe.abc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Symtab<E>
{
	private List<Name> names = new ArrayList<Name>();
	private List<E> values = new ArrayList<E>();
	
	E get(Name name)
	{
		if (name.nsset != null && name.name != null)
		{
			if (name.nsset.length == 1)
			{
				for (int i=0, n=names.size(); i < n; i++)
					if (0 == name.match(names.get(i)))
						return values.get(i);
			}
			else
			{
				for (Namespace ns : name.nsset)
				{
					E e = get(new Name(name.kind,ns,name.name));
					if (e != null)
						return e;
				}
			}
		}
		return null;
	}
	
	Name getName(Name n)
	{
		// return the matching name from the symbol table
		if (n.nsset.length > 1)
		{
			for (Namespace ns : n.nsset)
			{
				Name k = new Name(n.kind,ns,n.name);
				if (names.contains(k))
					return k;
			}
		}
		return n;
	}
	
	boolean contains(Name n)
	{
		return n != null && get(n) != null;
	}
	
	void put(Name n, E e)
	{
		assert(n.nsset.length == 1);
		names.add(n);
		values.add(e);
	}
	
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i=0, n=size(); i < n; i++)
		{
			b.append(names.get(i)).append('=').append(values.get(i));
			if (i+1 < n)
				b.append(", ");
		}
		b.append(']');
		return b.toString();
	}
	
	public Collection<E> values()
	{
		return values;
	}
	
	int size()
	{
		return names.size();
	}
}
