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

package macromedia.asc.embedding;

import java.io.InputStream;

import macromedia.asc.util.ObjectList;

/*

Extend CompilerHandler and override error to get custom error notification

*/

public class CompilerPlug 
{
    public InputStream in;
	public String pathspec;
	public String scriptname;
	public String filename;
    public String file_encoding;
	public String classname;
	public String base_classname;
    public ObjectList<IncludeInfo> includes; 
    public InputStream initScript;
    public String initScript_encoding;
    public ObjectList<String> import_filespecs;
    public ObjectList<String> use_namespaces;
	public String language;
	public CompilerHandler handler;
	public boolean emit_debug_info;
	public boolean emit_doc_info;
    public boolean save_comment_nodes;
	public boolean make_movieclip;
	public boolean class_provided;
	public boolean lint_mode;
	public boolean use_static_semantics;
    public boolean emit_metadata;
	public String swf_options;
	public String avmplus_exe;
    public int dialect;
    public int target;
    public boolean optimize;
    public ObjectList<ConfigVar> configs;
    public ObjectList<ConfigVar> optimizer_configs;
	public int api_version;

	public CompilerPlug()
	{
	    emit_debug_info = false;
		emit_doc_info = false;
        save_comment_nodes = false;
	    make_movieclip = false;
	    class_provided = false;
	    lint_mode = false;
	    use_static_semantics = false;
        emit_metadata = false;
	    includes = null;
	    initScript = null;
	    import_filespecs = null;
	    use_namespaces = null;
		language = "EN";
	    handler = new CompilerHandler();
        dialect = 7;
        target = 1;  // Default to FP10, constants in macromedia.asc.embedding.avmplus.Features
        optimize = false;
        optimizer_configs = null;
        in = null;

		filename = new String();
	}
}
