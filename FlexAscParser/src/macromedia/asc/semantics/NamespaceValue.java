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

import macromedia.asc.util.Context;

/**
 * @author Gary Grossman
 */
public class NamespaceValue extends ObjectValue
{
	public NamespaceValue()
	{
		super();
        ns_kind = Context.NS_PUBLIC;
        config_ns = false;
	}

    public byte ns_kind;
    boolean config_ns;

    public NamespaceValue(byte ns_kind)
    {
        this.ns_kind = ns_kind;
    }

    public boolean isInternal()
    {
        return ns_kind == Context.NS_INTERNAL;
    }

    public boolean isProtected()
    {
        return ns_kind == Context.NS_PROTECTED;
    }

    public boolean isPrivate()
    {
        return ns_kind == Context.NS_PRIVATE;
    }

    public boolean isConfigNS()
    {
        return config_ns;
    }
    
    public byte getNamespaceKind()
    {
        return ns_kind;
    }

}
