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

public class DoubleNumberConstant extends NumberConstant {

	
	double val;
	
	public DoubleNumberConstant(double num) {
		val = num;
	}
	
	@Override
	public Decimal128 decimalValue() {
		// TODO Auto-generated method stub
		return new Decimal128(val);
	}

	@Override
	public double doubleValue() {
		return val;
	}

	@Override
	public int intValue() {
		return (int)val;
	}

	@Override
	public byte number_type() {
		return NumberUsage.use_double;
	}

	@Override
	public long uintValue() {
		// Java truncates any positive double > Integer.MAX_VALUE to MAX_VALUE.  If we
		// launder the value through long, we get the result we want.
		long lval = (long)val;
		int ival = (int)lval;
		return ival;
	}
	
	@Override
	public String toString() {
		if (Double.isNaN(val))
			return ("NaN");
		return String.valueOf(val);
	}

}
