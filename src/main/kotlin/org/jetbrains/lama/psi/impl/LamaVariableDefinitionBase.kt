package org.jetbrains.lama.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.stubs.IStubElementType
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub

abstract class LamaVariableDefinitionBase : LamaBaseElementImpl<LamaVariableDefinitionStub>, PsiNameIdentifierOwner {
  internal constructor(node: ASTNode) : super(node)
  internal constructor(stub: LamaVariableDefinitionStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
}