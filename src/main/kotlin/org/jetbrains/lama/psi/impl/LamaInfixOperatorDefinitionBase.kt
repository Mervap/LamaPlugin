package org.jetbrains.lama.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.stubs.IStubElementType
import org.jetbrains.lama.psi.references.LamaReferenceBase
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStub
import org.jetbrains.lama.psi.stubs.LamaInfixOperatorDefinitionStub
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub

abstract class LamaInfixOperatorDefinitionBase : LamaBaseElementImpl<LamaInfixOperatorDefinitionStub>, PsiNamedElement {
  internal constructor(node: ASTNode) : super(node)
  internal constructor(stub: LamaInfixOperatorDefinitionStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
}