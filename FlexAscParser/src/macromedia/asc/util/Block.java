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

/**
 * @author Jeff Dyer
 */
public class Block
{
	public Block() { is_terminal = false; }
	
	public BitSet def_bits;
	public BitSet gen_bits;
	public BitSet kill_bits;
	public BitSet in_bits;
	public BitSet out_bits;
	public String stmts;
	public boolean is_terminal; // This block can not have a successor, it represents the block jumped to by "return"

	//public ObjectList<Node> nodes = new ObjectList<Node>();
	//public ObjectList<Node> epilog = new ObjectList<Node>();
	public IntList preds = new IntList(1);
	public IntList succs = new IntList(1);
	//public Blocks preds_blk = new Blocks();
	//public Blocks succs_blk = new Blocks();
}
