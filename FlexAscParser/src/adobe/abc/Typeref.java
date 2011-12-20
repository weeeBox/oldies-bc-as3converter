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

public class Typeref
{
	public final Type t;
	final boolean nullable;
	
	Typeref(Type t, boolean nullable)
	{
		assert(t != null);
		this.t = t;
		this.nullable = nullable;
	}
	
	Typeref nonnull()
	{
		return nullable ? new Typeref(t,false) : this;
	}
	
	Typeref nullable()
	{
		return nullable? this: new Typeref(t, true);
	}
	
	public boolean equals(Object o)
	{
		return (o instanceof Typeref) && ((Typeref)o).t == t && ((Typeref)o).nullable == nullable;
	}
	
	Binding find(Name n)
	{
		return t.find(n);
	}
	
	Binding findGet(Name n)
	{
		return t.findGet(n);
	}
	
	public String toString()
	{
		return !t.ref.nullable || t==TypeCache.instance().NULL || t==TypeCache.instance().VOID || t==TypeCache.instance().ANY ? t.toString() :
				nullable ? t.toString() + "?" :
				t.toString();
	}
	
	public Type getType()
	{
		return t;
	}
}
