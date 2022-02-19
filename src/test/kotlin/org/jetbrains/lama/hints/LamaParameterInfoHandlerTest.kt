package org.jetbrains.lama.hints

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.application.impl.NonBlockingReadActionImpl
import com.intellij.testFramework.fixtures.EditorHintFixture
import com.intellij.util.ui.UIUtil
import org.jetbrains.lama.LamaBaseTest
import org.jetbrains.lama.messages.LamaBundle
import org.junit.Test

class LamaParameterInfoHandlerTest : LamaBaseTest() {

  override fun setUp() {
    super.setUp()
    addStdlib()
  }

  @Test
  fun testEmptyArgumentList() = doTest("initArray(<caret>)", "<n>, f")

  @Test
  fun testComplexParameter() {
    doTest("addHashTab(<caret>)", "<ht@[a, compare, hash]>, k, v")
    doTest("addHashTab([a, v, d], <caret>)", "ht@[a, compare, hash], <k>, v")
    doTest("addHashTab([a, v<caret>, d], e)", "<ht@[a, compare, hash]>, k, v")
  }

  @Test
  fun testInnerInfo() = doTest("fwrite(randomString(<caret>))", "<len>")

  @Test
  fun testNoParameters() = doTest("emptyBuffer(<caret>)", noParametersMessage)

  @Test
  fun testStdUnitDots() {
    doTest("printf('%s', <caret>)", "fmt, <...>")
    doTest("printf('%d %d %d', 10, 20, 3<caret>0)", "fmt, <...>")
  }

  @Test
  fun testDisabled() = doTest("arrayList(10, 20, <caret>)", "a", isDisabled = true)

  fun doTest(text: String, vararg expectedResults: String, isDisabled: Boolean = false) {
    val hintFixture = EditorHintFixture(myFixture.testRootDisposable)
    myFixture.configureByText("lama.lama", text)
    showParameterInfo()
    waitForParameterInfo()

    val expectedVariants = wrap(*expectedResults, isDisabled = isDisabled).split('-').map { it.trim() }.sorted()
    val actualVariants = hintFixture.currentHintText?.split('-')?.map { it.trim() }?.sorted()
    assertEquals(expectedVariants, actualVariants)
  }

  private fun showParameterInfo() {
    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_SHOW_PARAMETER_INFO)
  }

  companion object {
    const val t = "\t"
    const val n = "\n"

    private val noParametersMessage = LamaBundle.message("parameter.info.no.arguments")
      .replace("<", "&lt;")
      .replace(">", "&gt;")

    private fun waitForParameterInfo() {
      // effective there is a chain of 3 nonBlockingRead actions
      for (i in 0..2) {
        UIUtil.dispatchAllInvocationEvents()
        NonBlockingReadActionImpl.waitForAsyncTaskCompletion()
      }
    }

    private fun wrap(vararg results: String, isDisabled: Boolean = false): String {
      return results.joinToString("\n-\n") { result ->
        val replacedQuote = result.replace("\"", "&quot;")
        val replacedHighlight = if (isDisabled) "<font color=a8a8a8>$replacedQuote</font>"
        else Regex("<(.+)>").replace(replacedQuote) { "<b>${it.groupValues[1]}</b>" }
        "<html>$replacedHighlight</html>"
      }
    }
  }
}