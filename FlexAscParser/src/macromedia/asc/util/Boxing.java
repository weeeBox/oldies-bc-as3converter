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

package macromedia.asc.util;

public class Boxing
{
    public static Integer valueOf(int i)
    {
        return IntegerPool.getNumber(i);
    }
    
    public static Double valueOf(double d)
    {
    	return new Double(d);
    }
}
