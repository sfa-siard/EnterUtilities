package ch.enterag.utils;

import org.junit.runner.*;
import org.junit.runners.*;
import ch.enterag.utils.configuration.*;
import ch.enterag.utils.io.*;
import ch.enterag.utils.lang.*;
import ch.enterag.utils.logging.*;

@RunWith(Suite.class)
@Suite.SuiteClasses(
  {
    ManifestAttributesTester.class,
    SpecialFolderTester.class,
    ExecuteTester.class,
    IndentLoggerTester.class,
    DuTester.class
  })
public class _EnterUtilsTestSuite
{
}
