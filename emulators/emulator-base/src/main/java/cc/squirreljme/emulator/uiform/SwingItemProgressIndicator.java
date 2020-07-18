// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.emulator.uiform;

import cc.squirreljme.runtime.cldc.debug.Debugging;
import javax.swing.JProgressBar;

/**
 * Progress indicator.
 *
 * @since 2020/07/18
 */
public class SwingItemProgressIndicator
	extends SwingItem
{
	/** The progress bar. */
	private final JProgressBar progressBar;
	
	/**
	 * Initializes the item.
	 * 
	 * @since 2020/07/18
	 */
	public SwingItemProgressIndicator()
	{
		this.progressBar = new JProgressBar();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2020/07/18
	 */
	@Override
	public JProgressBar component()
	{
		return this.progressBar;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2020/07/18
	 */
	@Override
	public void delete()
	{
	}
}
