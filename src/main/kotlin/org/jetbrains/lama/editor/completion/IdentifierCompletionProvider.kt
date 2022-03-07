package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.prevLeaf
import com.intellij.refactoring.suggested.startOffset
import com.intellij.util.ProcessingContext
import com.intellij.util.Processor
import org.jetbrains.lama.psi.LamaPsiUtil.controlFlowContainer
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionIdentifier
import org.jetbrains.lama.psi.api.*
import org.jetbrains.lama.psi.controlFlow.IdentifierSymbolInfo
import org.jetbrains.lama.psi.controlFlow.OperatorSymbolInfo
import org.jetbrains.lama.psi.references.LamaSearchScope
import org.jetbrains.lama.psi.stubs.indices.LamaIdentifierCompletionIndex
import org.jetbrains.lama.psi.stubs.indices.LamaOperatorCompletionIndex

class IdentifierCompletionProvider : CompletionProvider<CompletionParameters>() {

  override fun addCompletions(
    parameters: CompletionParameters,
    context: ProcessingContext,
    probableResult: CompletionResultSet,
  ) {
    val startPosition = parameters.position
    val probableIdentifier = PsiTreeUtil.getParentOfType(startPosition, LamaIdentifierExpression::class.java, false)
    val prevOperator by lazy {
      probableIdentifier?.prevLeaf(true)?.let {
        PsiTreeUtil.getParentOfType(it, LamaOperator::class.java, false)
      }
    }
    val (position, result, isOperator) = if (
      probableIdentifier != null &&
      probableIdentifier.startOffset == parameters.offset &&
      prevOperator !is LamaDotOperator
    ) {
      Triple(prevOperator!!, probableResult.withPrefixMatcher(prevOperator!!.name), true)
    }
    else {
      val id =
        probableIdentifier ?: PsiTreeUtil.getParentOfType(startPosition, LamaPsiElement::class.java, false) ?: return
      Triple(id, probableResult, false)
    }

    // don't complete variable, parameters and functions name
    if (position is LamaIdentifierExpression && position.isDefinitionIdentifier()) {
      return
    }

    val shownNames = HashSet<String>()
    val project = position.project
    val factory = LamaLookupElementFactory(prevOperator is LamaDotOperator)

    addCompletionFromLocals(position, shownNames, result, factory)
    addCompletionFromIndices(project, position, shownNames, result, isOperator, factory)
  }
}

private fun addCompletionFromLocals(
  position: LamaPsiElement,
  shownNames: HashSet<String>,
  result: CompletionResultSet,
  factory: LamaLookupElementFactory,
) {
  position.controlFlowContainer?.getLocalSymbolInfo(position)?.symbolInfos?.sortedBy { it.name }?.forEach { info ->
    val name = info.name
    if (!shownNames.add(name)) {
      return@forEach
    }
    val element = when (info) {
      is OperatorSymbolInfo -> {
        val definition = info.definition.parent as? LamaInfixOperatorDefinition
        if (definition == null) {
          factory.createLocalInfixLookupElement(info.name)
        }
        else {
          factory.createLocalInfixLookupElement(definition)
        }
      }
      is IdentifierSymbolInfo -> {
        when (val definition = info.definition.parent) {
          is LamaVariableDefinition -> {
            factory.createLocalVariableLookupElement(definition)
          }
          is LamaFunctionDefinition -> {
            factory.createLocalFunctionLookupElement(definition)
          }
          else -> {
            factory.createLocalVariableLookupElement(info.name)
          }
        }
      }
    }
    result.consume(element)
  }
}

private fun addCompletionFromIndices(
  project: Project,
  element: LamaPsiElement,
  shownNames: HashSet<String>,
  result: CompletionResultSet,
  isOperator: Boolean,
  factory: LamaLookupElementFactory,
) {
  processElementsFromIndex(project,
    LamaSearchScope.importsScope(element),
    isOperator,
    IMPORTS_GROUPING,
    factory) { it, _ ->
    if (shownNames.add(it.lookupString)) result.consume(it)
  }
  processElementsFromIndex(project, LamaSearchScope.allScope(element), isOperator, GLOBAL_GROUPING, factory) { it, _ ->
    if (shownNames.add(it.lookupString)) result.consume(it)
  }
}

private fun processElementsFromIndex(
  project: Project,
  scope: GlobalSearchScope,
  isOperator: Boolean,
  grouping: Int,
  factory: LamaLookupElementFactory,
  consumer: (LookupElement, VirtualFile) -> Unit,
) {
  LamaIdentifierCompletionIndex.process(project, scope, Processor { definition ->
    if (!definition.isPublic) return@Processor true
    val element = when (definition) {
      is LamaVariableDefinition -> factory.createVariableLookupElement(definition, grouping)
      is LamaFunctionDefinition -> factory.createFunctionLookupElement(definition, grouping)
      else -> error("Unknown definition type")
    }
    consumer(element, definition.containingFile.virtualFile)
    return@Processor true
  })

  if (!isOperator) return
  LamaOperatorCompletionIndex.process(project, scope, Processor { definition ->
    if (!definition.isPublic) return@Processor true
    consumer(
      factory.createInfixLookupElement(definition, grouping),
      definition.containingFile.virtualFile
    )
    return@Processor true
  })
}