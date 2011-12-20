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

package macromedia.asc.parser;

import macromedia.asc.util.Context;
import macromedia.asc.semantics.Value;

/**
 * @author Erik Tierney
 */
public class BinaryFunctionDefinitionNode extends FunctionDefinitionNode
{
    public BinaryFunctionDefinitionNode(Context cx, PackageDefinitionNode pkgdef, AttributeListNode attrs, FunctionNameNode name, FunctionCommonNode fexpr)
    {
        super(cx, pkgdef, attrs,name,fexpr);
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
        return "BinaryFunctionDefinition";
    }
}
