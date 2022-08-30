package org.jetbrains.lama.editor.formatting

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.psi.formatter.FormatterUtil.isWhitespaceOrEmpty
import com.intellij.psi.formatter.common.AbstractBlock
import org.jetbrains.lama.parser.LamaElementTypes.LAMA_CASE_BRANCH
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

  override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
    val indent = context.computeNewChildIndent(node, subBlocks.size == newChildIndex)
    val alignment = if (node.elementType == LAMA_CASE_BRANCH) null else getFirstChildAlignment()
    return ChildAttributes(indent, alignment)
  }

  override fun isIncomplete(): Boolean {
    if (context.isIncomplete(node)) return true
    return super.isIncomplete()
  }

  override fun isLeaf(): Boolean = node.firstChildNode == null

  override fun getLanguage(): Language = LamaLanguage

  private fun getFirstChildAlignment(): Alignment? {
    for (subBlock in subBlocks) {
      subBlock.alignment?.let { return it }
    }
    return null
  }
}
