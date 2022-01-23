package org.jetbrains.lama.psi.impl

import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import org.jetbrains.lama.psi.LamaLanguage
import org.jetbrains.lama.psi.api.LamaPsiElement

open class LamaBaseElementImpl<T : StubElement<*>> : StubBasedPsiElementBase<T>, LamaPsiElement {
  constructor(node: ASTNode) : super(node)
  constructor(stub: T, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

  override fun getLanguage(): Language = LamaLanguage
  override fun toString(): String = node.elementType.toString()
  override fun accept(visitor: PsiElementVisitor) {
    visitor.visitElement(this)
  }
}