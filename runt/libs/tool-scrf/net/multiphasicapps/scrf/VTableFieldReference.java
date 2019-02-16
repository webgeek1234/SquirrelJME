// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.scrf;

import net.multiphasicapps.classfile.ClassName;
import net.multiphasicapps.classfile.FieldDescriptor;
import net.multiphasicapps.classfile.MemberName;

/**
 * A VTable reference to a field.
 *
 * @since 2019/02/06
 */
@Deprecated
public final class VTableFieldReference
	extends VTableMemberReference
{
	/**
	 * Initializes the reference.
	 *
	 * @param __s Is this static?
	 * @param __cl The class name.
	 * @param __mn The member name.
	 * @param __mt The member type.
	 * @throws NullPointerException On null arguments.
	 * @since 2019/02/06
	 */
	public VTableFieldReference(boolean __s, ClassName __cl, MemberName __mn,
		FieldDescriptor __mt)
		throws NullPointerException
	{
		super(__s, __cl, __mn, __mt);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2019/02/06
	 */
	@Override
	public boolean equals(Object __o)
	{
		return super.equals(__o) && (__o instanceof VTableFieldReference);
	}
}
