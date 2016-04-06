// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.interpreter;

/**
 * This contains the class path which manages a set of classes which are
 * available in an engine.
 *
 * @see JVMClassPathElement
 * @since 2016/04/06
 */
public final class JVMClassPath
{
	/** The owning engine. */
	protected final JVMEngine engine;	
	
	/**
	 * Initializes the class path.
	 *
	 * @param __eng The owning engine.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/04/06
	 */
	JVMClassPath(JVMEngine __eng)
		throws NullPointerException
	{
		// Check
		if (__eng == null)
			throw new NullPointerException("NARG");
		
		// Set
		engine = __eng;
	}
}

