package org.jetbrains.lama

import com.intellij.codeInsight.lookup.Lookup
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.ResolveResult
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.lama.compiler.LamacLocation
import org.jetbrains.lama.util.LamaStdUnitUtil
import org.jetbrains.lama.util.ProjectRootUtil.invalidateProjectRoots
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.test.assertContains

abstract class LamaBaseTest : BasePlatformTestCase() {
  override fun getTestDataPath(): String = "src/test/testData"

  fun addStdlib() {
    val stdlibRoot = LamacLocation.stdlibSourcesRoot(myFixture.project) ?: error("Stdlib not found")
    var tryes = 0
    while (stdlibRoot.listDirectoryEntries().map { it.name }.sorted() != listOf("Lama", LamaStdUnitUtil.UNIT_NAME_WITH_EXT)) {
      if (tryes > 100) {
        error("Stdlib not fetched")
      }
      Thread.sleep(100)
      ++tryes
    }

    VfsUtil.markDirtyAndRefresh(false, true, true, stdlibRoot.toFile())
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

  protected fun resolve(): Array<ResolveResult> {
    val reference = myFixture.file.findReferenceAt(myFixture.caretOffset) ?: return emptyArray()
    return if (reference is PsiPolyVariantReference) {
      reference.multiResolve(false)
    }
    else {
      val result = reference.resolve() ?: return emptyArray()
      arrayOf(PsiElementResolveResult(result))
    }
  }

  protected open fun createDataContext(): DataContext {
    return DataContext {
      when (it) {
        CommonDataKeys.PROJECT.name -> myFixture.project
        CommonDataKeys.EDITOR.name -> myFixture.editor
        CommonDataKeys.PSI_FILE.name -> myFixture.file
        CommonDataKeys.VIRTUAL_FILE.name -> myFixture.file.virtualFile
        else -> null
      }
    }
  }


  companion object {
    const val DELIMITER = "\n-- DELIMITER --\n"
  }
}