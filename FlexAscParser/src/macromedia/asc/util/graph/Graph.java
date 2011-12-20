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
public class Graph <V,E>
{
	public Graph()
	{
		vertices = new HashSet<Vertex<V>>();
		edges = new HashSet<Edge<E>>();
	}

	private int counter;
	private Vertex<V> root;
	private Set<Vertex<V>> vertices;
	private Set<Edge<E>> edges;

	public Vertex getRoot()
	{
		return root;
	}

	public Set<Vertex<V>> getVertices()
	{
		return vertices;
	}

	public Set<Edge<E>> getEdges()
	{
		return edges;
	}

	public void addVertex(Vertex<V> v)
	{
		if (vertices.size() == 0)
		{
			root = v;
		}
		v.id = counter++;
		vertices.add(v);
	}

	public void addEdge(Edge<E> e)
	{
		edges.add(e);
	}

	public void normalize()
	{
		counter = 0;
		for (Iterator<Vertex<V>> i = vertices.iterator(); i.hasNext();)
		{
			i.next().id = counter++;
		}
	}
}
