package org.jetbrains.lama.psi.references

import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.ResolveResult
import org.jetbrains.lama.psi.LamaPsiUtil.controlFlowContainer
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionOperator
import org.jetbrains.lama.psi.api.*
import org.jetbrains.lama.psi.stubs.indices.LamaIdentifierNameIndex
import org.jetbrains.lama.psi.stubs.indices.LamaNameIndex
import org.jetbrains.lama.psi.stubs.indices.LamaOperatorNameIndex

class LamaIdentifierReference(element: LamaIdentifierExpression) :
  LamaToDefinitionReference<LamaIdentifierExpression, LamaIdentifierOwnerDefinition>(
    element,
    LamaIdentifierNameIndex
  ) {

  override fun definitionIdentifierElement(definition: LamaIdentifierOwnerDefinition): LamaIdentifierExpression? {
    return definition.identifyingElement as LamaIdentifierExpression?
  }
}

class LamaOperatorReference(element: LamaOperator) :
  LamaToDefinitionReference<LamaOperator, LamaInfixOperatorDefinition>(
    element,
    LamaOperatorNameIndex
  ) {

  override fun resolveImpl(incompleteCode: Boolean): Array<ResolveResult> {
    if (element.isDefinitionOperator()) return emptyArray()
    return super.resolveImpl(incompleteCode)
  }

  override fun definitionIdentifierElement(definition: LamaInfixOperatorDefinition): LamaOperator {
    return definition.nameOperator
  }
}

abstract class LamaToDefinitionReference<RefElem : LamaPsiElement, Def: LamaDefinition>(
  element: RefElem,
  private val nameIndex: LamaNameIndex<Def>,
) : LamaReferenceBase<RefElem>(element) {

  protected abstract fun definitionIdentifierElement(definition: Def): RefElem?

  override fun resolveImpl(incompleteCode: Boolean): Array<ResolveResult> {
    val name = element.name ?: return emptyArray()
    val localAnalysis = element.controlFlowContainer?.getLocalSymbolInfo(element)
    if (localAnalysis != null) {
      localAnalysis.get<RefElem>(name)?.let {
        return arrayOf(PsiElementResolveResult(it.definition))
      }
    }

    val project = element.project
    val fromImports = nameIndex.find(name, project, LamaSearchScope.importsScope(element))
    if (fromImports.isNotEmpty()) {
      return fromImports.toResolveResults()
    }

    return nameIndex.find(name, project, LamaSearchScope.allScope(element)).toResolveResults()
  }

  private fun Collection<Def>.toResolveResults(): Array<ResolveResult> = mapNotNull { def ->
    definitionIdentifierElement(def)?.let { PsiElementResolveResult(it) }
  }.toTypedArray()
}