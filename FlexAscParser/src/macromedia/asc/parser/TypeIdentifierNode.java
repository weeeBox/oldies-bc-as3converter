////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2008 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package macromedia.asc.parser;

import macromedia.asc.semantics.Value;
import macromedia.asc.util.Context;

/**
 * Node
 *
 * @author Erik Tierney
 */
public class TypeIdentifierNode extends IdentifierNode {

    public Node base;
    public ListNode typeArgs;

    public TypeIdentifierNode(Node base, ListNode typeArgs, int pos) {
        super("", pos);
        this.base = base;
        this.typeArgs = typeArgs;
    }
    public Value evaluate(Context cx, Evaluator evaluator)
    {
        if (evaluator.checkFeature(cx, this))
        {
            return evaluator.evaluate(cx, this);
        }
        else
        {
            return null;
        }
    }

    public String toString()
    {
        if(Node.useDebugToStrings)
             return "TypeIdentifier@" + pos();
          else
             return "TypeIdentifier";
    }
}
