////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2005-2007 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package macromedia.asc.semantics;

/**
 *
 * @author Erik Tierney
 */
public class QName
{
    public ObjectValue ns;
    public String name;
	private String fullname;

    public QName(ObjectValue ns, String name)
    {
        this.ns = ns;
        this.name = name;
    }

    public boolean equals(Object rhs)
    {
        if( rhs instanceof QName )
        {
            if( ns.equals(((QName)rhs).ns) && this.name.equals(((QName)rhs).name) )
            {
                return true;
            }
        }
        return false;
    }

    public int hashCode()
    {
        return ns.hashCode() + this.name.hashCode();
    }

	public String toString()
	{
		if (fullname == null)
		{
			if (ns != null && ns.name.length() != 0) // public, just return the name
			{
				fullname = (ns.name + ":" + name).intern();
			}
			else
			{
				fullname = name;
			}
		}
		return fullname;
	}
}
