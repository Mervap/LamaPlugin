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
import org.jetbrains.lama.psi.api.*
import org.jetbrains.lama.psi.controlFlow.IdentifierSymbolInfo
import org.jetbrains.lama.psi.controlFlow.OperatorSymbolInfo
import org.jetbrains.lama.psi.references.LamaSearchScope
import org.jetbrains.lama.psi.stubs.indices.LamaFunctionDefinitionNameIndex
import org.jetbrains.lama.psi.stubs.indices.LamaInfixOperatorDefinitionNameIndex
import org.jetbrains.lama.psi.stubs.indices.LamaVariableNameIndex

class IdentifierCompletionProvider : CompletionProvider<CompletionParameters>() {

  override fun addCompletions(
    parameters: CompletionParameters,
    context: ProcessingContext,
    probableResult: CompletionResultSet,
  ) {
    val startPosition = parameters.position
    val probableIdentifier = PsiTreeUtil.getParentOfType(startPosition, LamaIdentifierExpression::class.java, false)
    val prev by lazy {
      probableIdentifier?.prevLeaf(true)?.let {
        PsiTreeUtil.getParentOfType(it, LamaOperator::class.java, false)
      }
    }
    val (position, result, isOperator) = if (
      probableIdentifier != null &&
      probableIdentifier.startOffset == parameters.offset &&
      prev != null
    ) {
      Triple(prev!!, probableResult.withPrefixMatcher(prev!!.name), true)
    }
    else {
      val id = probableIdentifier ?:
          PsiTreeUtil.getParentOfType(startPosition, LamaPsiElement::class.java, false) ?:
          return
      Triple(id, probableResult, false)
    }

    // don't complete parameters name
    val parent = position.parent
    if (parent is LamaSOrAtPattern && parent.parent is LamaParameterList) {
      return
    }

    val shownNames = HashSet<String>()
    val project = position.project
    val originalFile = parameters.originalFile

    addCompletionFromLocals(position, shownNames, result)
    addCompletionFromIndices(project, LamaSearchScope.getScope(originalFile), shownNames, result, isOperator)
  }
}

private fun addCompletionFromLocals(
  position: LamaPsiElement,
  shownNames: HashSet<String>,
  result: CompletionResultSet,
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
          LamaLookupElementFactory.createLocalInfixLookupElement(info.name)
        }
        else {
          LamaLookupElementFactory.createLocalInfixLookupElement(definition)
        }
      }
      is IdentifierSymbolInfo -> {
        when (val definition = info.definition.parent) {
          is LamaVariableDefinition -> {
            LamaLookupElementFactory.createLocalVariableLookupElement(definition)
          }
          is LamaFunctionDefinition -> {
            LamaLookupElementFactory.createLocalFunctionLookupElement(definition)
          }
          else -> {
            LamaLookupElementFactory.createLocalVariableLookupElement(info.name)
          }
        }
      }
    }
    result.consume(element)
  }
}

private fun addCompletionFromIndices(
  project: Project,
  scope: GlobalSearchScope,
  shownNames: HashSet<String>,
  result: CompletionResultSet,
  isOperator: Boolean,
) {
  processElementsFromIndex(project, scope, isOperator) { it, _ ->
    if (shownNames.add(it.lookupString)) result.consume(it)
  }
}

private fun processElementsFromIndex(
  project: Project,
  scope: GlobalSearchScope,
  isOperator: Boolean,
  consumer: (LookupElement, VirtualFile) -> Unit,
) {
  LamaVariableNameIndex.process(project, scope, Processor { definition ->
    consumer(
      LamaLookupElementFactory.createGlobalVariableLookupElement(definition),
      definition.containingFile.virtualFile
    )
    return@Processor true
  })
  LamaFunctionDefinitionNameIndex.process(project, scope, Processor { definition ->
    consumer(
      LamaLookupElementFactory.createGlobalFunctionLookupElement(definition),
      definition.containingFile.virtualFile
    )
    return@Processor true
  })

  if (isOperator) {
    LamaInfixOperatorDefinitionNameIndex.process(project, scope, Processor { definition ->
      consumer(
        LamaLookupElementFactory.createGlobalInfixLookupElement(definition),
        definition.containingFile.virtualFile
      )
      return@Processor true
    })
  }
}