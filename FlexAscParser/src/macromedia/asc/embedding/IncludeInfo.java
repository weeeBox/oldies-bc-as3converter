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

package macromedia.asc.embedding;

import macromedia.asc.util.ObjectList;
import macromedia.asc.util.Context;
import java.io.InputStream;

public class IncludeInfo {
	public IncludeInfo() { script = null; encoding = null; name = null; params = null; paramTypes = null; cx = null; }

	public InputStream script;
    public String encoding;
	public String name;
	public Context cx;
	public ObjectList<String> params;
	public ObjectList<String> paramTypes;
}

