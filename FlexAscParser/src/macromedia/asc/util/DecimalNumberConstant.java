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

public class DecimalNumberConstant extends NumberConstant {

	private Decimal128 val;
	
	public DecimalNumberConstant(Decimal128 num) {
		val = num;
	}
	
	@Override
	public Decimal128 decimalValue() {
		return val;
	}

	@Override
	public double doubleValue() {
		return val.doubleValue();
	}

	@Override
	public int intValue() {
		return val.intValue();
	}

	@Override
	public byte number_type() {
		return NumberUsage.use_decimal;
	}

	@Override
	public long uintValue() {
		long lval = val.longValue();
		int ival = (int)lval; // truncates to 32 bits
		return ival;
	}
	
	@Override
	public String toString() {
		return val.toString();
	}


}
