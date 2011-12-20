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

import macromedia.asc.parser.Node;
import macromedia.asc.util.Context;

/**
 * @author Clement Wong
 */
public class UnresolvedNamespace extends NamespaceValue
{
	public UnresolvedNamespace(Context cx, Node node, ReferenceValue ref)
	{
		super();
		this.node = node;
		this.ref = ref;
		resolved = false;
        this.cx = cx.makeCopyOf();  // must make a copy of the current context, the actual context will have
                                    //  its guts swapped out when we go into or out of an included file.
	}

	public Node node;
	public ReferenceValue ref;
	public boolean resolved;
    public Context cx;              // We must report errors relative to this context.  node could come from an included file.
}
