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

package macromedia.asc.embedding.avmplus;

import macromedia.asc.util.Context;
import macromedia.asc.semantics.Builder;
import macromedia.asc.semantics.ObjectValue;

public class WithBuilder extends Builder
{
	public void build(Context cx, ObjectValue ob)
	{
		objectValue = ob;
		contextId = cx.getId();
	}

	public boolean hasRegisterOffset() { return false; }
}

