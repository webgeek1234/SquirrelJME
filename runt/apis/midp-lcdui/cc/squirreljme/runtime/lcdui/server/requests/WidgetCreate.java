// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.runtime.lcdui.server.requests;

import cc.squirreljme.runtime.lcdui.LcdFunction;
import cc.squirreljme.runtime.lcdui.server.LcdRequest;
import cc.squirreljme.runtime.lcdui.server.LcdServer;
import cc.squirreljme.runtime.lcdui.server.LcdWidget;
import cc.squirreljme.runtime.lcdui.WidgetType;

/**
 * Creates a new widget.
 *
 * @since 2018/03/23
 */
public class WidgetCreate
	extends LcdRequest
{
	/** The type of widget to create. */
	protected final WidgetType type;
	
	/**
	 * Initializes the request.
	 *
	 * @param __sv The calling server.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/03/23
	 */
	public WidgetCreate(LcdServer __sv, WidgetType __type)
		throws NullPointerException
	{
		super(__sv, LcdFunction.WIDGET_CREATE);
		
		if (__type == null)
			throw new NullPointerException("NARG");
		
		this.type = __type;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/03/23
	 */
	@Override
	protected final Object invoke()
	{
		throw new todo.TODO();
	}
}

