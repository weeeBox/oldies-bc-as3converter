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

public class IntNumberConstant extends NumberConstant {

	private int ival;
	
	public IntNumberConstant(int num) {
		ival = num;
	}
	
	@Override
	public double doubleValue() {
		return ival;
	}

	@Override
	public Decimal128 decimalValue() {
		return new Decimal128(ival);
	}

	@Override
	public int intValue() {
		return ival;
	}

	@Override
	public long uintValue() {
		// AS3 just returns the bits
		return ival;
	}

	@Override
	public byte number_type() {
		return NumberUsage.use_int;
	}

	@Override
	public String toString() {
		return String.valueOf(ival);
	}

}
