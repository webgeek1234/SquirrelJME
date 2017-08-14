// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.jit.bin;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import net.multiphasicapps.util.datadeque.ByteDeque;

/**
 * This class is used to build fractions which are placed into sections.
 * Since {@link Fragment} data should be as immutable as possible, this allows
 * such sections to be dynamically created and appended when they are ready.
 *
 * @since 2017/06/23
 */
public class FragmentBuilder
{
	/** Deque for the bytes within the section. */
	private final ByteDeque _bytes =
		new ByteDeque();
	
	/** Has the fragment been finished? */
	private volatile boolean _finished;
	
	/**
	 * Initializes the fragment builder.
	 *
	 * @since 2017/06/23
	 */
	public FragmentBuilder()
		throws NullPointerException
	{
	}
	
	/**
	 * Appends a single non-dynamic byte to the output section.
	 *
	 * @param __v The byte to add.
	 * @throws IllegalStateException If the fragment has been finished.
	 * @since 2017/06/27
	 */
	public final void append(byte __v)
		throws IllegalStateException
	{
		// {@squirreljme.error JI0z Cannot add a single non-dynamic byte
		// because the fragment builder has been finished.}
		if (this._finished)
			throw new IllegalStateException("JI0z");
		
		this._bytes.addLast(__v);
	}
	
	/**
	 * Appends multiple non-dynamic bytes to the output section.
	 *
	 * @param __v The bytes to add.
	 * @param __o The offset into the array.
	 * @param __l The number of bytes to add.
	 * @throws IllegalStateException If the fragment has been finished.
	 * @throws IndexOutOfBoundsException If the offset and/or length are
	 * negative or exceeds the array bounds.
	 * @throws NullPointerException On null arguments.
	 * @since 2017/06/27
	 */
	public final void append(byte[] __v, int __o, int __l)
		throws IllegalStateException, IndexOutOfBoundsException,
			NullPointerException
	{
		// Check
		if (__v == null)
			throw new NullPointerException("NARG");
		if (__o < 0 || __l < 0 || (__o + __l) > __v.length)
			throw new IndexOutOfBoundsException("IOOB");
		
		// {@squirreljme.error JI0y Cannot add multiple non-dynamic bytes
		// because the fragment builder has been finished.}
		if (this._finished)
			throw new IllegalStateException("JI0y");
		
		this._bytes.addLast(__v, __o, __l);
	}
	
	/**
	 * Finishes the specified fragment adding it to the section in the
	 * specified linker state.
	 *
	 * @param __ls The linker state to write the fragment to.
	 * @param __t The section the fragment which is in\.
	 * @throws IllegalStateException If the fragment has been finished.
	 * @since 2017/06/27
	 */
	public final Reference<Fragment> build(LinkerState __ls,
		SectionType __t)
		throws IllegalStateException, NullPointerException
	{
		// Check
		if (__ls == null || __t == null)
			throw new NullPointerException("NARG");
		
		// {@squirreljme.error JI0x The fragment being built has already been
		// finished.}
		if (this._finished)
			throw new IllegalStateException("JI0x");
		this._finished = true;
		
		// Build fragment
		throw new todo.TODO();
		/*return __linkerState().sections().__getOrCreate(this.type).__append(
			this.bytes.toByteArray());*/
	}
}

