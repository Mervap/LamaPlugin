package org.jetbrains.lama.hints

import com.intellij.lang.parameterInfo.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import org.jetbrains.lama.hints.LamaParameterInfoHandler.Companion.LamaParameterInfo
import org.jetbrains.lama.messages.LamaBundle
import org.jetbrains.lama.psi.api.LamaArgumentList
import org.jetbrains.lama.psi.api.LamaFunctionDefinition
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition
import org.jetbrains.lama.psi.api.LamaSOrCallExpression

class LamaParameterInfoHandler : ParameterInfoHandler<LamaArgumentList, LamaParameterInfo> {

  override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): LamaArgumentList? {
    return findCall(context, context.parameterListStart)?.argumentList
  }

  override fun findElementForParameterInfo(context: CreateParameterInfoContext): LamaArgumentList? {
    val call = findCall(context) ?: return null
    val resolve = call.expression.reference?.multiResolve(false)?.mapNotNull { it.element } ?: emptyList()
    context.itemsToShow = resolve.mapNotNull { resolveResult ->
      val parameterList = when (val definition = resolveResult.parent) {
        is LamaFunctionDefinition -> definition.parameterList
        is LamaInfixOperatorDefinition -> definition.parameterList
        else -> null
      }
      parameterList?.patternList?.map { it.text }
    }.map { LamaParameterInfo(it, -1) }.toTypedArray()

    return call.argumentList
  }

  override fun showParameterInfo(element: LamaArgumentList, context: CreateParameterInfoContext) {
    context.showHint(element, element.textOffset, this)
  }

  override fun updateParameterInfo(parameterOwner: LamaArgumentList, context: UpdateParameterInfoContext) {
    val caretOffset = context.offset
    if (!parameterOwner.textRange.contains(caretOffset)) {
      context.removeHint()
      return
    }

    context.objectsToView.map { it as LamaParameterInfo }.forEach { parameterInfo ->
      val arguments = parameterOwner.expressionSeriesList
      val ind = arguments.indexOfLast { it.endOffset < caretOffset }
      val firstArgumentOffset = arguments.firstOrNull()?.startOffset ?: Int.MAX_VALUE
      parameterInfo.currentIndex =
        when {
          ind != -1 -> ind + 1
          firstArgumentOffset <= caretOffset -> 0
          else -> arguments.size
        }
    }
  }

  override fun updateUI(argumentList: LamaParameterInfo, context: ParameterInfoUIContext) {
    var highlightOffsetStart = 0
    var highlightOffsetEnd = 0
    val params = argumentList.params
    var isDot = false
    val (text, isDisabled) =
      if (params.isEmpty()) LamaBundle.message("parameter.info.no.arguments") to false
      else buildString {
        params.forEachIndexed { ind, param ->
          isDot = argumentList.currentIndex >= params.size && param == "_"
          if (ind != 0) append(", ")
          if (ind == argumentList.currentIndex || isDot) {
            highlightOffsetStart = length
          }
          append(if (param != "_") param else "...")
          if (ind == argumentList.currentIndex || isDot) {
            highlightOffsetEnd = length
          }
        }
      } to (!isDot && argumentList.currentIndex >= params.size)

    context.setupUIComponentPresentation(
      text,
      highlightOffsetStart, highlightOffsetEnd, isDisabled,
      false, false, context.defaultParameterColor
    )
  }

  private fun findCall(context: ParameterInfoContext, parameterListStart: Int = -1): LamaSOrCallExpression? {
    var argumentList =
      ParameterInfoUtils.findParentOfType(context.file, context.offset - 1, LamaArgumentList::class.java)
    if (argumentList == null || parameterListStart < 0) {
      return argumentList?.parent as LamaSOrCallExpression
    }

    while (parameterListStart != argumentList!!.textRange.startOffset) {
      argumentList = PsiTreeUtil.getParentOfType(argumentList, LamaArgumentList::class.java) ?: return null
      if (parameterListStart > argumentList.textRange.startOffset) return null
    }

    return argumentList.parent as LamaSOrCallExpression
  }

  companion object {
    data class LamaParameterInfo(
      val params: List<String>,
      var currentIndex: Int,
    )
  }
}
