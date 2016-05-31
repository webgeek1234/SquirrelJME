// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.kernel.impl.jvm.swing;

import net.multiphasicapps.squirreljme.kernel.impl.jvm.BasicMain;
import net.multiphasicapps.squirreljme.kernel.impl.jvm.JVMKernel;
import net.multiphasicapps.squirreljme.terp.Interpreter;

/**
 * Main entry point for the Java SE JVM launcher interface kernel.
 *
 * @since 2016/05/14
 */
public class Main
	extends BasicMain
{
	/**
	 * Initializes the main kernel.
	 *
	 * @param __args Kernel arguments.
	 * @since 2016/05/30
	 */
	public Main(String... __args)
	{
		super(__args);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/05/30
	 */
	@Override
	protected JVMKernel createKernel(Interpreter __terp, String... __args)
		throws NullPointerException
	{
		// Check
		if (__terp == null)
			throw new NullPointerException("NARG");
		
		// Create it
		return new SwingKernel(__terp, __args);
	}
	
	/**
	 * Main entry point.
	 *
	 * @param __args Program arguments.
	 * @since 2016/05/14
	 */
	public static void main(String... __args)
	{
		// Set it up
		new Main(__args).run();
	}
}

