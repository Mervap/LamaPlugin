package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.*
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.refactoring.suggested.startOffset
import com.intellij.util.ProcessingContext
import org.jetbrains.lama.parser.LamaElementTypes.*
import org.jetbrains.lama.psi.api.LamaFile

class ImportListCompletionProvider : CompletionProvider<CompletionParameters>() {
  override fun addCompletions(
    parameters: CompletionParameters,
    context: ProcessingContext,
    result: CompletionResultSet,
  ) {
    result.addElement(
      LamaLookupElementFactory.createLookupElementWithGrouping(
        LamaLookupElement("import", true),
        { _, _ -> },
        GLOBAL_GROUPING
      )
    )

    // todo: modules completion
  }
}