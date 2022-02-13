package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.filters.AndFilter
import com.intellij.psi.filters.ElementFilter
import com.intellij.psi.filters.NotFilter
import com.intellij.psi.filters.position.FilterPattern
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import org.jetbrains.lama.psi.LamaLanguage
import org.jetbrains.lama.psi.api.LamaExpression
import org.jetbrains.lama.psi.api.LamaFile
import org.jetbrains.lama.psi.api.LamaIdentifierExpression

class LamaCompletionContributor : CompletionContributor() {
  init {
    addKeywordCompletion()
    addImportListCompletion()
    addIdentifierCompletion()
  }

  private fun addIdentifierCompletion() {
    extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement().withLanguage(LamaLanguage).and(LamaElementFilters.IDENTIFIER_FILTER),
      IdentifierCompletionProvider()
    )
  }

  private fun addKeywordCompletion() {
    extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement().withLanguage(LamaLanguage).and(LamaElementFilters.IDENTIFIER_FILTER),
      KeywordCompletionProvider()
    )
  }

  private fun addImportListCompletion() {
    extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement().withLanguage(LamaLanguage).and(LamaElementFilters.IMPORT_LIST_FILTER),
      ImportListCompletionProvider()
    )
  }
}

object LamaElementFilters {
  val IDENTIFIER_FILTER = FilterPattern(AndFilter(NotFilter(ImportListFilter(true)), IdentifierFilter()))
  val IMPORT_LIST_FILTER = FilterPattern(ImportListFilter(false))
}

private class IdentifierFilter : ElementFilter {
  override fun isAcceptable(element: Any?, context: PsiElement?): Boolean {
    val expression = PsiTreeUtil.getParentOfType(context, LamaExpression::class.java, false)

    return expression is LamaIdentifierExpression
  }

  override fun isClassAcceptable(hintClass: Class<*>?) = true
}

private class ImportListFilter(private val strict: Boolean) : ElementFilter {
  override fun isAcceptable(element: Any?, context: PsiElement?): Boolean {
    return context?.beforeEndOfImportList() ?: false
  }

  override fun isClassAcceptable(hintClass: Class<*>?) = true

  private fun PsiElement.beforeEndOfImportList(): Boolean {
    val scopeStartOffset = (containingFile as LamaFile).scope.startOffset
    val offset = if (strict) endOffset else startOffset
    return offset <= scopeStartOffset
  }
}
