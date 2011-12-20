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

import java.io.PrintWriter;
import java.io.StringWriter;


public final class StringPrintWriter extends PrintWriter
{
    public StringPrintWriter()
    {
        super(new StringWriter(), true);
    }

    public String str()
    {
        return out.toString();
    }

    public String toString()
    {
        return out.toString();
    }
}
