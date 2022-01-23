package org.jetbrains.lama.editor

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import org.jetbrains.lama.parser.LamaElementTypes

private val bracePairs = arrayOf(
  BracePair(LamaElementTypes.LAMA_LPAR, LamaElementTypes.LAMA_RPAR, false),
  BracePair(LamaElementTypes.LAMA_LBRACKET, LamaElementTypes.LAMA_RBRACKET, false),
  BracePair(LamaElementTypes.LAMA_LBRACE, LamaElementTypes.LAMA_RBRACE, false),
  BracePair(LamaElementTypes.LAMA_CASE, LamaElementTypes.LAMA_ESAC, false),
  BracePair(LamaElementTypes.LAMA_IF, LamaElementTypes.LAMA_FI, false),
  BracePair(LamaElementTypes.LAMA_DO, LamaElementTypes.LAMA_OD, false)
)

class LamaBraceMatcher : PairedBraceMatcher {
  override fun getPairs(): Array<BracePair> = bracePairs

  override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean = true

  override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int = openingBraceOffset
}