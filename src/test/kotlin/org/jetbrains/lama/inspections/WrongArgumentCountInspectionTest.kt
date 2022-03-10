package org.jetbrains.lama.inspections

import org.junit.Test

class WrongArgumentCountInspectionTest : LamaInspectionTest() {

  @Test fun testHighlightFewArgs() = doWeakTest()
  @Test fun testHighlightALotArgs() = doWeakTest()
  @Test fun testNotHighlightOkArgs() = doWeakTest()
  @Test fun testNotHighlightVarargs() = doWeakTest()
  @Test fun testNotHighlightDot() = doWeakTest()

  override val inspection = WrongArgumentCountInspection::class.java
}