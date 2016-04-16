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

import java.io.InputStream;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import net.multiphasicapps.classfile.CFMethod;
import net.multiphasicapps.classfile.CFMethodFlags;
import net.multiphasicapps.classprogram.CPOp;
import net.multiphasicapps.classprogram.CPProgram;
import net.multiphasicapps.classprogram.CPProgramException;
import net.multiphasicapps.descriptors.FieldSymbol;
import net.multiphasicapps.descriptors.MethodSymbol;

/**
 * This represents a bound method within a class.
 *
 * @since 2016/04/04
 */
public class JVMMethod
	extends JVMMember<MethodSymbol, CFMethodFlags, CFMethod, JVMMethod>
{
	/** Lock. */
	protected final Object lock =
		new Object();
	
	/** The current program. */
	private volatile Reference<CPProgram> _program;
	
	/**
	 * Initializes the method.
	 *
	 * @param __o The owning group.
	 * @param __b The base for it.
	 * @since 2016/04/05
	 */
	JVMMethod(JVMMethods __o, CFMethod __b)
	{
		super(__o, __b);
	}
	
	/**
	 * Checks whether the current method can access the given accessible
	 * object.
	 *
	 * @param __o The object check the access againt.
	 * @return {@code true} if it can be accessed, otherwise {@code false}.
	 * @throws JVMClassCastException If a class cannot be resolved for the
	 * target accessible object.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/04/09
	 */
	public boolean canAccess(JVMAccessibleObject __o)
		throws JVMClassCastException, NullPointerException
	{
		// Check
		if (__o == null)
			throw new NullPointerException("NARG");
		
		// If this is a member then get the class it is in
		JVMMember member;
		if (__o instanceof JVMMember)
			__o = (member = ((JVMMember)__o)).outerClass();
		else
			member = null;
		
		// Must be a class
		JVMClass intoclass;
		if (__o instanceof JVMClass)
			intoclass = (JVMClass)__o;
		
		// {@squirreljme.error IN0l Cannot determine the class of the object
		// to check access into. (The other object)}
		else
			throw new JVMClassCastException(null, String.format("IN0l %s",
				__o));
		
		// Get our own class
		JVMClass fromclass = super.outerClass();
		
		// If the same class, then all access is permitted
		if (fromclass == intoclass)
			return true;
		
		// OK if public
		if (__o.isPublic())
			return true;
		
		// Not OK if private
		if (__o.isPrivate())
			return false;
		
		// If package private, must be in the same package
		if (__o.isPackagePrivate() && Objects.equals(fromclass.inPackage(),
			intoclass.inPackage()))
			return true;
		
		// Otherwise check if this is a superclass of the given class
		throw new Error("TODO");
	}
	
	/**
	 * Checks whether the current method can access the given accessible
	 * object.
	 *
	 * @param __o The other accessible object to check.
	 * @return {@code true} if it can be access, otherwise an exception is
	 * thrown.
	 * @throws JVMIncompatibleClassChangeError If the access is denied.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/04/09
	 */
	public boolean checkAccess(JVMAccessibleObject __o)
		throws JVMIncompatibleClassChangeError, NullPointerException
	{
		// Check
		if (__o == null)
			throw new NullPointerException("NARG");
		
		// {@squirreljme.error IN0k The current method is not permitted to
		// access the given accessible object. (This method; The accessible
		// object)}
		if (!canAccess(__o))
			throw new JVMIncompatibleClassChangeError(null, String.format(
				"IN0k %s %s", this, __o));
		return true;
	}
	
	/**
	 * Runs the interpreter logic for the given thread.
	 *
	 * @param __thr The thread of execution, if {@code null} then there is none
	 * or the default thread is implied.
	 * @param __init Is this an class instance or static initializer, if it is
	 * then it is permitted to write to final fields.
	 * @param __args The arguments to the call of the method.
	 * @throws JVMEngineException Any thrown exceptions are either handled or
	 * propogated upwards.
	 * @since 2016/04/07
	 */
	public void interpret(JVMThread __thr, boolean __init, Object... __args)
		throws JVMEngineException
	{
		// Force arguments to exist
		if (__args == null)
			__args = new Object[0];
		
		// If no thread is specified, then 
		if (__thr == null)
			__thr = engine().threads().defaultThread();
		
		// On entry of a method, add this method to the call stack.
		JVMStackFrame currentframe = __thr.enterFrame(this, __init, __args);
		try
		{
			// Debug
			System.err.printf("DEBUG -- Interpret %s (%s)%n", this, __init);
			
			// Get program here
			CPProgram program = program();
			
			// Get the current class and the class object, this way before a
			// method is executed the current class is initialized properly.
			JVMClass incl = outerClass();
			JVMObject inclo = incl.classObject(__thr);
			
			// Keep executing until a return is reached or an unhandled
			// exception is done.
			JVMComputeMachine cm = engine().computeMachine();
			while (!currentframe.isReturning())
			{
				try
				{
					// Get the operation for the current address
					CPOp op = program.get(currentframe.getPCAddress());
					
					// Get the entry address
					int entryaddr = currentframe.getPCAddress();
					
					// Perform the operation
					op.<JVMStackFrame>compute(cm, currentframe);
					
					// Jumping?
					int potjump = currentframe.getJumpTarget();
					if (potjump >= 0)
					{
						// Set new position and clear the jump
						currentframe.setPCAddress(potjump);
						currentframe.clearJumpTarget();
					}
					
					// Otherwise, flow to the next instruction
					else
					{
						currentframe.setPCAddress(entryaddr + 1);
					}
				}
				
				// Caught exception, it needs to be handled.
				catch (JVMEngineException e)
				{
					// Record trace potentially
					e.setVMStackTrace(__thr);
					
					// If a VM error, do not wrap
					if (e instanceof JVMVirtualMachineError)
						throw e;
					
					// Currently wrap no exceptions into the guest
					System.err.println("TODO -- Wrap exceptions into guest.");
					throw e;
				}
				
				// Not as critical, but still pretty bad
				catch (CPProgramException e)
				{
					// {@squirreljme.error IN0s The current method is
					// malformed and is not correct.}
					throw new JVMIncompatibleClassChangeError(__thr, "IN0s",
						e);
				}
				
				// Very critical failure
				catch (RuntimeException|Error e)
				{
					// {@squirreljme.error IN0o Critical virtual machine
					// error.}
					throw new JVMVirtualMachineError(__thr, "IN0o", e);
				}
			}
		}
		
		// When execution terminates, remove the top stack item.
		finally
		{
			currentframe.leave();
		}
	}
	
	/**
	 * Returns {@code true} if this is a static initializer.
	 *
	 * @return {@code true} if a static initializer.
	 * @since 2016/04/16
	 */
	public boolean isClassInitializer()
	{
		return base.isClassInitializer();
	}
	
	/**
	 * Returns {@code true} if this is a constructor.
	 *
	 * @return {@code true} if this is a constructor.
	 * @since 2016/04/16
	 */
	public boolean isConstructor()
	{
		return base.isConstructor();
	}
	
	/**
	 * Is this an initializer for the static area or instance of class.
	 *
	 * @return {@code true} if it is an initializer.
	 * @since 2016/04/16
	 */
	public boolean isInitializer()
	{
		return isClassInitializer() | isConstructor();
	}
	
	/**
	 * Returns the program of the current method.
	 *
	 * @return The method's program.
	 * @since 2016/04/06
	 */
	public CPProgram program()
	{
		// Lock
		synchronized (lock)
		{
			// Get reference
			Reference<CPProgram> ref = _program;
			CPProgram rv;
			
			// Needs loading?
			if (ref == null || null == (rv = ref.get()))
				try (InputStream is = base.codeAttribute())
				{
					// {@squirreljme.error IN0a The current method has no
					// defined program, it is likely {@code abstract} or
					// {@code native}. (The current method)}
					if (is == null)
						throw new JVMClassFormatError(null,
							String.format("IN0a %s", this));
					
					// Load it
					_program = new WeakReference<>((rv = new CPProgram(
						container().outerClass().base(), base, is)));
				}
				
				// Failed to load program
				catch (CPProgramException|IOException e)
				{
					// {@squirreljme.error IN09 Could not get the program for
					// the current method either because it does not exist or
					// it is not a valid program. (The current method)}
					throw new JVMClassFormatError(null,
						String.format("IN09 %s", this), e);
				}
			
			return rv;
		}
	}
}

