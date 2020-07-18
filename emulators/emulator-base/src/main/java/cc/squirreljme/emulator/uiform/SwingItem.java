// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.emulator.uiform;

import cc.squirreljme.jvm.mle.brackets.UIItemBracket;
import javax.swing.JComponent;

/**
 * This is the base call for all of the item types that are implementing on
 * Swing.
 *
 * @since 2020/07/18
 */
public abstract class SwingItem
	implements UIItemBracket
{
	/** The form the item is on. */
	SwingForm _form;
	
	/**
	 * Returns the component item.
	 * 
	 * @return The component item.
	 * @since 2020/07/18
	 */
	public abstract JComponent component();
	
	/**
	 * Deletes the item.
	 * 
	 * @since 2020/07/18
	 */
	public abstract void delete();
	
	/**
	 * Removes the item from the form it is within.
	 * 
	 * @return If it was removed from the form.
	 * @since 2020/07/18
	 */
	public final boolean removeFromForm()
	{
		synchronized (this)
		{
			// Not on a form
			SwingForm form = this._form;
			if (form == null)
				return false;
			
			// Remove from this form
			form.itemRemove(this);
			return true;
		}
	}
}
