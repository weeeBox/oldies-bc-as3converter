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

import static macromedia.asc.embedding.avmplus.ActionBlockConstants.*;


class Namespace implements Comparable<Namespace>
{
	final int kind;
	final String uri;
	private final String comparableUri;
	
	Namespace(String uri)
	{
		this(CONSTANT_Namespace, uri);
	}
	
	Namespace(int kind, String uri)
	{
		this.kind = kind;
		this.uri = uri;
		this.comparableUri = isPrivate() ? GlobalOptimizer.unique() : uri;
	}
	
	boolean isPublic()
	{
		return (kind == CONSTANT_Namespace || kind == CONSTANT_PackageNamespace) && "".equals(uri);
	}
	
	boolean isInternal()
	{
		return kind == CONSTANT_PackageInternalNs;
	}
	
	boolean isPrivate()
	{
		return kind == CONSTANT_PrivateNamespace;
	}
	
	boolean isPrivateOrInternal()
	{
		return isPrivate() || isInternal();
	}

	boolean isProtected()
	{
		return kind == CONSTANT_ProtectedNamespace ||
			   kind == CONSTANT_StaticProtectedNs;
	}
	
	public String toString()
	{
		return uri.length() > 0 ? uri : "public";
	}
	
	public int hashCode()
	{
		return kind ^ uri.hashCode();
	}
	
	public boolean equals(Object o)
	{
		if (!(o instanceof Namespace))
			return false;
		Namespace other = (Namespace) o;
		return kind == other.kind && comparableUri.equals(other.comparableUri);
	}

	public int compareTo(Namespace other)
	{
		if (other == null) return 1; // nonnull > null
		int i;
		if ((i = kind-other.kind) != 0) return i;
		return comparableUri.compareTo(other.comparableUri);
	}
}
