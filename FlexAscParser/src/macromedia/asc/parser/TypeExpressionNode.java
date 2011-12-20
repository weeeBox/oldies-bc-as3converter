////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2006 Adobe Systems Incorporated
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
 * Created by IntelliJ IDEA.
 * User: tierney
 * Date: Nov 20, 2006
 * Time: 3:40:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class TypeExpressionNode extends Node
{
    public boolean nullable_annotation;
    public boolean is_nullable;

    public Node expr;
    public TypeExpressionNode(Node expr, boolean is_nullable, boolean is_explicit)
    {
        this.expr = expr;

        this.is_nullable = is_nullable;

        this.nullable_annotation = is_explicit;
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

    public void voidResult()
    {
        expr.voidResult();
    }

    public String toString()
    {
        if(Node.useDebugToStrings)
        {
            return "TypeExpression@" + pos();
        }
        else
        {
            return "TypeEpression";
        }
    }

    public boolean hasAttribute(String name)
    {
        return expr.hasAttribute(name);
    }

    public boolean hasSideEffect()
    {
        return expr.hasSideEffect();
    }

    public boolean isLValue()
    {
        return this.expr.isLValue();
    }

    public StringBuilder toCanonicalString(Context cx, StringBuilder buf)
    {
        return this.expr != null ? this.expr.toCanonicalString(cx, buf) : buf;
    }
    
}
