package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.icons.AllIcons
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.lama.psi.api.*
import javax.swing.Icon

const val LOCAL_GROUPING = 50
const val IMPORTS_GROUPING = 25
const val GLOBAL_GROUPING = 0

data class LamaLookupElement(
  val lookup: String,
  private val bold: Boolean,
  private val icon: Icon? = null,
  private val postline: String? = null,
  private val tailText: String? = null,
  private val itemText: String = lookup,
) : LookupElement() {
  override fun getLookupString() = lookup

  override fun renderElement(presentation: LookupElementPresentation) {
    presentation.itemText = itemText
    presentation.isItemTextBold = bold
    presentation.icon = icon
    presentation.typeText = postline
    if (tailText != null) presentation.appendTailText(tailText, true)
  }
}

open class LamaLookupElementFactory(private val isDotBefore: Boolean) {

  @Suppress("unused")
  fun createGlobalVariableLookupElement(variable: LamaVariableDefinition): LookupElement {
    return createVariableLookupElement(variable, GLOBAL_GROUPING)
  }

  fun createLocalVariableLookupElement(variable: LamaVariableDefinition): LookupElement {
    return createVariableLookupElement(variable, LOCAL_GROUPING)
  }

  fun createLocalVariableLookupElement(name: String): LookupElement {
    return createVariableLookupElement(name, null, LOCAL_GROUPING)
  }

  @Suppress("unused")
  fun createGlobalFunctionLookupElement(function: LamaFunctionDefinition): LookupElement {
    return createFunctionLookupElement(function, GLOBAL_GROUPING)
  }

  fun createLocalFunctionLookupElement(function: LamaFunctionDefinition): LookupElement {
    return createFunctionLookupElement(function, LOCAL_GROUPING)
  }

  @Suppress("unused")
  fun createGlobalInfixLookupElement(infix: LamaInfixOperatorDefinition): LookupElement {
    return createInfixLookupElement(infix, GLOBAL_GROUPING)
  }

  fun createLocalInfixLookupElement(infix: LamaInfixOperatorDefinition): LookupElement {
    return createInfixLookupElement(infix, LOCAL_GROUPING)
  }

  fun createUnitLookupElement(unit: String): LookupElement {
    val icon = AllIcons.Nodes.Module
    return createLookupElementWithGrouping(LamaLookupElement(unit, false, icon), { _, _ -> }, GLOBAL_GROUPING)
  }

  fun createLocalInfixLookupElement(name: String): LookupElement {
    return createInfixLookupElement(name, null, null, LOCAL_GROUPING)
  }

  fun createVariableLookupElement(variable: LamaVariableDefinition, grouping: Int): LookupElement {
    return createVariableLookupElement(variable.name, variable.containingScopePresentation, grouping)
  }

  private fun createVariableLookupElement(name: String, postline: String?, grouping: Int): LookupElement {
    val icon = AllIcons.Nodes.Variable
    return createLookupElementWithGrouping(LamaLookupElement(name, false, icon, postline), { _, _ -> }, grouping)
  }

  fun createFunctionLookupElement(function: LamaFunctionDefinition, grouping: Int): LookupElement {
    val parameterCnt = function.parameterList?.patternList?.size ?: 0
    val shiftedGrouping =
      if (isDotBefore) {
        if (parameterCnt == 1) grouping + 10
        else grouping
      }
      else grouping
    val icon = AllIcons.Nodes.Function
    return createLookupElementWithGrouping(
      LamaLookupElement(function.name, false, icon, function.containingScopePresentation, function.parameters),
      getInsertHandlerForFunctionCall(parameterCnt), shiftedGrouping
    )
  }

  fun createInfixLookupElement(infix: LamaInfixOperatorDefinition, grouping: Int): LookupElement {
    return createInfixLookupElement(infix.name, infix.containingScopePresentation, infix.parameters, grouping)
  }

  private fun createInfixLookupElement(
    name: String,
    postline: String?,
    parameters: String?,
    grouping: Int,
  ): LookupElement {
    val icon = AllIcons.Nodes.AnonymousClass
    return createLookupElementWithGrouping(
      LamaLookupElement(name, false, icon, postline, parameters),
      { _, _ -> },
      grouping
    )
  }

  private fun getInsertHandlerForFunctionCall(parametersCnt: Int): InsertHandler<LookupElement> {
    return InsertHandler { context, _ ->
      if (isDotBefore && parametersCnt <= 1) return@InsertHandler
      val document = context.document
      val parenthesesRelativeOffset = findParentheses(document.text, context.tailOffset)
      if (parenthesesRelativeOffset == null) {
        document.insertString(context.tailOffset, "()")
      }
      val relativeCaretOffset = (if (parametersCnt == 0) 2 else 1) + (parenthesesRelativeOffset ?: 0)
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

  private val LamaPsiElement.containingScopePresentation: String?
    get() {
      val parent = PsiTreeUtil.getParentOfType(
        this,
        LamaFunctionDefinition::class.java, LamaInfixOperatorDefinition::class.java
      ) as LamaDefinition?
      return if (parent == null) containingFile?.name
      else parent.name
    }

  companion object : LamaLookupElementFactory(false)
}