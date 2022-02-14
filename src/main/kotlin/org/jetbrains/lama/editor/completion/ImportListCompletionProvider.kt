package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import org.jetbrains.lama.psi.LamaPsiUtil.importedUnits
import org.jetbrains.lama.psi.api.LamaFile
import org.jetbrains.lama.psi.api.LamaImportStatement
import org.jetbrains.lama.psi.stubs.indices.LamaUnitsIndex

class ImportListCompletionProvider : CompletionProvider<CompletionParameters>() {
  override fun addCompletions(
    parameters: CompletionParameters,
    context: ProcessingContext,
    result: CompletionResultSet,
  ) {
    if (PsiTreeUtil.getParentOfType(parameters.position, LamaImportStatement::class.java) != null) {
      for (unit in STDLIB_UNITS) {
        result.addElement(LamaLookupElementFactory.createUnitLookupElement(unit))
      }
      completeUnitsFromIndex(parameters.position.containingFile as LamaFile, result)
    }
    else {
      result.addElement(
        LamaLookupElementFactory.createLookupElementWithGrouping(
          LamaLookupElement("import", true),
          { _, _ -> },
          GLOBAL_GROUPING
        )
      )
    }
  }

  private fun completeUnitsFromIndex(containingFile: LamaFile, result: CompletionResultSet) {
    val imported = containingFile.importedUnits
    val variants = LamaUnitsIndex.findAllUnitNames(containingFile.project)
    for (variant in variants) {
      if (variant !in STDLIB_UNITS && variant !in imported) {
        result.addElement(LamaLookupElementFactory.createUnitLookupElement(variant))
      }
    }
  }

  companion object {
    private val STDLIB_UNITS =
      arrayOf(
        "Array",
        "Collection",
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

