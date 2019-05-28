// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package dev.shadowtail.packfile;

/**
 * This represents the header for a minimized pack file, it mostly just
 * represents a number of JAR files which are combined into one.
 *
 * @since 2019/05/28
 */
public final class MinimizedPackHeader
{
	/** Magic number for the pack file. */
	public static final int MAGIC_NUMBER =
		0x58455223;
	
	/** The size of the header without the magic number. */
	public static final int HEADER_SIZE_WITHOUT_MAGIC =
		16;
	
	/** The size of the header with the magic number. */
	public static final int HEADER_SIZE_WITH_MAGIC =
		HEADER_SIZE_WITHOUT_MAGIC + 4;
	
	/** The number of jars in this packfile. */
	public final int numjars;
	
	/** The index of the JAR which should be the boot point. */
	public final int bootjarindex;
	
	/** The offset into the packfile where the boot entry is. */
	public final int bootjaroffset;
	
	/** The offset to the table of contents. */
	public final int tocoffset;
	
	/**
	 * Initializes the pack header.
	 *
	 * @param __fs Fields.
	 * @throws NullPointerException On null arguments.
	 * @since 2019/05/28
	 */
	public MinimizedPackHeader(int... __fs)
		throws NullPointerException
	{
		if (__fs == null)
			throw new NullPointerException("NARG");
		
		int at = 0;
		
		// Number of JAR files
		this.numjars = __fs[at++];
		
		// Boot JAR that may be specified
		this.bootjarindex = __fs[at++];
		this.bootjaroffset = __fs[at++];
		
		// Table of contents offset (names and offsets of the JARs)
		this.tocoffset = __fs[at++];
	}
}

