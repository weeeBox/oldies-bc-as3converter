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

package macromedia.asc.embedding.avmplus;

/**
 * DebugInfo
 *
 * @author Gary Grossman
 */
public class DebugInfo
{
    int debug_linenum = -1;
    boolean debug_linenum_dirty = false;
    String debug_file;
    boolean suppress_debug_method = false;
    boolean debug_file_dirty = false;
}

