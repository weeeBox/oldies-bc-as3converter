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

import macromedia.asc.semantics.MetaData;
import macromedia.asc.semantics.Value;
import macromedia.asc.util.Context;

/**
 * @author Clement Wong
 */
public class MetaDataNode extends Node
{
	public LiteralArrayNode data;

	public MetaDataNode(LiteralArrayNode data)
	{
		this.data = data;
		def = null;
	}

    private MetaData md;

	public DefinitionNode def;

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

    public void setMetadata(MetaData md)
    {
        this.md = md;
    }

	public String getValue(String key)
	{
        return md != null ? md.getValue(key) : null;
	}

	public String getValue(int index)
	{
        return md != null ? md.getValue(index) : null;
	}

	public int count()
	{
		return getValues() != null ? getValues().length : 0;
	}

	public String toString()
	{
		return "MetaData";
	}

    public String getId()
    {
        return md != null ? md.id : null;
    }

    public void setId(String id)
    {
        if( this.md == null )
            this.md = new MetaData();
        this.md.id = id;
    }

    public Value[] getValues()
    {
        return md != null ? md.values : null;
    }

    public void setValues(Value[] values)
    {
        if( this.md == null )
            this.md = new MetaData();
        this.md.values = values;
    }

    public MetaData getMetadata()
    {
        return md;
    }
}
