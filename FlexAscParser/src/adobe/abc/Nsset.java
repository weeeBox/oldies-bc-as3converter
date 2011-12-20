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

import java.util.Arrays;
import java.util.Iterator;

class Nsset implements Comparable<Nsset>, Iterable<Namespace>
{
	final Namespace[] nsset;
	final int length;
	Nsset(Namespace[] nsset)
	{
		this.nsset = nsset;
		this.length = nsset.length;
	}
	
	public int hashCode()
	{
		int h = deepHashCode(nsset);
		return h;
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof Nsset)) return false;
		Namespace[] s2 = ((Nsset)other).nsset;
		Namespace[] s1 = nsset;
		return deepEquals(s1, s2);
	}
	
	public int compareTo(Nsset other)
	{
		Namespace[] s1 = nsset;
		Namespace[] s2 = other.nsset;
		int d;
		if ((d = s1.length - s2.length) != 0) return d;
		for (int i=0, n=s1.length; i < n; i++)
			if ((d = s1[i].compareTo(s2[i])) != 0) return d;
		return 0;
	}

	public Iterator<Namespace> iterator()
	{
		return Arrays.asList(nsset).iterator();
	}
	
	public String toString()
	{
		return Arrays.toString(nsset);
	}
	
	public static int deepHashCode(Object a[]) 
    {
        if (a == null)
            return 0;
 
        int result = 1;
 
        for (Object element : a) {
            int elementHash = 0;
            if (element != null)
                elementHash = element.hashCode();
            result = 31 * result + elementHash;
        }
 
        return result;
    }
	
    public static boolean deepEquals(Object[] a1, Object[] a2) 
    {
        if (a1 == a2)
            return true;
        if (a1 == null || a2==null)
            return false;
        int length = a1.length;
        if (a2.length != length)
            return false;
 
        for (int i = 0; i < length; i++) {
            Object e1 = a1[i];
            Object e2 = a2[i];
 
            if (e1 == e2)
                continue;
            if (e1 == null)
                return false;
 
            // Figure out whether the two elements are equal
            if (!e1.equals(e2))
                return false;
        }
        return true;
    }
}
