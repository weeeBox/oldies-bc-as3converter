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

package macromedia.asc.parser;

import macromedia.asc.semantics.Value;
import macromedia.asc.util.ObjectList;
import macromedia.asc.util.ByteList;
import macromedia.asc.util.Context;
import macromedia.asc.semantics.Slot;
import macromedia.asc.semantics.TypeInfo;
import static macromedia.asc.parser.Tokens.*;

/**
 * @author Jeff Dyer
 */
public class ParameterListNode extends Node
{
	public ObjectList<ParameterNode> items = new ObjectList<ParameterNode>();
	public ObjectList<TypeInfo> types = new ObjectList<TypeInfo>(1);     // declared parameter types
	public ByteList   decl_styles = new ByteList(1); // a vector of PARAM_REQUIRED, PARAM_Optional, or PARAM_Rest

	public int count;

	public ParameterListNode(ParameterListNode list, ParameterNode item, int pos)
	{
		count = -1;
		this.items.add(item);
	}

    public Value evaluate( Context cx, Evaluator evaluator )
    {
        if( evaluator.checkFeature(cx,this) )
        {
            return evaluator.evaluate( cx, this );
        }
        else
        {
            return null;
        }
    }

    public int size()
    {
        if( count < 0 )
        {
			count = 0;
			for (ParameterNode item : items)
			{
				count += item.size();
			}
        }
        return count;
    }


    public String toString()
    {
        return "ParameterList";
    }

	public StringBuilder toCanonicalString(Context cx, StringBuilder buf)
	{       
		{			        
			buf.append(" param_names='");
			ParameterNode firstItem = items.get(0);
			for (ParameterNode item : items)
			{
				if (item != firstItem)
					buf.append(";");
				buf.append( item.ref != null ? item.ref.name : "" );
			}
			buf.append("'");
		}
         
		{      
			buf.append(" param_types='");
			ParameterNode firstItem = items.get(0);
			for (ParameterNode item : items)
			{
				if (item != firstItem)
					buf.append(";");
				if (item instanceof RestParameterNode)
					buf.append("restParam");
				else if ( item.typeref != null)			
				{
                    buf.append(DocCommentNode.getRefName(cx, item.typeref));
                }
			}
			buf.append("'");
		}

		{
			buf.append(" param_defaults='");
			ParameterNode firstItem = items.get(0);
			for (ParameterNode item : items)
			{
				if (item != firstItem)
					buf.append(";");

				if (item.init == null)
					buf.append("undefined");
				else
				{
					if (item.init instanceof LiteralNumberNode)
					{
						buf.append( ((LiteralNumberNode)(item.init)).value);
					}
					else if (item.init instanceof LiteralStringNode)
					{
						buf.append( DocCommentNode.escapeXml( ((LiteralStringNode)(item.init)).value) );
					}
					else if (item.init instanceof LiteralNullNode)
					{
						buf.append("null");
					}
					else if (item.init instanceof LiteralBooleanNode)
					{
						buf.append( (((LiteralBooleanNode)(item.init)).value) ? "true" : "false");
					}
					else
					{
						buf.append("unknown");
					}
				}
			}
			buf.append("'");
		}
			         
		return buf;
	}
	
};
