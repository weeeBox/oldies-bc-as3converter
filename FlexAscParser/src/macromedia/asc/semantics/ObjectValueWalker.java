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

import java.util.Iterator;

/** ObjectValueWalker<P>
 * Takes a starting ObjectValue and returns an interator that walks up the prototype/interface chain.<BR> 
 * All the next() are on demand, so we don't waste too much time preloading more than we may need.
 * 
 * @author Jono Spiro */
public abstract class ObjectValueWalker implements Iterator<ObjectValue>
{
	/** Doesn't do anything (needed to implement Iterator) */
	final public void remove() {}

	final public Iterator<ObjectValue> iterator()
	{
		return this;
	}
	
	abstract void clear();
};
