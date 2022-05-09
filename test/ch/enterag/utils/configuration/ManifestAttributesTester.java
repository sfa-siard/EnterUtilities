package ch.enterag.utils.configuration;

import java.util.*;
import org.junit.*;

public class ManifestAttributesTester
{
  private ManifestAttributes _mf;

  @Before
  public void SetUp()
  {
    _mf = ManifestAttributes.getInstance();
  }
  @Test
  public void testGetBuiltDate()
  {
    String res = _mf.getImplementationVersion();
    System.out.println(res);

    res = _mf.getImplementationTitle();
    System.out.println(res);

    res = _mf.getImplementationVendor();
    System.out.println(res);

    res = _mf.getSpecificationVersion();
    System.out.println(res);

    res = _mf.getSpecificationTitle();
    System.out.println(res);

    res = _mf.getSpecificationVendor();
    System.out.println(res);

    Calendar cal = _mf.getBuiltDate();
    System.out.println(cal.getTime());
  }

}
