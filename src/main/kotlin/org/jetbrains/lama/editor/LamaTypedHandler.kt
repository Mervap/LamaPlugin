package org.jetbrains.lama.editor

import com.intellij.codeInsight.editorActions.TypedHandler
import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import org.jetbrains.lama.parser.LamaElementTypes

class LamaTypedHandler(originalHandler: TypedActionHandler?) : TypedActionHandlerBase(originalHandler) {
  override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {
    myOriginalHandler?.execute(editor, charTyped, dataContext)

    val project = CommonDataKeys.PROJECT.getData(dataContext) ?: return
    val (brace, braceStr) = when (charTyped) {
      'c' -> LamaElementTypes.LAMA_ESAC to "esac"
      'd' -> LamaElementTypes.LAMA_OD to "od"
      'i' -> LamaElementTypes.LAMA_FI to "fi"
      else -> return
    }

    val offset = editor.caretModel.offset
    if (offset == 0) return

    val iterator = editor.highlighter.createIterator(offset - 1)
    if (iterator.atEnd() || iterator.tokenType != brace) return


    editor.caretModel.moveCaretRelatively(-braceStr.length + 1)
    TypedHandler.indentBrace(project, editor, braceStr.first())
    editor.caretModel.moveCaretRelatively(braceStr.length - 1)
  }

  private fun CaretModel.moveCaretRelatively(relativeOffset: Int) {
    moveCaretRelatively(relativeOffset, 0, false, false, false)
  }
}