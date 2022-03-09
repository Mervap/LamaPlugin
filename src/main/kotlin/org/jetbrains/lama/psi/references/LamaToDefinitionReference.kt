package org.jetbrains.lama.psi.references

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.ResolveResult
import com.intellij.psi.util.elementType
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.psi.LamaPsiUtil.controlFlowContainer
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionIdentifier
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionOperator
import org.jetbrains.lama.psi.api.*
import org.jetbrains.lama.psi.stubs.indices.LamaIdentifierNameIndex
import org.jetbrains.lama.psi.stubs.indices.LamaNameIndex
import org.jetbrains.lama.psi.stubs.indices.LamaOperatorNameIndex

class LamaIdentifierReference(element: LamaIdentifierExpression) :
  LamaToDefinitionReference<LamaIdentifierExpression, LamaDefinition>(element, LamaIdentifierNameIndex) {

  override fun resolveImpl(incompleteCode: Boolean): Array<ResolveResult> {
    if (element.firstChild.elementType == LamaElementTypes.LAMA_UINDENT) return ResolveResult.EMPTY_ARRAY
    if (element.isDefinitionIdentifier()) return ResolveResult.EMPTY_ARRAY
    return super.resolveImpl(incompleteCode)
  }

  override fun handleElementRename(newElementName: String): PsiElement {
    return element.setName(newElementName)
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

  override fun handleElementRename(newElementName: String): PsiElement {
    return element.setName(newElementName)
  }
}

abstract class LamaToDefinitionReference<RefElem : LamaPsiElement, Def: LamaDefinition>(
  element: RefElem,
  private val nameIndex: LamaNameIndex<Def>,
) : LamaReferenceBase<RefElem>(element) {

  override fun resolveImpl(incompleteCode: Boolean): Array<ResolveResult> {
    val name = element.name ?: return emptyArray()
    val localAnalysis = element.controlFlowContainer?.getLocalSymbolInfo(element)
    if (localAnalysis != null) {
      localAnalysis.get<RefElem>(name)?.let {
        return arrayOf(PsiElementResolveResult(it.definition.parent))
      }
    }

    val project = element.project
    val fromImports = nameIndex.find(name, project, LamaSearchScope.importsScope(element))
    if (fromImports.isNotEmpty()) {
      return fromImports.toResolveResults()
    }

    return nameIndex.find(name, project, LamaSearchScope.importsScope(element)).toResolveResults()
  }

  private fun Collection<Def>.toResolveResults(): Array<ResolveResult> = mapNotNull { PsiElementResolveResult(it) }.toTypedArray()
}