package org.jetbrains.lama.editor

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import org.jetbrains.lama.parser.LamaElementTypes.LAMA_CHAR
import org.jetbrains.lama.parser.LamaElementTypes.LAMA_STRING
import org.jetbrains.lama.parser.LamaParserDefinition.Companion.BAD_CHARACTER

class LamaQuoteHandler : SimpleTokenSetQuoteHandler(LAMA_STRING, LAMA_CHAR, BAD_CHARACTER) {
  override fun hasNonClosedLiteral(editor: Editor, iterator: HighlighterIterator, offset: Int): Boolean {
    val document = editor.document
    val lineEnd = document.getLineEndOffset(document.getLineNumber(offset))
    if (iterator.end > lineEnd) {
      return true
    }
    return super.hasNonClosedLiteral(editor, iterator, offset)
  }
}