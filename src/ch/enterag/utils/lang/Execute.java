/*== Execute.java ======================================================
Execute implements execution of an external command.
Version     : $Id: Execute.java 1445 2012-02-23 14:35:21Z hartwig $
Application : Siard2
Description : Execute implements execution of an external command.
------------------------------------------------------------------------
Copyright  : Enter AG, Zurich, Switzerland, 2007
Created    : 27.11.2007, Hartwig Thomas
======================================================================*/
package ch.enterag.utils.lang;

import java.io.*;
import ch.enterag.utils.logging.*;

/*====================================================================*/
/** Execute implements execution of an external command.
 @author Hartwig Thomas
 */
public class Execute
{
  /*====================================================================
  (private) data members
  ====================================================================*/
  /** logger */  
  private static IndentLogger _il = IndentLogger.getIndentLogger(Execute.class.getName());
  /** stdOut result of last execute */
  private String _sStdOut = null;
  /** @return stdOut from last execute */
  public String getStdOut() { return _sStdOut; }
  /** stdErr result of last execute */
  private String _sStdErr = null;
  /** @return stdErr from last execute */
  public String getStdErr() { return _sStdErr; }
  /** result of last execute */
  private int _iResult = -1;
  /** @return result of last execute. */
  public int getResult() { return _iResult; }
  
	/*------------------------------------------------------------------*/
  /** returns true, if the OS is Windows.
   * @return true, if the OS is Windows.
  */
  public static boolean isOsWindows()
  {
  	boolean bIsOsWindows = false;
  	String sOsName = System.getProperty("os.name");
  	if (sOsName != null)
  	  bIsOsWindows = sOsName.startsWith("Windows");
  	return bIsOsWindows;
  } /* isOsWindows */

  /*------------------------------------------------------------------*/
  /** returns true, if the OS is LINUX.
   * @return true, if the OS is LINUX.
  */
  public static boolean isOsLinux()
  {
    boolean bIsOsLinux = false;
    String sOsName = System.getProperty("os.name");
    if (sOsName != null)
      bIsOsLinux = sOsName.startsWith("Linux");
    return bIsOsLinux;
  } /* isOsLinux */

  /*------------------------------------------------------------------*/
  /** capture the output from the given input stream and return it as
   * as string.
   * @param is input stream.
   * @return captured output.
   * @throws IOException if an I/O error occurred.
   */
  private String captureOutput(InputStream is)
    throws IOException
  {
    InputStreamReader isrdrOut = new InputStreamReader(is);
    StringWriter swOut = new StringWriter();
    for (int i = isrdrOut.read(); i != -1; i = isrdrOut.read())
      swOut.write(i);
    swOut.close();
    return swOut.toString();
  } /* captureOutput */

  /*------------------------------------------------------------------*/
  /** redirect the characters from the given reader to the given 
   * output stream.
   * @param rdr redirected input.
   * @param os output stream to receive redirected input.
   * @throws IOException if an I/O error occurred.
   */
  private void redirectInput(Reader rdr, OutputStream os)
    throws IOException
  {
    _il.event("stdin redirected");
    OutputStreamWriter oswr = new OutputStreamWriter(os);
    for (int i = rdr.read(); i != -1; i = rdr.read())
      oswr.write(i);
    oswr.close();
  } /* redirectInput */
 
  /*------------------------------------------------------------------*/
  /** executes an external command line.
   @param sCommand external command line to be executed.
   @return return code from command.
  */
  private int run(String sCommand)
  {
  	_il.enter(sCommand);
  	int iReturn = -1;
  	try
  	{
  	  Process proc = Runtime.getRuntime().exec(sCommand);
      _sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: "+_sStdOut.toString());
      _sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: "+_sStdErr.toString());
      iReturn = proc.waitFor();
  	}
    catch(IOException ie) { _il.exception(ie); }
  	catch(InterruptedException ie) { _il.exception(ie); }
  	_il.exit(String.valueOf(iReturn));
  	return iReturn;
  } /* run */
  
