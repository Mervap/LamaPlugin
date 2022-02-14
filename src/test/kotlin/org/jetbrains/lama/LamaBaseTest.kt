package org.jetbrains.lama

import com.intellij.codeInsight.lookup.Lookup
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import kotlin.test.assertContains

abstract class LamaBaseTest : BasePlatformTestCase() {
  override fun getTestDataPath(): String = "src/test/testData"

  protected fun doWrongCompletionVariantsTest(vararg variants: String, configure: () -> Unit) {
    configure()
    val result = myFixture.completeBasic()
    assertNotNull(result)
    val lookupStrings = result.map { it.lookupString }
    UsefulTestCase.assertDoesntContain(lookupStrings, *variants)
  }

  protected fun doCompletionTest(vararg variants: String, strict: Boolean = true, configure: () -> Unit) {
    configure()
    val result = myFixture.completeBasic()
    assertNotNull(result)
    val lookupStrings = result.map { it.lookupString }
    if (strict) assertOrderedEquals(lookupStrings, *variants)
    else assertContainsOrdered(lookupStrings, *variants)
  }

  protected fun doApplyCompletionTest(expected: String, lookupString: String, configure: () -> Unit) {
    configure()
    val result = myFixture.completeBasic()
    assertNotNull(result)
    assertContains(result.map { it.lookupString }, lookupString)
    val element = result.first { it.lookupString == lookupString }

    myFixture.lookup.currentItem = element
    myFixture.finishLookup(Lookup.NORMAL_SELECT_CHAR)

    val caretPosition = myFixture.caretOffset
    val newText = myFixture.file.text
    assertEquals(expected, "${newText.substring(0, caretPosition)}<caret>${newText.substring(caretPosition)}")
  }

  companion object {
    const val DELIMITER = "\n-- DELIMITER --\n"
  }
}