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

package macromedia.asc.util;

public abstract class NumberConstant {
	
	protected final static double MAXUINT = ((double)Integer.MAX_VALUE) * 2 + 1;

	public abstract byte number_type();
	
	public abstract int intValue(); // value as an integer
	
	public abstract long uintValue(); // value as unsigned integer
	
	public abstract double doubleValue();
	
	public abstract Decimal128 decimalValue();
	
	public abstract String toString();


}
