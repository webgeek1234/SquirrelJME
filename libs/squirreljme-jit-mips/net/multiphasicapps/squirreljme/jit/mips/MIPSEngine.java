// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.jit.mips;

import net.multiphasicapps.squirreljme.classformat.CodeVariable;
import net.multiphasicapps.squirreljme.classformat.StackMapType;
import net.multiphasicapps.squirreljme.jit.Binding;
import net.multiphasicapps.squirreljme.jit.CacheState;
import net.multiphasicapps.squirreljme.jit.DataType;
import net.multiphasicapps.squirreljme.jit.JITStateAccessor;
import net.multiphasicapps.squirreljme.jit.TranslationEngine;

/**
 * This is the engine which is able to generate MIPS machine code.
 *
 * The ABI that this engine uses on MIPS machines is NUBI, documentation of it
 * is available here:
 * {@link ftp://ftp.linux-mips.org/pub/linux/mips/doc/NUBI/} in a file called
 * {@code MD00438-2C-NUBIDESC-SPC-00.20.pdf}.
 *
 * @see NUBI
 * @since 2017/02/11
 */
public class MIPSEngine
	extends TranslationEngine
{
	/** The configuration used. */
	protected final MIPSConfig config;
	
	/**
	 * Initializes the MIPS engine.
	 *
	 * @param __conf The MIPS configuration to use.
	 * @param __jsa The accessor to the JIT state.
	 * @since 2017/02/11
	 */
	public MIPSEngine(MIPSConfig __conf, JITStateAccessor __jsa)
	{
		super(__conf, __jsa);
		
		// Set
		this.config = __conf;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/02/19
	 */
	@Override
	public void bindStateForEntry(CacheState __cs)
		throws NullPointerException
	{
		// Check
		if (__cs == null)
			throw new NullPointerException("NARG");
		
		// Need some config details
		MIPSConfig config = this.config;
		int bits = config.bits();
		
		// Starting register points where arguments are placed
		MIPSRegister ni = NUBI.A1;
		MIPSRegister nf = NUBI.FA1;
		
		// Go through local variables
		CacheState.Locals locals = __cs.locals();
		for (int i = 0, n = locals.size(); i < n; i++)
		{
			// Get variable here
			CodeVariable cv = locals.get(i);
			
			// Ignore empty variables
			if (cv == null)
				continue;
			
			// Alias type (float can turn into int for example)
			DataType type = __aliasType(__cs.getType(cv));
			
			// Depends
			MIPSBinding bind;
			switch (type)
			{
					// int
				case INTEGER:
					// Use single register
					if (ni != null)
					{
						bind = new MIPSBinding(ni);
						ni = NUBI.nextArgument(ni);
					}
					
					// Stack allocate
					else
						bind = null;
					break;
					
					// long
				case LONG:
					// Register might be available
					if (ni != null)
					{
						// 32-bit uses single register
						if (bits > 32)
						{
							bind = new MIPSBinding(ni);
							ni = NUBI.nextArgument(ni);
						}
						
						// Otherwise
						else
						{
							MIPSRegister hi = ni;
							MIPSRegister lo = NUBI.nextArgument(hi);
							
							// Make sure the entire value vits in registers so
							// that cross stack/register combinations are not
							// used
							if (hi != null && lo != null)
							{
								bind = new MIPSBinding(hi, lo);
								ni = NUBI.nextArgument(lo);
							}
							
							// Use stack
							else
								bind = null;
						}
					}
					
					// Stack
					else
						bind = null;
					break;
					
					// float or double
				case FLOAT:
				case DOUBLE:
					// Use single register
					if (nf != null)
					{
						bind = new MIPSBinding(nf);
						nf = NUBI.nextArgument(nf);
					}
					
					// Stack allocate
					else
						bind = null;
					break;
				
					// Should not happen
				default:
					throw new RuntimeException("OOPS");
			}
			
			// No bind specified, allocate on the stack
			if (bind == null)
				throw new Error("TODO");
			
			// Set binding
			__cs.setBinding(cv, bind);
		}
	}
	
	/**
	 * Aliases the specified stack map type to a data type.
	 *
	 * @param __t The stack type to alias.
	 * @return The data type for the stack type.
	 * @throws NullPointerException On null arguments.
	 * @since 2017/02/20
	 */
	DataType __aliasType(StackMapType __t)
		throws NullPointerException
	{
		// Check
		if (__t == null)
			throw new NullPointerException("NARG");
		
		// If an object use the size of a pointer
		if (__t == StackMapType.OBJECT)
			return (this.config.bits() > 32 ? DataType.LONG :
				DataType.INTEGER);
		
		// Use normal mapping
		return __aliasType(DataType.of(__t));
	}
	
	/**
	 * Aliases the given data type to handle software floating point.
	 *
	 * @parma __t The type to alias.
	 * @return The same type or the alias of the type for example if it is
	 * using software floating point.
	 * @throws NullPointerException On null arguments.
	 * @since 2017/02/20
	 */
	DataType __aliasType(DataType __t)
		throws NullPointerException
	{
		// Check
		if (__t == null)
			throw new NullPointerException("NARG");
		
		// Depends
		switch (__t)
		{
				// Keep as is
			case INTEGER:
			case LONG:
				return __t;
				
				// Adjust if software mode
			case FLOAT:
			case DOUBLE:
				throw new Error("TODO");
				
				// Unknown
			default:
				throw new RuntimeException("OOPS");
		}
	}
}

