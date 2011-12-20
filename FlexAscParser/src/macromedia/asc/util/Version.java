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

// build version info

// build number counts 1-n for development builds, and restarts at 1-n for release builds

// the visible build code d for development, r for release candidate

public class Version {
    public static final String ASC_BUILD_CODE ="cyclone";

    public static final String ASC_VERSION_USER = "1.0";

    // Version codes
    // Major version, minor version, build number high order value, build number low order value
    public static final String ASC_VERSION_NUMBER = "1,0,0,100";
    public static final String ASC_VERSION_STRING = "1,0,0,100";
    public static final int ASC_MAJOR_VERSION = 1;
    public static final int ASC_MINOR_VERSION = 0;
    public static final int ASC_BUILD_NUMBER = 0;

    // Define Mac only resource change to 'vers' resource so that player installer will
    // replace previous player versions
    public static final int ASC_MAC_RESOURCE_MINOR_VERSION	= 0x00;
}

