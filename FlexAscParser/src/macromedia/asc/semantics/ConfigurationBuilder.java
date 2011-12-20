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

package macromedia.asc.semantics;

import macromedia.asc.util.Context;

public class ConfigurationBuilder extends Builder {

	@Override
	public void build(Context cx, ObjectValue ob) {
		// TODO Auto-generated method stub
		objectValue = ob;
		contextId = cx.getId();
	}

}
