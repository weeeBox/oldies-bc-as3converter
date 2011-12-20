////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2004-2008 Adobe Systems Incorporated
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
 */
public class ApplyTypeExprNode extends SelectorNode
{
    public ListNode typeArgs;

    public ApplyTypeExprNode(Node expr, ListNode typeArgs)
    {
        super();
        this.expr = expr;
        this.typeArgs = typeArgs;
        this.ref = null;
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
             return "ApplyTypeExpression@" + pos();
          else
             return "ApplyTypeExpression";
    }

    public boolean isApplyTypeExpression()
    {
        return true;
    }
}
