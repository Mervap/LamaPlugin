package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.icons.AllIcons
import org.jetbrains.lama.psi.api.LamaFunctionDefinition
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition
import org.jetbrains.lama.psi.api.LamaVariableDefinition
import javax.swing.Icon

const val GLOBAL_GROUPING = 0

data class LamaLookupElement(
  val lookup: String,
  private val bold: Boolean,
  private val icon: Icon? = null,
  private val moduleName: String? = null,
  private val tailText: String? = null,
  private val itemText: String = lookup,
) : LookupElement() {
  override fun getLookupString() = lookup

  override fun renderElement(presentation: LookupElementPresentation) {
    presentation.itemText = itemText
    presentation.isItemTextBold = bold
    presentation.icon = icon
    presentation.typeText = moduleName
    if (tailText != null) presentation.appendTailText(tailText, true)
  }
}

object LamaLookupElementFactory {
  fun createGlobalVariableLookupElement(variable: LamaVariableDefinition): LookupElement {
    return createVariableLookupElement(variable, GLOBAL_GROUPING)
  }

  fun createGlobalFunctionLookupElement(function: LamaFunctionDefinition): LookupElement {
    return createFunctionLookupElement(function, GLOBAL_GROUPING)
  }

  fun createGlobalInfixLookupElement(infix: LamaInfixOperatorDefinition): LookupElement {
    return createInfixLookupElement(infix, GLOBAL_GROUPING)
  }

  private fun createVariableLookupElement(variable: LamaVariableDefinition, grouping: Int): LookupElement {
    val name = variable.name
    val icon = AllIcons.Nodes.Variable
    return createLookupElementWithGrouping(
      LamaLookupElement(name, false, icon, variable.containingFile.name),
      { _, _ ->  }, grouping
    )
  }

  private fun createFunctionLookupElement(function: LamaFunctionDefinition, grouping: Int): LookupElement {
    val icon = AllIcons.Nodes.Function
    return createLookupElementWithGrouping(
      LamaLookupElement(function.name, false, icon, function.containingFile.name, function.parameters),
      getInsertHandlerForFunctionCall(function.parameters), grouping
    )
  }

  private fun createInfixLookupElement(infix: LamaInfixOperatorDefinition, grouping: Int): LookupElement {
    val icon = AllIcons.Nodes.AnonymousClass
    return createLookupElementWithGrouping(
      LamaLookupElement(infix.name, false, icon, infix.containingFile.name, infix.parameters),
      { _, _ ->  }, grouping
    )
  }

  private fun getInsertHandlerForFunctionCall(parameterList: String): InsertHandler<LookupElement> {
    val noArgs = parameterList == "()"
    return InsertHandler { context, _ ->
      val document = context.document
      val parenthesesRelativeOffset = findParentheses(document.text, context.tailOffset)
      if (parenthesesRelativeOffset == null) {
        document.insertString(context.tailOffset, "()")
      }
      val relativeCaretOffset = (if (noArgs) 2 else 1) + (parenthesesRelativeOffset ?: 0)
      context.editor.caretModel.moveCaretRelatively(relativeCaretOffset, 0, false, false, false)
    }
  }

  private fun findParentheses(text: String, offset: Int): Int? {
    var currentOffset = offset
    while (currentOffset < text.length && text[currentOffset].isWhitespace()) {
      ++currentOffset
    }
    if (currentOffset < text.length && text[currentOffset] == '(') {
      return currentOffset - offset
    }
    return null
  }

  fun createLookupElementWithGrouping(
    lookupElement: LookupElement,
    insertHandler: InsertHandler<LookupElement>,
    grouping: Int,
  ): LookupElement {
    val lookupElementWithInsertHandler = PrioritizedLookupElement.withInsertHandler(lookupElement, insertHandler)
    return PrioritizedLookupElement.withGrouping(lookupElementWithInsertHandler, grouping)
  }
}