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

package adobe.abc;

import static adobe.abc.Algorithms.*;
import static adobe.abc.OptimizerConstants.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static macromedia.asc.embedding.avmplus.ActionBlockConstants.*;

public class Block implements Iterable<Expr>, Comparable<Block>
{
	/**
	 *  The statement-level expressions that make up this block's code. 
	 */
	public Deque<Expr> exprs = new ArrayDeque<Expr>();

	/**
	 *  Expressions known to be used in successor blocks.
	 *  They're known to be used because they're inputs
	 *  to a phi expression.
	 */
	Set<Expr> live_out = new HashSet<Expr>();
	
	/**
	 *   Block ID number, unique within a Method.
	 */
	public int id;

	/**
	 *   Post-order walk number.
	 *   More interesting than the Block's id. 
	 */
	public int postorder;
	
	/**
	 * in-scope handlers for this block.
	 */
	public Edge[] xsucc = noedges;
	
	/** 
	 *  Don't change control flow/data flow to this block when set.
	 *  Indicates a construct such as hasnext2 is present and should
	 *  be left as the original compiler emitted it. 
	 */
	boolean must_isolate_block = false;
	
	/**
	 *  Set if any edge coming into this block is a back-edge
	 *  as determined by the block scheduler.
	 *  @pre schedule() sets this, it's not meaningful 'til then.
	 */
	boolean is_backwards_branch_target = false;
	
	public Block(Method m)
	{
		this.id = m.getNextBlockId();
	}
	
	public void appendExpr(Expr e)
	{	
		if ( (exprs.peekLast().succ != null) )
		{
			Expr last = exprs.removeLast();
			exprs.add(e);
			exprs.add(last);
		}
		else
		{
			exprs.add(e);
		}
	}

	public void killRegister(Method m, int regnum)
	{
		appendExpr(new Expr(m, OP_kill, regnum));
		
	}
	
	public String toString()
	{ 
		return 'B'+String.valueOf(id); 
	}
	
	public Expr first()
	{
		return exprs.peekFirst();
	}
	
	public Expr last()
	{
		return exprs.peekLast();
	}
	
	public Edge[] succ()
	{
		if ( last().succ != null )
			return last().succ;
		else
			return noedges;
	}
	
	public Iterator<Expr> iterator()
	{
		return exprs.iterator();
	}
	
	public void add(Expr e)
	{
		exprs.add(e);
	}
	
	void addAll(Block b)
	{
		exprs.addAll(b.exprs);
	}
	
	public boolean isEmpty()
	{
		return exprs.isEmpty();
	}
	
	public int size()
	{
		return exprs.size();
	}
	
	public void remove(Expr e)
	{
		exprs.remove(e);
	}
	
	public int compareTo(Block b)
	{
		return this.id - b.id;
	}
	
	public void addLiveOut(Expr e)
	{
		assert(exprs.contains(e));
		this.live_out.add(e);
	}
	
	public Set<Expr> getLiveOut()
	{
		return this.live_out;
	}
}