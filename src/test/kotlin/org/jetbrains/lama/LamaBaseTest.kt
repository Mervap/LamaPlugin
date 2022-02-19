package org.jetbrains.lama

import com.intellij.codeInsight.lookup.Lookup
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.project.DumbServiceImpl
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.indexing.FileBasedIndex
import com.intellij.util.indexing.FileBasedIndexImpl
import com.intellij.util.indexing.UnindexedFilesUpdater
import org.jetbrains.lama.compiler.LamacLocation
import org.jetbrains.lama.compiler.LamacManager
import org.jetbrains.lama.util.ProjectRootUtil.invalidateProjectRoots
import java.io.File
import kotlin.test.assertContains

abstract class LamaBaseTest : BasePlatformTestCase() {
  override fun getTestDataPath(): String = "src/test/testData"

  fun addStdlib() {
    LamacManager.limaEnabled = SystemInfo.isMac
    val stdlibRoot = LamacLocation.stdlibSourcesRoot(myFixture.project)?.toFile() ?: error("Stdlib not found")
    var tryes = 0
    while (stdlibRoot.list()?.isNotEmpty() != true) {
      if (tryes > 100) {
        error("Stdlib not fetched")
      }
      Thread.sleep(30)
      ++tryes
    }

    VfsUtil.markDirtyAndRefresh(false, true, true, stdlibRoot)
    runWriteAction { myFixture.project.invalidateProjectRoots() }
  }

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