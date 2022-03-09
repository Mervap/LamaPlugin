package org.jetbrains.lama.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.lama.LamaMultiFileTest
import org.jetbrains.lama.psi.LamaLanguage
import org.jetbrains.lama.psi.api.LamaDefinition
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition
import org.junit.Test

class LamaMultiFileResolveTest : LamaMultiFileTest("resolve") {

  @Test fun testGlobalUser() = doTest()
  @Test fun testImported() = doTest()
  @Test fun testImportedConflict() = doTest()
  @Test fun testImportedInfix() = doTest()
  @Test fun testImportedInfixConflict() = doTest()

  private fun doTest() {
    val expected = getExpectedResult()
    val actual = getActualResults()
    assertEquals(expected, actual)
  }

  private fun getActualResults(): Set<PsiElementWrapper> {
    return resolve().mapNotNull { PsiElementWrapper(it.element ?: return@mapNotNull null) }.toSet()
  }

  private fun getExpectedResult(): Set<PsiElementWrapper> {
    return collectExpectedResult(configureFiles()).map { PsiElementWrapper(it) }.toSet()
  }

  private fun collectExpectedResult(files: List<PsiFile>): List<PsiElement> {
    return files.flatMap { file ->
      val text = file.viewProvider.getPsi(LamaLanguage)?.text ?: return@flatMap emptyList<PsiElement>()
      Regex(KEY_COMMENT).findAll(text).map { it.range.first }.mapNotNull {
        val comment = file.findElementAt(it)
        PsiTreeUtil.getPrevSiblingOfType(comment, LamaDefinition::class.java) ?:
        PsiTreeUtil.getParentOfType(comment, LamaDefinition::class.java)
      }.toList()
    }
  }

  private class PsiElementWrapper(val elem: PsiElement) {
    override fun equals(other: Any?): Boolean {
      if (other !is PsiElementWrapper) return false
      return elem == other.elem
    }

    override fun hashCode(): Int {
      return elem.hashCode()
    }

    override fun toString(): String {
      return "${(elem as? PsiNamedElement)?.name ?: elem.text} from `${elem.containingFile.name}`"
    }
  }

  companion object {
    private const val KEY_COMMENT = "-- this"
  }
}
