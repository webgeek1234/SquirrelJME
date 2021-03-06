// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.jvm.boot.task;

/**
 * This is thrown when there is no such given task.
 *
 * @since 2019/12/14
 */
public class NoSuchTaskException
	extends TaskException
{
	/**
	 * Initializes the exception with no message or cause.
	 *
	 * @since 2019/12/14
	 */
	public NoSuchTaskException()
	{
	}
	
	/**
	 * Initializes the exception with the given message and no cause.
	 *
	 * @param __m The message.
	 * @since 2019/12/14
	 */
	public NoSuchTaskException(String __m)
	{
		super(__m);
	}
	
	/**
	 * Initializes the exception with the given message and cause.
	 *
	 * @param __m The message.
	 * @param __t The cause.
	 * @since 2019/12/14
	 */
	public NoSuchTaskException(String __m, Throwable __t)
	{
		super(__m, __t);
	}
	
	/**
	 * Initializes the exception with the given cause and no message.
	 *
	 * @param __t The cause.
	 * @since 2019/12/14
	 */
	public NoSuchTaskException(Throwable __t)
	{
		super(__t);
	}
}

