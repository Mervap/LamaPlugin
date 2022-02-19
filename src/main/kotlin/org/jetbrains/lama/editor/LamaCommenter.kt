package org.jetbrains.lama.editor

import com.intellij.codeInsight.generation.IndentedCommenter
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.psi.PsiComment
import com.intellij.psi.tree.IElementType
import org.jetbrains.lama.parser.LamaParserDefinition

class LamaCommenter : CodeDocumentationAwareCommenter, IndentedCommenter {
  override fun getLineCommentPrefix(): String = "-- "

  override fun getBlockCommentPrefix(): String = "(*"

  override fun getBlockCommentSuffix(): String = "*)"

  override fun getCommentedBlockCommentPrefix(): String = "(*"

  override fun getCommentedBlockCommentSuffix(): String = "*)"

  override fun getLineCommentTokenType(): IElementType = LamaParserDefinition.END_OF_LINE_COMMENT

  override fun getBlockCommentTokenType(): IElementType = LamaParserDefinition.BLOCK_COMMENT

  override fun getDocumentationCommentTokenType(): IElementType? = null

  override fun getDocumentationCommentPrefix(): String? = null

  override fun getDocumentationCommentLinePrefix(): String? = null

  override fun getDocumentationCommentSuffix(): String? = null

  override fun isDocumentationComment(element: PsiComment): Boolean = false

  override fun forceIndentedLineComment(): Boolean = false
}