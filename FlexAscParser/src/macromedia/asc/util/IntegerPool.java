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

package macromedia.asc.util;

/**
 * @author Clement Wong
 */
public final class IntegerPool
{
	private static final Integer[] constants;
	private static final int max = 10000;
	private static final int min = 0;

	static
	{
		constants = new Integer[max-min];
		for (int i = 0; i < max-min; i++)
		{
			constants[i] = new Integer(i+min);
		}
	}

	public static Integer getNumber(int num)
	{
		return (num >= min && num < max) ? constants[num-min] : new Integer(num);
	}
}
