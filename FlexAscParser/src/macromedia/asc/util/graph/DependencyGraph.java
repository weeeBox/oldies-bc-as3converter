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
public final class DependencyGraph<T> extends Graph<String, Object>
{
	public DependencyGraph()
	{
		map = new HashMap<String, T>();
		vertices = new HashMap<String, Vertex<String>>();
	}

	private Map<String, T> map;
	private Map<String, Vertex<String>> vertices;

	// put(), get(), remove() are methods for 'map'

	public void put(String key, T value)
	{
		map.put(key, value);
	}

	public T get(String key)
	{
		return map.get(key);
	}

	public void remove(String key)
	{
		map.remove(key);
	}

	public Set<String> keySet()
	{
		return map.keySet();
	}

	public int size()
	{
		return map.size();
	}

	public boolean containsKey(String key)
	{
		return map.containsKey(key);
	}

	public boolean containsVertex(String key)
	{
		return vertices.containsKey(key);
	}
	
	// methods for graph manipulations

	public void addVertex(Vertex<String> v)
	{
		super.addVertex(v);
		vertices.put(v.getWeight(), v);
	}

	public void addDependency(String name, String dep)
	{
		Vertex<String> tail = null, head = null;

		if ((head = vertices.get(name)) == null)
		{
			head = new Vertex<String>(name);
			addVertex(head);
		}

		if ((tail = vertices.get(dep)) == null)
		{
			tail = new Vertex<String>(dep);
			addVertex(tail);
		}

		addEdge(new Edge<Object>(tail, head, null));
	}
}

