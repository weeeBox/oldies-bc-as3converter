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

import java.util.*;

/**
 * @author Clement Wong
 */
public final class Vertex <W>
{
	private static final int INITIAL_CAPACITY = 5;

	public Vertex(W weight)
	{
		this.weight = weight;
	}

	private W weight;

	int id;
	private Set<Edge> incidentEdges;
	private Set<Edge> emanatingEdges;
	private Set<Vertex<W>> predecessors;
	private List<Vertex<W>> successors;

	public W getWeight()
	{
		return weight;
	}

	public void addIncidentEdge(Edge e)
	{
		if (incidentEdges == null)
		{
			incidentEdges = new HashSet<Edge>(INITIAL_CAPACITY);
		}
		incidentEdges.add(e);
	}

	public Set<Edge> getIncidentEdges()
	{
		return incidentEdges;
	}

	public void addEmanatingEdge(Edge e)
	{
		if (emanatingEdges == null)
		{
			emanatingEdges = new HashSet<Edge>(INITIAL_CAPACITY);
		}
		emanatingEdges.add(e);
	}

	public Set<Edge> getEmanatingEdges()
	{
		return emanatingEdges;
	}

	public void addPredecessor(Vertex<W> v)
	{
		if (predecessors == null)
		{
			predecessors = new HashSet<Vertex<W>>(INITIAL_CAPACITY);
		}
		predecessors.add(v);
	}

	public Set<Vertex<W>> getPredecessors()
	{
		return predecessors;
	}

	public void addSuccessor(Vertex<W> v)
	{
		if (successors == null)
		{
			successors = new ArrayList<Vertex<W>>(INITIAL_CAPACITY);
		}
		successors.add(v);
	}

	public List<Vertex<W>> getSuccessors()
	{
		return successors;
	}

	public int inDegrees()
	{
		return incidentEdges == null ? 0 : incidentEdges.size();
	}

	public int outDegrees()
	{
		return emanatingEdges == null ? 0 : emanatingEdges.size();
	}

	public boolean equals(Object object)
	{
		if (object instanceof Vertex)
		{
			return (weight == null) ? super.equals(object) : weight.equals(((Vertex) object).weight);
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
