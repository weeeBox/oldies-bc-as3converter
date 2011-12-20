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

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Clement Wong
 */
public final class Algorithms
{
	public static void topologicalSort(Graph g, Visitor visitor)
	{
		int[] inDegree = new int[g.getVertices().size()];
		Vertex[] vertices = new Vertex[inDegree.length];

		for (Iterator<Vertex> i = g.getVertices().iterator(); i.hasNext();)
		{
			Vertex v = i.next();
			vertices[v.id] = v;
			inDegree[v.id] = v.inDegrees();
		}

		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		for (int i = 0, length = vertices.length; i < length; i++)
		{
			// in case of seeing multiple degree-zero candidates, we could
			// use the vertices different weights...
			if (inDegree[i] == 0)
			{
				queue.add(vertices[i]);
			}
		}

		while (!queue.isEmpty())
		{
			Vertex v = queue.removeFirst();
			if (visitor != null)
			{
				visitor.visit(v);
			}
			if (v.getSuccessors() != null)
			{
				for (Iterator<Vertex> i = v.getSuccessors().iterator(); i.hasNext();)
				{
					Vertex head = i.next();
					inDegree[head.id] -= 1;
					if (inDegree[head.id] == 0)
					{
						queue.add(head);
					}
				}
			}
		}
	}
}
