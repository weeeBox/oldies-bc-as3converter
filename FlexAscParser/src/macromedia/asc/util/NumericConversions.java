////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2009 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package macromedia.asc.util;

/**
 *  Implement numeric conversions as specified in ECMA-262.
 */
public class NumericConversions
{
	public static final long TwoPow31 = 2147483648L;
	public static final long TwoPow32 = 4294967296L;
	
    public static long toUint32(double d)
    {
    	if ( Double.isNaN(d) || Double.isInfinite(d) || 0.0 == d )
    		return 0;
    	
    	double result3 = Math.floor(Math.abs(d));
    	return (long)result3 % TwoPow32;
    }

    public static int toInt32(double d)
    {
        long result4 = (long)Math.signum(d) * toUint32(d);
                
        if ( result4 >= TwoPow31 )
        	return (int)(result4 - TwoPow32);
        else
        	return (int)result4;
    }
}
