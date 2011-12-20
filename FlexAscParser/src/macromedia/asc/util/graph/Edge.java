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

package macromedia.asc.util.graph;

/**
 * @author Clement Wong
 */
public final class Edge <W>
{
	public Edge(Vertex tail, Vertex head, W weight)
	{
		this.head = head;
		this.tail = tail;
		this.weight = weight;

		tail.addEmanatingEdge(this);
		tail.addSuccessor(head);
		head.addIncidentEdge(this);
		head.addPredecessor(tail);
	}

	private Vertex head, tail;
	private W weight;

	public Vertex getHead()
	{
		return head;
	}

	public Vertex getTail()
	{
		return tail;
	}

	public W getWeight()
	{
		return weight;
	}

	public boolean equals(Object object)
	{
		if (object instanceof Edge)
		{
			Edge e = (Edge) object;
			return e.head == head && e.tail == tail && e.weight == weight;
		}
		else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return (weight != null) ? weight.hashCode() : super.hashCode();
	}
}
