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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Clement Wong
 */
public class ObjectList<E> extends ArrayList<E>
{
	public ObjectList()
	{
		super(0);
	}

	public ObjectList(int size)
	{
		super(size);
	}

	public ObjectList(Collection<E> list)
	{
		super(list);
	}

	final public E first()
	{
		return (size() == 0) ? null : (E) get(0);
	}

	final public E last()
	{
		return (size() == 0) ? null : (E) get(size() - 1);
	}

	final public E removeLast()
	{
		return (size() == 0) ? null : (E) remove(size() - 1);
	}

    final public void push_back(E e)
    {
        add(e);
    }

    final public E back()
    {
        return last();
    }

	final public void resize(int s)
	{
		if (s > size())
		{
			for (int i = 0, n = s - size(); i < n; i++)
			{
				add(null);
			}
		}
	}

    final public void pop_back()
    {
        if (!isEmpty()) {
            remove(size()-1);
        }
    }

    final public E at(int i)
    {
        return get(i);
    }

	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (!(o instanceof List))
		{
			return false;
		}

		List list = (List) o;
		if (list.size() != size())
		{
			return false;
		}

		for (int i = 0, size = size(); i < size; i++)
		{
			if (!get(i).equals(list.get(i)))
			{
				return false;
			}
		}

		return true;
	}
}
