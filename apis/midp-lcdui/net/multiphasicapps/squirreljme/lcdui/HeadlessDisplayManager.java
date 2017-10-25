// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.lcdui;

import java.lang.ref.Reference;
import javax.microedition.lcdui.Displayable;
import net.multiphasicapps.squirreljme.lcdui.DisplayHead;
import net.multiphasicapps.squirreljme.lcdui.DisplayManager;
import net.multiphasicapps.squirreljme.lcdui.widget.DisplayableWidget;

/**
 * This is a headless display manager which provides only a non-visible
 * display.
 *
 * @since 2017/08/21
 */
public class HeadlessDisplayManager
	extends DisplayManager
{
	/**
	 * {@inheritDoc}
	 * @since 2017/10/25
	 */
	@Override
	public DisplayableWidget createDisplayableWidget(
		Reference<Displayable> __rd)
		throws NullPointerException
	{
		if (__rd == null)
			throw new NullPointerException("NARG");
		
		throw new todo.TODO();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/08/21
	 */
	public DisplayHead[] heads()
	{
		return new DisplayHead[0];
	}
}

