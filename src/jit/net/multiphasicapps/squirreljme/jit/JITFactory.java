// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.jit;

import java.io.InputStream;
import java.io.IOException;
import java.util.ServiceLoader;

/**
 * This factory is used to create instances of the JIT compiler which reads an
 * input class and produces output from them.
 *
 * This is used with the service loader.
 *
 * @since 2016/07/02
 */
public abstract class JITFactory
{
	/** Service for JIT factory lookup. */
	private static final ServiceLoader<JITFactory> _SERVICES =
		ServiceLoader.<JITFactory>load(JITFactory.class);
	
	/**
	 * Returns the name of the architecture this compiles for.
	 *
	 * @return The name of the architecture.
	 * @since 2016/07/02
	 */
	public abstract String architectureName();
}

