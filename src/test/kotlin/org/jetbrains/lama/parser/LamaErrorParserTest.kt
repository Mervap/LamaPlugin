package org.jetbrains.lama.parser

class LamaErrorParserTest : LamaBaseParserTest("parser/error") {

  fun testInCase() = doTest()
  fun testInFor() = doTest()
  fun testInForBody() = doTest()
  fun testInFun() = doTest()
  fun testInIf() = doTest()
  fun testInImport() = doTest()
  fun testInVar() = doTest()

  private fun doTest() = doTest(true, false)
}