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

import macromedia.asc.semantics.*;

/**
 * @author Jeff Dyer
 */
public final class Slots extends ObjectList<Slot>
{
	public boolean put(Slot slot)
	{
		// Find the position for this character using binary search
		int lo = -1;
		int hi = size();
		int id = slot.id;
		
		while (hi - lo > 1) {
			int pivot = (lo+hi)>>1;
			int testID = at(pivot).id;

			if (id == testID) {
				// Slot is already present 
				return true;
			} else if (id < testID) {
				hi = pivot;
			} else {
				lo = pivot;
			}
		}
		
		add(hi, slot);
		return true;
	}
	
	public Slot getByID(int id)
	{
		// Find the position for this character using binary search
		int lo = 0;
		int hi = size() - 1;

		// fail fast if the id is not in the range of slot id's in this object
		if( hi > lo && (id < at(lo).id || id > at(hi).id) )
			return null;
		
		while (lo <= hi) {
			int pivot = (lo+hi)>>1;
			Slot slot = at(pivot);
			int testID = slot.id;

			if (id == testID) {
				return slot;
			} else if (id < testID) {
				hi = pivot-1;
			} else {
				lo = pivot+1;
			}
		}
		
		return null;
	}
}

