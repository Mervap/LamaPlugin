package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import org.jetbrains.lama.psi.api.LamaImportStatement

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

    if (PsiTreeUtil.getParentOfType(parameters.position, LamaImportStatement::class.java) != null) {
      for (module in MODULES) {
        result.addElement(
          LamaLookupElementFactory.createLookupElementWithGrouping(
            LamaLookupElement(module, true),
            { _, _ -> },
            GLOBAL_GROUPING
          )
        )
      }
    }
  }

  companion object {
    private val MODULES =
      arrayOf(
        "Array",
        "Collection",
        "Data1",
        "Lazy",
        "Matcher",
        "Random",
        "STM",
        "Buffer",
        "Data",
        "Fun",
        "List",
        "Ostap",
        "Ref",
        "Timer"
      )
  }
}

