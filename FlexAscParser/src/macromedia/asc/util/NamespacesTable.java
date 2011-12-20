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

package macromedia.asc.util;
import java.util.HashMap;

import macromedia.asc.semantics.ObjectValue;

public class NamespacesTable extends HashMap<Namespaces,Namespaces>  
{
	private ObjectList<ObjectValue> list = new ObjectList<ObjectValue>(1);
	
	public NamespacesTable()
	{
		list.add(null);
	}
	
	/**
	 * fast intern lookup of one namespace
	 * @param ns
	 * @return
	 */
	public Namespaces intern(ObjectValue ns)
	{
		list.set(0, ns);
		Namespaces n = this.get(list);
		if(n == null) {
			n = new Namespaces(ns);
			put(n,n);			
		}
		return n;
	}
	
	/**
	 * fast intern lookup of one namespace
	 * @param ns
	 * @return
	 */
	public Namespaces intern(Namespaces ns)
	{		
		Namespaces n = get(ns);
		if(n == null) {
			put(ns,ns);
			n = ns;
		}
		return n;
	}
}
