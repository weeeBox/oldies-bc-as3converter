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

package adobe.abc;

import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_Class;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_Const;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_FLAG_final;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_FLAG_metadata;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_FLAG_override;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_Getter;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_Method;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_Setter;
import static macromedia.asc.embedding.avmplus.ActionBlockConstants.TRAIT_Var;

import adobe.abc.GlobalOptimizer.InputAbc;
import adobe.abc.GlobalOptimizer.Metadata;
import static adobe.abc.OptimizerConstants.*;

public class Binding
{
	final InputAbc abc;
	private final Name name;
	final int flags_kind;
	int slot;
	int id;
	int offset; // if slot
	
	// var, const, class
	Object value;// default value if any
	Typeref type; // if slot
	
	// function, getter, setter, method
	Method method;	
	
	// metadata
	Metadata[] md = nometadata;
	
	// if this is a get/set, points to corresponding set/get, if any.
	Binding peer = null;

	Binding(int kind, Name name, InputAbc abc)
	{
		this.name = name;
		this.flags_kind = kind;
		this.abc = abc;
	}
	
	public int kind()
	{
		return flags_kind & 15;
	}
	
	public boolean isFinal()
	{
		return ((flags_kind>>4) & TRAIT_FLAG_final) != 0;
	}

	public boolean isOverride()
	{
		return ((flags_kind>>4) & TRAIT_FLAG_override) != 0;
	}
	
	public boolean hasMetadata()
	{
		return ((flags_kind>>4) & TRAIT_FLAG_metadata) != 0;
	}
	
	public boolean isConst()
	{
		return kind() == TRAIT_Const || kind() == TRAIT_Getter;
	}
	
	public boolean isClass()
	{
		return kind() == TRAIT_Class;
	}
	
	public boolean isMethod()
	{
		int tk = kind();
		return tk == TRAIT_Method;
	}
	
	public boolean isGetter()
	{
		int tk = kind();
		return tk == TRAIT_Getter;
	}
	
	public boolean isSetter()
	{
		int tk = kind();
		return tk == TRAIT_Setter;
	}
	
	public boolean isSlot() 
	{
		int tk = kind();
		return tk == TRAIT_Var || tk == TRAIT_Const || tk == TRAIT_Class;
	}
	
	public boolean defaultValueChanged()
	{
		if ( this.type.t.defaultValue != null )
			return !this.type.t.defaultValue.equals(this.value);
		else
			return this.value != null;
	}
	
	public String toString()
	{
		switch (kind())
		{
		case TRAIT_Class: return "["+slot+"] class";
		case TRAIT_Var: return "["+slot+"] var";
		case TRAIT_Const: return "["+slot+"] const";
		case TRAIT_Method: return "["+slot+"] method";
		case TRAIT_Getter: return "["+slot+"] get";
		case TRAIT_Setter: return "["+slot+"] set";
		default:
			assert(false);
		}
		return null;
	}

	public Name getName() {
		return name;
	}
}
