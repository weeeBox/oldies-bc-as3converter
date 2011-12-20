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

package macromedia.asc.embedding;

import macromedia.asc.embedding.avmplus.ActionBlockEmitter;
import macromedia.asc.semantics.*;
import macromedia.asc.util.*;

/*
 * jkamerer ported from c++ start
 */

public class CompilerState
{
    ContextStatics statics;
    Context mainCX;
    ActionBlockEmitter emitter;
    Builder globalBuilder;
    ObjectValue global;

    CompilerState()
	{
		statics = null;
		mainCX = null;
		emitter = null;
		globalBuilder = null;
		global = null;
	}

	/*
	 * jkamerer ported from c++ end
	 */

};
