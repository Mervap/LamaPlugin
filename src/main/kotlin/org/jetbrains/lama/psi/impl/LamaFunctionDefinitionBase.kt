package org.jetbrains.lama.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.stubs.IStubElementType
import org.jetbrains.lama.psi.references.LamaReferenceBase
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStub
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub

abstract class LamaFunctionDefinitionBase : LamaBaseElementImpl<LamaFunctionDefinitionStub>, PsiNameIdentifierOwner {
  internal constructor(node: ASTNode) : super(node)
  internal constructor(stub: LamaFunctionDefinitionStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
}