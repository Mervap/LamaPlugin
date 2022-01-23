package org.jetbrains.lama.editor.formatting

import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.psi.formatter.DocumentBasedFormattingModel

class LamaFormattingModelBuilder : FormattingModelBuilder {
  override fun createModel(formattingContext: FormattingContext): FormattingModel {
    val containingFile = formattingContext.containingFile
    val astNode = formattingContext.node
    val settings = formattingContext.codeStyleSettings

    val root = LamaFormatterBlock(LamaFormattingContext(settings), astNode)
    return DocumentBasedFormattingModel(root, settings, containingFile)
  }

  override fun getRangeAffectingIndent(file: PsiFile, offset: Int, elementAtOffset: ASTNode): TextRange? {
    return null
  }
}
