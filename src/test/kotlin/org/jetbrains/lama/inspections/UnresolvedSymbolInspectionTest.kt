package org.jetbrains.lama.inspections

import org.junit.Test

class UnresolvedSymbolInspectionTest : LamaInspectionTest() {

  @Test fun testHighlightUnknown() = doTest()
  @Test fun testHighlightNotImported() = doTest()
  @Test fun testNotHighlightPatternsSExpr() = doTest()
  @Test fun testNotImported() = doReplacementTest()

  override val inspection = UnresolvedSymbolInspection::class.java
}