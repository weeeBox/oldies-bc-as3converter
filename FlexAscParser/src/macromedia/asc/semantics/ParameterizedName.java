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

package macromedia.asc.semantics;

import macromedia.asc.util.ObjectList;

/**
 * Created by IntelliJ IDEA.
 * User: tierney
 * Date: Mar 4, 2008
 * Time: 6:01:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParameterizedName extends QName
{
    public ObjectList<QName> type_params;
    private String fullname;
    private String namepart;

    public ParameterizedName(ObjectValue ns, String name, ObjectList<TypeValue> type_params)
    {
        super(ns, name);
        this.type_params = new ObjectList<QName>(type_params.size());
        for( TypeValue t : type_params )
            this.type_params.add(t.name);
    }

    public ParameterizedName(ObjectList<QName> type_names, ObjectValue ns, String name)
    {
        super(ns, name);
        this.type_params = type_names;
    }

    public boolean equals(Object rhs)
    {
        if( rhs instanceof ParameterizedName )
        {
            if( super.equals(rhs) )
            {
                ParameterizedName rp = (ParameterizedName)rhs;
                if( rp.type_params.size() == type_params.size() )
                {
                    for( int i = 0, limit = type_params.size(); i < limit; ++i)
                    {
                        if( !type_params.at(i).equals(rp.type_params.at(1)) )
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode()
    {
        return ns.hashCode() + this.name.hashCode() + type_params.hashCode();
    }

    public String getNamePart()
    {
        if( namepart == null)
        {
            namepart = name;
            namepart += ".<";
            for( int i = 0, limit = type_params.size(); i < limit; ++i )
            {
                namepart += type_params.at(i).toString();
            }
            namepart += ">";
            namepart = namepart.intern();
        }

        return namepart;
    }
    public String toString()
    {
        if (fullname == null)
        {
            if (ns != null && ns.name.length() != 0) // public, just return the name
            {
                fullname = (ns.name + ":" + getNamePart()).intern();
            }
            else
            {
                fullname = getNamePart();
            }
        }
        return fullname;
    }
}
