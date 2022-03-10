package org.jetbrains.lama.inspections

import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.command.CommandProcessor
import org.jetbrains.lama.LamaBaseTest
import org.jetbrains.lama.psi.LamaFileType
import java.io.File
import java.nio.file.Path
import java.util.*

abstract class LamaInspectionTest : LamaBaseTest() {

  internal abstract val inspection: Class<out LamaInspection>

  override fun getTestDataPath(): String =
    Path.of(super.getTestDataPath(), "inspections", javaClass.toDirectory()).toString()

  private fun doTestBase(
    filename: String,
    checkWarnings: Boolean = false,
    checkInfos: Boolean = false,
    checkWeakWarnings: Boolean = false,
  ) {
    myFixture.configureByFile(filename)
    myFixture.enableInspections(inspection)
    myFixture.testHighlighting(checkWarnings, checkInfos, checkWeakWarnings, filename)
  }

  protected fun doTest(filename: String = testFilename) {
    doTestBase(filename, checkWarnings = true)
  }

  protected fun doWeakTest(filename: String = testFilename) {
    doTestBase(filename, checkWarnings = true, checkWeakWarnings = true)
  }

  private val testFilename: String
    get() = getTestName(true) + ".${LamaFileType.defaultExtension}"

  protected fun doReplacementTest() {
    val (before, after) = File("$testDataPath/$testFilename").readText().split(DELIMITER)
    myFixture.configureByText("lama.lama", before)
    myFixture.enableInspections(inspection)
    val highlightingInfo = myFixture.doHighlighting(HighlightSeverity.WEAK_WARNING)

    if (highlightingInfo.isEmpty()) {
      if (after.isNotBlank()) {
        fail("No code highlighting")
      }
    }
    else {
      if (after.isBlank()) {
        fail("Founded highlighted code: ${highlightingInfo.joinToString("\n") { it.text }}")
      }

      val quickFixes = highlightingInfo.mapNotNull { it.quickFixActionMarkers?.firstOrNull()?.first?.action }
      runWriteAction {
        CommandProcessor.getInstance().executeCommand(
          project,
          { quickFixes.forEach { it.invoke(project, myFixture.editor, myFixture.file) } },
          "", null
        )
      }

      assertEquals(after, myFixture.file.text)
    }
  }

  private fun Class<*>.toDirectory() =
    simpleName.replace("Test", "").replaceFirstChar { it.lowercase(Locale.getDefault()) }
}
