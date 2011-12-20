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

public final class PrototypeWalker extends ObjectValueWalker
{
	private ObjectValue curObjVal  = null;

	public PrototypeWalker(ObjectValue startObj) {
		curObjVal = startObj;
	}

	public boolean hasNext() {
		return (curObjVal != null);
	}

	public ObjectValue next() {
		final ObjectValue ret = curObjVal;
		curObjVal = curObjVal.proto();
		return ret;
	}

	void clear() {
		curObjVal = null;
	}
}
