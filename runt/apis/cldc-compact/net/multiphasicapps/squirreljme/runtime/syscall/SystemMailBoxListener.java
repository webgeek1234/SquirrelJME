// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.runtime.syscall;

import java.io.Closeable;
import net.multiphasicapps.squirreljme.runtime.syscall.KernelMailBoxException;

/**
 * This represents a listener for incoming mailbox connections and acts as
 * a server.
 *
 * @since 2017/12/10
 */
public interface SystemMailBoxListener
	extends Closeable
{
	/**
	 * Accepts an incoming mailbox request to create a mailbox connection.
	 *
	 * @param __id The listening mailbox to accept.
	 * @return The mailbox descriptor for the server end of the mailbox.
	 * @throws IllegalArgumentException If the mailbox is not valid.
	 * @throws InterruptedException If the thread was interrupted accepting
	 * a connection.
	 * @throws KernelMailBoxException If the mailbox is closed.
	 * @since 2016/10/13
	 */
	public abstract SystemMailBoxConnection accept(int __ld)
		throws IllegalArgumentException, InterruptedException,
			KernerlMailBoxException;
}

