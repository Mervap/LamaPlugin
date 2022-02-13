package org.jetbrains.lama.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.stubs.IStubElementType
import org.jetbrains.lama.psi.stubs.LamaInfixOperatorDefinitionStub

abstract class LamaInfixOperatorDefinitionBase : LamaControlFlowHolderImpl<LamaInfixOperatorDefinitionStub>, PsiNamedElement {
  internal constructor(node: ASTNode) : super(node)
  internal constructor(stub: LamaInfixOperatorDefinitionStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
}