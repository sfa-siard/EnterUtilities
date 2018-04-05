/*== Glue.java =========================================================
Implements access to private members of classes. 
Version     : $Id: Glue.java 49 2015-02-26 08:34:20Z hartwigthomas $
Application : EnterAccess JDBC driver
Description : Implements access to private members of classes. 
------------------------------------------------------------------------
Copyright  : 2014, Enter AG, Zurich, Switzerland
Created    : 12.12.2014, Hartwig Thomas
------------------------------------------------------------------------
The class ch.enterag.utils.reflect.Glue is free software;
you can redistribute it and/or modify it under the terms of the 
GNU General Public License version 2.1 or later as published by the 
Free Software Foundation.

ch.enterag.utils.reflect.Glue is distributed in the hope 
that it will be useful, but WITHOUT ANY WARRANTY; without even the 
implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

If you have a need for licensing ch.enterag.utils.reflect.Glue
without some of the restrictions specified in the GNU General Public License,
it is possible to negotiate a different license with the copyright holder.
======================================================================*/
package ch.enterag.utils.reflect;

import java.lang.reflect.*;

/*====================================================================*/
/** Implements access to private members of classes. 
 * @author Hartwig
 */
public abstract class Glue
{
  /*------------------------------------------------------------------*/
  /** Returns the value of the private - static, if oOwner is a class - field 
   * of the owner
   * @param oOwner owner object or class.
   * @param sFieldName name of (static) field.
   * @return value of (static) field.
   */
  public static Object getPrivate(Object oOwner, String sFieldName)
  {
    Object oResult = null;
    try
    {
      Field field = null;
      Class<?> cls = null;
      if (oOwner.getClass() != Class.class)
        cls = oOwner.getClass();
      else
      {
        cls = (Class<?>)oOwner;
        oOwner = null;
      }
      for (; (field == null) && (cls != null); cls = cls.getSuperclass())
      {
        try { field = cls.getDeclaredField(sFieldName); }
        catch(NoSuchFieldException nsfe) {}
      }
      if (field != null)
      {
        field.setAccessible(true);
        oResult = field.get(oOwner);
      }
      else
        throw new IllegalArgumentException("Field "+sFieldName+" not found!");
    }
    catch(IllegalAccessException iae) { throw new IllegalArgumentException(iae.getClass().getName()+": "+iae.getMessage()); }
    return oResult;
  } /* getPrivate */
  
  /*------------------------------------------------------------------*/
  /** Sets the value of a private - static of oOwner is a class - field
   * of the owner - even if it was declared final.
   * The latter comment is only partially true. If the field is a static
   * final primitive constant, it is most likely inlined by the compiler
   * and thus not amenable to changes. 
   * @param oOwner owner object or class.
   * @param sFieldName name of (static) field.
   * @param oValue value of (static) field.
   */
  public static void setPrivate(Object oOwner, String sFieldName, Object oValue)
  {
    try
    {
      Field field = null;
      Class<?> cls = null;
      if (oOwner.getClass() != Class.class)
        cls = oOwner.getClass();
      else
      {
        cls = (Class<?>)oOwner;
        oOwner = null;
      }
      for (; (field == null) && (cls != null); cls = cls.getSuperclass())
      {
        try { field = cls.getDeclaredField(sFieldName); }
        catch(NoSuchFieldException nsfe) {}
      }
      if (field != null)
      {
        field.setAccessible(true);
        if ((field.getModifiers() & Modifier.FINAL) != 0)
        {
          try
          {
            Field modifiersField = field.getClass().getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
          }
          catch(NoSuchFieldException nsfe) {}
        }
        field.set(oOwner, oValue);
      }
      else
        throw new IllegalArgumentException("Field "+sFieldName+" not found!");
    }
    catch(IllegalAccessException iae) { throw new IllegalArgumentException(iae.getClass().getName()+": "+iae.getMessage()); }
  } /* setPrivate */

  /*------------------------------------------------------------------*/
  /** returns the constructor for the given class with the given signature.
   * @param cls class.
   * @param vaTypes signature.
   * @return constructor.
   */
  public static Constructor<?> getConstructor(Class<?> cls, Class<?>... vaTypes)
  {
    Constructor<?> constructor = null;
    try
    {
      constructor = cls.getDeclaredConstructor(vaTypes);
      constructor.setAccessible(true);
    }
    catch(NoSuchMethodException nsme) {}
    return constructor;
  } /* getConstructor */
  
  /*------------------------------------------------------------------*/
  /** invoke a private method on the given object.
   * @param oInstance instance of object.
   * @param sMethodName name of private method to be invoked.
   * @param aoTypes method signature (types of arguments).
   * @param aoArguments method arguments.
   * @return return value when function is invoked.
   */
  public static Object invokePrivate(Object oInstance, String sMethodName, Class<?>[] aoTypes, Object[] aoArguments)
  {
    Object oResult = null;
    try 
    { 
      Method method = oInstance.getClass().getDeclaredMethod(sMethodName, aoTypes);
      method.setAccessible(true);
      oResult = method.invoke(oInstance, aoArguments);
    }
    catch(NoSuchMethodException nsme){ throw new IllegalArgumentException("Method "+sMethodName+" does not exist for this object and argument types!"); }
    catch(InvocationTargetException ite){ throw new IllegalArgumentException(ite.getClass().getName()+": "+ite.getMessage()); }
    catch(IllegalAccessException iae) { throw new IllegalArgumentException(iae.getClass().getName()+": "+iae.getMessage()); }
    return oResult;
  } /* invokePrivate */
  
} /* class Glue */
