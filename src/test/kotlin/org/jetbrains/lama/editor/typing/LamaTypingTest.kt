package org.jetbrains.lama.editor.typing

import com.intellij.application.options.CodeStyle
import com.intellij.codeInsight.highlighting.BraceMatchingUtil
import com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter
import com.intellij.lang.LanguageBraceMatching
import org.jetbrains.lama.LamaBaseTest
import org.jetbrains.lama.editor.LamaBraceMatcher
import org.jetbrains.lama.psi.LamaFileType
import org.jetbrains.lama.psi.LamaLanguage
import org.junit.Test
import java.io.File

class LamaTypingTest : LamaBaseTest() {

  override fun setUp() {
    super.setUp()
    LanguageBraceMatching.INSTANCE.addExplicitExtension(LamaLanguage, LamaBraceMatcher()) // Otherwise
  }

  override fun getTestDataPath(): String = super.getTestDataPath() + "/typing"

  @Test fun testCase() = doNlTest()
  @Test fun testCaseBegin() = doNlTest()
  @Test fun testCaseNoScopes() = doNlTest()
  @Test fun testCaseNoScopesFirst() = doNlTest()
  @Test fun testCaseNoScopesLast() = doNlTest()
  @Test fun testEmptyFunctionBody() = doNlTest()
  @Test fun testFileScope() = doNlTest()
  @Test fun testFileScopeBefore() = doNlTest()
  @Test fun testFileScopeEnd() = doNlTest()
  @Test fun testFor() = doNlTest()
  @Test fun testForBegin() = doNlTest()
  @Test fun testForEnd() = doNlTest()
  @Test fun testFunctionBody() = doNlTest()
  @Test fun testFunctionBodyBegin() = doNlTest()
  @Test fun testIfElif() = doNlTest()
  @Test fun testIfElse() = doNlTest()
  @Test fun testIfNoScopes() = doNlTest()
  @Test fun testIfNoScopesFirst() = doNlTest()
  @Test fun testIfNoScopesLast() = doNlTest()
  @Test fun testIfThen() = doNlTest()
  @Test fun testImport() = doNlTest()
  @Test fun testIncompleteExpression() = doNlTest()
  @Test fun testParenthesis() = doNlTest()
  @Test fun testParenthesisBegin() = doNlTest()
  @Test fun testParenthesisEnd() = doNlTest()

  @Test fun testSingleQuote() = doTest()
  @Test fun testDoubleQuote() = doTest()

  @Test
  fun testFunctionBodyLBracket() = doTest {
    while (BraceMatchingUtil.getBraceMatcher(LamaFileType, LamaLanguage) !is PairedBraceMatcherAdapter) {
      Thread.sleep(30) // Wtf, I don't know why this test flaky
    }
  }

  private fun doNlTest() {
    val (before, after) = testData
    doTest(before, after) { myFixture.type('\n') }
  }

  private fun doTest(wait: () -> Unit = {}) {
    val (insert, before, after) = testData
    wait()
    doTest(before, after) { myFixture.type(insert) }
  }

  private fun doTest(before: String, after: String, action: () -> Unit) {
    myFixture.configureByText("lama.lama", before)
    CodeStyle.doWithTemporarySettings(project, CodeStyle.getSettings(myFixture.file)) { settings ->
      val indentOptions = settings.getCommonSettings(LamaLanguage).indentOptions
      indentOptions?.CONTINUATION_INDENT_SIZE = 4
      indentOptions?.INDENT_SIZE = 2
      action()
    }
    myFixture.checkResult(after)
  }

  private val testData: List<String>
    get() {
      return File("$testDataPath/${getTestName(true)}.lama").readText().split(DELIMITER)
    }
}
