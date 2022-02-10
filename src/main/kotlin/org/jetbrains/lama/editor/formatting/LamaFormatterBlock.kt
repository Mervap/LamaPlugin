package org.jetbrains.lama.editor.formatting

import com.intellij.formatting.Block
import com.intellij.formatting.BlockEx
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.psi.formatter.FormatterUtil.isWhitespaceOrEmpty
import com.intellij.psi.formatter.common.AbstractBlock
import org.jetbrains.lama.psi.LamaLanguage

class LamaFormatterBlock(private val context: LamaFormattingContext, node: ASTNode) :
  AbstractBlock(node, context.computeWrap(node), context.computeAlignment(node)), BlockEx {

  override fun buildChildren(): List<LamaFormatterBlock> =
    node.getChildren(null).mapNotNull {
      if (isWhitespaceOrEmpty(it)) null
      else LamaFormatterBlock(context, it)
    }

  override fun getIndent(): Indent? = context.computeBlockIndent(node)

  override fun getSpacing(child1: Block?, child2: Block): Spacing? = context.computeSpacing(this, child1, child2)

  override fun getChildIndent(): Indent = context.computeNewChildIndent(node)

//  override fun isIncomplete(): Boolean = context.isIncomplete(node)

  override fun isLeaf(): Boolean = node.firstChildNode == null

  override fun getLanguage(): Language = LamaLanguage
}