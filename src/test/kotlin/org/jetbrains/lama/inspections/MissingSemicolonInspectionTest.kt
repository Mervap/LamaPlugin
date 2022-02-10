package org.jetbrains.lama.inspections

import org.junit.Test

class MissingSemicolonInspectionTest : LamaInspectionTest() {

  @Test fun testHighlightImport() = doTest()
  @Test fun testHighlightVariable() = doTest()
  @Test fun testHighlightExpressions() = doTest()
  @Test fun testNoInspectionsAfterLastExpression() = doTest()

  @Test fun testVariable() = doReplacementTest()
  @Test fun testSeveralVariables() = doReplacementTest()
  @Test fun testImportVariablesExpressions() = doReplacementTest()

  override val inspection = MissingSemicolonInspection::class.java
}