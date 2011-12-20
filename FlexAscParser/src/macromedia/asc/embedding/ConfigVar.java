////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2007 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package macromedia.asc.embedding;

public class ConfigVar 
{
    // jono: made these final as a sanity check that they don't get mutated.
    //       feel free to undo this.
    final public String ns;
    final public String name;
    final public String value;
    
    public ConfigVar(String ns, String name, String value)
    {
        this.ns    = ns;
        this.name  = name;
        this.value = value;
    }

    
    @Override
    public String toString()
    {
        return String.format("%s::%s=%s", ns, name, value);
    }
}