  /*------------------------------------------------------------------*/
  /** executes an external command line.
   @param sCommand external command line to be executed.
   @return execution context with result, stdout, and stderr.
  */
  public static Execute execute(String sCommand)
  {
    Execute ex = new Execute();
    ex._iResult = ex.run(sCommand);
    return ex;
  } /* execute */

  /*------------------------------------------------------------------*/
  /** executes an external command line.
   @param sCommand external command line to be executed.
   @param rdrInput redirected input to be used.
   @return return code from command.
  */
  private int run(String sCommand, Reader rdrInput)
  {
    _il.enter(sCommand, rdrInput);
    int iReturn = -1;
    try
    { 
      Process proc = Runtime.getRuntime().exec(sCommand);
      redirectInput(rdrInput, proc.getOutputStream());
      _sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: "+_sStdOut.toString());
      _sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: "+_sStdErr.toString());
      iReturn = proc.waitFor();
    }
    catch(InterruptedException ie) { _il.exception(ie); }
    catch(IOException ie) { _il.exception(ie); }
    _il.exit(String.valueOf(iReturn));
    return iReturn;
  } /* run */
  
  /*------------------------------------------------------------------*/
  /** executes an external command line.
   @param sCommand external command line to be executed.
   @param rdrInput redirected input to be used.
   @return execution context with result, stdout, and stderr.
  */
  public static Execute execute(String sCommand, Reader rdrInput)
  {
    Execute ex = new Execute();
    ex._iResult = ex.run(sCommand, rdrInput);
    return ex;
  } /* execute */

  /*------------------------------------------------------------------*/
  /** executes an external command with arguments.
   @param asCommand external command and arguments to be executed.
   @return return code from command.
  */
  private int run(String[] asCommand)
  {
    _il.enter((Object[])asCommand);
    int iReturn = -1;
    try
    { 
      Process proc = Runtime.getRuntime().exec(asCommand);
      _sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: "+_sStdOut.toString());
      _sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: "+_sStdErr.toString());
      iReturn = proc.waitFor();
    }
    catch(InterruptedException ie) { _il.exception(ie); }
    catch(IOException ie) { _il.exception(ie); }
    _il.exit(String.valueOf(iReturn));
    return iReturn;
  } /* run */
  
  /*------------------------------------------------------------------*/
  /** executes an external command line.
   @param asCommand external command and arguments to be executed.
   @return execution context with result, stdout, and stderr.
  */
  public static Execute execute(String[] asCommand)
  {
    Execute ex = new Execute();
    ex._iResult = ex.run(asCommand);
    return ex;
  } /* execute */

  /*------------------------------------------------------------------*/
  /** executes an external command with arguments.
   @param asCommand external command and arguments to be executed.
   @param rdrInput redirected input to be used.
   @return return code from command.
  */
  private int run(String[] asCommand, Reader rdrInput)
  {
    _il.enter((Object[])asCommand, rdrInput);
    int iReturn = -1;
    try
    { 
      Process proc = Runtime.getRuntime().exec(asCommand);
      redirectInput(rdrInput, proc.getOutputStream());
      _sStdOut = captureOutput(proc.getInputStream());
      _il.event("stdout: "+_sStdOut.toString());
      _sStdErr = captureOutput(proc.getErrorStream());
      _il.event("stderr: "+_sStdErr.toString());
      iReturn = proc.waitFor();
    }
    catch(InterruptedException ie) { _il.exception(ie); }
    catch(IOException ie) { _il.exception(ie); }
    _il.exit(String.valueOf(iReturn));
    return iReturn;
  } /* run */
  
  /*------------------------------------------------------------------*/
  /** executes an external command line with redirected input from
   * a reader.
   @param asCommand command and arguments to be executed.
   @param rdrInput redirected input to be used.
   @return execution context with result, stdout, and stderr.
   */
  public static Execute execute(String[] asCommand, Reader rdrInput)
  {
    Execute ex = new Execute();
    ex._iResult = ex.run(asCommand, rdrInput);
    return ex;
  } /* execute */
  
} /* Execute */
