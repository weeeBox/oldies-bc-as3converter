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

import macromedia.asc.util.ByteList;

public class MethodSlot extends Slot
{
	public MethodSlot(TypeInfo type, int id)
	{
		super(type, id);
	}

    public MethodSlot(TypeValue type, int id)
    {
        super(type, id);
    }

	public String getMethodName()
	{
		return method_name;
	}
	
	public void setMethodName(String method_name)
	{
		this.method_name = method_name;
	}
	
	public int getMethodID()
	{
		return method_id;
	}
	
	public void setMethodID(int method_id)
	{
		this.method_id = method_id;
	}

	public void setDeclStyles(ByteList decl_styles)
	{
		this.decl_styles = decl_styles;
	}

	public ByteList getDeclStyles()
	{
		return decl_styles;
	}

	public void addDeclStyle(int style)
	{
		if (decl_styles == null)
			decl_styles = new ByteList(2);
		decl_styles.push_back((byte)style);
	}

	public void setVarIndex(int var_index)
	{
		throw new IllegalArgumentException();
	}

	public int getVarIndex()
	{
		return -1;
	}

	public void setTypeRef(ReferenceValue typeref)
	{
		throw new IllegalArgumentException();
	}
	
	public ReferenceValue getTypeRef()
	{
		return null;
	}

	private String method_name = "";
	private int method_id = -1;
    private ByteList decl_styles; // for functions, vector of PARAM_REQUIRED, PARAM_Optional, or PARAM_Rest
}
