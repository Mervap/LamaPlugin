package org.jetbrains.lama.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.stubs.IStubElementType
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStub

abstract class LamaFunctionDefinitionBase : LamaControlFlowHolderImpl<LamaFunctionDefinitionStub>, PsiNameIdentifierOwner {
  constructor(node: ASTNode) : super(node)
  constructor(stub: LamaFunctionDefinitionStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
}