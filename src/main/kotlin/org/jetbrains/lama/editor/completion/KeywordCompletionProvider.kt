package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import com.intellij.util.ProcessingContext
import org.jetbrains.lama.parser.LamaElementTypes.*
import org.jetbrains.lama.psi.api.LamaScope

class KeywordCompletionProvider : CompletionProvider<CompletionParameters>() {

  override fun addCompletions(
    parameters: CompletionParameters,
    context: ProcessingContext,
    result: CompletionResultSet,
  ) {
    val position = parameters.position
    for (keyword in KEYWORDS) {
      if (keyword in KEYWORD_NECESSARY_CONDITION) {
        if (!KEYWORD_NECESSARY_CONDITION[keyword]!!(position)) {
          continue
        }
      }
      addElement(keyword, result)
    }
  }

  private fun addElement(keyword: IElementType, result: CompletionResultSet) {
    val stringValue = keyword.toString()
    result.addElement(
      LamaLookupElementFactory.createLookupElementWithGrouping(
        LamaLookupElement(stringValue, true),
        { _, _ -> },
        GLOBAL_GROUPING
      )
    )
  }

  companion object {
    private val KEYWORDS = listOf(
      LAMA_FALSE, LAMA_TRUE, LAMA_SKIP, LAMA_SYNTAX, LAMA_INFIX, LAMA_ETA, LAMA_LAZY,
      LAMA_PUBLIC, LAMA_INFIXL, LAMA_INFIXR, LAMA_AT, LAMA_BEFORE, LAMA_AFTER,
      LAMA_FUN, LAMA_VAR,
      LAMA_VAL, LAMA_BOX, LAMA_VAL, LAMA_STR, LAMA_ARRAY, LAMA_SEXP,
      LAMA_IF, LAMA_THEN, LAMA_ELSE, LAMA_ELIF, LAMA_FI,
      LAMA_WHILE, LAMA_DO, LAMA_OD, LAMA_FOR,
      LAMA_CASE, LAMA_ESAC, LAMA_OF
    )
    private val KEYWORD_NECESSARY_CONDITION = mapOf<IElementType, (PsiElement) -> Boolean>(
      LAMA_PUBLIC to ::beforeEndOfDefinitionList,
      LAMA_INFIXL to ::beforeEndOfDefinitionList,
      LAMA_INFIXR to ::beforeEndOfDefinitionList,
      LAMA_AT to ::beforeEndOfDefinitionList,
      LAMA_BEFORE to ::beforeEndOfDefinitionList,
      LAMA_AFTER to ::beforeEndOfDefinitionList,
      LAMA_VAR to ::beforeEndOfDefinitionList,
      LAMA_FUN to ::beforeEndOfDefinitionList,
    )

    private fun beforeEndOfDefinitionList(psi: PsiElement): Boolean {
      val parentScope = PsiTreeUtil.getParentOfType(psi, LamaScope::class.java) ?: return true
      val firstExpression = parentScope.expressionSeries?.expressionList?.firstOrNull() ?: return true
      val firstLeaf = PsiTreeUtil.getDeepestVisibleFirst(firstExpression) ?: return true
      return psi.startOffset <= firstLeaf.endOffset
    }
  }
}