package org.jetbrains.lama.editor.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.prevLeaf
import com.intellij.refactoring.suggested.startOffset
import com.intellij.util.ProcessingContext
import com.intellij.util.Processor
import org.jetbrains.lama.psi.api.*
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
    addCompletionFromIndices(project, LamaSearchScope.getScope(originalFile), shownNames, result, isOperator)
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