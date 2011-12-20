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

package macromedia.asc.embedding.avmplus;

import macromedia.asc.semantics.Builder;
import macromedia.asc.semantics.ObjectValue;
import macromedia.asc.util.Context;

/**
 * @author Jeff Dyer
 */
public class FunctionBuilder extends Builder
{

	public void build(Context cx, ObjectValue ob)
	{
		objectValue = ob;
		contextId = cx.getId();
	}
}


