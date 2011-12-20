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

import java.util.LinkedList;
import java.util.HashSet;
import macromedia.asc.embedding.avmplus.InstanceBuilder;

/** Does a breadth-first search of interfaces starting with the starting object */
public final class InterfaceWalker extends ObjectValueWalker
{
	private LinkedList<TypeValue> queue = new LinkedList<TypeValue>();
	private HashSet<TypeValue> set = new HashSet<TypeValue>();
	private ObjectValue nextInterface = null;
	private boolean superInterfaces = false;

	public InterfaceWalker(ObjectValue startObj)
	{
		this(startObj, true);
	}
	
	public InterfaceWalker(ObjectValue startObj, boolean superInterfaces)
	{
		this.superInterfaces = superInterfaces;
		
		queue.add(startObj instanceof TypeValue ? (TypeValue)startObj : startObj.type.getTypeValue());

		// Don't return the starting object in the interface stream
		processQueue();
		if (nextInterface == startObj)
		{
			nextInterface = null;
		}
	}

	public boolean hasNext()
	{
		processQueue();	
		return nextInterface != null;
	}
		
	public ObjectValue next()
	{
		final ObjectValue returnValue = nextInterface;
		nextInterface = null;
		return returnValue;
	}
	
	public void clear()
	{
		set.clear();
		queue.clear();
		nextInterface = null;
	}

	private void processQueue()
	{
		while (nextInterface == null && !queue.isEmpty())
		{
			final TypeValue type = queue.removeFirst();

            // skip unresolved types
            // these can occur when referencing baseclasses
            // that may not exist due to versioning (for instance, some classes
            // only exist when compiling for flash 10).
            // these errors will be picked up downstream
            if( !type.resolved )
                continue;
            
			// Queue base class
			if (type.baseclass != null && !type.isInterface())
			{
				queue.add(type.baseclass);
			}
			
			// Queue interfaces
			final InstanceBuilder ibui = (InstanceBuilder)type.prototype.builder;
			if (ibui.interface_refs != null)
			{
				if (superInterfaces || !type.isInterface())
				{
					for (int i = 0, size = ibui.interface_refs.size(); i < size; i++)
					{
						ReferenceValue ref = ibui.interface_refs.get(i);
						queue.add((TypeValue)ref.slot.getObjectValue());
					}
				}
			}

			// Return this object, if it is an interface and hasn't been
			// returned already
			if (type.isInterface() && !set.contains(type))
			{
				nextInterface = type.prototype;
				set.add(type);
			}
		}		
	}
}
