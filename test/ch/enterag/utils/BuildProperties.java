package ch.enterag.utils;

import java.io.*;
import java.util.*;
import static org.junit.Assert.*;

public class BuildProperties
  extends Properties
{
  private static final long serialVersionUID = 1L;

  private void readProperties()
  {
    try
    {
      Reader rdr = new FileReader("build.properties");
      load(rdr);
      rdr.close();
    }
    catch (IOException ie) { fail(ie.getClass().getName()+": "+ie.getMessage()); }
  }
  
  public BuildProperties()
  {
    readProperties();
  }
  
}
