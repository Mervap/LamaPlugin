package org.jetbrains.lama.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType
import org.jetbrains.lama.lexer.LamaLexer
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.parser.LamaParserDefinition
import org.jetbrains.lama.psi.LamaPsiUtil

class LamaSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
  override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
    return LamaHighlighter()
  }
}

class LamaHighlighter : SyntaxHighlighterBase() {

  override fun getHighlightingLexer(): Lexer = LamaLexer()

  override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
    return pack(ATTRIBUTES[tokenType])
  }

  companion object {
    private val ATTRIBUTES = HashMap<IElementType, TextAttributesKey>()

    init {
      fillMap(ATTRIBUTES, LamaPsiUtil.RESERVED_WORDS, KEYWORD)

      ATTRIBUTES[LamaElementTypes.LAMA_STRING] = STRING
      ATTRIBUTES[LamaElementTypes.LAMA_CHAR] = STRING
      ATTRIBUTES[LamaElementTypes.LAMA_NUMBER] = NUMBER

      ATTRIBUTES[LamaElementTypes.LAMA_LPAR] = PARENTHESES
      ATTRIBUTES[LamaElementTypes.LAMA_RPAR] = PARENTHESES

      ATTRIBUTES[LamaElementTypes.LAMA_LBRACE] = BRACES
      ATTRIBUTES[LamaElementTypes.LAMA_RBRACE] = BRACES

      ATTRIBUTES[LamaElementTypes.LAMA_COMMA] = COMMA
      ATTRIBUTES[LamaElementTypes.LAMA_SEMI] = SEMICOLON

      ATTRIBUTES[LamaParserDefinition.END_OF_LINE_COMMENT] = COMMENT
      ATTRIBUTES[LamaParserDefinition.BLOCK_COMMENT] = COMMENT
    }
  }
}
