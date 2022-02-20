package org.jetbrains.lama.inspections

import org.junit.Test

class MissingSemicolonInspectionTest : LamaInspectionTest() {

  @Test fun testHighlightExpressions() = doTest()
  @Test fun testHighlightImport() = doTest()
  @Test fun testHighlightVariable() = doTest()
  @Test fun testNoInspectionsAfterLastExpression() = doTest()

  @Test fun testImportVariablesExpressions() = doReplacementTest()
  @Test fun testSeveralVariables() = doReplacementTest()
  @Test fun testVariable() = doReplacementTest()

  override val inspection = MissingSemicolonInspection::class.java
}