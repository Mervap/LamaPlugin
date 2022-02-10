package org.jetbrains.lama.parser

import org.junit.Test

class LamaErrorParserTest : LamaBaseParserTest("parser/error") {

  @Test fun testInCase() = doTest()
  @Test fun testInFor() = doTest()
  @Test fun testInForBody() = doTest()
  @Test fun testInFun() = doTest()
  @Test fun testInIf() = doTest()
  @Test fun testInImport() = doTest()
  @Test fun testInVar() = doTest()

  private fun doTest() = doTest(true, false)
}