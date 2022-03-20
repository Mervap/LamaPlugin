package org.jetbrains.lama.editor.formatting

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import org.jetbrains.lama.LamaBaseTest
import org.jetbrains.lama.psi.LamaLanguage
import org.junit.Test
import java.io.File
import kotlin.reflect.KMutableProperty1

private typealias CommonSettings = CommonCodeStyleSettings
private typealias LamaSettings = LamaCodeStyleSettings

class LamaFormatterTest : LamaBaseTest() {

  @Test fun testSpaceAroundAssign() = doCommonOptionTest(CommonSettings::SPACE_AROUND_ASSIGNMENT_OPERATORS)
  @Test fun testSpaceAroundCompare() = doCommonOptionTest(CommonSettings::SPACE_AROUND_RELATIONAL_OPERATORS)
  @Test fun testSpaceAroundMulDiv() = doCommonOptionTest(CommonSettings::SPACE_AROUND_MULTIPLICATIVE_OPERATORS)
  @Test fun testSpaceAroundPlusMinus() = doCommonOptionTest(CommonSettings::SPACE_AROUND_ADDITIVE_OPERATORS)

  @Test fun testSpaceAroundAnd() = doCustomOptionTest(LamaSettings::SPACE_AROUND_CONJUNCTION_OPERATORS)
  @Test fun testSpaceAroundCustomOperator() = doCustomOptionTest(LamaSettings::SPACE_AROUND_INFIX_OPERATOR)
  @Test fun testSpaceAroundDot() = doCustomOptionTest(LamaSettings::SPACE_AROUND_DOT_OPERATOR)
  @Test fun testSpaceAroundListCons() = doCustomOptionTest(LamaSettings::SPACE_AROUND_LIST_CONS_OPERATOR)
  @Test fun testSpaceAroundOr() = doCustomOptionTest(LamaSettings::SPACE_AROUND_DISJUNCTION_OPERATORS)
  @Test fun testSpaceAroundSyntaxEq() = doCustomOptionTest(LamaSettings::SPACE_AROUND_BINDING_EQ)

  @Test fun testSpaceWithinArgumentList() = doCommonOptionTest(CommonSettings::SPACE_WITHIN_METHOD_CALL_PARENTHESES)
  @Test fun testSpaceWithinBraces() = doCommonOptionTest(CommonSettings::SPACE_WITHIN_BRACES)
  @Test fun testSpaceWithinBrackets() = doCommonOptionTest(CommonSettings::SPACE_WITHIN_BRACKETS)
  @Test fun testSpaceWithinEmptyArgumentList() = doCommonOptionTest(CommonSettings::SPACE_WITHIN_EMPTY_METHOD_CALL_PARENTHESES)
  @Test fun testSpaceWithinEmptyParameterList() = doCommonOptionTest(CommonSettings::SPACE_WITHIN_EMPTY_METHOD_PARENTHESES)
  @Test fun testSpaceWithinParameterList() = doCommonOptionTest(CommonSettings::SPACE_WITHIN_METHOD_PARENTHESES)
  @Test fun testSpaceWithinParenthesis() = doCommonOptionTest(CommonSettings::SPACE_WITHIN_PARENTHESES)

  @Test fun testSpaceBeforeArgumentList() = doCommonOptionTest(CommonSettings::SPACE_BEFORE_METHOD_CALL_PARENTHESES)
  @Test fun testSpaceBeforeComma() = doCommonOptionTest(CommonSettings::SPACE_BEFORE_COMMA)
  @Test fun testSpaceBeforeParameterList() = doCommonOptionTest(CommonSettings::SPACE_BEFORE_METHOD_PARENTHESES)

  @Test fun testSpaceAfterComma() = doCommonOptionTest(CommonSettings::SPACE_AFTER_COMMA)

  @Test fun testAlignSyntaxOr() = doCustomOptionTest(LamaSettings::ALIGN_SYNTAX_OR)
  @Test fun testAlignCaseArrow() = doCustomOptionTest(LamaSettings::ALIGN_CASE_ARROW)
  @Test fun testAlignCaseBranch() = doCustomOptionTest(LamaSettings::ALIGN_CASE_BRANCH)

  @Test fun testScopes() = doTest()
  @Test fun testSyntax() = doTest()

  private fun doCommonOptionTest(option: KMutableProperty1<CommonSettings, Boolean>) {
    return doOptionTest { option.set(getCommonSettings(LamaLanguage), it) }
  }

  private fun doCustomOptionTest(option: KMutableProperty1<LamaSettings, Boolean>) {
    return doOptionTest { option.set(lamaSettings(), it) }
  }

  private fun doOptionTest(configure: CodeStyleSettings.(Boolean) -> Unit = {}) {
    val fileName = getTestName(true)
    val (disabled, enabled) = File("$testDataPath/editor/formatter/$fileName.lama").readText().split(DELIMITER)

    check(enabled, enabled) { configure(true) }
    check(disabled, enabled) { configure(true) }

    check(disabled, disabled) { configure(false) }
    check(enabled, disabled) { configure(false) }
  }

  private fun doTest() {
    val fileName = getTestName(true)
    val (before, after) = File("$testDataPath/editor/formatter/$fileName.lama").readText().split(DELIMITER)
    check(before, after) {}
  }

  private fun check(before: String, after: String, configure: CodeStyleSettings.() -> Unit) {
    try {
      myFixture.configureByText("lama.lama", before.trimIndent())
      val file = myFixture.file

      val manager = CodeStyleSettingsManager.getInstance(project)
      val settings = manager.cloneSettings(CodeStyle.getSettings(file))
      manager.setTemporarySettings(settings)
      configure(settings)

      WriteCommandAction.runWriteCommandAction(project) {
        val textRange = file.textRange
        CodeStyleManager.getInstance(project).reformatText(file, textRange.startOffset, textRange.endOffset)
      }

      myFixture.checkResult(after.trimIndent())
    }
    finally {
      CodeStyleSettingsManager.getInstance(project).dropTemporarySettings()
    }
  }
}