package org.jetbrains.lama.parser

import org.junit.Test

class LamaParserTest : LamaBaseParserTest("parser/correct") {

  @Test fun testALotOfSubtractions() = doTest()
  @Test fun testAssign() = doTest()
  @Test fun testCase() = doTest()
  @Test fun testCaseAt() = doTest()
  @Test fun testCaseListMatch() = doTest()
  @Test fun testCaseMatch() = doTest()
  @Test fun testCaseSharp() = doTest()
  @Test fun testCompareOperators() = doTest()
  @Test fun testComplexArgumentList() = doTest()
  @Test fun testComplexExpression() = doTest()
  @Test fun testDoWhile() = doTest()
  @Test fun testEta() = doTest()
  @Test fun testFor() = doTest()
  @Test fun testForSkip() = doTest()
  @Test fun testFun() = doTest()
  @Test fun testFunFor() = doTest()
  @Test fun testFunIf() = doTest()
  @Test fun testHuge() = doTest()
  @Test fun testIf() = doTest()
  @Test fun testInfixCall() = doTest()
  @Test fun testInfixCallInScope() = doTest()
  @Test fun testInfixDefinition() = doTest()
  @Test fun testInfixr() = doTest()
  @Test fun testLazy() = doTest()
  @Test fun testManyOperators() = doTest()
  @Test fun testMatch() = doTest()
  @Test fun testNestedFun() = doTest()
  @Test fun testNestedIf() = doTest()
  @Test fun testNoSemiRequired() = doTest()
  @Test fun testOperatorsInCall() = doTest()
  @Test fun testParenthesisScope() = doTest()
  @Test fun testParenthesisScopeFun() = doTest()
  @Test fun testPrintSExpr() = doTest()
  @Test fun testRecursive() = doTest()
  @Test fun testSeveralExpressions() = doTest()
  @Test fun testSimpleFile() = doTest()
  @Test fun testSyntax() = doTest()
  @Test fun testWhile() = doTest()
  @Test fun testWhileIf() = doTest()
  @Test fun testWhileWhileIf() = doTest()

  private fun doTest() = doTest(true, true)
}