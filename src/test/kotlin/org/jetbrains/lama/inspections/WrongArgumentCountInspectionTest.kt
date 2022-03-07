package org.jetbrains.lama.inspections

import org.junit.Test

class WrongArgumentCountInspectionTest : LamaInspectionTest() {

  @Test fun testHighlightFewArgs() = doTest()
  @Test fun testHighlightALotArgs() = doTest()
  @Test fun testNotHighlightOkArgs() = doTest()
  @Test fun testNotHighlightVarargs() = doTest()

  override val inspection = WrongArgumentCountInspection::class.java
}