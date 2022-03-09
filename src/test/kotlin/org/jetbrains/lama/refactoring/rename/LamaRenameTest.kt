package org.jetbrains.lama.refactoring.rename

import com.intellij.codeInsight.TargetElementUtil
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.impl.SimpleDataContext
import com.intellij.testFramework.fixtures.CodeInsightTestUtil
import org.jetbrains.lama.LamaBaseTest
import org.junit.Test
import java.io.File
import java.nio.file.Path

class LamaRenameTest : LamaBaseTest() {

  override fun getTestDataPath(): String = Path.of(super.getTestDataPath(), "refactoring", "rename").toString()

  override fun setUp() {
    super.setUp()
    addStdlib()
  }

  @Test fun testVar() = doTest("hello")
  @Test fun testVarDefinition() = doTest("hello")
  @Test fun testFun() = doTest("baz")
  @Test fun testFunDefinition() = doTest("baz")
  @Test fun testInfix() = doTest("++++")
  @Test fun testInfixDefinition() = doTest("++++")
  @Test fun testFor() = doTest("j")
  @Test fun testParameters() = doTest("laba")
  @Test fun testParametersAt() = doTest("yyy")
  @Test fun testCase() = doTest("zzz")

  private fun doTest(newName: String) {
    val testFilename = getTestName(true) + ".lama"
    val (before, after) = File("$testDataPath/$testFilename").readText().split(DELIMITER)
    myFixture.configureByText(testFilename, before)

    val memberHandler = LamaMemberInplaceRenameHandler()
    val element = TargetElementUtil.findTargetElement(myFixture.editor, TargetElementUtil.getInstance().allAccepted)!!

    assertNotNull(element)
    val dataContext = SimpleDataContext.getSimpleContext(CommonDataKeys.PSI_ELEMENT, element, createDataContext())
    val handler = when {
      memberHandler.isRenaming(dataContext) -> memberHandler
      else -> {
        assertTrue("In-place rename not allowed for $element", false)
        return
      }
    }

    CodeInsightTestUtil.doInlineRename(handler, newName, myFixture)
    myFixture.checkResult(after, true)
  }
}
